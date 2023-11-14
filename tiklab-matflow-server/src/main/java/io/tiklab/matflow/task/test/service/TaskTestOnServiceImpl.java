package io.tiklab.matflow.task.test.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.task.test.model.*;
import io.tiklab.rpc.client.RpcClient;
import io.tiklab.rpc.client.config.RpcClientConfig;
import io.tiklab.rpc.client.router.lookup.FixedLookup;
import io.tiklab.teston.repository.model.Repository;
import io.tiklab.teston.repository.service.RepositoryService;
import io.tiklab.teston.support.environment.model.*;
import io.tiklab.teston.support.environment.service.ApiEnvService;
import io.tiklab.teston.support.environment.service.AppEnvService;
import io.tiklab.teston.support.environment.service.WebEnvService;
import io.tiklab.teston.testplan.cases.model.TestPlan;
import io.tiklab.teston.testplan.cases.model.TestPlanQuery;
import io.tiklab.teston.testplan.cases.service.TestPlanService;
import io.tiklab.teston.testplan.execute.model.TestPlanTestData;
import io.tiklab.teston.testplan.execute.model.TestPlanTestResponse;
import io.tiklab.teston.testplan.execute.service.TestPlanExecuteDispatchService;
import io.tiklab.teston.testplan.instance.model.TestPlanCaseInstanceBind;
import io.tiklab.teston.testplan.instance.model.TestPlanCaseInstanceBindQuery;
import io.tiklab.teston.testplan.instance.model.TestPlanInstance;
import io.tiklab.teston.testplan.instance.service.TestPlanCaseInstanceBindService;
import io.tiklab.teston.testplan.instance.service.TestPlanInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TaskTestOnServiceImpl implements TaskTestOnService {

    private static final Logger logger = LoggerFactory.getLogger(TaskTestOnServiceImpl.class);

    @Autowired
    private AuthThirdService authThirdService;


    RpcClient rpcClient(){
        RpcClientConfig rpcClientConfig = RpcClientConfig.instance();
        return new RpcClient(rpcClientConfig);
    }

    // @Bean
    private RepositoryService repositoryServer(String testOnAddress){
        return rpcClient().getBean(RepositoryService.class, new FixedLookup(testOnAddress));
    }

    private TestPlanService testPlanService(String testOnAddress){
        return rpcClient().getBean(TestPlanService.class, new FixedLookup(testOnAddress));
    }

    private ApiEnvService apiEnvService(String testOnAddress){
        return rpcClient().getBean(ApiEnvService.class, new FixedLookup(testOnAddress));
    }

    private  AppEnvService appEnvService(String testOnAddress){
        return rpcClient().getBean(AppEnvService.class, new FixedLookup(testOnAddress));
    }

    private WebEnvService webEnvService(String testOnAddress){
        return rpcClient().getBean(WebEnvService.class, new FixedLookup(testOnAddress));
    }

    private TestPlanCaseInstanceBindService testPlanCaseInstanceBindService(String testOnAddress){
        return rpcClient().getBean(TestPlanCaseInstanceBindService.class, new FixedLookup(testOnAddress));
    }

    private TestPlanExecuteDispatchService testPlanExecuteService(String testOnAddress){
        return rpcClient().getBean(TestPlanExecuteDispatchService.class, new FixedLookup(testOnAddress));
    }

    private TestPlanInstanceService testPlanInstanceService(String testOnAddress){
        return rpcClient().getBean(TestPlanInstanceService.class, new FixedLookup(testOnAddress));
    }

    private String findServerAddress(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }
        return authServer.getServerAddress();
    }


    @Override
    public TestOnRepository findOneRepository(String authId, String rpyId){
        String serverAddress = findServerAddress(authId);
        try {
            Repository repository = repositoryServer(serverAddress).findOne(rpyId);
            return bindTestOnRepository(repository);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestOnTestPlan findOneTestPlan(String authId, String planId){
        String serverAddress = findServerAddress(authId);
        try {
            TestPlan testPlan = testPlanService(serverAddress).findOne(planId);
            return bindTestOnTestPlan(testPlan);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public List<TestOnRepository> findAllRepository(String authId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            return null;
        }
        List<Repository> allRepository;
        try {
            allRepository = repositoryServer(serverAddress).findAllRepository();
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (allRepository == null || allRepository.isEmpty()){
            return Collections.emptyList();
        }

        List<TestOnRepository> list = new ArrayList<>();
        for (Repository repository : allRepository) {
            list.add(bindTestOnRepository(repository));
        }
        return list;
    }

    @Override
    public List<TestOnTestPlan> findAllTestPlan(String authId,String rpyId){

        String serverAddress = findServerAddress(authId);

        if (Objects.isNull(serverAddress)){
            return null;
        }

        TestOnRepository repository = findOneRepository(authId,rpyId);
        List<TestPlan> testPlanList;
        try {
            TestPlanQuery testPlanQuery = new TestPlanQuery();
            testPlanQuery.setRepositoryId(repository.getId());
            testPlanList = testPlanService(serverAddress).findTestPlanList(testPlanQuery);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (testPlanList == null || testPlanList.isEmpty()){
            return Collections.emptyList();
        }
        List<TestOnTestPlan> list = new ArrayList<>();

        for (TestPlan testPlan : testPlanList) {
            list.add(bindTestOnTestPlan(testPlan));
        }
        return list;
    }

    @Override
    public List<Object> findAllEnv(String authId,String rpyId,String env){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            return null;
        }
        TestOnRepository repository = findOneRepository(authId,rpyId);
        String repositoryId = repository.getId();
        List<Object> list = new ArrayList<>();
        try {
            switch (env){
                case "api" ->{
                    ApiEnvQuery apiEnvQuery = new ApiEnvQuery();
                    apiEnvQuery.setRepositoryId(repositoryId);
                    List<ApiEnv> apiEnvList = apiEnvService(serverAddress).findApiEnvList(apiEnvQuery);
                    list.addAll(apiEnvList);
                    return list;
                }
                case "app" ->{
                    AppEnvQuery appEnvQuery = new AppEnvQuery();
                    appEnvQuery.setRepositoryId(repositoryId);
                    List<AppEnv> appEnvList = appEnvService(serverAddress).findAppEnvList(appEnvQuery);
                    list.addAll(appEnvList);
                    return list;
                }
                case "web" ->{
                    WebEnvQuery webEnvQuery = new WebEnvQuery();
                    webEnvQuery.setRepositoryId(repositoryId);
                    List<WebEnv> webEnvList = webEnvService(serverAddress).findWebEnvList(webEnvQuery);
                    list.addAll(webEnvList);
                    return list;
                }
                default -> throw new ApplicationException("未知的类型："+env);
            }
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public void execTestPlan(String authId, TestOnPlanTestData testPlanTestData){

        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            throw new ApplicationException("TestOn远程地址不能为空！");
        }

        TestPlanTestData testData = bindTestPlanTestData(testPlanTestData);

        try {
            testPlanExecuteService(serverAddress).execute(testData);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestPlanExecResult findTestPlanExecResult(String authId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            throw new ApplicationException("TestOn远程地址不能为空！");
        }
        TestPlanTestResponse testPlanTestResponse;
        try {
            testPlanTestResponse = testPlanExecuteService(serverAddress).exeResult();
        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (Objects.isNull(testPlanTestResponse)){
            throw new ApplicationException("获取测试结果为空！");
        }

        return bindTestPlanExecResult(testPlanTestResponse);
    }

    @Override
    public TestOnPlanInstance findAllTestPlanInstance(String authId,String instanceId){

        String serverAddress = findServerAddress(authId);

        if (serverAddress == null){
            return null;
        }

        try {
            TestPlanInstance testPlanInstance = testPlanInstanceService(serverAddress).findOne(instanceId);
            if (Objects.isNull(testPlanInstance)){
                return null;
            }
            String testPlanId = testPlanInstance.getTestPlanId();
            TestOnPlanInstance testOnPlanInstance = bindTestOnPlanInstance(testPlanInstance);
            TestPlan testPlan = testPlanService(serverAddress).findOne(testPlanId);

            if (!Objects.isNull(testPlan)){
                testOnPlanInstance.setTestPlanName(testPlan.getName());
            }else {
                testOnPlanInstance.setTestPlanName("测试计划已被删除！");
            }
            testOnPlanInstance.setUrl(serverAddress);
            return testOnPlanInstance;
        }catch (Throwable throwable){
            return null;
        }
    }

    @Override
    public List<TestOnPlanCaseInstance> findTestPlanExecResult(String authId,String instanceId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            return Collections.emptyList();
        }
        TestPlanCaseInstanceBindQuery testPlanCaseInstanceBindQuery = new TestPlanCaseInstanceBindQuery();
        testPlanCaseInstanceBindQuery.setTestPlanInstanceId(instanceId);
        List<TestPlanCaseInstanceBind> instanceBindList;
        try {
             instanceBindList = testPlanCaseInstanceBindService(serverAddress)
                    .findTestPlanCaseInstanceBindList(testPlanCaseInstanceBindQuery);

        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (instanceBindList== null || instanceBindList.isEmpty()){
            return Collections.emptyList();
        }
        List<TestOnPlanCaseInstance> list = new ArrayList<>();
        for (TestPlanCaseInstanceBind testPlanCaseInstanceBind : instanceBindList) {
            list.add(bindTestOnPlanCaseInstance(testPlanCaseInstanceBind));
        }
        return list;
    }

    @Override
    public TestOnApiEnv findOneTestOnApiEnv(String authId,String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }
        ApiEnv apiEnv;
        try {
             apiEnv = apiEnvService(serverAddress).findOne(id);
        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (Objects.isNull(apiEnv)){
            return null;
        }
        TestOnApiEnv testOnApiEnv = new TestOnApiEnv();
        testOnApiEnv.setId(apiEnv.getId());
        testOnApiEnv.setName(apiEnv.getName());
        testOnApiEnv.setUrl(apiEnv.getPreUrl());
        return testOnApiEnv;
    }

    @Override
    public TestOnAppEnv findOneTestOnAppEnv(String authId, String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }
        AppEnv appEnv;
        try {
             appEnv = appEnvService(serverAddress).findOne(id);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }


        if (Objects.isNull(appEnv)){
            return null;
        }
        TestOnAppEnv testOnAppEnv = new TestOnAppEnv();
        testOnAppEnv.setId(appEnv.getId());
        testOnAppEnv.setName(appEnv.getName());
        testOnAppEnv.setUrl(appEnv.getAppiumSever());
        return testOnAppEnv;
    }

    @Override
    public TestOnWebEnv findOneTestOnWebEnv(String authId, String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }
        WebEnv webEnv;
        try {
             webEnv = webEnvService(serverAddress).findOne(id);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }

        if (Objects.isNull(webEnv)){
            return null;
        }
        TestOnWebEnv testOnWebEnv = new TestOnWebEnv();
        testOnWebEnv.setId(webEnv.getId());
        testOnWebEnv.setName(webEnv.getName());
        testOnWebEnv.setUrl(webEnv.getWebDriver());
        return testOnWebEnv;
    }

    private TestOnRepository bindTestOnRepository(Repository repository){
        TestOnRepository testOnRepository = new TestOnRepository();
        testOnRepository.setId(repository.getId());
        testOnRepository.setName(repository.getName());
        return testOnRepository;

    }

    private TestOnTestPlan bindTestOnTestPlan(TestPlan testPlan){
        TestOnTestPlan testOnTestPlan = new TestOnTestPlan();
        testOnTestPlan.setId(testPlan.getId());
        testOnTestPlan.setName(testPlan.getName());
        return testOnTestPlan;
    }

    private TestPlanTestData bindTestPlanTestData(TestOnPlanTestData testPlanTestData){
        TestPlanTestData testData = new TestPlanTestData();
        testData.setRepositoryId(testPlanTestData.getRepositoryId());
        testData.setTestPlanId(testPlanTestData.getTestPlanId());
        testData.setWebEnv(testPlanTestData.getWebEnv());
        testData.setAppEnv(testPlanTestData.getAppEnv());
        testData.setApiEnv(testPlanTestData.getApiEnv());
        return testData;
    }

    private TestOnPlanInstance bindTestOnPlanInstance(TestPlanInstance testPlanInstance){
        TestOnPlanInstance testOnPlanInstance = new TestOnPlanInstance();
        testOnPlanInstance.setId(testPlanInstance.getId());
        testOnPlanInstance.setTestPlanId(testPlanInstance.getTestPlanId());
        testOnPlanInstance.setRepositoryId(testPlanInstance.getRepositoryId());
        testOnPlanInstance.setCreateTime(testPlanInstance.getCreateTime());
        testOnPlanInstance.setCreateUser(testPlanInstance.getCreateUser());
        testOnPlanInstance.setErrorRate(testPlanInstance.getErrorRate());
        testOnPlanInstance.setExecuteNumber(testPlanInstance.getExecuteNumber());
        testOnPlanInstance.setFailNum(testPlanInstance.getFailNum());
        testOnPlanInstance.setPassNum(testPlanInstance.getPassNum());
        testOnPlanInstance.setTotal(testPlanInstance.getTotal());
        testOnPlanInstance.setResult(testPlanInstance.getResult());
        testOnPlanInstance.setPassRate(testPlanInstance.getPassRate());
        return testOnPlanInstance;
    }

    private TestPlanExecResult bindTestPlanExecResult(TestPlanTestResponse testPlanTestResponse){
        TestPlanExecResult testPlanExecResult = new TestPlanExecResult();
        TestPlanInstance testPlanInstance = testPlanTestResponse.getTestPlanInstance();
        testPlanExecResult.setTestPlanInstance(bindTestOnPlanInstance(testPlanInstance));
        testPlanExecResult.setStatus(testPlanTestResponse.getStatus());
        return testPlanExecResult;
    }

    private TestOnPlanCaseInstance bindTestOnPlanCaseInstance(TestPlanCaseInstanceBind testPlanCaseInstanceBind){
        TestOnPlanCaseInstance testOnPlanCaseInstance = new TestOnPlanCaseInstance();
        testOnPlanCaseInstance.setCaseInstanceId(testPlanCaseInstanceBind.getCaseInstanceId());
        testOnPlanCaseInstance.setCaseType(testPlanCaseInstanceBind.getCaseType());
        testOnPlanCaseInstance.setTestType(testPlanCaseInstanceBind.getTestType());
        testOnPlanCaseInstance.setId(testPlanCaseInstanceBind.getId());
        testOnPlanCaseInstance.setName(testPlanCaseInstanceBind.getName());
        testOnPlanCaseInstance.setTestPlanInstanceId(testPlanCaseInstanceBind.getTestPlanInstanceId());
        testOnPlanCaseInstance.setResult(testPlanCaseInstanceBind.getResult());
        return testOnPlanCaseInstance;
    }




}
