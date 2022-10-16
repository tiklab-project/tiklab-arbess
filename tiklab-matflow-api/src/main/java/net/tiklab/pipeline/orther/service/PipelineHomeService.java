package net.tiklab.pipeline.orther.service;

import net.tiklab.pipeline.orther.model.PipelineActivityQuery;
import net.tiklab.pipeline.orther.model.PipelineFollow;

public interface PipelineHomeService {


    /**
     * 收藏流水线
     * @param pipelineFollow 收藏信息
     */
    String updateFollow(PipelineFollow pipelineFollow);





}
