package com.easipass.util.controller;

import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(BaseController.API + "/config")
public final class ConfigController {

    @Resource
    private ConfigService configService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(configService.getAll());
    }

    @PostMapping("save")
    public Response save(@RequestBody(required = false) List<Object> requestBody) {
        if (requestBody == null) {
            throw new InfoException("请求参数缺失");
        }
        List<ConfigPO> configPOList = new ArrayList<>();
        for (Object o : requestBody) {
            configPOList.add(ObjectUtil.parse(o, ConfigPO.class));
        }
        configService.save(configPOList);
        return Response.returnTrue();
    }

}