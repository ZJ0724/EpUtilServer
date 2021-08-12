package com.easipass.util.test;

import com.zj0724.common.util.Base64Util;
import com.zj0724.common.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public final class Main {

    public static void main(String[] args) throws Exception {
        final String data = FileUtil.getData(new File("C:\\Users\\ZJ\\Desktop\\file\\1.txt"));
        byte[] bytes = Base64Util.decodeToByte(data);
        OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\ZJ\\Desktop\\file", "test.accdb"));
        outputStream.write(bytes);
    }

}