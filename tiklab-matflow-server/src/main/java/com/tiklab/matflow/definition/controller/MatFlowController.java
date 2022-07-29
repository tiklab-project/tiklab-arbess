package com.tiklab.matflow.definition.controller;



import com.tiklab.core.Result;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.service.MatFlowService;
import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import com.tiklab.user.user.model.DmUser;
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
@RequestMapping("/matFlow")
@Api(name = "MatFlowController",desc = "流水线")
public class MatFlowController {

    private static Logger logger = LoggerFactory.getLogger(MatFlowController.class);

    @Autowired
    MatFlowService matFlowService;

    //创建
    @RequestMapping(path="/createMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "createMatFlow",desc = "创建流水线")
    @ApiParam(name = "matFlow",desc = "matFlow",required = true)
    public Result<String> createMatFlow(@RequestBody @NotNull @Valid MatFlow matFlow){

        String matFlowId = matFlowService.createMatFlow(matFlow);

        return Result.ok(matFlowId);
    }

    //查询所有
    @RequestMapping(path="/findAllMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "findAllMatFlow",desc = "查询所有流水线")
    public Result<List<MatFlow>> findAllMatFlow(){

        List<MatFlow> selectAllMatFlow = matFlowService.findAllMatFlow();

        return Result.ok(selectAllMatFlow);
    }

    //删除
    @RequestMapping(path="/deleteMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "deleteMatFlow",desc = "删除流水线")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<Integer> deleteMatFlow(@NotNull String matFlowId,String userId){

        Integer integer = matFlowService.deleteMatFlow(matFlowId, userId);

        return Result.ok(integer);
    }

    //查询
    @RequestMapping(path="/findOneMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "selectMatFlow",desc = "查询单个流水线")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<MatFlow> findOneMatFlow(@NotNull String matFlowId){

        MatFlow matFlow = matFlowService.findMatFlow(matFlowId);

        return Result.ok(matFlow);
    }

    //更新
    @RequestMapping(path="/updateMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "updateMatFlow",desc = "更新流水线")
    @ApiParam(name = "matFlow",desc = "matFlow",required = true)
    public Result<String> updateMatFlow(@RequestBody @NotNull @Valid MatFlow matFlow){

        int i = matFlowService.updateMatFlow(matFlow);

        return Result.ok(i);
    }

    //模糊查询
    @RequestMapping(path="/findOneName",method = RequestMethod.POST)
    @ApiMethod(name = "findOneName",desc = "模糊查询")
    @ApiParam(name = "matFlowName",desc = "模糊查询条件",required = true)
    public Result<List<MatFlow>> findOneName(@NotNull String matFlowName, String userId){

        List<MatFlow> matFlowQueryList = matFlowService.findLike(matFlowName,userId);

        return Result.ok(matFlowQueryList);
    }

    @RequestMapping(path="/findUserMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "findUserMatFlow",desc = "查询用户流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<MatFlow>> findUserMatFlow(@NotNull String userId){

        List<MatFlow> matFlowQueryList = matFlowService.findUserMatFlow(userId);

        return Result.ok(matFlowQueryList);
    }


    @RequestMapping(path="/findMatFlowUser",method = RequestMethod.POST)
    @ApiMethod(name = "findMatFlowUser",desc = "查询此拥有流水线的用户")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<List<DmUser>> findMatFlowUser(@NotNull String matFlowId){

        List<DmUser> dmUser = matFlowService.findMatFlowUser(matFlowId);

        return Result.ok(dmUser);
    }
}
