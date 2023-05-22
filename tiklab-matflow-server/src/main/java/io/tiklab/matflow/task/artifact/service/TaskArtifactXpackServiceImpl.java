package io.tiklab.matflow.task.artifact.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
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
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;

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


    public List<Repository> findAllRepository(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress = authServer.getServerAddress();
        RepositoryQuery repositoryQuery = new RepositoryQuery();
        repositoryQuery.setRepositoryType("local");
        List<Repository> repositoryList ;
        try {
            repositoryList = repositoryServer(serverAddress).findRepositoryList(repositoryQuery);
            if (repositoryList == null || repositoryList.isEmpty()){
                return Collections.emptyList();
            }
            return repositoryList;
        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
    }

    public String findRepository(String authId,String rpyName){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);
        if (Objects.isNull(authServer)){
            return null;
        }
        String serverAddress = authServer.getServerAddress();
        RepositoryQuery repositoryQuery = new RepositoryQuery();
        repositoryQuery.setRepositoryType("local");
        repositoryQuery.setName(rpyName);
        List<Repository> repositoryList = repositoryServer(serverAddress).findRepositoryList(repositoryQuery);
        if (repositoryList == null || repositoryList.isEmpty()){
            return null;
        }
        Repository repository = repositoryList.get(0);
        return repository.getRepositoryUrl();
    }



}















