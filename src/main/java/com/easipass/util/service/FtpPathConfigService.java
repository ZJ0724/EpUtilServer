package com.easipass.util.service;

import com.easipass.util.entity.po.FtpPathConfigPO;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface FtpPathConfigService {

    void save(FtpPathConfigPO ftpPathConfigPO);

    QueryResult<FtpPathConfigPO> query(Query query);

    void delete(Long id);

    void delete(List<FtpPathConfigPO> idList);

}