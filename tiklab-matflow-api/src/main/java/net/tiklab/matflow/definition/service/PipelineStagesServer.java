package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineStages;

import java.util.List;

@JoinProvider(model = PipelineStages.class)
public interface PipelineStagesServer {


    /**
     * 创建阶段
     * @param stages 阶段信息
     * @return 阶段id
     */
    String createStages(PipelineStages stages);

    /**
     * 更新阶段
     * @param stages 阶段信息
     */
    void updateStages(PipelineStages stages);

    /**
     * 删除阶段
     * @param stageId 阶段id
     */
    void deleteStages(String stageId);

    /**
     * 查询单个阶段
     * @param stageId 阶段id
     * @return 阶段信息
     */
    @FindOne
    PipelineStages findOneStages(String stageId);

    /**
     * 查询所有阶段
     * @return 阶段信息集合
     */
    @FindAll
    List<PipelineStages> findAllStages();

    @FindList
    List<PipelineStages> findAllStagesList(List<String> idList);




}



























