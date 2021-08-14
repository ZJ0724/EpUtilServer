package com.easipass.util.service;

import org.springframework.stereotype.Service;
import java.io.File;

@Service
public interface SystemService {

    /**
     * 导出数据库
     *
     * @return 数据库文件
     * */
     File exportDatabase();

     /**
      * 导入
      *
      * @param database 数据库文件
      * */
     void importDatabase(File database);

     /**
      * 获取工作空间
      *
      * @return 工作空间
      * */
     String getWorkspace();

}