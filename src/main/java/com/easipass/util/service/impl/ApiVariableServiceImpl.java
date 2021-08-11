package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ApiVariablePO;
import com.easipass.util.service.ApiVariableService;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public final class ApiVariableServiceImpl implements ApiVariableService {

    @Override
    public QueryResult<ApiVariablePO> query(Long apiModuleId) {
        if (apiModuleId == null) {
            throw new InfoException("apiModuleId不能为空");
        }
        Query query = new Query();
        query.addFilter("apiModuleId", apiModuleId);
        return query(query);
    }

    @Override
    public QueryResult<ApiVariablePO> query(Query query) {
        Database<ApiVariablePO> database = Database.getDatabase(ApiVariablePO.class);
        return database.query(query);
    }

    @Override
    public void save(Long apiModuleId, List<ApiVariablePO> apiVariablePOList) {
        if (StringUtil.isEmpty(apiModuleId)) {
            throw new InfoException("apiModuleId 不能为空");
        }

        for (ApiVariablePO apiVariablePO : apiVariablePOList) {
            apiVariablePO.setApiModuleId(apiModuleId);
            check(apiVariablePO);
        }

        Database<ApiVariablePO> database = Database.getDatabase(ApiVariablePO.class);
        database.delete(query(apiModuleId).getData());
        database.save(apiVariablePOList);
    }

    @Override
    public void check(ApiVariablePO apiVariablePO) {
        if (StringUtil.isEmpty(apiVariablePO.getCode())) {
            throw new InfoException("code不能为空");
        }
        if (StringUtil.isEmpty(apiVariablePO.getData())) {
            throw new InfoException("data不能为空");
        }
    }

}