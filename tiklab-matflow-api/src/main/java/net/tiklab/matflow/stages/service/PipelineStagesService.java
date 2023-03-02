package net.tiklab.matflow.stages.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.stages.model.PipelineStages;

import java.util.List;

/**
 * 流水线阶段服务接口
 */
@JoinProvider(model = PipelineStages.class)
public interface PipelineStagesService {


    /**
     * 创建阶段及关联任务
     * @param tasks 阶段信息
     * @return 阶段id
     */
    String createStagesOrTask(Tasks tasks);

    /**
     * 查询所有阶段任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<PipelineStages> findAllStagesOrTask(String pipelineId);

    /**
     * 删除阶段及任务
     * @param configId 配置id
     */
    void deleteStagesOrTask(String configId);

    /**
     *  删除流水线所有阶段
     * @param pipelineId 流水线id
     */
    void  deleteAllStagesOrTask(String pipelineId);

    /**
     * 更新阶段名称
     * @param stageId 阶段id
     * @param stageName 名称
     */
    void updateStageName(String stageId,String stageName);

    /**
     * 获取所有阶段的根节点
     * @param pipelineId 流水线id
     * @return 主分支
     */
    List<PipelineStages> findAllMainStage(String pipelineId);

    /**
     * 获取指定阶段的根节点
     * @param pipelineId 流水线id
     * @param stages 阶段
     * @return 根节点
     */
    PipelineStages findMainStages(String pipelineId,int stages);

    /**
     * 根据根节点查询从节点
     * @param stagesId 根节点id
     * @return 从节点列表
     */
    List<PipelineStages> findOtherStage(String stagesId);

    /**
     * 更新阶段任务
     * @param tasks 配置
     */
    void updateStagesTask(Tasks tasks);


    /**
     * 效验阶段配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validStagesMustField(String pipelineId);

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



























