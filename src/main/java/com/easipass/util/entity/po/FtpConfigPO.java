package com.easipass.util.entity.po;

import com.zj0724.common.jdbc.AccessDatabaseJdbc;

@Table(name = "FTP_CONFIG")
public final class FtpConfigPO extends AbstractPO {

    @Column(name = "TYPE", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String type;

    @Column(name = "NAME", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String name;

    @Column(name = "HOST", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String host;

    @Column(name = "PORT", type = AccessDatabaseJdbc.FieldType.NUMBER)
    private Integer port;

    @Column(name = "USERNAME", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String username;

    @Column(name = "PASSWORD", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String password;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Type {

        FTP,

        SFTP;

        public static Type getInstance(String name) {
            Type[] values = Type.values();
            for (Type type : values) {
                if (type.name().equals(name)) {
                    return type;
                }
            }
            return null;
        }

    }

}