package io.tiklab.arbess.setting.other.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.setting.other.model.SystemMassage;
import io.tiklab.arbess.setting.other.service.SystemMassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @pi.protocol: http
 * @pi.groupName: 系统信息控制器
 */
@RestController
@RequestMapping("/systemMassage")
public class SystemMassageController {


    @Autowired
    SystemMassageService systemMassageService;

    /**
     * @pi.name:获取系统信息
     * @pi.url:/systemMassage/getSystemMassage
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/getSystemMassage",method = RequestMethod.POST)
    public Result<SystemMassage> getSystemMassage(){
        SystemMassage systemMassage = systemMassageService.getSystemMassage();
        return Result.ok(systemMassage);
    }


}































