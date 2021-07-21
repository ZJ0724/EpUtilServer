package com.easipass.util.entity.po;

import com.easipass.util.entity.AbstractPO;
import com.zj0724.common.component.jdbc.AccessDatabaseJdbc.FieldType;

/**
 * 配置
 *
 * @author ZJ
 * */
@Table(name = "CONFIG")
public final class ConfigPO extends AbstractPO {

    @Column(name = "GROUP", type = FieldType.VARCHAR)
    private String group;

    @Column(name = "CODE", type = FieldType.VARCHAR)
    private String code;

    @Column(name = "NOTE", type = FieldType.VARCHAR)
    private String note;

    @Column(name = "DATA", type = FieldType.VARCHAR)
    private String data;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static final class Groups {
        public static final SWGD_DATABASE SWGD_DATABASE = new SWGD_DATABASE();
        public static final UPLOAD_CUS_RESULT UPLOAD_CUS_RESULT = new UPLOAD_CUS_RESULT();
        public static final UPLOAD_AGENT_RESULT UPLOAD_AGENT_RESULT = new UPLOAD_AGENT_RESULT();
        public static final UPLOAD_TRANS_RESULT UPLOAD_TRANS_RESULT = new UPLOAD_TRANS_RESULT();
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
        };

        public final Code USERNAME = new Code() {
            @Override
            public String getCode() {
                return "USERNAME";
            }

            @Override
            public String getName() {
                return "密码";
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
        };

        @Override
        public String getCode() {
            return "SWGD_DATABASE";
        }

        @Override
        public String getName() {
            return "SWGD数据库";
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
        };

        @Override
        public String getCode() {
            return "UPLOAD_CUS_RESULT";
        }

        @Override
        public String getName() {
            return "上传报关单回执";
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
        };

        @Override
        public String getCode() {
            return "UPLOAD_AGENT_RESULT";
        }

        @Override
        public String getName() {
            return "上传代理委托回执";
        }
    }

    // 上传转关单回执
    public static final class UPLOAD_TRANS_RESULT implements Group {
        public final Code FTP_TYPE = new Code() {
            @Override
            public String getCode() {
                return "FTP_TYPE";
            }

            @Override
            public String getName() {
                return "ftp类型";
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
        };

        @Override
        public String getCode() {
            return "UPLOAD_TRANS_RESULT";
        }

        @Override
        public String getName() {
            return "上传转关单回执";
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
        };

        @Override
        public String getCode() {
            return "SELENIUM";
        }

        @Override
        public String getName() {
            return "selenium";
        }
    }

    public interface Group {
        String getCode();

        String getName();
    }

    public interface Code {
        String getCode();

        String getName();
    }

}