package com.easipass.util.entity.po;

import com.zj0724.common.jdbc.AccessDatabaseJdbc.FieldType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 配置
 *
 * @author ZJ
 * */
@Table(name = "CONFIG")
public final class ConfigPO extends AbstractPO {

    @Column(name = "GROUP_CODE", type = FieldType.VARCHAR)
    private String group;

    @Column(name = "GROUP_NAME", type = FieldType.VARCHAR)
    private String groupName;

    @Column(name = "CODE", type = FieldType.VARCHAR)
    private String code;

    @Column(name = "CODE_NAME", type = FieldType.VARCHAR)
    private String codeName;

    @Column(name = "DATA", type = FieldType.VARCHAR)
    private String data;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static final class Groups {

        public static final SWGD_DATABASE SWGD_DATABASE = new ConfigPO.SWGD_DATABASE();

        public static final UPLOAD_CUS_RESULT UPLOAD_CUS_RESULT = new ConfigPO.UPLOAD_CUS_RESULT();

        public static final UPLOAD_AGENT_RESULT UPLOAD_AGENT_RESULT = new ConfigPO.UPLOAD_AGENT_RESULT();

        public static final UPLOAD_TRANS_RESULT UPLOAD_TRANS_RESULT = new ConfigPO.UPLOAD_TRANS_RESULT();

        public static final SELENIUM SELENIUM = new ConfigPO.SELENIUM();

    }

    // SWGD_DATABASE
    public static final class SWGD_DATABASE implements Group {

        public final Code HOST = new Code() {
            @Override
            public String getCode() {
                return "HOST";
            }

            @Override
            public String getName() {
                return "地址";
            }

            @Override
            public Group getGroup() {
                return Groups.SWGD_DATABASE;
            }
        };

        public final Code PORT = new Code() {
            @Override
            public String getCode() {
                return "PORT";
            }

            @Override
            public String getName() {
                return "端口";
            }

            @Override
            public Group getGroup() {
                return Groups.SWGD_DATABASE;
            }
        };

        public final Code SID = new Code() {
            @Override
            public String getCode() {
                return "SID";
            }

            @Override
            public String getName() {
                return "SID";
            }

            @Override
            public Group getGroup() {
                return Groups.SWGD_DATABASE;
            }
        };

        public final Code USERNAME = new Code() {
            @Override
            public String getCode() {
                return "USERNAME";
            }

            @Override
            public String getName() {
                return "账号";
            }

            @Override
            public Group getGroup() {
                return Groups.SWGD_DATABASE;
            }
        };

        public final Code PASSWORD = new Code() {
            @Override
            public String getCode() {
                return "PASSWORD";
            }

            @Override
            public String getName() {
                return "密码";
            }

            @Override
            public Group getGroup() {
                return Groups.SWGD_DATABASE;
            }
        };

        @Override
        public String getCode() {
            return "SWGD_DATABASE";
        }

        @Override
        public String getName() {
            return "SWGD数据库";
        }

        @Override
        public List<Code> codes() {
            List<Code> codes = new LinkedList<>();
            codes.add(HOST);
            codes.add(PORT);
            codes.add(SID);
            codes.add(USERNAME);
            codes.add(PASSWORD);
            return codes;
        }
    }

    // 上传报关单回执
    public static final class UPLOAD_CUS_RESULT implements Group {

        public final Code FTP_TYPE = new Code() {
            @Override
            public String getCode() {
                return "FTP_TYPE";
            }

            @Override
            public String getName() {
                return "ftp类型";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        public final Code FTP_HOST = new Code() {
            @Override
            public String getCode() {
                return "FTP_HOST";
            }

            @Override
            public String getName() {
                return "ftp地址";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        public final Code FTP_PORT = new Code() {
            @Override
            public String getCode() {
                return "FTP_PORT";
            }

            @Override
            public String getName() {
                return "ftp端口";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        public final Code FTP_USERNAME = new Code() {
            @Override
            public String getCode() {
                return "FTP_USERNAME";
            }

            @Override
            public String getName() {
                return "ftp用户名";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        public final Code FTP_PASSWORD = new Code() {
            @Override
            public String getCode() {
                return "FTP_PASSWORD";
            }

            @Override
            public String getName() {
                return "ftp密码";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        public final Code FTP_UPLOAD_PATH = new Code() {
            @Override
            public String getCode() {
                return "FTP_UPLOAD_PATH";
            }

            @Override
            public String getName() {
                return "ftp上传路径";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_CUS_RESULT;
            }
        };

        @Override
        public String getCode() {
            return "UPLOAD_CUS_RESULT";
        }

        @Override
        public String getName() {
            return "上传报关单回执";
        }

        @Override
        public List<Code> codes() {
            List<Code> codes = new ArrayList<>();
            codes.add(FTP_TYPE);
            codes.add(FTP_HOST);
            codes.add(FTP_PORT);
            codes.add(FTP_USERNAME);
            codes.add(FTP_PASSWORD);
            codes.add(FTP_UPLOAD_PATH);
            return codes;
        }

    }

    // 上传代理委托回执
    public static final class UPLOAD_AGENT_RESULT implements Group {

        public final Code FTP_TYPE = new Code() {
            @Override
            public String getCode() {
                return "FTP_TYPE";
            }

            @Override
            public String getName() {
                return "ftp类型";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        public final Code FTP_HOST = new Code() {
            @Override
            public String getCode() {
                return "FTP_HOST";
            }

            @Override
            public String getName() {
                return "ftp地址";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        public final Code FTP_PORT = new Code() {
            @Override
            public String getCode() {
                return "FTP_PORT";
            }

            @Override
            public String getName() {
                return "ftp端口";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        public final Code FTP_USERNAME = new Code() {
            @Override
            public String getCode() {
                return "FTP_USERNAME";
            }

            @Override
            public String getName() {
                return "ftp用户名";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        public final Code FTP_PASSWORD = new Code() {
            @Override
            public String getCode() {
                return "FTP_PASSWORD";
            }

            @Override
            public String getName() {
                return "ftp密码";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        public final Code FTP_UPLOAD_PATH = new Code() {
            @Override
            public String getCode() {
                return "FTP_UPLOAD_PATH";
            }

            @Override
            public String getName() {
                return "ftp上传路径";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_AGENT_RESULT;
            }
        };

        @Override
        public String getCode() {
            return "UPLOAD_AGENT_RESULT";
        }

        @Override
        public String getName() {
            return "上传代理委托回执";
        }

        @Override
        public List<Code> codes() {
            List<Code> codes = new ArrayList<>();
            codes.add(FTP_TYPE);
            codes.add(FTP_HOST);
            codes.add(FTP_PORT);
            codes.add(FTP_USERNAME);
            codes.add(FTP_PASSWORD);
            codes.add(FTP_UPLOAD_PATH);
            return codes;
        }

    }

    // 上传转关单回执
    public static final class UPLOAD_TRANS_RESULT implements Group {

        public final Code DATABASE_HOST = new Code() {
            @Override
            public String getCode() {
                return "DATABASE_HOST";
            }

            @Override
            public String getName() {
                return "数据库地址";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code DATABASE_PORT = new Code() {
            @Override
            public String getCode() {
                return "DATABASE_PORT";
            }

            @Override
            public String getName() {
                return "数据库端口";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code DATABASE_SID = new Code() {
            @Override
            public String getCode() {
                return "DATABASE_SID";
            }

            @Override
            public String getName() {
                return "数据库SID";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code DATABASE_USERNAME = new Code() {
            @Override
            public String getCode() {
                return "DATABASE_USERNAME";
            }

            @Override
            public String getName() {
                return "数据库用户名";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code DATABASE_PASSWORD = new Code() {
            @Override
            public String getCode() {
                return "DATABASE_PASSWORD";
            }

            @Override
            public String getName() {
                return "数据库密码";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_TYPE = new Code() {
            @Override
            public String getCode() {
                return "FTP_TYPE";
            }

            @Override
            public String getName() {
                return "ftp类型";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_HOST = new Code() {
            @Override
            public String getCode() {
                return "FTP_HOST";
            }

            @Override
            public String getName() {
                return "ftp地址";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_PORT = new Code() {
            @Override
            public String getCode() {
                return "FTP_PORT";
            }

            @Override
            public String getName() {
                return "ftp端口";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_USERNAME = new Code() {
            @Override
            public String getCode() {
                return "FTP_USERNAME";
            }

            @Override
            public String getName() {
                return "ftp用户名";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_PASSWORD = new Code() {
            @Override
            public String getCode() {
                return "FTP_PASSWORD";
            }

            @Override
            public String getName() {
                return "ftp密码";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        public final Code FTP_UPLOAD_PATH = new Code() {
            @Override
            public String getCode() {
                return "FTP_UPLOAD_PATH";
            }

            @Override
            public String getName() {
                return "ftp上传路径";
            }

            @Override
            public Group getGroup() {
                return Groups.UPLOAD_TRANS_RESULT;
            }
        };

        @Override
        public String getCode() {
            return "UPLOAD_TRANS_RESULT";
        }

        @Override
        public String getName() {
            return "上传转关单回执";
        }

        @Override
        public List<Code> codes() {
            List<Code> codes = new ArrayList<>();
            codes.add(DATABASE_HOST);
            codes.add(DATABASE_PORT);
            codes.add(DATABASE_SID);
            codes.add(DATABASE_USERNAME);
            codes.add(DATABASE_PASSWORD);
            codes.add(FTP_TYPE);
            codes.add(FTP_HOST);
            codes.add(FTP_PORT);
            codes.add(FTP_USERNAME);
            codes.add(FTP_PASSWORD);
            codes.add(FTP_UPLOAD_PATH);
            return codes;
        }

    }

    // SELENIUM
    public static final class SELENIUM implements Group {

        public final Code SERVER = new Code() {
            @Override
            public String getCode() {
                return "SERVER";
            }

            @Override
            public String getName() {
                return "服务地址";
            }

            @Override
            public Group getGroup() {
                return Groups.SELENIUM;
            }
        };

        public final Code IS_SHOW = new Code() {
            @Override
            public String getCode() {
                return "IS_SHOW";
            }

            @Override
            public String getName() {
                return "是否显示浏览器";
            }

            @Override
            public Group getGroup() {
                return Groups.SELENIUM;
            }
        };

        @Override
        public String getCode() {
            return "SELENIUM";
        }

        @Override
        public String getName() {
            return "selenium";
        }

        @Override
        public List<Code> codes() {
            List<Code> codes = new ArrayList<>();
            codes.add(SERVER);
            codes.add(IS_SHOW);
            return codes;
        }

    }

    public interface Group {
        String getCode();

        String getName();

        List<Code> codes();
    }

    public interface Code {
        String getCode();

        String getName();

        Group getGroup();
    }

}