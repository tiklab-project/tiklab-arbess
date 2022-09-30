package net.tiklab.pipeline.orther.service;

import net.tiklab.pipeline.orther.model.PipelineActivityQuery;
import net.tiklab.pipeline.orther.model.PipelineFollow;

public interface PipelineHomeService {


    /**
     * 收藏流水线
     * @param pipelineFollow 收藏信息
     */
    String updateFollow(PipelineFollow pipelineFollow);

    /**
     * 查询动态
     * @param pipelineActivityQuery 信息
     * @return 动态信息
     */
    PipelineActivityQuery findUserActivity(PipelineActivityQuery pipelineActivityQuery);




}
