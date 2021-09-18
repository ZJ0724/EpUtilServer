package com.easipass.util.service.impl;

import com.easipass.util.Main;
import com.easipass.util.component.Database;
import com.easipass.util.entity.FtpConnect;
import com.easipass.util.entity.FtpFile;
import com.easipass.util.entity.po.FtpConfigPO;
import com.easipass.util.service.FtpConfigService;
import com.easipass.util.service.SystemService;
import com.zj0724.common.entity.FileInfo;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public final class FtpConfigServiceImpl implements FtpConfigService {

    @Resource
    private SystemService systemService;

    @Override
    public List<FtpConfigPO> getAll() {
        Database<FtpConfigPO> database = Database.getDatabase(FtpConfigPO.class);
        QueryResult<FtpConfigPO> query = database.query();
        return query.getData();
    }

    @Override
    public void save(FtpConfigPO ftpConfigPO) {
        if (ftpConfigPO == null) {
            throw new InfoException("请求参数缺失");
        }
        if (StringUtil.isEmpty(ftpConfigPO.getType())) {
            throw new InfoException("类型不能为空");
        }
        if (StringUtil.isEmpty(ftpConfigPO.getName())) {
            throw new InfoException("名称不能为空");
        }
        if (StringUtil.isEmpty(ftpConfigPO.getHost())) {
            throw new InfoException("host不能为空");
        }
        if (StringUtil.isEmpty(ftpConfigPO.getUsername())) {
            throw new InfoException("账号不能为空");
        }
        if (StringUtil.isEmpty(ftpConfigPO.getPassword())) {
            throw new InfoException("密码不能为空");
        }

        // type要存在
        if (FtpConfigPO.Type.getInstance(ftpConfigPO.getType()) == null) {
            throw new InfoException("type不存在");
        }

        // 名称不能重复
        QueryResult<FtpConfigPO> query = query(new Query().addFilter("name", ftpConfigPO.getName()));
        List<FtpConfigPO> data = query.getData();
        for (FtpConfigPO ftpConfigPO1 : data) {
            if (!ftpConfigPO1.getId().equals(ftpConfigPO.getId())) {
                throw new InfoException("名称不能重复");
            }
        }

        Database<FtpConfigPO> database = Database.getDatabase(FtpConfigPO.class);
        database.save(ftpConfigPO);
    }

    @Override
    public QueryResult<FtpConfigPO> query(Query query) {
        Database<FtpConfigPO> database = Database.getDatabase(FtpConfigPO.class);
        return database.query(query);
    }

    @Override
    public void delete(Long id) {
        Database<FtpConfigPO> database = Database.getDatabase(FtpConfigPO.class);
        database.delete(id);
    }

    @Override
    public void connect(Long id) {
        if (id == null) {
            throw new InfoException("未选择ftp");
        }
        FtpConnect ftpConnect1 = Main.FTP_CONNECT.get(id);
        if (ftpConnect1 != null) {
            // 检查连接的完整性
            if (!ftpConnect1.getFtp().isConnected()) {
                Main.FTP_CONNECT.remove(id);
                throw new InfoException("连接已失效");
            }
            return;
        }
        FtpConfigPO byId = getById(id);
        FtpConnect ftpConnect = new FtpConnect(byId);
        Main.FTP_CONNECT.put(id, ftpConnect);
    }

    @Override
    public FtpConfigPO getById(Long id) {
        List<FtpConfigPO> data = query(new Query().addFilter("id", id)).getData();
        if (data.size() > 1) {
            throw new InfoException("错误");
        }
        if (data.size() == 1) {
            return data.get(0);
        } else {
            throw new InfoException("id未找到");
        }
    }

    @Override
    public List<FtpFile> ls(Long id, String path) {
        FtpConnect ftpConnect = Main.FTP_CONNECT.get(id);
        if (ftpConnect == null) {
            throw new InfoException("连接不存在");
        }
        if (StringUtil.isEmpty(path)) {
            throw new InfoException("路径不能为空");
        }

        List<FileInfo> ls = ftpConnect.getFtp().ls(path);

        // 返回
        List<FtpFile> result = new ArrayList<>();
        for (FileInfo fileInfo : ls) {
            result.add(new FtpFile(fileInfo));
        }
        return result;
    }

    @Override
    public File download(Long id, String path, String name) {
        FtpConnect ftpConnect = Main.FTP_CONNECT.get(id);
        if (ftpConnect == null) {
            throw new InfoException("连接不存在");
        }
        if (StringUtil.isEmpty(path)) {
            throw new InfoException("路径不能为空");
        }
        if (StringUtil.isEmpty(name)) {
            throw new InfoException("文件名不能为空");
        }

        java.io.File file = new File(systemService.getWorkspace(), name);
        ftpConnect.getFtp().download(path, name, file);
        return file;
    }

}