package net.tiklab.pipeline.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.pipeline.definition.model.PipelineDeploy;
import net.tiklab.pipeline.definition.model.PipelineTest;

import java.util.List;

@JoinProvider(model = PipelineTest.class)
public interface PipelineTestService {
    /**
     * 创建
     * @param pipelineTest test信息
     * @return testId
     */
    String createTest(PipelineTest pipelineTest);

    /**
     * 删除
     * @param testId testId
     */
    void deleteTest(String testId);

    /**
     * 根据流水线id查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    PipelineTest findTest(String pipelineId);

    /**
     * 更新
     * @param pipelineTest 更新信息
     * @param pipelineId 流水线id
     */
    void updateTest(PipelineTest pipelineTest, String pipelineId);

    /**
     * 更新
     * @param pipelineTest 更新信息
     */
    void updateTest(PipelineTest pipelineTest);

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
