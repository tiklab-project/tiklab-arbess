package io.tiklab.matflow.task.artifact.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.code.service.TaskCodeXcodeServiceImpl;
import io.tiklab.rpc.client.RpcClient;
import io.tiklab.rpc.client.config.RpcClientConfig;
import io.tiklab.rpc.client.router.lookup.FixedLookup;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.model.RepositoryQuery;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TaskArtifactXpackServiceImpl implements TaskArtifactXpackService {


    private static final Logger logger = LoggerFactory.getLogger(TaskCodeXcodeServiceImpl.class);

    @Autowired
    private AuthThirdService authThirdService;


    RpcClient rpcClient(){
        RpcClientConfig rpcClientConfig = RpcClientConfig.instance();
        return new RpcClient(rpcClientConfig);
    }

    RepositoryService repositoryServer(String xpackAddress){
        return rpcClient().getBean(RepositoryService.class, new FixedLookup(xpackAddress));
    }


    public List<XpackRepository> findAllRepository(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress = authServer.getServerAddress();
        RepositoryQuery repositoryQuery = new RepositoryQuery();
        repositoryQuery.setRepositoryType("local");
        List<Repository> repositoryList ;
        List<XpackRepository> list = new ArrayList<>();
        try {
            repositoryList = repositoryServer(serverAddress).findRepositoryList(repositoryQuery);
            if (repositoryList == null || repositoryList.isEmpty()){
                return Collections.emptyList();
            }

            for (Repository repository : repositoryList) {
                list.add(bindXpackRepository(repository));
            }
            return list;
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
        Repository repository ;
        try {
             repository = repositoryServer(serverAddress).findRepository(rpyId);

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

        if (repository == null){
            throw new ApplicationException("获取仓库信息为空！");
        }

        return bindXpackRepository(repository);
    }


    private XpackRepository bindXpackRepository(Repository repository){
        XpackRepository xpackRepository = new XpackRepository();

        xpackRepository.setId(repository.getId());
        xpackRepository.setName(repository.getName());
        xpackRepository.setAddress(repository.getRepositoryUrl());
        return xpackRepository;

    }



}















