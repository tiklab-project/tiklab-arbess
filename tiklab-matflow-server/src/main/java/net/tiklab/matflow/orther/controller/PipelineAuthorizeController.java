package net.tiklab.matflow.orther.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.orther.model.PipelineAuthorize;
import net.tiklab.matflow.orther.service.PipelineAuthorizeService;
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
@RequestMapping("/pipelineAuthorize")
@Api(name = "PipelineAuthorizeController",desc = "流水线授权")
public class PipelineAuthorizeController {


    @Autowired
    PipelineAuthorizeService authorizeService;


    @RequestMapping(path="/createAuthorize",method = RequestMethod.POST)
    @ApiMethod(name = "createAuthorize",desc = "创建权限信息")
    @ApiParam(name = "pipelineAuthorize",desc = "权限信息",required = true)
    public Result<String> createAuthorize(@RequestBody @Valid @NotNull PipelineAuthorize pipelineAuthorize) {
        String authorizeId = authorizeService.createAuthorize(pipelineAuthorize);
        return Result.ok(authorizeId);
    }


    @RequestMapping(path="/updateAuthorize",method = RequestMethod.POST)
    @ApiMethod(name = "updateAuthorize",desc = "更新权限信息")
    @ApiParam(name = "pipelineAuthorize",desc = "权限信息",required = true)
    public Result<Void> updateAuthorize(@RequestBody @Valid @NotNull PipelineAuthorize pipelineAuthorize) {
        authorizeService.updateAuthorize(pipelineAuthorize);
        return Result.ok();
    }

    @RequestMapping(path="/deleteAuthorize",method = RequestMethod.POST)
    @ApiMethod(name = "deleteAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<Void> deleteAuthorize(@NotNull String authorizeId) {
        authorizeService.deleteAuthorize(authorizeId);
        return Result.ok();
    }

    @RequestMapping(path="/findOneAuthorize",method = RequestMethod.POST)
    @ApiMethod(name = "findOneAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<PipelineAuthorize> findOneAuthorize(@NotNull String authorizeId) {
        PipelineAuthorize oneAuthorize = authorizeService.findOneAuthorize(authorizeId);
        return Result.ok(oneAuthorize);
    }


    @RequestMapping(path="/findAllAuthorize",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<List<PipelineAuthorize>> findAllAuthorize() {
        List<PipelineAuthorize> allAuthorize = authorizeService.findAllAuthorize();
        return Result.ok(allAuthorize);
    }

}


















































