package com.easipass.util.test;

import com.zj0724.common.jdbc.AccessDatabaseJdbc;
import java.io.File;

public final class Main {

    public static void main(String[] args) {
        new AccessDatabaseJdbc(new File("\\\\192.168.1.108\\Users\\ZJ\\.ep-util", "database.accdb"));
    }

}