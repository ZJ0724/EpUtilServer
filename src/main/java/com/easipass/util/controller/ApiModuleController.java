package com.easipass.util.controller;

import com.easipass.util.entity.po.ApiModulePO;
import com.easipass.util.service.ApiModuleService;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/apiModule")
public final class ApiModuleController {

    @Resource
    private ApiModuleService apiModuleService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(apiModuleService.getAll());
    }

    @PostMapping("save")
    public Response save(@RequestBody(required = false) Map<String, Object> requestBody) {
        apiModuleService.save(ObjectUtil.parse(requestBody, ApiModulePO.class));
        return Response.returnTrue();
    }

    @PostMapping("delete")
    public Response delete(@RequestBody(required = false) Map<String, Object> requestBody) {
        apiModuleService.delete(MapUtil.getValue(requestBody, "id", Long.class));
        return Response.returnTrue();
    }

}