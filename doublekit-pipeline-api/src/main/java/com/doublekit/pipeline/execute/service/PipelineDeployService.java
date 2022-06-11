package com.doublekit.pipeline.execute.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.execute.model.PipelineDeploy;

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
     * 创建配置信息
     * @param pipelineId 流水线id
     * @return 配置id
     */
    String createConfigure(String pipelineId,int taskType, PipelineDeploy pipelineDeploy);

    /**
     * 删除
     * @param deployId deployId
     */
     void deleteDeploy(String deployId) ;

    /**
     * 删除关联配置
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteTask(String taskId,int taskType);

    /**
     * 更新
     * @param pipelineDeploy 更新信息
     */
     void updateDeploy(PipelineDeploy pipelineDeploy) ;

    /**
     * 更新任务
     * @param pipelineExecConfigure 更新信息
     */
    void updateTask(PipelineExecConfigure pipelineExecConfigure);

    /**
     * 查询单个信息
     * @param deployId pipelineDeployId
     * @return deploy信息
     */
    @FindOne
    PipelineDeploy findOneDeploy(String deployId) ;

    /**
     * 查询信息
     * @param pipelineConfigure 配置信息
     * @return 配置
     */
    List<Object> findOneTask( PipelineConfigure pipelineConfigure ,List<Object> list);

    /**
     * 查询所有信息
     * @return deploy信息集合
     */
    @FindAll
     List<PipelineDeploy> findAllDeploy() ;

    @FindList
    List<PipelineDeploy> findAllDeployList(List<String> idList);

}
