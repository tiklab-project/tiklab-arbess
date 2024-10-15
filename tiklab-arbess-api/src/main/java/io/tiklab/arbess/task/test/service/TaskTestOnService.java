package io.tiklab.arbess.task.test.service;


import io.tiklab.arbess.task.test.model.*;
import io.tiklab.arbess.task.test.model.*;

import java.util.List;

public interface TaskTestOnService {

    /**
     * 根据名称查询仓库
     * @param authId 认证id
     * @param rpyId 仓库id
     * @return 仓库
     */
    TestOnRepository findOneRepository(String authId, String rpyId);


    /**
     * 获取测试计划
     * @param authId 认证id
     * @param planId 测试计划id
     * @return 测试计划
     */
    TestOnTestPlan findOneTestPlan(String authId, String planId);


    /**
     * 获取test仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<TestOnRepository> findAllRepository(String authId);


    /**
     * 获取teston测试计划
     * @param authId 认证id
     * @param rpyId 仓库id
     * @return 测试计划
     */
    List<TestOnTestPlan> findAllTestPlan(String authId, String rpyId);

    /**
     * 获取测试计划环境
     * @param authId 认证id
     * @param rpyId 仓库id
     * @param env 环境
     * @return 环境信息
     */
    List<Object> findAllEnv(String authId,String rpyId,String env);

    /**
     * 获取测试集合执行需要的环境
     * @param authId teston环境地址
     * @param testPlanId 测试计划ID
     * @return 需要的环境
     */
    List<String> findTestPlanEnv(String authId,String testPlanId);

    /**
     * 获取api环境地址
     * @param authId teston环境地址
     * @param id id
     * @return api环境
     */
    TestOnApiEnv findOneTestOnApiEnv(String authId, String id);

    /**
     * 获取app环境地址
     * @param authId teston环境地址
     * @param id id
     * @return app环境
     */
    TestOnAppEnv findOneTestOnAppEnv(String authId, String id);

    /**
     * 获取web环境地址
     * @param authId teston环境地址
     * @param id id
     * @return web环境
     */
    TestOnWebEnv findOneTestOnWebEnv(String authId, String id);


    /**
     * 执行测试计划
     * @param authId 认证id
     * @param testPlanTestData 执行信息
     */
    String execTestPlan(String authId, TestOnPlanTestData testPlanTestData);


    /**
     * 获取测试结果详情
     * @param authId 认证id
     * @return 测试结果
     */
    List<TestOnPlanCaseInstance> findTestPlanExecResult(String authId, String instanceId);


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
    TestOnPlanInstance findAllTestPlanInstance(String authId, String instanceId);




}
