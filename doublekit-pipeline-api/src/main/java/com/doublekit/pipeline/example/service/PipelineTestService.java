package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.model.PipelineTest;

import java.util.List;
import java.util.Map;

@JoinProvider(model = PipelineTest.class)
public interface PipelineTestService {
    /**
     * 创建
     * @param pipelineTest test信息
     * @return testId
     */
    String createTest(PipelineTest pipelineTest);

    /**
     * 创建配置
     * @param pipelineId 流水线id
     * @return 配置id
     */
    String createConfigure(String pipelineId,int taskType, PipelineTest pipelineTest);


    /**
     * 删除
     * @param testId testId
     */
    void deleteTest(String testId);

    /**
     * 删除关联配置
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteTask(String taskId,int taskType);

    /**
     * 更新
     * @param pipelineTest 更新信息
     */
    void updateTest(PipelineTest pipelineTest);

    /**
     * 更新任务
     * @param map 更新信息
     */
    void updateTask(Map<String,Object> map);

    /**
     * 查询单个信息
     * @param testId testId
     * @return test信息
     */
    @FindOne
    PipelineTest findOneTest(String testId);

    /**
     * 查询信息
     * @param pipelineConfigure 配置信息
     * @return 配置
     */
    List<Object>  findOneTask(PipelineConfigure pipelineConfigure,List<Object> list);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<PipelineTest> findAllTest();

    @FindList
    List<PipelineTest> findAllTestList(List<String> idList);
}
