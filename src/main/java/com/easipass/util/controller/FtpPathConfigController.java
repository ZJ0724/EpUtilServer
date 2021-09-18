package com.easipass.util.controller;

import com.easipass.util.entity.po.FtpPathConfigPO;
import com.easipass.util.service.FtpPathConfigService;
import com.zj0724.common.entity.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping(BaseController.API + "/ftpPathConfig")
public final class FtpPathConfigController {

    @Resource
    private FtpPathConfigService ftpPathConfigService;

    @PostMapping("save")
    public Response save(@RequestBody(required = false) FtpPathConfigPO ftpPathConfigPO) {
        ftpPathConfigService.save(ftpPathConfigPO);
        return Response.returnTrue();
    }

    @PostMapping("delete")
    public Response delete(@RequestBody(required = false) FtpPathConfigPO ftpPathConfigPO) {
        ftpPathConfigService.delete(ftpPathConfigPO == null ? null : ftpPathConfigPO.getId());
        return Response.returnTrue();
    }

    @PostMapping("query")
    public Response query(@RequestBody(required = false) Query query) {
        return Response.returnTrue(ftpPathConfigService.query(query));
    }

}