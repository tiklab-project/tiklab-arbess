package io.tiklab.matflow.task.code.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.code.model.XcodeBranch;
import io.tiklab.matflow.task.code.model.XcodeRepository;
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
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.TASK_CODE_DEFAULT_BRANCH;

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
    public List<XcodeRepository> findAllRepository(String authId){

        List<Repository> allRpy  ;
        AuthThird authServer = serverServer.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress =  authServer.getServerAddress();
        try {

            String username = authServer.getUsername();
            String password = authServer.getPassword();

            allRpy = repositoryServer(serverAddress).findRepositoryByUser(username,password,"1");
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

        if (allRpy == null){
            return Collections.emptyList();
        }

        List<XcodeRepository> list = new ArrayList<>();

        for (Repository repository : allRpy) {
            list.add(bindXcodeRepository(repository));
        }

        return list;
    }

    @Override
    public List<XcodeBranch> findAllBranch(String authId, String rpyId){
        List<Branch> allBranch;

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();

        Repository repository = repositoryServer(serverAddress).findOneRpy(rpyId);

        if (Objects.isNull(repository)){
            throw new ApplicationException("找不到"+rpyId +"仓库！");
        }
        List<XcodeBranch> list = new ArrayList<>();
        try {
            allBranch = branchServer(serverAddress).findAllBranch(rpyId);
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

        if (Objects.isNull(allBranch) || allBranch.isEmpty()){
            throw new ApplicationException("获取Xcode分支失败，该仓库没有分支或该仓库为空仓库");
            // allBranch = new ArrayList<>();
            // Branch branch = new Branch();
            // branch.setBranchName(TASK_CODE_DEFAULT_BRANCH);
            // branch.setBranchId(TASK_CODE_DEFAULT_BRANCH);
            // allBranch.add(branch);
        }

        for (Branch branch : allBranch) {
            list.add(bindXcodeBranch(branch));
        }

        return list;
    }

    @Override
    public XcodeBranch findOneBranch(String authId,String rpyId,String branchId){
        if (Objects.isNull(authId) || Objects.isNull(branchId)){
            return null;
        }
        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();
        Branch branch;
        try {
            branch = branchServer(serverAddress).findBranch(rpyId, branchId);
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
            throw new ApplicationException("无法连接到："+ serverAddress );
        }
        if (Objects.isNull(branch)){
            return null;
        }

        return bindXcodeBranch(branch);
    }

    @Override
    public XcodeRepository findRepository(String authId,String rpyId){

        if (Objects.isNull(authId) || Objects.isNull(rpyId)){
            return null;
        }

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();


        Repository repository ;
        try {
            repository = repositoryServer(serverAddress).findOneRpy(rpyId);
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

        if (Objects.isNull(repository)){
            throw new ApplicationException("找不到"+ rpyId +"仓库！");
        }
        return bindXcodeRepository(repository);

    }

    private XcodeRepository bindXcodeRepository(Repository repository){
        XcodeRepository xcodeRepository = new XcodeRepository();
        xcodeRepository.setId(repository.getRpyId());
        xcodeRepository.setAddress(repository.getFullPath());
        xcodeRepository.setName(repository.getName());
        return xcodeRepository;
    }

    private XcodeBranch bindXcodeBranch(Branch branch){
        XcodeBranch xcodeBranch = new XcodeBranch();
        xcodeBranch.setId(branch.getBranchId());
        xcodeBranch.setName(branch.getBranchName());
        return xcodeBranch;
    }





}





















