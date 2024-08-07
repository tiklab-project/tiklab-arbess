package io.thoughtware.matflow.task.test.service;

import io.thoughtware.matflow.task.test.model.TaskTest;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

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
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskTest findTestBuAuth(String configId);

    /**
     * 测试是否有效
     * @param taskType 任务类型
     * @param object object
     * @return 是否有效
     */
    Boolean testValid(String taskType,Object object);

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
