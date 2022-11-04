package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuthCodeScan;
import net.tiklab.matflow.setting.service.PipelineAuthCodeScanServer;
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
@RequestMapping("/authCodeScan")
@Api(name = "PipelineAuthCodeScanController",desc = "其他认证")
public class PipelineAuthCodeScanController {

    @Autowired
    PipelineAuthCodeScanServer authCodeScanService;


    @RequestMapping(path="/createAuthCodeScan",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthCodeScan",desc = "创建")
    @ApiParam(name = "pipelineAuthCodeScan",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthCodeScan(@RequestBody @Valid PipelineAuthCodeScan pipelineAuthCodeScan) {
        String pipelineAuthCodeScanId = authCodeScanService.createAuthCodeScan(pipelineAuthCodeScan);
        return Result.ok(pipelineAuthCodeScanId);
    }

    //删除
    @RequestMapping(path="/deleteAuthCodeScan",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthCodeScan",desc = "删除")
    @ApiParam(name = "authCodeScanId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthCodeScan(@NotNull @Valid String codeScanId) {
        authCodeScanService.deleteAuthCodeScan(codeScanId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthCodeScan",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthCodeScan",desc = "更新")
    @ApiParam(name = "pipelineAuthCodeScan",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthCodeScan(@RequestBody @NotNull @Valid PipelineAuthCodeScan pipelineAuthCodeScan) {
        authCodeScanService.updateAuthCodeScan(pipelineAuthCodeScan);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findAuthCodeScan",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthCodeScan",desc = "查询")
    @ApiParam(name = "authCodeScanId",desc = "配置id",required = true)
    public Result<PipelineAuthCodeScan> findPipelineAuthCodeScan(@NotNull @Valid String codeScanId) {
        PipelineAuthCodeScan pipelineAuthCodeScan = authCodeScanService.findOneAuthCodeScan(codeScanId);
        return Result.ok(pipelineAuthCodeScan);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthCodeScan",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuthCodeScan",desc = "查询所有")
    public Result<List<PipelineAuthCodeScan>> findAllPipelineAuthCodeScan() {
        List<PipelineAuthCodeScan> allPipelineAuthCodeScan = authCodeScanService.findAllAuthCodeScan();
        return Result.ok(allPipelineAuthCodeScan);
    }


}
