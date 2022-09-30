package net.tiklab.pipeline.orther.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.orther.model.PipelineOpen;

import java.util.List;

@JoinProvider(model = PipelineOpen.class)
public interface PipelineOpenService {


    /**
     * 获取经常打开列表
     * @param userId 用户id
     * @return 列表
     */
    List<PipelineOpen> findAllOpen(String userId);

    /**
     * 删除流水线收藏
     * @param pipelineId 流水线id
     */
    void deleteAllOpen(String pipelineId);

    /**
     * 获取打开的流水线
     * @param userId 用户id
     * @param pipeline 流水线
     */
    void findOpen(String userId, Pipeline pipeline);

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
