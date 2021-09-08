package com.easipass.util.entity.po;

import com.zj0724.common.jdbc.AccessDatabaseJdbc;
import com.zj0724.common.exception.ErrorException;
import com.zj0724.common.util.ClassUtil;
import com.zj0724.common.util.PackageUtil;
import com.zj0724.common.util.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实体
 *
 * @author ZJ
 * */
public abstract class AbstractPO {

    @Column(name = "ID", type = AccessDatabaseJdbc.FieldType.NUMBER)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取实体类通过表名
     *
     * @param name 表名
     *
     * @return 实体类
     * */
    @SuppressWarnings("unchecked")
    public static Class<? extends AbstractPO> getClassByName(String name) {
        if (StringUtil.isEmpty(name)) {
            return null;
        }

        List<Class<?>> scan = PackageUtil.scan(AbstractPO.class);
        for (Class<?> c : scan) {
            Table table = c.getAnnotation(Table.class);
            if (table != null) {
                if (name.equals(table.name())) {
                    return (Class<? extends AbstractPO>) c;
                }
            }
        }
        return null;
    }

    /**
     * 将map的key转换成column注解的值
     *
     * @param map map
     * @param c c
     *
     * @return 新的map
     * */
    public static Map<String, Object> mapToColumn(Map<?, ?> map, Class<? extends AbstractPO> c) {
        Map<String, Object> result = new HashMap<>();
        Set<? extends Map.Entry<?, ?>> entries = map.entrySet();
        List<Field> allFields = ClassUtil.getAllFields(c);
        for (Map.Entry<?, ?> entry : entries) {
            String newKey = null;
            for (Field field : allFields) {
                if (field.getName().equals(entry.getKey())) {
                    newKey = field.getAnnotation(Column.class).name();
                    break;
                }
            }
            if (newKey == null) {
                throw new ErrorException("map转换出错：" + entry.getKey());
            }
            result.put(newKey, entry.getValue());
        }
        return result;
    }

    /**
     * 将map的key转换成类的值
     *
     * @param map map
     * @param c c
     *
     * @return 新的map
     * */
    public static Map<String, Object> mapToField(Map<String, Object> map, Class<? extends AbstractPO> c) {
        Map<String, Object> result = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        List<Field> allFields = ClassUtil.getAllFields(c);
        for (Map.Entry<String, Object> entry : entries) {
            String newKey = null;
            for (Field field : allFields) {
                if (field.getAnnotation(Column.class).name().equals(entry.getKey())) {
                    newKey = field.getName();
                    break;
                }
            }
            if (newKey == null) {
                throw new ErrorException("map转换出错：" + entry.getKey());
            }
            result.put(newKey, entry.getValue());
        }
        return result;
    }

}