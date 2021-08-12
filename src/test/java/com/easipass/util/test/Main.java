package com.easipass.util.test;

import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;
import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public final class Main {

    public static void main(String[] args) throws Exception {
        new AccessDatabaseJdbc(new File("\\\\192.168.1.108\\Users\\ZJ\\.ep-util", "database.accdb"));
    }

}