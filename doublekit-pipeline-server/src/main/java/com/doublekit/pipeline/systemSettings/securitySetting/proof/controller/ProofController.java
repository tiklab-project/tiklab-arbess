package com.doublekit.pipeline.systemSettings.securitySetting.proof.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    ProofService proofService;

    //添加
    @RequestMapping(path="/createProof",method = RequestMethod.POST)
    @ApiMethod(name = "createProof",desc = "添加凭证")
    @ApiParam(name = "proof",desc = "proofController",required = true)
    public Result<String> createProof(@RequestBody @NotNull @Valid Proof proof){

        String proofId = proofService.createProof(proof);

        return Result.ok(proofId);
    }

    //删除
    @RequestMapping(path="/deleteProof",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProof",desc = "删除凭证")
    @ApiParam(name = "proof",desc = "proofController",required = true)
    public Result<Void> deleteProof(String id){

        proofService.deleteProof(id);

        return Result.ok();
    }

    //修改
    @RequestMapping(path="/updateProof",method = RequestMethod.POST)
    @ApiMethod(name = "updateProof",desc = "修改凭证")
    @ApiParam(name = "proof",desc = "proofController",required = true)
    public Result<Void> updateProof(@RequestBody @NotNull @Valid Proof proof){

        proofService.updateProof(proof);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/selectProof",method = RequestMethod.POST)
    @ApiMethod(name = "selectProof",desc = "查询凭证")
    @ApiParam(name = "proof",desc = "proofController",required = true)
    public Result<Proof> selectProof(String id){

        Proof proof = proofService.selectProof(id);

        return Result.ok(proof);
    }

    //查询所有
    @RequestMapping(path="/selectAllProof",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllProof",desc = "查询所有凭证")
    @ApiParam(name = "proof",desc = "proofController",required = true)
    public Result<List<Proof>> selectAllProof(){

        List<Proof> proofList = proofService.selectAllProof();

        return Result.ok(proofList);
    }
}
