package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.model.PipelineLog;

import java.io.IOException;

public interface GitCloneService {

    /**
     * 克隆
     * @param pipelineId 流水线id
     */
    int gitClone(String pipelineId,String logId) throws Exception;


    int write(String pipelineId ,String logId) throws Exception;

    int deploy(String pipelineId,String logId) throws Exception;

    PipelineLog selectStructureState() throws InterruptedException;

    String pipelineStructure(String pipelineId) throws Exception;

}
