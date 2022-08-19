package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.definition.model.MatFlowStatus;
import com.tiklab.matflow.instance.model.MatFlowActionQuery;
import com.tiklab.matflow.instance.model.MatFlowExecState;
import com.tiklab.matflow.instance.model.MatFlowFollow;
import com.tiklab.matflow.instance.model.MatFlowOpen;
import com.tiklab.matflow.setting.proof.model.Proof;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MatFlowHomeService {

    /**
     * 获取经常打开列表
     * @param userId 用户id
     * @return 列表
     */
    List<MatFlowOpen> findAllOpen(String userId);

    /**
     * 获取用户所有收藏
     * @param userId 用户id
     * @return 收藏列表
     */
    List<MatFlowStatus> findAllFollow(String userId);


    /**
     * 收藏流水线
     * @param matFlowFollow 收藏信息
     */
    String updateFollow(MatFlowFollow matFlowFollow);

    /**
     * 查询用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    List<MatFlowStatus> findUserMatFlow(String userId);

    /**
     * 获取用户7天内的构建状态
     * @param userId 用户id
     * @return 构建状态
     */
    List<MatFlowExecState> runState(String userId);

    /**
     * 查询动态
     * @param matFlowActionQuery 信息
     * @return 动态信息
     */
    MatFlowActionQuery findUserAction(MatFlowActionQuery matFlowActionQuery);


    /**
     * 获取用户凭证
     * @param userId 用户id
     * @param matFlowId 流水线id
     * @param type 类型
     * @return 凭证信息
     */
    List<Proof> findMatFlowProof(@NotNull String userId, String matFlowId, int type);


}
