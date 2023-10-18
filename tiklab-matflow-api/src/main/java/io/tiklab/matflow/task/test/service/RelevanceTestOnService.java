package io.tiklab.matflow.task.test.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.task.test.model.RelevanceTestOn;
import io.tiklab.matflow.task.test.model.RelevanceTestOnQuery;

import java.util.List;

@JoinProvider(model = RelevanceTestOn.class)
public interface RelevanceTestOnService {


    void createRelevance(String pipelineId,String instanceId,String authId);


    /**
     * 创建
     * @param relevanceTestOn 关联关系
     * @return 关联关系id
     */
    String createRelevance(RelevanceTestOn relevanceTestOn);

    /**
     * 删除
     * @param relevanceId 关联id
     */
    void deleteRelevance(String relevanceId);


    /**
     * 根据配置id删除任务
     * @param pipelineId 流水线id
     */
    void deleteAllRelevance(String pipelineId);

    /**
     * 获取流水线关联的testOn关系
     * @param pipelineId 流水线id
     * @return 关联关系
     */
    List<RelevanceTestOn> findAllRelevance(String pipelineId);


    /**
     * 获取流水线关联的testOn关系
     * @param relevanceTestOnQuery 条件
     * @return 关联关系
     */
    Pagination<RelevanceTestOn> findAllRelevancePage(RelevanceTestOnQuery relevanceTestOnQuery);


    /**
     * 查询单个信息
     * @param relevanceId 关联id
     * @return 关联关系
     */
    @FindOne
    RelevanceTestOn findOneRelevance(String relevanceId);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<RelevanceTestOn> findAllRelevance();

    @FindList
    List<RelevanceTestOn> findAllRelevanceList(List<String> idList);







}
