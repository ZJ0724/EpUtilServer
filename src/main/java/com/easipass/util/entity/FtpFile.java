package com.easipass.util.entity;

import com.zj0724.common.entity.FileInfo;
import com.zj0724.common.util.DateUtil;

public final class FtpFile {

    private final boolean isFile;

    private final String name;

    private final String updateTime;

    private final String size;

    public FtpFile(FileInfo fileInfo) {
        this.isFile = fileInfo.isFile();
        this.name = fileInfo.getName();
        this.updateTime = DateUtil.format(fileInfo.getUpdateTime(), DateUtil.DEFAULT_DATE_FORMAT);
        if (this.isFile) {
            long size = fileInfo.getSize();
            if (size < 1024) {
                this.size = size + " B";
            } else if (size < 1048576) {
                this.size = String.format("%.2f", size / 1024.0) + " KB";

            } else {
                this.size = String.format("%.2f", size / 1048576.0) + " MB";
            }
        } else {
            this.size = "-";
        }
    }

    public boolean isFile() {
        return isFile;
    }

    public String getName() {
        return name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getSize() {
        return size;
    }

}