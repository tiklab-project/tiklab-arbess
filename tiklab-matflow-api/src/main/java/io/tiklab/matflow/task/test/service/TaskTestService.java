package io.tiklab.matflow.task.test.service;

import io.tiklab.matflow.task.test.model.TaskTest;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;

import java.util.List;
/**
 * 任务测试服务接口
 */
@JoinProvider(model = TaskTest.class)
public interface TaskTestService {
    /**
     * 创建
     * @param taskTest test信息
     * @return testId
     */
    String createTest(TaskTest taskTest);

    /**
     * 删除
     * @param testId testId
     */
    void deleteTest(String testId);


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteTestConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskTest findOneTestConfig(String configId);

    /**
     * 更新
     * @param taskTest 更新信息
     */
    void updateTest(TaskTest taskTest);

    /**
     * 查询单个信息
     * @param testId testId
     * @return test信息
     */
    @FindOne
    TaskTest findOneTest(String testId);

    /**
     * 查询所有信息
     * @return test信息集合
     */
    @FindAll
    List<TaskTest> findAllTest();

    @FindList
    List<TaskTest> findAllTestList(List<String> idList);
}
