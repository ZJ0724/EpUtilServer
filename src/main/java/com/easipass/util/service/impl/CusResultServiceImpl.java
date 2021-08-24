package com.easipass.util.service.impl;

import com.easipass.util.entity.CusResult;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.CusResultService;
import com.easipass.util.util.ChromeDriverUtil;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.easipass.util.util.SftpUtil;
import com.zj0724.common.component.Ftp;
import com.zj0724.common.component.Jdbc;
import com.zj0724.common.component.ftp.Sftp;
import com.zj0724.common.component.jdbc.OracleJdbc;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.DateUtil;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.WebDriver;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public final class CusResultServiceImpl implements CusResultService {

    @Resource
    private ConfigService configService;

    @Override
    public void uploadCustomsDeclaration(String customsDeclarationNumber, CusResult tongXunCusResult, CusResult yeWuCusResult) {
        // 参数校验
        if (customsDeclarationNumber == null) {
            throw new InfoException("报关单号不能为空");
        }

        // 检查报关单数据是否存在
        if (SWGDDatabaseUtil.getFormHead(customsDeclarationNumber) == null) {
            throw new InfoException("报关单信息不存在");
        }

        // 回执上传地址
        ConfigPO uploadPathConfigPO = configService.getByCode(ConfigPO.Groups.UPLOAD_CUS_RESULT.FTP_UPLOAD_PATH);
        if (uploadPathConfigPO == null || StringUtil.isEmpty(uploadPathConfigPO.getData())) {
            throw new InfoException("上传回执路径未配置");
        }
        String uploadPath = uploadPathConfigPO.getData();

        // sftp
        Sftp uploadCusResultSftp = SftpUtil.getUploadCusResultSftp();
        // 谷歌驱动
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();

        try {
            // 上传通讯回执
            if (tongXunCusResult != null) {
                uploadTongXunCustomsDeclaration(customsDeclarationNumber, tongXunCusResult, uploadCusResultSftp, webDriver, uploadPath);
            }

            // 上传业务回执
            if (yeWuCusResult != null) {
                uploadYeWuCustomsDeclaration(customsDeclarationNumber, yeWuCusResult, uploadCusResultSftp, webDriver, uploadPath);
            }
        } finally {
            uploadCusResultSftp.close();
            webDriver.close();
        }
    }

    @Override
    public void uploadAgentResult(String customsDeclarationNumber, CusResult cusResult) {
        // 校验
        if (StringUtil.isEmpty(customsDeclarationNumber)) {
            throw new InfoException("报关单号不能为空");
        }
        if (cusResult == null) {
            throw new InfoException("回执信息为空");
        }
        String code = cusResult.getCode();
        String note = cusResult.getNote();
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("code不能为空");
        }
        if (StringUtil.isEmpty(note)) {
            throw new InfoException("note不能为空");
        }

        // 获取代理委托上传路径
        ConfigPO configPO = configService.getByCode(ConfigPO.Groups.UPLOAD_AGENT_RESULT.FTP_UPLOAD_PATH);
        String agentUploadPath = null;
        if (configPO != null) {
            agentUploadPath = configPO.getData();
        }
        if (StringUtil.isEmpty(agentUploadPath)) {
            throw new InfoException("上传代理委托回执路径为空");
        }

        // 查询agent信息
        Map<String, Object> agentData = SWGDDatabaseUtil.queryOne("SELECT * FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO = '" + customsDeclarationNumber + "'");
        if (agentData == null) {
            throw new InfoException("未查询到数据：" + customsDeclarationNumber);
        }

        // 校验userName不能为空
        if (StringUtil.isEmpty(agentData.get("USER_NAME"))) {
            throw new InfoException("USER_NAME为空");
        }

        // 校验fileName不能为空
        String fileName = MapUtil.getValue(agentData, "FILE_NAME", String.class);
        if (StringUtil.isEmpty(fileName)) {
            throw new InfoException("FILE_NAME为空");
        }

        // 回执信息
        String resultData;
        if ("999".equals(code)) {
            resultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<ImportAgrResponse>\n" +
                    "\t<ResponseInfo>\n" +
                    "\t\t<ResponseMessage>" + note + "</ResponseMessage>\n" +
                    "\t</ResponseInfo>\n" +
                    "</ImportAgrResponse>";
        } else {
            // 获取代理委托编号
            String ConsignNo = "000" + customsDeclarationNumber;
            resultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<ImportAgrResponse>\n" +
                    "\t<ResponseInfo>\n" +
                    "\t\t<ResponseCode>" + code + "</ResponseCode>\n" +
                    "\t\t<ResponseMessage>" + note + "</ResponseMessage>\n" +
                    "\t</ResponseInfo>\n" +
                    "\t<ConsignNo>" + ConsignNo + "</ConsignNo>\n" +
                    "</ImportAgrResponse>";
        }

        // 上传
        Sftp uploadCusResultSftp = SftpUtil.getUploadCusResultSftp();
        try {
            uploadCusResultSftp.upload(agentUploadPath, fileName, resultData);
        } finally {
            uploadCusResultSftp.close();
        }

        // run
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();
        try {
            ChromeDriverUtil.agentRun(webDriver);
        } finally {
            webDriver.close();
        }
    }

    @Override
    public void uploadTransResult(String copSeqNo, CusResult tongXun, CusResult yeWu) {
        if (StringUtil.isEmpty(copSeqNo)) {
            throw new InfoException("企业内部编号不能为空");
        }

        // 获取ftp上传路径
        ConfigPO FTP_UPLOAD_PATH = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_UPLOAD_PATH);
        if (FTP_UPLOAD_PATH == null || StringUtil.isEmpty(FTP_UPLOAD_PATH.getData())) {
            throw new InfoException("ftp上传路径不能为空");
        }

        Jdbc jdbc = null;
        Ftp ftp = null;
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();
        try {
            // 连接ftp
            ConfigPO FTP_TYPE = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_TYPE);
            ConfigPO FTP_HOST = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_HOST);
            ConfigPO FTP_PORT = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_PORT);
            ConfigPO FTP_USERNAME = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_USERNAME);
            ConfigPO FTP_PASSWORD = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.FTP_PASSWORD);
            if (StringUtil.isEmpty(FTP_TYPE)) {
                throw new InfoException("ftp类型不能为空");
            }
            if ("FTP".equals(FTP_TYPE.getData())) {
                ftp = new com.zj0724.common.component.ftp.Ftp(FTP_HOST.getData(), Integer.parseInt(FTP_PORT.getData()), FTP_USERNAME.getData(), FTP_PASSWORD.getData());
            } else if ("SFTP".equals(FTP_TYPE.getData())) {
                ftp = new Sftp(FTP_HOST.getData(), Integer.parseInt(FTP_PORT.getData()), FTP_USERNAME.getData(), FTP_PASSWORD.getData());
            } else {
                throw new InfoException("ftp类型错误");
            }

            // 连接数据库
            ConfigPO DATABASE_HOST = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.DATABASE_HOST);
            ConfigPO DATABASE_PORT = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.DATABASE_PORT);
            ConfigPO DATABASE_SID = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.DATABASE_SID);
            ConfigPO DATABASE_USERNAME = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.DATABASE_USERNAME);
            ConfigPO DATABASE_PASSWORD = configService.getByCode(ConfigPO.Groups.UPLOAD_TRANS_RESULT.DATABASE_PASSWORD);
            jdbc = new OracleJdbc(DATABASE_HOST.getData(), Integer.parseInt(DATABASE_PORT.getData()), DATABASE_SID.getData(), DATABASE_USERNAME.getData(), DATABASE_PASSWORD.getData());

            // 上传通讯回执
            if (tongXun != null) {
                // 查询TRANS_PRE_ID
                List<Map<String, Object>> maps = jdbc.queryBySql("SELECT TRANS_PRE_ID FROM SWGDIMAP.TRANS_PRE_HEAD WHERE COP_SEQ_NO = '" + copSeqNo + "'");
                if (maps.size() == 0) {
                    throw new InfoException(copSeqNo + "数据未找到");
                }
                if (maps.size() > 1) {
                    throw new InfoException(copSeqNo + "存在多条数据");
                }
                String TRANS_PRE_ID = MapUtil.getValue(maps.get(0), "TRANS_PRE_ID", String.class);
                if (StringUtil.isEmpty(TRANS_PRE_ID)) {
                    TRANS_PRE_ID = copSeqNo + "_SEQ_NO";
                    TRANS_PRE_ID = TRANS_PRE_ID.substring(0, 20);
                }
                String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<TrnImportResponse xmlns=\"http://www.chinaport.gov.cn/trn\">\n" +
                        "\t<EportResult>\n" +
                        "\t\t<SeqNo>" + TRANS_PRE_ID + "</SeqNo>\n" +
                        "\t\t<CopSeqNo>" + copSeqNo + "</CopSeqNo>\n" +
                        "\t\t<ResponseCode>" + tongXun.getCode() + "</ResponseCode>\n" +
                        "\t\t<ResponseMessage>" + tongXun.getNote() + "</ResponseMessage>\n" +
                        "\t</EportResult>\n" +
                        "</TrnImportResponse>";
                ftp.upload(FTP_UPLOAD_PATH.getData(), "trans-tongXun-" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".xml", data);
                ChromeDriverUtil.transRun(webDriver);
            }

            // 上传业务回执
            if (yeWu != null) {
                // 查询TRANS_PRE_ID, PRE_NO
                List<Map<String, Object>> maps = jdbc.queryBySql("SELECT TRANS_PRE_ID, PRE_NO FROM SWGDIMAP.TRANS_PRE_HEAD WHERE COP_SEQ_NO = '" + copSeqNo + "'");
                if (maps.size() == 0) {
                    throw new InfoException(copSeqNo + "数据未找到");
                }
                if (maps.size() > 1) {
                    throw new InfoException(copSeqNo + "存在多条数据");
                }
                String TRANS_PRE_ID = MapUtil.getValue(maps.get(0), "TRANS_PRE_ID", String.class);
                if (StringUtil.isEmpty(TRANS_PRE_ID)) {
                    TRANS_PRE_ID = copSeqNo + "_SEQ_NO";
                    TRANS_PRE_ID = TRANS_PRE_ID.substring(0, 20);
                }
                String PRE_NO = MapUtil.getValue(maps.get(0), "PRE_NO", String.class);
                if (StringUtil.isEmpty(PRE_NO)) {
                    PRE_NO = copSeqNo + "_TRANS_NO";
                    PRE_NO = PRE_NO.substring(0, 20);
                }
                String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<TrnImportResponse xmlns=\"http://www.chinaport.gov.cn/trn\">\n" +
                        "\t<EportResult>\n" +
                        "\t\t<SeqNo>" + TRANS_PRE_ID + "</SeqNo>\n" +
                        "\t</EportResult>\n" +
                        "\t<CusResult>\n" +
                        "\t\t<TransNo>" + PRE_NO + "</TransNo>\n" +
                        "\t\t<NoticeDate>" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + "</NoticeDate>\n" +
                        "\t\t<Channel>" + yeWu.getCode() + "</Channel>\n" +
                        "\t\t<Note>" + yeWu.getNote() + "</Note>\n" +
                        "\t</CusResult>\n" +
                        "</TrnImportResponse>";
                ftp.upload(FTP_UPLOAD_PATH.getData(), "trans-yeWu-" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".xml", data);
                ChromeDriverUtil.transRun(webDriver);
            }
        } finally {
            webDriver.close();
            if (ftp !=null) {
                ftp.close();
            }
            if (jdbc != null) {
                jdbc.close();
            }
        }
    }

    /**
     * 上传报关单通讯回执
     *
     * @param customsDeclarationNumber 报关单号
     * @param cusResult cusResult
     * @param sftp sftp
     * @param webDriver webDriver
     * @param uploadPath 回执上传路径
     * */
    private static void uploadTongXunCustomsDeclaration(String customsDeclarationNumber, CusResult cusResult, Sftp sftp, WebDriver webDriver, String uploadPath) {
        // 获取报关单数据
        Map<String, Object> formHead = SWGDDatabaseUtil.getFormHead(customsDeclarationNumber);
        if (formHead == null) {
            throw new InfoException("报关单数据不存在");
        }

        // 默认的通讯回执
        if (cusResult == null) {
            cusResult = new CusResult();
            cusResult.setCode("0");
            cusResult.setNote("通讯回执");
        }

        // seqNo
        String seqNo = formHead.get("SEQ_NO") == null ? null : formHead.get("SEQ_NO").toString();
        if (StringUtil.isEmpty(seqNo)) {
            seqNo = "seqNo00000000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
        }

        // 回执数据
        String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DecImportResponse xmlns=\"http://www.chinaport.gov.cn/dec\">\n" +
                "\t<ResponseCode>" + cusResult.getCode() + "</ResponseCode>\n" +
                "\t<ErrorMessage>" + cusResult.getNote() + "</ErrorMessage>\n" +
                "\t<ClientSeqNo>" + customsDeclarationNumber + "</ClientSeqNo>\n" +
                "\t<SeqNo>" + seqNo + "</SeqNo>\n" +
                "\t<TrnPreId></TrnPreId>\n" +
                "</DecImportResponse>";
        // 加密
        cusResultData = Base64Util.encode(cusResultData);

        cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DxpMsg xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns=\"http://www.chinaport.gov.cn/dxp\" ver=\"1.0\" Id=\"ID01\">\n" +
                "\t<TransInfo>\n" +
                "\t\t<CopMsgId></CopMsgId>\n" +
                "\t\t<SenderId></SenderId>\n" +
                "\t\t<ReceiverIds>\n" +
                "\t\t\t<ReceiverId></ReceiverId>\n" +
                "\t\t</ReceiverIds>\n" +
                "\t\t<CreatTime>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</CreatTime>\n" +
                "\t\t<MsgType>DECCUSSH1</MsgType>\n" +
                "\t</TransInfo>\n" +
                "\t<Data>" + cusResultData + "</Data>\n" +
                "\t<AddInfo>\n" +
                "\t\t<FileName></FileName>\n" +
                "\t\t<IcCard></IcCard>\n" +
                "\t\t<BizKey>\n" +
                "\t\t\t<Key name=\"RetType\"></Key>\n" +
                "\t\t\t<Key name=\"DealFlag\"></Key>\n" +
                "\t\t</BizKey>\n" +
                "\t</AddInfo>\n" +
                "</DxpMsg>";

        // 上传
        sftp.upload(uploadPath, "tongXunCusResult_" + customsDeclarationNumber, cusResultData);
        ChromeDriverUtil.swgdRecvRun(webDriver);
    }

    /**
     * 上传报关单业务回执
     *
     * @param customsDeclarationNumber 报关单号
     * @param cusResult cusResult
     * @param sftp sftp
     * @param webDriver webDriver
     * @param uploadPath 回执上传路径
     * */
    private static void uploadYeWuCustomsDeclaration(String customsDeclarationNumber, CusResult cusResult, Sftp sftp, WebDriver webDriver, String uploadPath) {
        // 获取报关单数据
        Map<String, Object> formHead = SWGDDatabaseUtil.getFormHead(customsDeclarationNumber);
        if (formHead == null) {
            throw new InfoException("报关单数据不存在");
        }

        // seqNo
        String seqNo = formHead.get("SEQ_NO") == null ? null : formHead.get("SEQ_NO").toString();
        if (StringUtil.isEmpty(seqNo)) {
            seqNo = "seqNo00000000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
        }

        // 回执数据
        String preEntryId = formHead.get("PRE_ENTRY_ID") == null ? "" : formHead.get("PRE_ENTRY_ID").toString();
        if (preEntryId.equals(formHead.get("EDI_NO").toString())) {
            String declPort = formHead.get("DECL_PORT") == null ? "" : formHead.get("DECL_PORT").toString();
            String ieFlag = formHead.get("IE_FLAG") == null ? "" : formHead.get("IE_FLAG").toString();
            preEntryId = declPort + "0000" + ieFlag + "0000" + customsDeclarationNumber.substring(customsDeclarationNumber.length() - 5);
        }

        String cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DEC_RESULT>\n" +
                "\t<CUS_CIQ_NO>" + seqNo + "</CUS_CIQ_NO>\n" +
                "\t<ENTRY_ID>" + preEntryId + "</ENTRY_ID>\n" +
                "\t<NOTICE_DATE>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</NOTICE_DATE>\n" +
                "\t<CHANNEL>" + cusResult.getCode() + "</CHANNEL>\n" +
                "\t<NOTE>" + cusResult.getNote() + "</NOTE>\n" +
                "\t<CUSTOM_MASTER></CUSTOM_MASTER>\n" +
                "\t<I_E_DATE></I_E_DATE>\n" +
                "\t<D_DATE>" + DateUtil.format(new Date(), "yyyy-MM-dd") + "</D_DATE>\n" +
                "</DEC_RESULT>";

        // 加密
        cusResultData = Base64Util.encode(cusResultData);

        cusResultData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<DxpMsg xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns=\"http://www.chinaport.gov.cn/dxp\" ver=\"1.0\" Id=\"ID01\">\n" +
                "\t<TransInfo>\n" +
                "\t\t<CopMsgId></CopMsgId>\n" +
                "\t\t<SenderId></SenderId>\n" +
                "\t\t<ReceiverIds>\n" +
                "\t\t\t<ReceiverId></ReceiverId>\n" +
                "\t\t</ReceiverIds>\n" +
                "\t\t<CreatTime>" + DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss") + "</CreatTime>\n" +
                "\t\t<MsgType></MsgType>\n" +
                "\t</TransInfo>\n" +
                "\t<Data>" + cusResultData + "</Data>\n" +
                "\t<AddInfo>\n" +
                "\t\t<FileName></FileName>\n" +
                "\t\t<IcCard></IcCard>\n" +
                "\t</AddInfo>\n" +
                "</DxpMsg>";

        // 上传
        sftp.upload(uploadPath, "yeWuCusResult_" + customsDeclarationNumber, cusResultData);
        ChromeDriverUtil.swgdRecvRun(webDriver);

        // N回执清空SEQ_NO, CUS_CIQ_NO
        if ("N".equals(cusResult.getCode())) {
            SWGDDatabaseUtil.execute("UPDATE SWGD.T_SWGD_FORM_HEAD SET SEQ_NO = NULL, CUS_CIQ_NO = NULL WHERE EDI_NO = '" + formHead.get("EDI_NO") + "'");
        }
    }

}