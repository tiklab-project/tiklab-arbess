package io.tiklab.matflow.setting.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.setting.model.SystemMassage;
import io.tiklab.matflow.setting.service.SystemMassageService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/systemMassage")
@Api(name = "SystemMassageController",desc = "系统信息")
public class SystemMassageController {


    @Autowired
    SystemMassageService systemMassageService;

    //开始构建
    @RequestMapping(path="/getSystemMassage",method = RequestMethod.POST)
    @ApiMethod(name = "getSystemMassage",desc = "获取系统信息")
    public Result<SystemMassage> getSystemMassage(){
        SystemMassage systemMassage = systemMassageService.getSystemMassage();
        return Result.ok(systemMassage);
    }


}































