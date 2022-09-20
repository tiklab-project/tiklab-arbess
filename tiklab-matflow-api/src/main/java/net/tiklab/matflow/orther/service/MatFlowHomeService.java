package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.definition.model.MatFlowStatus;
import net.tiklab.matflow.execute.model.MatFlowExecState;
import net.tiklab.matflow.orther.model.MatFlowActivityQuery;
import net.tiklab.matflow.orther.model.MatFlowFollow;
import net.tiklab.matflow.orther.model.MatFlowOpen;
import net.tiklab.matflow.setting.model.Proof;

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
     * @param matFlowActivityQuery 信息
     * @return 动态信息
     */
    MatFlowActivityQuery findUserActivity(MatFlowActivityQuery matFlowActivityQuery);


    /**
     * 获取用户凭证
     * @param userId 用户id
     * @param matFlowId 流水线id
     * @param type 类型
     * @return 凭证信息
     */
    List<Proof> findMatFlowProof(@NotNull String userId, String matFlowId, int type);


}
