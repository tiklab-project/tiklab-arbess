package net.tiklab.matflow.task.server;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.model.PipelineDeploy;

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
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteDeployConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    PipelineDeploy findOneDeployConfig(String configId);

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
