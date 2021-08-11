package com.easipass.util.service;

import com.easipass.util.entity.po.ApiModulePO;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ApiModuleService {

    /**
     * 获取所有
     *
     * @return List<ApiModulePO>
     * */
    List<ApiModulePO> getAll();

    /**
     * 保存
     *
     * @param apiModulePO apiModulePO
     * */
    void save(ApiModulePO apiModulePO);

    /**
     * 查询
     *
     * @param query query
     *
     * @return QueryResult<ApiModulePO>
     * */
    QueryResult<ApiModulePO> query(Query query);

    /**
     * 通过名称查找
     *
     * @param name name
     *
     * @return ApiModulePO
     * */
    ApiModulePO getByName(String name);

    /**
     * 删除
     *
     * @param id id
     * */
    void delete(Long id);

}