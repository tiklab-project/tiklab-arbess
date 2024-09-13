package io.thoughtware.arbess.setting.controller;

import io.thoughtware.core.Result;
import io.thoughtware.arbess.setting.model.SystemMassage;
import io.thoughtware.arbess.setting.service.SystemMassageService;
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
     * @pi.path:/systemMassage/getSystemMassage
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/getSystemMassage",method = RequestMethod.POST)
    public Result<SystemMassage> getSystemMassage(){
        SystemMassage systemMassage = systemMassageService.getSystemMassage();
        return Result.ok(systemMassage);
    }


}































