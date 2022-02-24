package com.doublekit.pipeline.instance.service;




public interface PipelineCloneService {

    /**
     * 克隆代码
     */
    void  gitClone(String pipelineId) throws Exception;

}
