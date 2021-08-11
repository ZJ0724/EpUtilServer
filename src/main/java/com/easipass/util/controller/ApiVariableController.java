package com.easipass.util.controller;

import com.easipass.util.entity.po.ApiVariablePO;
import com.easipass.util.service.ApiVariableService;
import com.zj0724.common.entity.Query;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(BaseController.API + "/apiVariable")
public final class ApiVariableController {

    @Resource
    private ApiVariableService apiVariableService;

    @PostMapping("query")
    public Response getAll(@RequestBody(required = false) Query query) {
        return Response.returnTrue(apiVariableService.query(query));
    }

    @PostMapping("save")
    public Response save(@RequestParam(required = false) Long apiModuleId, @RequestBody(required = false) List<ApiVariablePO> apiVariablePOList) {
        apiVariableService.save(apiModuleId, apiVariablePOList);
        return Response.returnTrue();
    }

}