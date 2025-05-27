package io.tiklab.arbess.task.test.service;


import io.tiklab.arbess.task.test.model.*;
import io.tiklab.core.page.Pagination;

import java.util.List;

public interface TaskTestOnService {

    /**
     * 根据名称查询仓库
     * @param authId 认证id
     * @param rpyId 仓库id
     * @return 仓库
     */
    TestHuboRpy findRepository(String authId, String rpyId);

    /**
     * 获取TestHubo仓库
     * @param rpyQuery 认证id
     * @return 仓库
     */
    List<TestHuboRpy> findRepositoryList(TestHuboRpyQuery  rpyQuery);

    /**
     * 获取TestHubo仓库
     * @param rpyQuery 认证id
     * @return 仓库
     */
    Pagination<TestHuboRpy> findRepositoryPage(TestHuboRpyQuery  rpyQuery);


    /**
     * 获取测试计划
     * @param authId 认证id
     * @param planId 测试计划id
     * @return 测试计划
     */
    TestHuboTestPlan findTestPlan(String authId, String planId);

    /**
     * 获取teston测试计划
     * @return 测试计划
     */
    List<TestHuboTestPlan> findTestPlanList(TestHuboTestPlanQuery testPlanQuery);

    /**
     * 获取teston测试计划
     * @return 测试计划
     */
    Pagination<TestHuboTestPlan> findTestPlanPage(TestHuboTestPlanQuery testPlanQuery);


    /**
     * 获取测试计划环境列表
     * @return 环境信息
     */
    TestHuboEnv findEnv(String authId, String envId);

    /**
     * 获取测试计划环境列表
     * @param envQuery 查询条件
     * @return 环境信息
     */
    List<TestHuboEnv> findEnvList(TestHuboEnvQuery envQuery);

    /**
     * 获取测试计划环境分页
     * @param envQuery 查询条件
     * @return 环境信息
     */
    Pagination<TestHuboEnv> findEnvPage(TestHuboEnvQuery envQuery);


    /**
     * 执行测试计划
     * @param authId 认证id
     * @param testPlanTestData 执行信息
     */
    String execTestPlan(String authId, TestOnPlanTestData testPlanTestData);

    /**
     * 获取测试结果
     * @param authId 认证id
     * @return 测试结果
     */
    TestPlanExecResult findPlanExecResult(String authId,String testPlanId);

    /**
     * 查询测试计划的详情
     * @param authId 认证id
     * @param instanceId 实例id
     * @return 测试计划详情
     */
    TestOnPlanInstance findTestPlanInstance(String authId, String instanceId);




}
