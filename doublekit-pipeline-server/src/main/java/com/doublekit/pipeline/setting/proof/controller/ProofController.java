package com.doublekit.pipeline.setting.proof.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/proof")
@Api(name = "proofController",desc = "凭证")
public class ProofController {

    @Autowired
    ProofService proofService;

    //添加
    @RequestMapping(path="/createProof",method = RequestMethod.POST)
    @ApiMethod(name = "createProof",desc = "添加凭证")
    @ApiParam(name = "proof",desc = "proof",required = true)
    public Result<String> createProof(@RequestBody @NotNull @Valid Proof proof){

        String proofId = proofService.createProof(proof);

        return Result.ok(proofId);
    }

    //删除
    @RequestMapping(path="/deleteProof",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProof",desc = "删除凭证")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<Void> deleteProof(@NotNull String proofId){

        proofService.deleteProof(proofId);

        return Result.ok();
    }

    //修改
    @RequestMapping(path="/updateProof",method = RequestMethod.POST)
    @ApiMethod(name = "updateProof",desc = "修改凭证")
    @ApiParam(name = "proof",desc = "proof",required = true)
    public Result<Void> updateProof(@RequestBody @NotNull @Valid Proof proof){

        proofService.updateProof(proof);

        return Result.ok();
    }

    //查询所有git凭证
    @RequestMapping(path="/findAllGitProof",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStructureProof",desc = "查询所有构建凭证")
    public Result<List<Proof>> findAllGitProof(){

        List<Proof> proofList = proofService.findAllGitProof();

        return Result.ok(proofList);
    }

    //查询所有部署凭证
    @RequestMapping(path="/findAllDeployProof",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDeployProof",desc = "查询所有部署凭证")
    public Result<List<Proof>> findAllDeployProof(){

        List<Proof> proofList = proofService.findAllDeployProof();

        return Result.ok(proofList);
    }

    //查询
    @RequestMapping(path="/findOneProof",method = RequestMethod.POST)
    @ApiMethod(name = "findOneProof",desc = "查询凭证信息")
    @ApiParam(name = "proofId",desc = "凭证id",required = true)
    public Result<Proof> findOneProof(@NotNull String proofId){

        Proof proof = proofService.findOneProof(proofId);

        return Result.ok(proof);
    }

}
