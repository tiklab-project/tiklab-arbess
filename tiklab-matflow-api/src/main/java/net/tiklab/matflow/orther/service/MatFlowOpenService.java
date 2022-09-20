package net.tiklab.matflow.orther.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.orther.model.MatFlowOpen;

import java.util.List;

@JoinProvider(model = MatFlowOpen.class)
public interface MatFlowOpenService {

    /**
     * 创建次数
     * @param matFlowOpen 次数
     * @return 次数id
     */
    String createOpen(MatFlowOpen matFlowOpen);

    /**
     * 删除次数
     * @param openId 次数id
     */
    void deleteOpen(String openId);

    /**
     * 删除流水线收藏
     * @param matFlowId 流水线id
     */
    void deleteAllOpen(String matFlowId);

    /**
     * 查询流水线打开次数
     * @param userId 用户id
     * @param matFlowId 流水线id
     * @return 信息
     */
    MatFlowOpen findOneOpenNumber(String userId , String matFlowId);

    /**
     * 更新次数
     * @param matFlowOpen 更新信息
     */
    void updateOpen(MatFlowOpen matFlowOpen);

    /**
     * 获取打开的流水线
     * @param userId 用户id
     * @param matFlow 流水线
     */
    void findOpen(String userId, MatFlow matFlow);

    /**
     * 用户经常打开的流水线
     * @param userId 用户id
     * @return 经常打开的流水线
     */
    List<MatFlowOpen> findAllOpen(String userId, StringBuilder s);

    /**
     * 查询单个次数信息
     * @param openId 次数id
     * @return 次数信息
     */
    @FindOne
    MatFlowOpen findOneOpen(String openId);

    /**
     * 查询所有次数
     * @return 次数集合
     */
    @FindAll
    List<MatFlowOpen> findAllOpen();

    @FindList
    List<MatFlowOpen> findAllOpenList(List<String> idList);

}
