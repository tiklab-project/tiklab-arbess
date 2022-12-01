package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineAfterConfig;

import java.util.List;

@JoinProvider(model = PipelineAfterConfig.class)
public interface PipelineAfterConfigServer {


    /**
     * 创建
     * @param pipelineAfterConfig message信息
     * @return messageId
     */
    String createAfterConfig(PipelineAfterConfig pipelineAfterConfig) ;


    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<Object> findAllConfig(String pipelineId);

    /**
     * 删除
     * @param afterConfigId messageId
     */
    void deleteAfterConfig(String afterConfigId) ;


    /**
     * 更新信息
     * @param pipelineAfterConfig 信息
     */
    void updateAfterConfig(PipelineAfterConfig pipelineAfterConfig);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    PipelineAfterConfig findOneAfterConfig(String messageId);



    /**
     * 根据流水线id查询后置配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<PipelineAfterConfig> findAllAfterConfig(String pipelineId);

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<PipelineAfterConfig> findAllAfterConfig() ;

    @FindList
    List<PipelineAfterConfig> findAllAfterConfigList(List<String> idList);
    
}
