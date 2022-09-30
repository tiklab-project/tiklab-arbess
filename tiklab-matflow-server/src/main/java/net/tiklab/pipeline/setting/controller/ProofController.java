package net.tiklab.pipeline.setting.controller;


import net.tiklab.core.Result;
import net.tiklab.pipeline.setting.model.Proof;
import net.tiklab.pipeline.setting.service.ProofService;
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


    @RequestMapping(path="/findPipelineProof",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineProof",desc = "查询流水线凭证")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<Proof>> findPipelineProof(@NotNull String userId, String pipelineId, int type){

        List<Proof> pipelineProof = proofService.findPipelineProof(userId,pipelineId,type);

        return Result.ok(pipelineProof);
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
