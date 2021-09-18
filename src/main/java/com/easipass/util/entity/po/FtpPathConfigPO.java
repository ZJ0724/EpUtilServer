package com.easipass.util.entity.po;

import com.zj0724.common.jdbc.AccessDatabaseJdbc;

@Table(name = "FTP_PATH_CONFIG")
public final class FtpPathConfigPO extends AbstractPO {

    @Column(name = "FTP_CONFIG_ID", type = AccessDatabaseJdbc.FieldType.NUMBER)
    private Long ftpConfigId;

    @Column(name = "NAME", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String name;

    @Column(name = "PATH", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String path;

    public Long getFtpConfigId() {
        return ftpConfigId;
    }

    public void setFtpConfigId(Long ftpConfigId) {
        this.ftpConfigId = ftpConfigId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}