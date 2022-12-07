package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;

import java.util.List;

/**
 * 流水线流程设计配置
 */
@JoinProvider(model = PipelineCourseConfig.class)
public interface PipelineCourseConfigService {

    /**
     * 删除流水线配置
     * @param configId 流水线id
     */
    void deleteConfig(String configId);

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    void deleteAllConfig(String pipelineId);

    /**
     * 创建流水线模板
     * @param pipelineId 流水线id
     * @param template 模板类型
     */
    void createTemplate(String pipelineId, String template);

    /**
     * 按顺序返回配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    List<Object> findAllConfig(String pipelineId);

    /**
     * 查询一个阶段下的所有配置
     * @param stagesId 阶段id
     * @return 配置集合
     */
    List<Object> findAllStagesConfig(String stagesId);

    /**
     * 查询一个阶段下的所有配置
     * @param stagesId 阶段id
     * @return 配置
     */
    List<PipelineCourseConfig> findAllStagesCourseConfig(String stagesId);

    /**
     * 流水线配置
     * @param config 流水线配置信息
     */
    void updateConfig(PipelineCourseConfig config);

    /**
     * 创建流水线配置及关联任务
     * @param config 配置信息
     * @return 配置id
     */
    String createConfig(PipelineCourseConfig config);

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 效验结果
     */
    List<String> configValid(String pipelineId);

    /**
     * 更改顺序
     * @param config 配置
     */
    void updateOrderConfig(PipelineCourseConfig config);

    /**
     * 根据流水线id查询流水线配置顺序
     * @param pipelineId 流水线id
     * @return 配置顺序
     */
    List<PipelineCourseConfig> findAllCourseConfig(String pipelineId);

    /**
     * 查询单个配置
     * @param configId 配置id
     * @return 配置
     */
    @FindOne
    PipelineCourseConfig findOneCourseConfig(String configId);

    /**
     * 查询所有配置
     * @return 配置
     */
    @FindAll
    List<PipelineCourseConfig> findAllCourseConfig();

    @FindList
    List<PipelineCourseConfig> findAllCourseConfigList(List<String> idList);


}
