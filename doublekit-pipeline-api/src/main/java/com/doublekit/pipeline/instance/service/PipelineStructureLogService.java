package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineStructureLog;

import java.util.List;
import java.util.Map;

public interface PipelineStructureLogService {

    /**
     * 创建
     * @param pipelineStructureLog structureLog信息
     * @return structureLogId
     */
    String createStructureLog(PipelineStructureLog pipelineStructureLog);

    /**
     * 删除
     * @param structureLogId structureLogId
     */
    void deleteStructureLog(String structureLogId);

    //删除
    void deleteStructureLog(PipelineExecLog pipelineExecLog);

    /**
     * 更新
     * @param pipelineStructureLog 更新信息
     */
    void updateStructureLog(PipelineStructureLog pipelineStructureLog);

    /**
     * 查询单个信息
     * @param structureLogId structureLogId
     * @return structureLog信息
     */
    @FindOne
    PipelineStructureLog findOneStructureLog(String structureLogId);

    /**
     * 查询所有信息
     * @return structureLog信息集合
     */
    @FindAll
    List<PipelineStructureLog> findAllStructureLog();

    @FindList
    List<PipelineStructureLog> findAllStructureLogList(List<String> idList);
}
