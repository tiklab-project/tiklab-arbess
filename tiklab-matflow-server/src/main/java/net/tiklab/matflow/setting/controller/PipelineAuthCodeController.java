package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuthCode;
import net.tiklab.matflow.setting.service.PipelineAuthCodeServer;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/authCode")
@Api(name = "PipelineAuthCodeController",desc = "认证")
public class PipelineAuthCodeController {

    @Autowired
    PipelineAuthCodeServer authCodeService;


    @RequestMapping(path="/createAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthCode",desc = "创建")
    @ApiParam(name = "pipelineAuthCode",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthCode(@RequestBody @Valid PipelineAuthCode pipelineAuthCode) {
        String pipelineAuthCodeId = authCodeService.createAuthCode(pipelineAuthCode);
        return Result.ok(pipelineAuthCodeId);
    }

    //删除
    @RequestMapping(path="/deleteAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthCode",desc = "删除")
    @ApiParam(name = "authCodeId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthCode(@NotNull @Valid String codeId) {
        authCodeService.deleteAuthCode(codeId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthCode",desc = "更新")
    @ApiParam(name = "pipelineAuthCode",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthCode(@RequestBody @NotNull @Valid PipelineAuthCode pipelineAuthCode) {
        authCodeService.updateAuthCode(pipelineAuthCode);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOneAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthCode",desc = "查询")
    @ApiParam(name = "authCodeId",desc = "配置id",required = true)
    public Result<PipelineAuthCode> findPipelineAuthCode(@NotNull @Valid String codeId) {
        PipelineAuthCode pipelineAuthCode = authCodeService.findOneAuthCode(codeId);
        return Result.ok(pipelineAuthCode);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuthCode",desc = "查询所有")
    public Result<List<PipelineAuthCode>> findAllPipelineAuthCode() {
        List<PipelineAuthCode> allPipelineAuthCode = authCodeService.findAllAuthCode();
        return Result.ok(allPipelineAuthCode);
    }

    @RequestMapping(path="/findAllAuthCodeList",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthCodeList",desc = "查询所有")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<List<PipelineAuthCode>> findAllAuthCodeList(int type) {
        List<PipelineAuthCode> allPipelineAuthCode = authCodeService.findAllAuthCodeList(type);
        return Result.ok(allPipelineAuthCode);
    }
    
    
}


















