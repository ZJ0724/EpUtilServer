package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.FtpPathConfigPO;
import com.easipass.util.service.FtpConfigService;
import com.easipass.util.service.FtpPathConfigService;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public final class FtpPathConfigServiceImpl implements FtpPathConfigService {

    @Resource
    private FtpConfigService ftpConfigService;

    @Override
    public void save(FtpPathConfigPO ftpPathConfigPO) {
        if (ftpPathConfigPO.getFtpConfigId() == null) {
            throw new InfoException("ftpConfigId is null");
        }
        if (StringUtil.isEmpty(ftpPathConfigPO.getName())) {
            throw new InfoException("name is null");
        }
        if (StringUtil.isEmpty(ftpPathConfigPO.getPath())) {
            throw new InfoException("path is null");
        }

        // 关联id要存在
        ftpConfigService.getById(ftpPathConfigPO.getFtpConfigId());

        Database<FtpPathConfigPO> ftpPathConfigPODatabase = Database.getDatabase(FtpPathConfigPO.class);
        ftpPathConfigPODatabase.save(ftpPathConfigPO);
    }

    @Override
    public QueryResult<FtpPathConfigPO> query(Query query) {
        Database<FtpPathConfigPO> ftpPathConfigPODatabase = Database.getDatabase(FtpPathConfigPO.class);
        return ftpPathConfigPODatabase.query(query);
    }

    @Override
    public void delete(Long id) {
        Database<FtpPathConfigPO> ftpPathConfigPODatabase = Database.getDatabase(FtpPathConfigPO.class);
        ftpPathConfigPODatabase.delete(id);
    }

    @Override
    public void delete(List<FtpPathConfigPO> idList) {
        Database<FtpPathConfigPO> ftpPathConfigPODatabase = Database.getDatabase(FtpPathConfigPO.class);
        ftpPathConfigPODatabase.delete(idList);
    }

}