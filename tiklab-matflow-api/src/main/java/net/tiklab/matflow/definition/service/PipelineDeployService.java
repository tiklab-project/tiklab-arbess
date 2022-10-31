package net.tiklab.matflow.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineDeploy;

import java.util.List;


@JoinProvider(model = PipelineDeploy.class)
public interface PipelineDeployService {

    /**
     * 创建
     * @param pipelineDeploy deploy信息
     * @return deployId
     */
     String createDeploy(PipelineDeploy pipelineDeploy) ;

    /**
     * 删除
     * @param deployId deployId
     */
     void deleteDeploy(String deployId) ;

    /**
     * 更新
     * @param pipelineDeploy 更新信息
     */
    void updateDeploy(PipelineDeploy pipelineDeploy);

    /**
     * 查询单个信息
     * @param deployId pipelineDeployId
     * @return deploy信息
     */
    @FindOne
    PipelineDeploy findOneDeploy(String deployId) ;

    /**
     * 查询所有信息
     * @return deploy信息集合
     */
    @FindAll
     List<PipelineDeploy> findAllDeploy() ;

    @FindList
    List<PipelineDeploy> findAllDeployList(List<String> idList);

}
