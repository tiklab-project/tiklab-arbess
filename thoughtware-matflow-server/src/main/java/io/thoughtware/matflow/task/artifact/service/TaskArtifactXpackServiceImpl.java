package io.thoughtware.matflow.task.artifact.service;

import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.util.PipelineRequestUtil;
import io.thoughtware.matflow.task.artifact.model.XpackRepository;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.task.artifact.model.XpackRepositoryQuery;
import io.thoughtware.matflow.task.code.service.TaskCodeXcodeServiceImpl;
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
public class TaskArtifactXpackServiceImpl implements TaskArtifactXpackService {


    private static final Logger logger = LoggerFactory.getLogger(TaskCodeXcodeServiceImpl.class);

    @Autowired
    AuthThirdService authThirdService;

    @Autowired
    PipelineRequestUtil requestUtil;


    public List<XpackRepository> findAllRepository(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress = authServer.getServerAddress();
        XpackRepositoryQuery repositoryQuery = new XpackRepositoryQuery();
        repositoryQuery.setRepositoryType("local");
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            String requestUrl = serverAddress+"/api/xpackRepository/findRepositoryList";
            return requestUtil.requestPostList(headers, requestUrl, repositoryQuery, XpackRepository.class);

        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业为订阅Xpack");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    public XpackRepository findRepository(String authId,String rpyId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);
        if (Objects.isNull(authServer) || Objects.isNull(rpyId)){
            return null;
        }
        String serverAddress = authServer.getServerAddress();

        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            String requestUrl = serverAddress+"/api/xpackRepository/findRepository";
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("id",rpyId);
            return requestUtil.requestPost(headers, requestUrl, multiValueMap, XpackRepository.class);

        } catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业为订阅Xpack");
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }



}















