package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import java.util.List;

/**
 * 流水线配置
 */
@JoinProvider(model = PipelineConfigOrder.class)
public interface PipelineConfigOrderService {

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    void deleteConfig(String pipelineId);

    /**
     * 创建流水线模板
     * @param pipelineId 流水线id
     * @param type 模板类型
     */
    void createTemplate(String pipelineId, int type);

    /**
     * 按顺序返回配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    List<Object> findAllConfig(String pipelineId);

    /**
     * 流水线配置
     * @param config 流水线配置信息
     */
    void updateConfig(PipelineConfigOrder config);

    /**
     * 根据流水线id查询流水线配置顺序
     * @param pipelineId 流水线id
     * @return 配置顺序
     */
    List<PipelineConfigOrder> findAllPipelineConfig(String pipelineId);

    /**
     * 获取单个配置详情
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置详情
     */
    PipelineConfigOrder findOneConfig(String pipelineId,int type);

    /**
     * 查询单个配置
     * @param configId 配置id
     * @return 配置
     */
    @FindOne
    PipelineConfigOrder findOneConfig(String configId);

    /**
     * 查询所有配置
     * @return 配置
     */
    @FindAll
    List<PipelineConfigOrder> findAllConfigOrder();

    @FindList
    List<PipelineConfigOrder> findAllConfigOrderList(List<String> idList);


}
