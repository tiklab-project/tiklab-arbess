package com.tiklab.matflow.definition.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matFlowCommon")
@Api(name = "MatFlowCommonController",desc = "流水线文件信息")
public class MatFlowCommonController {

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCommonController.class);

    //获取文件地址
    @RequestMapping(path="/fileAddress",method = RequestMethod.POST)
    @ApiMethod(name = "fileAddress",desc = "根据流水线id查询配置信息")
    public Result<String> fileAddress() {
        String fileAddress = matFlowCommonService.getFileAddress();
        return Result.ok(fileAddress);
    }


    //获取部署文件
    @RequestMapping(path="/getFile",method = RequestMethod.POST)
    @ApiMethod(name = "getFile",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "matFlowName",desc = "流水线名称",required = true)
    public Result<String> getFile(String matFlowName,String regex) {
        String fileAddress = matFlowCommonService.getFile(matFlowName,regex);
        return Result.ok(fileAddress);
    }
}
