package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineConfig;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.definition.model.PipelineStagesTask;

import java.util.List;

@JoinProvider(model = PipelineStages.class)
public interface PipelineStagesServer {


    /**
     * 创建阶段及关联任务
     * @param config 阶段信息
     * @return 阶段id
     */
    String createStagesTask(PipelineConfig config);


    /**
     * 查询所有阶段任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<PipelineStages> findAllStagesStageTask(String pipelineId);


    /**
     * 更新阶段名称
     * @param stageId 阶段id
     * @param stageName 名称
     */
    void updateStageName(String stageId,String stageName);


    /**
     * 删除阶段任务
     * @param configId 配置id
     */
    void deleteStagesTask(String configId);

    /**
     * 更新配置及任务
     * @param config 配置id
     */
    void updateStagesTask(PipelineConfig config);

    /**
     *  删除流水线所有阶段
     * @param pipelineId 流水线id
     */
    void  deleteAllStagesTask(String pipelineId);

    /**
     * 根据阶段id查询所有任务配置
     * @param stagesId 阶段id
     * @return 任务配置
     */
    List<PipelineStagesTask> findAllStagesTask(String stagesId);


    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validAllConfig(String pipelineId);


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



























