package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.setting.proof.model.Proof;

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
     void updateDeploy(PipelineDeploy pipelineDeploy) ;

    /**
     * 查询单个信息
     * @param deployId pipelineDeployId
     * @return deploy信息
     */
    @FindOne
    PipelineDeploy findOneDeploy(String deployId) ;

    /**
     * 查询凭证
     * @param pipelineConfigure 配置信息
     * @return 凭证信息
     */
    Proof findOneProof(PipelineConfigure pipelineConfigure);

    /**
     * 查询所有信息
     * @return deploy信息集合
     */
    @FindAll
     List<PipelineDeploy> findAllDeploy() ;

    @FindList
    List<PipelineDeploy> findAllDeployList(List<String> idList);
}