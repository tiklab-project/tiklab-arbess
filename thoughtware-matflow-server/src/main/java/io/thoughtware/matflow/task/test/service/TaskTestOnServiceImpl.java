package io.thoughtware.matflow.task.test.service;

import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.util.PipelineRequestUtil;
import io.thoughtware.matflow.task.test.model.*;
import io.thoughtware.core.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.*;

@Service
public class TaskTestOnServiceImpl implements TaskTestOnService {

    private static final Logger logger = LoggerFactory.getLogger(TaskTestOnServiceImpl.class);

    @Autowired
    AuthThirdService authThirdService;

    @Autowired
    PipelineRequestUtil requestUtil;


    private String findServerAddress(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }
        return authServer.getServerAddress();
    }

    @Override
    public List<TestOnRepository> findAllRepository(String authId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/repository/findAllRepository";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            return requestUtil.requestPostList(headers, requestUrl, paramMap, TestOnRepository.class);

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
    public TestOnRepository findOneRepository(String authId, String rpyId){
        String serverAddress = findServerAddress(authId);
        String requestUrl = serverAddress + "/api/repository/findRepository";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",rpyId);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestOnRepository.class);
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
        String requestUrl = serverAddress + "/api/testPlan/findTestPlan";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",planId);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestOnTestPlan.class);
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
    public List<TestOnTestPlan> findAllTestPlan(String authId,String rpyId){

        String serverAddress = findServerAddress(authId);

        if (Objects.isNull(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlan/findTestPlanList";

        try {
            TestOnPlanQuery testPlanQuery = new TestOnPlanQuery();
            testPlanQuery.setRepositoryId(rpyId);
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);

            return requestUtil.requestPostList(headers, requestUrl, testPlanQuery, TestOnTestPlan.class);

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
    public List<Object> findAllEnv(String authId,String rpyId,String env){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            return null;
        }

        List<Object> list = new ArrayList<>();

        HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);

        try {
            switch (env){
                case "api" ->{
                    TestOnApiEnvQuery apiEnvQuery = new TestOnApiEnvQuery();
                    apiEnvQuery.setRepositoryId(rpyId);
                    String requestUrl = serverAddress + "/api/apxEnv/findApiEnvList";
                    List<TestOnApiEnv> apiEnvList =
                            requestUtil.requestPostList(headers, requestUrl, apiEnvQuery, TestOnApiEnv.class);
                    list.addAll(apiEnvList);
                    return list;
                }
                case "app" ->{
                    TestOnAppEnvQuery appEnvQuery = new TestOnAppEnvQuery();
                    appEnvQuery.setRepositoryId(rpyId);
                    String requestUrl = serverAddress + "/api/appEnv/findAppEnvList";
                    List<TestOnAppEnv> appEnvList =
                            requestUtil.requestPostList(headers, requestUrl, appEnvQuery, TestOnAppEnv.class);
                    list.addAll(appEnvList);
                    return list;
                }
                case "web" ->{
                    TestOnWebEnvQuery webEnvQuery = new TestOnWebEnvQuery();
                    webEnvQuery.setRepositoryId(rpyId);
                    String requestUrl = serverAddress + "/api/webEnv/findWebEnvList";
                    List<TestOnWebEnv> webEnvList =
                            requestUtil.requestPostList(headers, requestUrl, webEnvQuery, TestOnWebEnv.class);
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

        String requestUrl = serverAddress + "/api/testPlanTestDispatch/execute";
        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);

            requestUtil.requestPostList(headers, requestUrl, testPlanTestData, null);

        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
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
        String requestUrl = serverAddress + "/api/testPlanTestDispatch/exeResult";

        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestPlanExecResult.class);

        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestOnPlanInstance findAllTestPlanInstance(String authId,String instanceId){

        String serverAddress = findServerAddress(authId);

        if (serverAddress == null){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlanTestDispatch/exeResult";

        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            TestPlanExecResult testPlanExecResult =
                    requestUtil.requestPost(headers, requestUrl, paramMap, TestPlanExecResult.class);

            if (Objects.isNull(testPlanExecResult)){
                return null;
            }
            TestOnPlanInstance testPlanInstance = testPlanExecResult.getTestPlanInstance();
            String testPlanId = testPlanInstance.getTestPlanId();
            TestOnTestPlan testPlan = findOneTestPlan(authId, testPlanId);
            if (!Objects.isNull(testPlan)){
                testPlanInstance.setTestPlanName(testPlan.getName());
            }else {
                testPlanInstance.setTestPlanName("测试计划已被删除！");
            }
            testPlanInstance.setUrl(serverAddress);

            return testPlanInstance;
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
        String requestUrl = serverAddress + "/api/testPlanCaseInstanceBind/findTestPlanCaseInstanceBindList";

        TestOnPlanCaseInstanceBindQuery testPlanCaseInstanceBindQuery = new TestOnPlanCaseInstanceBindQuery();
        testPlanCaseInstanceBindQuery.setTestPlanInstanceId(instanceId);
        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            return requestUtil.requestPostList(headers, requestUrl, testPlanCaseInstanceBindQuery, TestOnPlanCaseInstance.class);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestOnApiEnv findOneTestOnApiEnv(String authId,String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }

        String requestUrl = serverAddress + "/api/apxEnv/findApiEnv";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",id);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestOnApiEnv.class);
        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestOnAppEnv findOneTestOnAppEnv(String authId, String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }

        String requestUrl = serverAddress + "/api/appEnv/findAppEnv";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",id);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestOnAppEnv.class);

        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public TestOnWebEnv findOneTestOnWebEnv(String authId, String id){
        String serverAddress = findServerAddress(authId);
        if (serverAddress == null){
            return null;
        }

        String requestUrl = serverAddress + "/api/webEnv/findWebEnvEnv";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",id);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestOnWebEnv.class);

        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

}
