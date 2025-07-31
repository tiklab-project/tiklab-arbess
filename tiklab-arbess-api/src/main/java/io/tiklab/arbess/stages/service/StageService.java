package io.tiklab.arbess.stages.service;


import io.tiklab.arbess.stages.model.StageGroup;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.stages.model.Stage;

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
     * 创建阶段组及关联任务
     * @param stageGroup 阶段信息
     * @return 阶段id
     */
    void createStagesGroupOrTask(StageGroup stageGroup);


    /**
     * 创建阶段模板
     * @param pipelineId 流水线id
     * @param template 模板
     */
    void createStageTemplate(String pipelineId,String[] template);


    /**
     * 克隆阶段任务
     * @param pipelineId 流水线id
     */
    void cloneStage(String pipelineId,String clonePipelineId);

    /**
     * 查询所有阶段任务以及任务详情
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<Stage> findAllStagesOrTask(String pipelineId);

    /**
     * 查询所有阶段任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<Stage> findAllStagesTask(String pipelineId);

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



    /**
     * 根据ID列表批量查询阶段
     * @param idList 阶段ID列表
     * @return 阶段列表
     */
    @FindList
    List<Stage> findAllStagesList(List<String> idList);




}



























