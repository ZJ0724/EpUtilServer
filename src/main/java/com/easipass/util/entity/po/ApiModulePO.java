package com.easipass.util.entity.po;

import com.zj0724.common.jdbc.AccessDatabaseJdbc;

/**
 * ApiModule
 *
 * @author ZJ
 * */
@Table(name = "API_MODULE")
public final class ApiModulePO extends AbstractPO {

    @Column(name = "NAME", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String name;

    @Column(name = "API_URL", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String apiUrl;

    @Column(name = "API_TYPE", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String apiType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public enum Type {

        POST,

        GET,

        PULL,

        DELETE

    }

}