package net.tiklab.matflow.orther.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.orther.model.PipelineThirdAddress;
import net.tiklab.matflow.orther.service.PipelineThirdAddressService;
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
@RequestMapping("/pipelineThirdAddress")
@Api(name = "PipelineAuthorizeController",desc = "流水线授权")
public class PipelineThirdAddressController {


    @Autowired
    PipelineThirdAddressService authorizeService;


    @RequestMapping(path="/createThirdAddress",method = RequestMethod.POST)
    @ApiMethod(name = "createAuthorize",desc = "创建权限信息")
    @ApiParam(name = "pipelineAuthorize",desc = "权限信息",required = true)
    public Result<String> createAuthorize(@RequestBody @Valid @NotNull PipelineThirdAddress pipelineThirdAddress) {
        String authorizeId = authorizeService.createAuthorize(pipelineThirdAddress);
        return Result.ok(authorizeId);
    }


    @RequestMapping(path="/updateThirdAddress",method = RequestMethod.POST)
    @ApiMethod(name = "updateAuthorize",desc = "更新权限信息")
    @ApiParam(name = "pipelineAuthorize",desc = "权限信息",required = true)
    public Result<Void> updateAuthorize(@RequestBody @Valid @NotNull PipelineThirdAddress pipelineThirdAddress) {
        authorizeService.updateAuthorize(pipelineThirdAddress);
        return Result.ok();
    }

    @RequestMapping(path="/deleteThirdAddress",method = RequestMethod.POST)
    @ApiMethod(name = "deleteAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<Void> deleteAuthorize(@NotNull String authorizeId) {
        authorizeService.deleteAuthorize(authorizeId);
        return Result.ok();
    }

    @RequestMapping(path="/findOneThirdAddress",method = RequestMethod.POST)
    @ApiMethod(name = "findOneAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<PipelineThirdAddress> findOneAuthorize(@NotNull String authorizeId) {
        PipelineThirdAddress oneAuthorize = authorizeService.findOneAuthorize(authorizeId);
        return Result.ok(oneAuthorize);
    }


    @RequestMapping(path="/findAllThirdAddress",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthorize",desc = "删除权限信息")
    @ApiParam(name = "authorizeId",desc = "权限信息",required = true)
    public Result<List<PipelineThirdAddress>> findAllAuthorize() {
        List<PipelineThirdAddress> allAuthorize = authorizeService.findAllAuthorize();
        return Result.ok(allAuthorize);
    }

}


















































