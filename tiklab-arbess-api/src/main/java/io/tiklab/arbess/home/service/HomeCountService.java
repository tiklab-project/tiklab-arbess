package io.tiklab.arbess.home.service;

import java.util.Map;

public interface HomeCountService {

    /**
     * 获取统计信息
     * @return 统计信息
     */
    Map<String,Object> findCount();


    /**
     * 获取流水线统计信息
     * @param pipelineId 流水线ID
     * @return 统计信息
     */
    Map<String,Object> findPipelineCount(String pipelineId);


    /**
     * 获取流水线任务统计信息
     * @param pipelineId 流水线ID
     * @return 统计信息
     */
    Map<String,Object> findTaskCount(String pipelineId,String taskId);

}