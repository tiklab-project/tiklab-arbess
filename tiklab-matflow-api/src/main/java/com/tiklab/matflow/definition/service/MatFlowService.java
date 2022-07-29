package com.tiklab.matflow.definition.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowStatus;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.user.user.model.DmUser;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线
 */
@JoinProvider(model = MatFlow.class)
public interface MatFlowService {

    /**
     * 创建流水线
     * @param matFlow 流水线信息
     * @return 流水线id
     */
    String createMatFlow(@NotNull @Valid MatFlow matFlow);

    /**
     * 删除流水线
     * @param matFlowId 流水线id
     */
    Integer deleteMatFlow(@NotNull String matFlowId,String userId);

    /**
     * 更新流水线
     * @param matFlow 更新后流水线信息
     */
    int updateMatFlow(@NotNull @Valid MatFlow matFlow);

    /**
     * 查询单个流水线
     * @param matFlowId 流水线id
     * @return 流水线信息
     */
    @FindOne
    MatFlow findMatFlow(@NotNull String matFlowId);

    /**
     * 根据流水线id获取配置信息
     * @param matFlowId 流水线id
     * @return 配置信息
     */
    List<MatFlowConfigure> findMatFlowConfigure(String matFlowId);

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    @FindAll
    List<MatFlow> findAllMatFlow();

    @FindList
    List<MatFlow> findAllMatFlowList(List<String> idList);


    List<MatFlow> findUserMatFlow(String userId);


    StringBuilder findUserMatFlowId(String userId);

    /**
     * 根据名称模糊查询
     * @param matFlowName 查询条件
     * @return 查询到的集合
     */
    List<MatFlow> findLike(String matFlowName, String userId);


    /**
     *查询流水线状态
     * @param allMatFlow 用户id
     * @return 状态集合
     */
    List<MatFlowStatus> findAllStatus(List<MatFlow> allMatFlow);

    /**
     * 获取用户7天内的执行历史
     * @param userId 用户id
     * @return 历史信息
     */
    List<MatFlowExecHistory> findAllUserHistory(String userId);

    /**
     * 获取拥有此流水线的用户
     * @param MatFlowId 流水线id
     * @return 用户信息
     */
    List<DmUser> findMatFlowUser(String MatFlowId);
}
