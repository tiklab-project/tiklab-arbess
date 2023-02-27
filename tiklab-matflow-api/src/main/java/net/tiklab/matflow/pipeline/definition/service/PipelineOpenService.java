package net.tiklab.matflow.pipeline.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.pipeline.definition.model.PipelineOpen;

import java.util.List;

@JoinProvider(model = PipelineOpen.class)
public interface PipelineOpenService {


    /**
     * 获取经常打开列表
     * @return 列表
     */
    List<PipelineOpen> findAllOpen(StringBuilder s);


    /**
     * 查询流水线最近打开
     * @param number 查询数量
     * @return 最近打开的流水线
     */
    List<PipelineOpen> findAllOpen(int number);

    /**
     * 删除流水线收藏
     * @param pipelineId 流水线id
     */
    void deleteAllOpen(String pipelineId);

    /**
     * 获取打开的流水线
     * @param userId 用户id
     * @param pipelineId 流水线id
     */
    void updatePipelineOpen(String userId, String pipelineId);

    /**
     * 查询单个次数信息
     * @param openId 次数id
     * @return 次数信息
     */
    @FindOne
    PipelineOpen findOneOpen(String openId);

    /**
     * 查询所有次数
     * @return 次数集合
     */
    @FindAll
    List<PipelineOpen> findAllOpen();

    @FindList
    List<PipelineOpen> findAllOpenList(List<String> idList);

}