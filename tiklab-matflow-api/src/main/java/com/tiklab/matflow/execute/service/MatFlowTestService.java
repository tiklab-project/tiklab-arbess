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
     * 创建配置
     * @param matFlowId 流水线id
     * @return 配置id
     */
    String createConfigure(String matFlowId, MatFlowTest matFlowTest);


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
     * @param matFlowTest 更新信息
     */
    void updateTest(MatFlowTest matFlowTest);

    /**
     * 更新任务
     * @param matFlowExecConfigure 更新信息
     */
    void updateTask(MatFlowExecConfigure matFlowExecConfigure);

    /**
     * 查询单个信息
     * @param testId testId
     * @return test信息
     */
    @FindOne
    MatFlowTest findOneTest(String testId);

    /**
     * 查询信息
     * @param matFlowConfigure 配置信息
     * @return 配置
     */
    List<Object>  findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<MatFlowTest> findAllTest();

    @FindList
    List<MatFlowTest> findAllTestList(List<String> idList);
}
