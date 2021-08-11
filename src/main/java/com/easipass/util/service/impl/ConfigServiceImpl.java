package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.entity.Query;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public final class ConfigServiceImpl implements ConfigService {

    @Override
    public ConfigPO getByCode(String group, String code) {
        if (StringUtil.isEmpty(group)) {
            throw new InfoException("group不能为空");
        }
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("code不能为空");
        }
        Database<ConfigPO> configPODatabase = Database.getDatabase(ConfigPO.class);
        Query query = new Query();
        query.addFilter("group", group);
        query.addFilter("code", code);
        QueryResult<ConfigPO> query1 = configPODatabase.query(query);
        List<ConfigPO> data = query1.getData();
        if (data.size() > 1) {
            throw new InfoException("存在多条记录");
        }
        if (data.size() == 0) {
            return null;
        } else {
            return data.get(0);
        }
    }

    @Override
    public ConfigPO getByCode(ConfigPO.Code code) {
        if (code == null) {
            throw new InfoException("code is null");
        }
        return getByCode(code.getGroup().getCode(), code.getCode());
    }

    @Override
    public List<ConfigPO> getAll() {
        List<ConfigPO> result = new ArrayList<>();

        Field[] declaredFields = ConfigPO.Groups.class.getDeclaredFields();
        for (Field Field : declaredFields) {
            ConfigPO.Group group;
            try {
                group = (ConfigPO.Group)Field.get(null);
            } catch (Exception e) {
                throw new InfoException(e.getMessage());
            }

            List<ConfigPO.Code> codes = group.codes();
            for (ConfigPO.Code code : codes) {
                ConfigPO configPO = getByCode(group.getCode(), code.getCode());
                if (configPO == null) {
                    configPO = new ConfigPO();
                }
                configPO.setGroup(group.getCode());
                configPO.setGroupName(group.getName());
                configPO.setCode(code.getCode());
                configPO.setCodeName(code.getName());
                result.add(configPO);
            }
        }
        return result;
    }

    @Override
    public void save(ConfigPO configPO) {
        if (configPO == null) {
            throw new InfoException("参数不能为空");
        }

        String group = configPO.getGroup();
        String code = configPO.getCode();

        if (StringUtil.isEmpty(group)) {
            throw new InfoException("group不能为空");
        }
        if (StringUtil.isEmpty(code)) {
            throw new InfoException("code不能为空");
        }

        // 防止重复记录
        ConfigPO byCode = getByCode(group, code);
        if (byCode != null) {
            configPO.setId(byCode.getId());
        }

        Database<ConfigPO> configPODatabase = Database.getDatabase(ConfigPO.class);
        configPODatabase.save(configPO);
    }

    @Override
    public void save(List<ConfigPO> configPOList) {
        for (ConfigPO configPO : configPOList) {
            save(configPO);
        }
    }

}