package com.easipass.util.service;

import com.easipass.util.entity.FtpFile;
import com.easipass.util.entity.po.FtpConfigPO;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

@Service
public interface FtpConfigService {

    List<FtpConfigPO> getAll();

    void save(FtpConfigPO ftpConfigPO);

    QueryResult<FtpConfigPO> query(Query query);

    void delete(Long id);

    void connect(Long id);

    FtpConfigPO getById(Long id);

    List<FtpFile> ls(Long id, String path);

    File download(Long id, String path, String name);

}