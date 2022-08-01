package com.tiklab.matflow.execute.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.execute.model.MatFlowTest;

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
