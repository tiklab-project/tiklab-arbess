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

    Map<String,String> createStructure(PipelineConfigure pipelineConfigure);

    void deleteStructure(PipelineConfigure pipelineConfigure);

    /**
     * 删除
     * @param testId testId
     */
    void deleteTest(String testId);

    /**
     * 更新
     * @param pipelineTest 更新信息
     */
    void updateTest(PipelineTest pipelineTest);

    void updateStructure(PipelineConfigure pipelineConfigure);

    /**
     * 查询单个信息
     * @param testId testId
     * @return test信息
     */
    @FindOne
    PipelineTest findOneTest(String testId);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<PipelineTest> findAllTest();

    @FindList
    List<PipelineTest> findAllTestList(List<String> idList);
}
