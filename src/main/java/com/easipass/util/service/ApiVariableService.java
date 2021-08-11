package com.easipass.util.service;

import com.easipass.util.entity.po.ApiVariablePO;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ApiVariableService {

    /**
     * 查询
     *
     * @param apiModuleId apiModuleId
     *
     * @return List<ApiVariablePO>
     * */
    QueryResult<ApiVariablePO> query(Long apiModuleId);

    /**
     * 查询
     *
     * @param query query
     *
     * @return List<ApiVariablePO>
     * */
    QueryResult<ApiVariablePO> query(Query query);

    /**
     * 保存
     *
     * @param apiModuleId apiModuleId
     * @param apiVariablePOList apiVariablePOList
     * */
    void save(Long apiModuleId, List<ApiVariablePO> apiVariablePOList);

    /**
     * 校验
     *
     * @param apiVariablePO apiVariablePO
     * */
    void check(ApiVariablePO apiVariablePO);

}