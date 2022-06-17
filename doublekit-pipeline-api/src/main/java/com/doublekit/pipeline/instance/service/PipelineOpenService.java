package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.instance.model.PipelineOpen;

import java.util.List;

@JoinProvider(model = PipelineOpen.class)
public interface PipelineOpenService {

    /**
     * 创建次数
     * @param pipelineOpen 次数
     * @return 次数id
     */
    String createOpen(PipelineOpen pipelineOpen);

    /**
     * 删除次数
     * @param openId 次数id
     */
    void deleteOpen(String openId);

    /**
     * 删除流水线收藏
     * @param pipelineId 流水线id
     */
    void deleteAllOpen(String pipelineId);

    /**
     * 查询流水线打开次数
     * @param userId 用户id
     * @param pipelineId 流水线id
     * @return 信息
     */
    PipelineOpen findOneOpenNumber(String userId , String pipelineId);

    /**
     * 更新次数
     * @param pipelineOpen 更新信息
     */
    void updateOpen(PipelineOpen pipelineOpen);

    /**
     * 获取打开的流水线
     * @param userId 用户id
     * @param pipeline 流水线
     */
    void findOpen(String userId, Pipeline pipeline);

    /**
     * 用户经常打开的流水线
     * @param userId 用户id
     * @return 经常打开的流水线
     */
    List<PipelineOpen> findAllOpen(String userId);

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
