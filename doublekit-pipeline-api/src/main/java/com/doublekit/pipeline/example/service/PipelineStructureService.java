package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.example.model.PipelineStructure;

import java.util.List;

@JoinProvider(model = PipelineStructure.class)
public interface PipelineStructureService {

    /**
     * 创建
     * @param PipelineStructure structure信息
     * @return structureId
     */
    String createStructure(PipelineStructure PipelineStructure);

    /**
     * 创建配置信息
     * @param pipelineId 流水线id
     * @return 配置id
     */
    String createConfigure(String pipelineId,int taskType,PipelineStructure pipelineStructure );

    /**
     * 删除
     * @param structureId structureId
     */
    void deleteStructure(String structureId);

    /**
     * 删除关联配置
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteTask(String taskId,int taskType);

    /**
     * 更新
     * @param PipelineStructure 更新信息
     */
    void updateStructure(PipelineStructure PipelineStructure);

    /**
     * 更新任务
     * @param map 更新信息
     */
    void updateTask(PipelineExecConfigure pipelineExecConfigure);

    /**
     * 查询单个信息
     * @param structureId structureId
     * @return structure信息
     */
    @FindOne
    PipelineStructure findOneStructure(String structureId);

    /**
     * 查询信息
     * @param pipelineConfigure 配置信息
     * @return 配置
     */
    List<Object>  findOneTask(PipelineConfigure pipelineConfigure,List<Object> list);

    /**
     * 查询所有信息
     * @return structure信息集合
     */
    @FindAll
    List<PipelineStructure> findAllStructure();

    @FindList
    List<PipelineStructure> findAllStructureList(List<String> idList);
}
