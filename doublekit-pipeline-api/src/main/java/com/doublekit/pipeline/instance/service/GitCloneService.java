package com.doublekit.pipeline.instance.service;

import java.io.IOException;

public interface GitCloneService {

    /**
     * 克隆
     * @param pipelineId 流水线id
     */
    int gitClone(String pipelineId) throws Exception;


    String write(String pipelineId) throws Exception;

    int deploy(String pipelineId) throws Exception;

}
