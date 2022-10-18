package net.tiklab.matflow.setting.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.SystemMassage;
import net.tiklab.matflow.setting.service.SystemMassageService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
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

    //@RequestMapping(path="/getSystemLog",method = RequestMethod.POST)
    //@ApiMethod(name = "getSystemLog",desc = "获取系统日志")
    //public Result<List<String>> getSystemLog(){
    //    List<String> systemLog = systemMassageService.getSystemLog();
    //    return Result.ok(systemLog);
    //}
}
