package net.tiklab.pipeline.definition.service;

import net.tiklab.pipeline.definition.model.PipelineCode;

public interface PipelineCodeAuthorizeService {

    /**
     * 更新源码地址
     * @param pipelineCode 源码信息
     * @return 源码信息
     */
    PipelineCode getAuthorizeUrl(PipelineCode pipelineCode);


}
