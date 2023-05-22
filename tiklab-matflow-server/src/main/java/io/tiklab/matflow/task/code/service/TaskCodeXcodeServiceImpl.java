package io.tiklab.matflow.task.code.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.rpc.client.RpcClient;
import io.tiklab.rpc.client.config.RpcClientConfig;
import io.tiklab.rpc.client.router.lookup.FixedLookup;
import io.tiklab.xcode.branch.model.Branch;
import io.tiklab.xcode.branch.service.BranchServer;
import io.tiklab.xcode.repository.model.Repository;
import io.tiklab.xcode.repository.service.RepositoryServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TaskCodeXcodeServiceImpl implements TaskCodeXcodeService {

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeXcodeServiceImpl.class);

    @Autowired
    private AuthThirdService serverServer;


    RpcClient rpcClient(){
        RpcClientConfig rpcClientConfig = RpcClientConfig.instance();
        return new RpcClient(rpcClientConfig);
    }

    RepositoryServer repositoryServer(String xcodeAddress){
        return rpcClient().getBean(RepositoryServer.class, new FixedLookup(xcodeAddress));
    }

    BranchServer branchServer(String xcodeAddress){
        return rpcClient().getBean(BranchServer.class, new FixedLookup(xcodeAddress));
    }

    @Override
    public List<Repository> findAllRepository(String authId){

        List<Repository> allRpy ;
        AuthThird authServer = serverServer.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }
        String serverAddress =  authServer.getServerAddress();
        try {

            allRpy = repositoryServer(serverAddress).findAllRpy();

            if (allRpy == null){
                return Collections.emptyList();
            }

        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
        return allRpy;
    }

    @Override
    public List<Branch> findAllBranch(String authId,String rpyName){
        List<Branch> allBranch;

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();

        Repository repository = repositoryServer(serverAddress).findNameRpy(rpyName);

        if (Objects.isNull(repository)){
            throw new ApplicationException("找不到"+rpyName +"仓库！");
        }

        try {
            String rpyId = repository.getRpyId();
            allBranch = branchServer(serverAddress).findAllBranch(rpyId);

            if (allBranch == null || allBranch.isEmpty()){
                allBranch = new ArrayList<>();
                Branch branch = new Branch();
                branch.setBranchName("master");
                allBranch.add(branch);
            }

        }catch (Throwable throwable){
            if (throwable instanceof RemoteAccessException){
                logger.error(throwable.getMessage());
                throw new ApplicationException("无法连接到:" + serverAddress);
            }
            throw new ApplicationException(throwable.getMessage());
        }
        return allBranch;
    }


    public String findRepository(String authId,String rpyName){

        if (Objects.isNull(authId) || Objects.isNull(rpyName)){
            return null;
        }

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();

        Repository repository = repositoryServer(serverAddress).findNameRpy(rpyName);
        if (Objects.isNull(repository)){
            throw new ApplicationException("找不到"+rpyName +"仓库！");
        }

        return repository.getFullPath();
    }





}





















