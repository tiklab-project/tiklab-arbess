package io.tiklab.arbess.task.test.service;

import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineRequestUtil;
import io.tiklab.arbess.task.test.model.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.arbess.task.test.model.*;
import org.apache.commons.lang3.StringUtils;
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
    public List<String> findTestPlanEnv(String authId,String testPlanId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            throw new ApplicationException("TestOn远程地址不能为空！");
        }
        String requestUrl = serverAddress + "/api/testPlanCase/getCaseTypeNum";
        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.MULTIPART_FORM_DATA, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("testPlanId",testPlanId);
            Map<String,Integer> map = requestUtil.requestPost(headers, requestUrl, paramMap, Map.class);

            return null;
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
    public String execTestPlan(String authId, TestOnPlanTestData testPlanTestData){

        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            throw new ApplicationException("TestOn远程地址不能为空！");
        }

        String requestUrl = serverAddress + "/api/testPlanTestDispatch/execute";
        try {

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);

            return requestUtil.requestPost(headers, requestUrl, testPlanTestData, String.class);

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
    public TestPlanExecResult findPlanExecResult(String authId,String testPlanId){
        String serverAddress = findServerAddress(authId);
        if (Objects.isNull(serverAddress)){
            throw new ApplicationException("TestOn远程地址不能为空！");
        }
        String requestUrl = serverAddress + "/api/testPlanTestDispatch/exeResult";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("testPlanId",testPlanId);
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
    public TestOnPlanInstance findAllTestPlanInstance(String authId, String instanceId){

        String serverAddress = findServerAddress(authId);

        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlanInstance/findTestPlanInstance";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",instanceId);
            TestOnPlanInstance testPlanInstance = requestUtil.requestPost(headers, requestUrl, paramMap, TestOnPlanInstance.class);

            if (Objects.isNull(testPlanInstance)){
                testPlanInstance = new TestOnPlanInstance();
                testPlanInstance.setTestPlanName("测试计划已被删除！");
            }else {
                testPlanInstance.setTestPlanName(testPlanInstance.getTestPlan().getName());
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
