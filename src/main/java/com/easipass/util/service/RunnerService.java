package com.easipass.util.service;

import com.easipass.util.Main;
import com.easipass.util.entity.FtpConnect;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface RunnerService extends ApplicationRunner {

    @Override
    default void run(ApplicationArguments args) {
        gc();

        // 清理ftp
        new Thread(() -> {
            while (true) {
                for (Long id : Main.FTP_CONNECT.keySet()) {
                    FtpConnect ftpConnect = Main.FTP_CONNECT.get(id);
                    // 1分钟不操作，自动关闭
                    int timeout = 60 * 1000;
                    if (new Date().getTime() - ftpConnect.getOperateTime().getTime() > timeout) {
                        ftpConnect.close();
                        Main.FTP_CONNECT.remove(id);
                        System.out.println("关闭：" + id);
                        System.out.println("还剩：" + Main.FTP_CONNECT.size());
                    }
                }
            }
        }).start();
    }

    void gc();

}