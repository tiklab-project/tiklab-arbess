package io.tiklab.matflow.stages.service;


import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.stages.model.Stage;

import java.util.List;

/**
 * 流水线阶段服务接口
 */
@JoinProvider(model = Stage.class)
public interface StageService {

    /**
     * 创建阶段及关联任务
     * @param stage 阶段信息
     * @return 阶段id
     */
    String createStagesOrTask(Stage stage);

    /**
     * 查询所有阶段任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<Stage> findAllStagesOrTask(String pipelineId);

    /**
     * 删除阶段及任务
     * @param taskId 配置id
     */
    void deleteStagesOrTask(String taskId);

    /**
     *  删除流水线所有阶段
     * @param pipelineId 流水线id
     */
    void  deleteAllStagesOrTask(String pipelineId);

    /**
     * 更新阶段名称
     * @param stage 阶段
     */
    void updateStageName(Stage stage);

    /**
     * 获取所有阶段的根节点
     * @param pipelineId 流水线id
     * @return 主分支
     */
    List<Stage> findAllMainStage(String pipelineId);

    /**
     * 获取指定阶段的根节点
     * @param pipelineId 流水线id
     * @param stages 阶段
     * @return 根节点
     */
    Stage findMainStages(String pipelineId, int stages);

    /**
     * 根据根节点查询从节点
     * @param stagesId 根节点id
     * @return 从节点列表
     */
    List<Stage> findOtherStage(String stagesId);

    /**
     * 更新阶段任务
     * @param stage 更新内容
     */
    void updateStagesTask(Stage stage);


    /**
     * 效验阶段配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validStagesMustField(String pipelineId);

    /**
     * 创建阶段
     * @param stage 阶段信息
     * @return 阶段id
     */
    String createStages(Stage stage);

    /**
     * 更新阶段
     * @param stage 阶段信息
     */
    void updateStages(Stage stage);

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
    Stage findOneStages(String stageId);

    /**
     * 查询所有阶段
     * @return 阶段信息集合
     */
    @FindAll
    List<Stage> findAllStages();

    @FindList
    List<Stage> findAllStagesList(List<String> idList);




}



























