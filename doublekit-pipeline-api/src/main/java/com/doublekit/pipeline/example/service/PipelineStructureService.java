package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;

import java.util.List;
import java.util.Map;

@JoinProvider(model = PipelineStructure.class)
public interface PipelineStructureService {

    /**
     * 创建
     * @param PipelineStructure structure信息
     * @return structureId
     */
    String createStructure(PipelineStructure PipelineStructure);

    Map<String ,String> createDeploy(PipelineConfigure pipelineConfigure);

    /**
     * 删除
     * @param structureId structureId
     */
    void deleteStructure(String structureId);

    //删除部署表
    void deleteDeploy(PipelineConfigure pipelineConfigure);

    /**
     * 更新
     * @param PipelineStructure 更新信息
     */
    void updateStructure(PipelineStructure PipelineStructure);

    //更新部署表
    void updateDeploy(PipelineConfigure pipelineConfigure);

    /**
     * 查询单个信息
     * @param structureId structureId
     * @return structure信息
     */
    @FindOne
    PipelineStructure findOneStructure(String structureId);

    /**
     * 查询所有信息
     * @return structure信息集合
     */
    @FindAll
    List<PipelineStructure> findAllStructure();

    @FindList
    List<PipelineStructure> findAllStructureList(List<String> idList);
}
