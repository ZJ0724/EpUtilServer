package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ApiModulePO;
import com.easipass.util.service.ApiModuleService;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public final class ApiModuleServiceImpl implements ApiModuleService {

    @Override
    public List<ApiModulePO> getAll() {
        Database<ApiModulePO> database = Database.getDatabase(ApiModulePO.class);
        QueryResult<ApiModulePO> query = database.query();
        return query.getData();
    }

    @Override
    public void save(ApiModulePO apiModulePO) {
        if (apiModulePO == null) {
            throw new InfoException("参数缺失");
        }
        if (StringUtil.isEmpty(apiModulePO.getName())) {
            throw new InfoException("名称不能为空");
        }
        if (StringUtil.isEmpty(apiModulePO.getApiUrl())) {
            throw new InfoException("url不能为空");
        }
        if (StringUtil.isEmpty(apiModulePO.getApiType())) {
            throw new InfoException("type不能为空");
        }

        // 名称不能重复
        ApiModulePO byName = getByName(apiModulePO.getName());
        if (byName != null) {
            if (!(byName.getId().equals(apiModulePO.getId()) && byName.getName().equals(apiModulePO.getName()))) {
                throw new InfoException("名称不能重复");
            }
        }

        // type存在
        boolean isOk = false;
        for (ApiModulePO.Type type : ApiModulePO.Type.values()) {
            if (type.name().equals(apiModulePO.getApiType())) {
                isOk = true;
                break;
            }
        }
        if (!isOk) {
            throw new InfoException("type不存在");
        }

        Database<ApiModulePO> database = Database.getDatabase(ApiModulePO.class);
        database.save(apiModulePO);
    }

    @Override
    public QueryResult<ApiModulePO> query(Query query) {
        Database<ApiModulePO> database = Database.getDatabase(ApiModulePO.class);
        return database.query(query);
    }

    @Override
    public ApiModulePO getByName(String name) {
        if (StringUtil.isEmpty(name)) {
            throw new InfoException("name不能为空");
        }
        Query query = new Query();
        query.addFilter("name", name);
        QueryResult<ApiModulePO> result = query(query);
        if (result.getData().size() == 0) {
            return null;
        }
        if (result.getData().size() != 1) {
            throw new InfoException("name存在多条记录");
        }
        return result.getData().get(0);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        Database<ApiModulePO> database = Database.getDatabase(ApiModulePO.class);
        database.delete(id);
    }

}