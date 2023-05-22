package io.tiklab.matflow.task.test.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.rpc.client.RpcClient;
import io.tiklab.rpc.client.config.RpcClientConfig;
import io.tiklab.rpc.client.router.lookup.FixedLookup;
import io.tiklab.teston.repository.model.Repository;
import io.tiklab.teston.repository.model.RepositoryQuery;
import io.tiklab.teston.repository.service.RepositoryService;
import io.tiklab.teston.support.environment.model.*;
import io.tiklab.teston.support.environment.service.ApiEnvService;
import io.tiklab.teston.support.environment.service.AppEnvService;
import io.tiklab.teston.support.environment.service.WebEnvService;
import io.tiklab.teston.testplan.cases.model.TestPlan;
import io.tiklab.teston.testplan.cases.model.TestPlanQuery;
import io.tiklab.teston.testplan.cases.service.TestPlanService;
import io.tiklab.teston.testplan.execute.model.TestPlanTestData;
import io.tiklab.teston.testplan.execute.service.TestPlanExecuteDispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    RepositoryService repositoryServer(String testOnAddress){
        return rpcClient().getBean(RepositoryService.class, new FixedLookup(testOnAddress));
    }


    TestPlanService testPlanService(String testOnAddress){
        return rpcClient().getBean(TestPlanService.class, new FixedLookup(testOnAddress));
    }

    ApiEnvService apiEnvService(String testOnAddress){
        return rpcClient().getBean(ApiEnvService.class, new FixedLookup(testOnAddress));
    }


    AppEnvService appEnvService(String testOnAddress){
        return rpcClient().getBean(AppEnvService.class, new FixedLookup(testOnAddress));
    }

    WebEnvService webEnvService(String testOnAddress){
        return rpcClient().getBean(WebEnvService.class, new FixedLookup(testOnAddress));
    }

    TestPlanExecuteDispatchService testPlanExecuteService(String testOnAddress){
        return rpcClient().getBean(TestPlanExecuteDispatchService.class, new FixedLookup(testOnAddress));
    }

    public Object findEnvUrl(String authId,String rpyName,String env){
        String serverAddress = findServerAddress(authId);
        Repository repository = findOneRepository(authId,rpyName);
        String repositoryId = repository.getId();
        List<Object> list = new ArrayList<>();
        try {
            switch (env){
                case "api" ->{
                    ApiEnvQuery apiEnvQuery = new ApiEnvQuery();
                    apiEnvQuery.setRepositoryId(repositoryId);
                    // apiEnvQuery.set
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
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }

    private String findServerAddress(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }
        return authServer.getServerAddress();
    }

    @Override
    public Repository findOneRepository(String authId,String rpyName){
        String serverAddress = findServerAddress(authId);
        RepositoryQuery repositoryQuery = new RepositoryQuery();
        repositoryQuery.setName(rpyName);
        List<Repository> repositoryList;
        try {
             repositoryList = repositoryServer(serverAddress).findRepositoryList(repositoryQuery);
            if (repositoryList == null || repositoryList.isEmpty()){
                throw new ApplicationException("仓库查询失败："+ rpyName);
            }
        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
       return repositoryList.get(0);
    }


    public TestPlan findOneTestPlan(String authId,String planName){
        String serverAddress = findServerAddress(authId);
        TestPlanQuery testPlanQuery = new TestPlanQuery();
        testPlanQuery.setName(planName);
        List<TestPlan> testPlanList;
        try {
            testPlanList = testPlanService(serverAddress).findTestPlanList(testPlanQuery);
            if (testPlanList == null || testPlanList.isEmpty()){
                throw new ApplicationException("测试查询失败："+ planName);
            }
        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
       return testPlanList.get(0);
    }


    @Override
    public List<Repository> findAllRepository(String authId){

        String serverAddress = findServerAddress(authId);

        try {
          return repositoryServer(serverAddress).findAllRepository();

        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }

    @Override
    public List<TestPlan> findAllTestPlan(String authId,String rpyName){

        String serverAddress = findServerAddress(authId);

        Repository repository = findOneRepository(authId,rpyName);

        try {
            TestPlanQuery testPlanQuery = new TestPlanQuery();
            testPlanQuery.setRepositoryId(repository.getId());
            return testPlanService(serverAddress).findTestPlanList(testPlanQuery);
        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }


    @Override
    public List<Object> findAllEnv(String authId,String rpyName,String env){
        String serverAddress = findServerAddress(authId);
        Repository repository = findOneRepository(authId,rpyName);
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
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }


    @Override
    public void execTestPlan(String authId, TestPlanTestData testPlanTestData){

        String serverAddress = findServerAddress(authId);

        try {
            testPlanExecuteService(serverAddress).execute(testPlanTestData);
        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }



}
