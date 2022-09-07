package com.tiklab.matflow.setting.envConfig.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.setting.other.model.SystemMassage;
import com.tiklab.matflow.setting.other.service.SystemMassageService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/envConfig")
@Api(name = "MatFlowEnvConfigController",desc = "系统信息")
public class MatFlowEnvConfigController {

    @Autowired
    MatFlowEnvConfigController matFlowEnvConfigController;




}
