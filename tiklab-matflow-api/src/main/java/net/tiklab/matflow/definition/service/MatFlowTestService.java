package net.tiklab.matflow.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.MatFlowTest;

import java.util.List;

@JoinProvider(model = MatFlowTest.class)
public interface MatFlowTestService {
    /**
     * 创建
     * @param matFlowTest test信息
     * @return testId
     */
    String createTest(MatFlowTest matFlowTest);

    /**
     * 删除
     * @param testId testId
     */
    void deleteTest(String testId);

    /**
     * 更新
     * @param matFlowTest 更新信息
     */
    void updateTest(MatFlowTest matFlowTest);

    /**
     * 查询单个信息
     * @param testId testId
     * @return test信息
     */
    @FindOne
    MatFlowTest findOneTest(String testId);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<MatFlowTest> findAllTest();

    @FindList
    List<MatFlowTest> findAllTestList(List<String> idList);
}
