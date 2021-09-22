package com.easipass.util.controller;

import com.easipass.util.service.SystemService;
import com.zj0724.common.File;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.ServletUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(BaseController.API + "/system")
public final class SystemController {

    @Resource
    private SystemService systemService;

    @GetMapping("exportDatabase")
    public void exportDatabase(HttpServletResponse httpServletResponse) {
        ServletUtil.responseToFile(httpServletResponse, systemService.exportDatabase());
    }

    @PostMapping("importDatabase")
    public Response importDatabase(@RequestParam(required = false, name = "database") MultipartFile database) {
        if (database == null) {
            throw new InfoException("database不能为空");
        }
        if (database.getOriginalFilename() == null) {
            throw new InfoException("database不能为空");
        }
        java.io.File file = null;
        try {
            file = new java.io.File(systemService.getWorkspace(), database.getOriginalFilename());
            File.createFile(file).setData(database);
            systemService.importDatabase(file);
            return Response.returnTrue();
        } finally {
            if (file != null && file.exists()) {
                File.createFile(file).parent().delete();
            }
        }
    }

}