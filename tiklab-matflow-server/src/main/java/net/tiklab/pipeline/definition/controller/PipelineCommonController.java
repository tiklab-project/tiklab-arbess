package net.tiklab.pipeline.definition.controller;


import net.tiklab.core.Result;
import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelineCommon")
@Api(name = "PipelineCommonController",desc = "流水线文件信息")
public class PipelineCommonController {

    @Autowired
    PipelineFileService pipelineFileService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCommonController.class);

    //获取文件地址
    @RequestMapping(path="/fileAddress",method = RequestMethod.POST)
    @ApiMethod(name = "fileAddress",desc = "根据流水线id查询配置信息")
    public Result<String> fileAddress() {
        String fileAddress = pipelineFileService.getFileAddress();
        return Result.ok(fileAddress);
    }


    //获取部署文件
    @RequestMapping(path="/getFile",method = RequestMethod.POST)
    @ApiMethod(name = "getFile",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineName",desc = "流水线名称",required = true)
    public Result<String> getFile(String pipelineName,String regex) {
        String fileAddress = pipelineFileService.getFile(pipelineName,regex);
        return Result.ok(fileAddress);
    }
}
