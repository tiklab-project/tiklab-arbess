package com.tiklab.matflow.setting.proof.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.service.ProofService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
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


    @RequestMapping(path="/findMatFlowProof",method = RequestMethod.POST)
    @ApiMethod(name = "findMatFlowProof",desc = "查询流水线凭证")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<Proof>> findMatFlowProof(@NotNull String userId,String matFlowId,int type){

        List<Proof> matFlowProof = proofService.findMatFlowProof(userId,matFlowId,type);

        return Result.ok(matFlowProof);
    }

    //修改
    @RequestMapping(path="/updateProof",method = RequestMethod.POST)
    @ApiMethod(name = "updateProof",desc = "修改凭证")
    @ApiParam(name = "proof",desc = "proof",required = true)
    public Result<Void> updateProof(@RequestBody @NotNull @Valid Proof proof){

        proofService.updateProof(proof);

        return Result.ok();
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
