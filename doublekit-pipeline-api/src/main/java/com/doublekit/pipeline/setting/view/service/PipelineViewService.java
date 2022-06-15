package com.doublekit.pipeline.setting.view.service;

import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.setting.view.model.PipelineView;

import java.util.List;

@JoinProvider(model = PipelineView.class)
public interface PipelineViewService {

    /**
     * 创建视图
     * @param pipelineView 视图
     * @return 视图id
     */
    String createView(PipelineView pipelineView);

    /**
     * 删除视图
     * @param viewId 视图id
     */
    void deleteView(String viewId);

    /**
     * 更新视图
     * @param pipelineView 更新信息
     */
    void updateView(PipelineView pipelineView);

    /**
     * 查询单个视图信息
     * @param viewId 视图id
     * @return 视图信息
     */
    PipelineView findOneView(String viewId);

    /**
     * 查询所有视图
     * @return 视图集合
     */
    List<PipelineView> findAllView();

}
