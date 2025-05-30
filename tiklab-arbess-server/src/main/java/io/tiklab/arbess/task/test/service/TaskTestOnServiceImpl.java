package io.tiklab.arbess.task.test.service;

import io.tiklab.arbess.agent.util.PipelineUtil;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineRequestUtil;
import io.tiklab.arbess.task.test.model.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
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
    public List<TestHuboRpy> findRepositoryList(TestHuboRpyQuery rpyQuery){
        String serverAddress = findServerAddress(rpyQuery.getAuthId());
        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/repository/findAllRepository";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken", PipelineUtil.findTiklabToken());
            return requestUtil.requestPostList(headers, requestUrl, rpyQuery, TestHuboRpy.class);

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
    public Pagination<TestHuboRpy> findRepositoryPage(TestHuboRpyQuery rpyQuery) {
        String serverAddress = findServerAddress(rpyQuery.getAuthId());
        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/repository/findAllRepository";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            // MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            return requestUtil.requestPostPage(headers, requestUrl, rpyQuery, TestHuboRpy.class);

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
    public TestHuboRpy findRepository(String authId, String rpyId){
        String serverAddress = findServerAddress(authId);
        String requestUrl = serverAddress + "/api/repository/findRepository";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",rpyId);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestHuboRpy.class);
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
    public TestHuboTestPlan findTestPlan(String authId, String planId){
        String serverAddress = findServerAddress(authId);
        String requestUrl = serverAddress + "/api/testPlan/findTestPlan";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",planId);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestHuboTestPlan.class);
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
    public List<TestHuboTestPlan> findTestPlanList(TestHuboTestPlanQuery testPlanQuery){

        String serverAddress = findServerAddress(testPlanQuery.getAuthId());

        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlan/findTestPlanList";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            return requestUtil.requestPostList(headers, requestUrl, testPlanQuery, TestHuboTestPlan.class);

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
    public Pagination<TestHuboTestPlan> findTestPlanPage(TestHuboTestPlanQuery testPlanQuery) {
        String serverAddress = findServerAddress(testPlanQuery.getAuthId());

        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlan/findTestPlanList";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            return requestUtil.requestPostPage(headers, requestUrl, testPlanQuery, TestHuboTestPlan.class);

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
    public TestHuboEnv findEnv(String authId, String envId) {
        String serverAddress = findServerAddress(authId);
        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }

        String requestUrl = serverAddress + "/api/commonEnv/findCommonEnv";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("id",envId);
            return requestUtil.requestPost(headers, requestUrl, paramMap, TestHuboEnv.class);
        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throwable.printStackTrace();
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }


    @Override
    public List<TestHuboEnv> findEnvList(TestHuboEnvQuery envQuery){
        String serverAddress = findServerAddress(envQuery.getAuthId());
        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }

        String requestUrl = serverAddress + "/api/commonEnv/findCommonEnvList";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            return requestUtil.requestPostList(headers, requestUrl, envQuery, TestHuboEnv.class);
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
    public Pagination<TestHuboEnv> findEnvPage(TestHuboEnvQuery envQuery){
        String serverAddress = findServerAddress(envQuery.getAuthId());
        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }

        String requestUrl = serverAddress + "/api/commonEnv/findCommonEnvList";
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
            return requestUtil.requestPostPage(headers, requestUrl, envQuery, TestHuboEnv.class);
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
            headers.add("accessToken",PipelineUtil.findTiklabToken());
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
            headers.add("accessToken",PipelineUtil.findTiklabToken());
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
    public TestOnPlanInstance findTestPlanInstance(String authId, String instanceId){

        String serverAddress = findServerAddress(authId);

        if (StringUtils.isEmpty(serverAddress)){
            return null;
        }
        String requestUrl = serverAddress + "/api/testPlanInstance/findTestPlanInstance";

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, null);
            headers.add("accessToken",PipelineUtil.findTiklabToken());
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


}
