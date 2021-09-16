package com.easipass.util.controller;

import com.easipass.util.util.ChromeDriverUtil;
import com.easipass.util.util.SWGDDatabaseUtil;
import com.zj0724.common.Ftp;
import com.zj0724.common.uiAuto.Selector;
import com.zj0724.common.uiAuto.WebDriver;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("test")
    public Response addUser(@RequestParam(name = "reset") String reset, @RequestParam(name = "channel") String channel) {
        // reset
        SWGDDatabaseUtil.execute("UPDATE SWGDIMAP.TRANS_PRE_HEAD SET COP_SEQ_NO = '2021-07-20-01', STATUS = '" + reset + "' WHERE ID = '15'");

        // 上传报文
        Ftp ftp = new com.zj0724.common.ftp.Ftp("192.168.115.18", 21, "user", "P@ssw0rd");
        try {
            String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<TrnImportResponse xmlns=\"http://www.chinaport.gov.cn/trn\">\n" +
                    "\t<EportResult>\n" +
                    "\t\t<SeqNo>SEQ_2021-07-20-01</SeqNo>\n" +
                    "\t\t<CopSeqNo>2021-07-20-01</CopSeqNo>\n" +
                    "\t</EportResult>\n" +
                    "\t<CusResult>\n" +
                    "\t\t<TransNo>TransNo</TransNo>\n" +
                    "\t\t<NoticeDate>20210720111111</NoticeDate>\n" +
                    "\t\t<Channel>" + channel + "</Channel>\n" +
                    "\t\t<Note>note</Note>\n" +
                    "\t</CusResult>\n" +
                    "</TrnImportResponse>";
            ftp.upload("/Trn/test/InBox", "yw.xml", data);
        } finally {
            ftp.close();
        }

        // 点击run
        WebDriver webDriver = ChromeDriverUtil.getChromeDriver();
        try {
            webDriver.open("http://192.168.120.83:9909/console");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(3) > td > button")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(1) > table > tbody > tr > td > div > div:nth-child(2) > div > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > img")).click();
            webDriver.findElement(Selector.byCssSelector("#gwt-uid-26 > span")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > img")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(49) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button")).click();
        } finally {
            webDriver.close();
        }

        // 查询状态
        Map<String, Object> map = SWGDDatabaseUtil.queryOne("SELECT COP_SEQ_NO, TRANS_PRE_ID, PRE_NO, STATUS FROM SWGDIMAP.TRANS_PRE_HEAD WHERE ID = '15'");

        return Response.returnTrue(map == null ? null : map.get("STATUS"));
    }

}