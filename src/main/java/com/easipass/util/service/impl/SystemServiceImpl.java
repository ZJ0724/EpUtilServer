package com.easipass.util.service.impl;

import com.easipass.util.component.Database;
import com.easipass.util.config.BaseConfig;
import com.easipass.util.entity.po.AbstractPO;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.entity.po.Table;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.SystemService;
import com.zj0724.common.jdbc.AccessDatabaseJdbc;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.MapUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public final class SystemServiceImpl implements SystemService {

    private static final Object OBJECT = new Object();

    @Resource
    private ConfigService configService;

    @Override
    public File exportDatabase() {
        return BaseConfig.DATABASE_FILE;
    }

    @Override
    public void importDatabase(File database) {
        if (database == null) {
            throw new InfoException("文件不能为空");
        }

        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(database);
        try {
            // 获取所有码表
            List<String> tables = accessDatabaseJdbc.getTables();

            for (String table : tables) {
                // 获取源数据
                List<Map<String, Object>> tableData = accessDatabaseJdbc.query(table).getData();

                // 过滤
                if (ConfigPO.class.getAnnotation(Table.class).name().equals(table)) {
                    for (Map<String, Object> map : tableData) {
                        if (ConfigPO.Groups.SELENIUM.getCode().equals(map.get("GROUP_CODE")) && ConfigPO.Groups.SELENIUM.SERVER.getCode().equals(map.get("CODE"))) {
                            ConfigPO byCode = configService.getByCode(ConfigPO.Groups.SELENIUM.SERVER);
                            map.put("DATA", byCode == null ? null : byCode.getData());
                        }
                        if (ConfigPO.Groups.SELENIUM.getCode().equals(map.get("GROUP_CODE")) && ConfigPO.Groups.SELENIUM.IS_SHOW.getCode().equals(map.get("CODE"))) {
                            ConfigPO byCode = configService.getByCode(ConfigPO.Groups.SELENIUM.IS_SHOW);
                            map.put("DATA", byCode == null ? null : byCode.getData());
                        }
                    }
                }

                // 获取对应实体类
                Class<? extends AbstractPO> classByName = AbstractPO.getClassByName(table);
                if (classByName == null) {
                    throw new InfoException(table + "未找到");
                }

                // 数据转换
                List<Object> data = new ArrayList<>();
                for (Map<String, Object> map : tableData) {
                    Map<String, Object> map1 = AbstractPO.mapToField(map, classByName);
                    AbstractPO abstractPO = MapUtil.parseObject(map1, classByName);
                    data.add(abstractPO);
                }

                // 先删后插
                Database<? extends AbstractPO> database1 = Database.getDatabase(classByName);
                database1.deleteAll();
                database1.save(data, classByName);
            }
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    @Override
    public String getWorkspace() {
        synchronized (OBJECT) {
            File file = new File(BaseConfig.WORKSPACE, "workspace-" + new Date().getTime());
            while (file.isDirectory() && file.exists()) {
                file = new File(BaseConfig.WORKSPACE, "workspace-" + new Date().getTime());
            }
            return file.getAbsolutePath();
        }
    }

}