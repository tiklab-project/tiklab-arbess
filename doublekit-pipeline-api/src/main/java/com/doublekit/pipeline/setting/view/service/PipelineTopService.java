package com.doublekit.pipeline.setting.view.service;

import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.setting.view.model.PipelineTop;

import java.util.List;

@JoinProvider(model = PipelineTop.class)
public interface PipelineTopService {

    /**
     * 创建视图
     * @param pipelineTop 视图
     * @return 视图id
     */
     String createTop(PipelineTop pipelineTop);

    /**
     * 删除视图
     * @param topId 视图id
     */
     void deleteTop(String topId);

    /**
     * 更新视图
     * @param pipelineTop 更新信息
     */
     void updateTop(PipelineTop pipelineTop);

    /**
     * 查询单个视图信息
     * @param topId 视图id
     * @return 视图信息
     */
     PipelineTop findOneTop(String topId);

    /**
     * 查询视图id绑定的流水线
     * @param viewId 视图id
     * @return 绑定集合
     */
    List<PipelineTop>  findAllViewTop(String viewId);

    /**
     * 查询所有视图
     * @return 视图集合
     */
     List<PipelineTop> findAllTop();

}
