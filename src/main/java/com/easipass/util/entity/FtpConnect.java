package com.easipass.util.entity;

import com.easipass.util.entity.po.FtpConfigPO;
import com.zj0724.common.Ftp;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.ftp.Sftp;
import java.util.Date;

public final class FtpConnect {

    private Date operateTime = new Date();

    private final Ftp ftp;

    public FtpConnect(FtpConfigPO ftpConfigPO) {
        if (ftpConfigPO.getType().equals(FtpConfigPO.Type.FTP.name())) {
            ftp = new com.zj0724.common.ftp.Ftp(ftpConfigPO.getHost(), ftpConfigPO.getPort(), ftpConfigPO.getUsername(), ftpConfigPO.getPassword());
        } else if (ftpConfigPO.getType().equals(FtpConfigPO.Type.SFTP.name())) {
            ftp = new Sftp(ftpConfigPO.getHost(), ftpConfigPO.getPort(), ftpConfigPO.getUsername(), ftpConfigPO.getPassword());
        } else {
            throw new InfoException("类型未找到");
        }
    }

    public void close() {
        this.ftp.close();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public Ftp getFtp() {
        this.operateTime = new Date();
        return ftp;
    }

    public boolean isConnected() {
        return this.ftp.isConnected();
    }

}