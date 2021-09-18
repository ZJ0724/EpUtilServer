package com.easipass.util.controller;

import com.easipass.util.entity.po.FtpConfigPO;
import com.easipass.util.service.FtpConfigService;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.ServletUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/ftpConfig")
public final class FtpConfigController {

    @Resource
    private FtpConfigService ftpConfigService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(ftpConfigService.getAll());
    }

    @PostMapping("save")
    public Response save(@RequestBody(required = false) FtpConfigPO ftpConfigPO) {
        ftpConfigService.save(ftpConfigPO);
        return Response.returnTrue();
    }

    @PostMapping("delete")
    public Response delete(@RequestBody(required = false) FtpConfigPO ftpConfigPO) {
        ftpConfigService.delete(ftpConfigPO == null ? null : ftpConfigPO.getId());
        return Response.returnTrue();
    }

    @PostMapping("connect")
    public Response connect(@RequestBody(required = false) FtpConfigPO ftpConfigPO) {
        ftpConfigService.connect(ftpConfigPO == null ? null : ftpConfigPO.getId());
        return Response.returnTrue();
    }

    @PostMapping("ls")
    public Response ls(@RequestBody(required = false) Map<String, Object> requestBody) {
        return Response.returnTrue(ftpConfigService.ls(
                MapUtil.getValue(requestBody, "id", Long.class),
                MapUtil.getValue(requestBody, "path", String.class)
                ));
    }

    @GetMapping("download")
    public void download(@RequestParam(required = false) Long id,
                         @RequestParam(required = false) String path,
                         @RequestParam(required = false) String name,
                         HttpServletResponse httpServletResponse) {
        File download = null;
        try {
            download = ftpConfigService.download(id, path, name);
            ServletUtil.responseToFile(httpServletResponse, download);
        } finally {
            if (download != null) {
                com.zj0724.common.File.createDir(download.getParent()).delete();
            }
        }
    }

}