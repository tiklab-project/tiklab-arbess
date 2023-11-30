package io.tiklab.matflow.task.code.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.support.util.PipelineRequestUtil;
import io.tiklab.matflow.task.code.model.XcodeBranch;
import io.tiklab.matflow.task.code.model.XcodeRepository;
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
public class TaskCodeXcodeServiceImpl implements TaskCodeXcodeService {

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeXcodeServiceImpl.class);

    @Autowired
    AuthThirdService serverServer;

    @Autowired
    PipelineRequestUtil requestUtil;


    @Override
    public List<XcodeRepository> findAllRepository(String authId){

        AuthThird authServer = serverServer.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress =  authServer.getServerAddress();
        try {

            String username = authServer.getUsername();
            String password = authServer.getPassword();

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("account",username);
            valueMap.add("password",password);
            valueMap.add("dirId","1");
            String requestUrl = serverAddress+"/api/rpy/findRepositoryByUser";
            return requestUtil.requestPostList(headers, requestUrl, valueMap, XcodeRepository.class);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅Xcode");
            }
            if (message.contains("用户校验失败")){
                throw new ApplicationException("用户校验失败！");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }

            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public List<XcodeBranch> findAllBranch(String authId, String rpyId){

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("rpyId",rpyId);
            String requestUrl = serverAddress+"/api/branch/findAllBranch";
            return requestUtil.requestPostList(headers, requestUrl, valueMap, XcodeBranch.class);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅Xcode");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public XcodeBranch findOneBranch(String authId,String rpyId,String branchId){
        if (Objects.isNull(authId) || Objects.isNull(branchId)){
            return null;
        }

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();

        HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("rpyId",rpyId);
        valueMap.add("commitId",branchId);
        String requestUrl = serverAddress+"/api/branch/findBranch";
        return requestUtil.requestPost(headers, requestUrl, valueMap, XcodeBranch.class);
    }

    @Override
    public XcodeRepository findRepository(String authId,String rpyId){

        if (Objects.isNull(authId) || Objects.isNull(rpyId)){
            return null;
        }
        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("id",rpyId);
            String requestUrl = serverAddress+"/api/rpy/findRepository";
            XcodeRepository repository = requestUtil.requestPost(headers, requestUrl, valueMap, XcodeRepository.class);
            if (Objects.isNull(repository)){
                throw new ApplicationException("找不到"+ rpyId +"仓库！");
            }
            return repository;
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅Xcode");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

}





















