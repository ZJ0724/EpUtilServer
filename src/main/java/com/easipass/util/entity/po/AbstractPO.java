package com.easipass.util.entity.po;

import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;

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

}