package io.tiklab.matflow.task.test.service;


import io.tiklab.teston.repository.model.Repository;
import io.tiklab.teston.testplan.cases.model.TestPlan;
import io.tiklab.teston.testplan.execute.model.TestPlanTestData;

import java.util.List;

public interface TaskTestOnService {

    /**
     * 根据名称查询仓库
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 仓库
     */
    Repository findOneRepository(String authId,String rpyName);


    /**
     * 获取测试计划
     * @param authId 认证id
     * @param planName 测试计划名称
     * @return 测试计划
     */
    TestPlan findOneTestPlan(String authId,String planName);


    /**
     * 获取test仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<Repository> findAllRepository(String authId);


    /**
     * 获取teston测试计划
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 测试计划
     */
    List<TestPlan> findAllTestPlan(String authId, String rpyName);

    /**
     * 获取测试计划环境
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @param env 环境
     * @return 环境信息
     */
    List<Object> findAllEnv(String authId,String rpyName,String env);

    /**
     * 执行测试计划
     * @param authId 认证id
     * @param testPlanTestData 执行信息
     */
    void execTestPlan(String authId, TestPlanTestData testPlanTestData);


    /**
     *
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 仓库地址
     */
    // String findRepository(String authId,String rpyName);



}
