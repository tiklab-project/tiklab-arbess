package io.tiklab.arbess.pipeline.definition.service;


import io.tiklab.arbess.pipeline.definition.model.PipelineOpen;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpenQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 流水线最近打开服务接口
 */
@JoinProvider(model = PipelineOpen.class)
public interface PipelineOpenService {


    /**
     * 删除流水线收藏
     * @param pipelineId 流水线id
     */
    void deleteAllOpen(String pipelineId);

    /**
     * 获取打开的流水线
     * @param pipelineId 流水线id
     */
    void updatePipelineOpen(String pipelineId);

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

    /**
     * 根据查询条件查询流水线打开信息
     * @param query 查询条件
     * @return 流水线打开信息集合
     */
    List<PipelineOpen> findOpenByQuery(PipelineOpenQuery query);

    /**
     * 根据ID列表批量查询流水线打开信息
     * @param idList 流水线ID列表
     * @return 流水线打开信息列表
     */
    @FindList
    List<PipelineOpen> findOpenList(List<String> idList);

    /**
     * 根据查询条件批量查询流水线打开信息
     * @param query 查询条件
     * @return 流水线打开信息列表
     */
    List<PipelineOpen> findOpenList(PipelineOpenQuery query);

    /**
     * 分页查询流水线打开信息
     * @param query 查询条件
     * @return 流水线打开信息分页
     */
    Pagination<PipelineOpen> findOpenPage(PipelineOpenQuery query);


}













