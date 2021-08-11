package com.easipass.util.entity.po;

import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;

/**
 * ApiVariablePO
 *
 * @author ZJ
 * */
@Table(name = "API_VARIABLE")
public class ApiVariablePO extends AbstractPO {

    @Column(name = "API_MODULE_ID", type = AccessDatabaseJdbc.FieldType.NUMBER)
    private Long apiModuleId;

    @Column(name = "CODE", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String code;

    @Column(name = "DATA", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String data;

    public Long getApiModuleId() {
        return apiModuleId;
    }

    public void setApiModuleId(Long apiModuleId) {
        this.apiModuleId = apiModuleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}