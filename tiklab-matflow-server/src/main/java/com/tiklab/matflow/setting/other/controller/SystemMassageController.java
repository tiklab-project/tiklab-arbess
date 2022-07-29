package com.tiklab.matflow.setting.other.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.core.Result;
import com.tiklab.matflow.setting.other.model.SystemMassage;
import com.tiklab.matflow.setting.other.service.SystemMassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    @RequestMapping(path="/getSystemLog",method = RequestMethod.POST)
    @ApiMethod(name = "getSystemLog",desc = "获取系统日志")
    public Result<List<String>> getSystemLog(){
        List<String> systemLog = systemMassageService.getSystemLog();
        return Result.ok(systemLog);
    }
}
