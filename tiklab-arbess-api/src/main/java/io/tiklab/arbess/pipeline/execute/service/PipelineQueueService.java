package io.tiklab.arbess.pipeline.execute.service;

import io.tiklab.arbess.pipeline.execute.model.PipelineQueue;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueueQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 队列接口
 */
@JoinProvider(model = PipelineQueue.class)
public interface PipelineQueueService {
    
    /**
     * 创建队列
     * @param pipelineQueue 队列信息
     * @return ID
     */
    String createPipelineQueue(PipelineQueue pipelineQueue) ;

    /**
     * 更新队列
     * @param pipelineQueue 队列信息
     */
    void updatePipelineQueue(PipelineQueue pipelineQueue);


    /**
     * 删除队列
     * @param id 队列ID
     */
    void deletePipelineQueue(String id);

    /**
     * 查询流水线队列
     * @param id 队列ID
     * @return 队列
     */
    @FindOne
    PipelineQueue findPipelineQueue(String id);

    /**
     * 查询流水线队列
     * @param pipelineQueueQuery 条件
     * @return 队列
     */
    PipelineQueue findExecPipelineQueue(PipelineQueueQuery pipelineQueueQuery);

    /**
     * 条件查询环流水线队列
     * @param pipelineQueueQuery 条件
     * @return 队列列表
     */
    @FindList
    List<PipelineQueue> findPipelineQueueList(PipelineQueueQuery pipelineQueueQuery) ;

    /**
     * 查询所有队列
     * @return 队列列表
     */
    @FindAll
    List<PipelineQueue> findAllPipelineQueue() ;

    /**
     * 分页条件查询环流水线队列
     * @param pipelineQueueQuery 条件
     * @return 队列列表
     */
    Pagination<PipelineQueue> findPipelineQueuePage(PipelineQueueQuery pipelineQueueQuery);




}
