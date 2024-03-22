package io.thoughtware.matflow.task.code.service;

import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.code.model.*;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.matflow.task.code.dao.TaskCodeDao;
import io.thoughtware.matflow.task.code.entity.TaskCodeEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.*;

@Service
@Exporter
public class TaskCodeServiceImpl implements TaskCodeService {

    @Autowired
    TaskCodeDao taskCodeDao;

    @Autowired
    AuthService authServer;

    @Autowired
    AuthThirdService authServerServer;

    @Autowired
    TaskCodeGiteeService giteeService;

    @Autowired
    TaskCodeGitHubService gitHubService;

    @Autowired
    TaskCodeGitLabService gitLabService;

    @Autowired
    TaskCodeGittokService xcodeService;

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(TaskCode taskCode) {
        return taskCodeDao.createCode(BeanMapper.map(taskCode, TaskCodeEntity.class));
    }

    /**
     * 根据配置id删除任务
     * @param taskId 配置id
     */
    @Override
    public void deleteTaskCode(String taskId){
        taskCodeDao.deleteCode(taskId);
    }


    /**
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    @Override
    public TaskCode findOneCodeConfig(String taskId,String taskType){
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(taskId);
        TaskCode taskCode = BeanMapper.map(oneCodeEntity, TaskCode.class);
        String authId = taskCode.getAuthId();

        if (Objects.isNull(authId)){
            return taskCode;
        }

        taskCode.setAuth(findAuth(taskCode.getAuthId()));
        if (Objects.isNull(oneCodeEntity.getXcodeId())){
            return taskCode;
        }

        // String xcodeId = oneCodeEntity.getXcodeId();
        // XcodeRepository repository = taskCodeGittokService.findRepository(authId, xcodeId);
        // taskCode.setRepository(repository);
        // // 查询不到仓库
        // if (Objects.isNull(repository)){
        //     return taskCode;
        // }else {
        //     taskCode.setCodeName(repository.getName());
        // }
        //
        // // 查询不到分支
        // String codeBranch = oneCodeEntity.getBranchId();
        // if (Objects.isNull(codeBranch)){
        //     return taskCode;
        // }
        // XcodeBranch branch = taskCodeGittokService.findOneBranch(authId, repository.getRpyId(), codeBranch);
        // if (Objects.isNull(branch)){
        //     branch = new XcodeBranch();
        //     branch.setBranchName(PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        //     branch.setBranchId(PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        // }
        // taskCode.setBranch(branch);
        return taskCode;
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        taskCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(TaskCode taskCode) {
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(taskCode.getTaskId());
        TaskCode oneCode = BeanMapper.map(oneCodeEntity, TaskCode.class);
        String authId = oneCode.getAuthId();
        String houseId = taskCode.getHouseId();

        if (!StringUtils.isEmpty(houseId)){
            ThirdHouse storeHouse = null;
            ThirdQuery thirdQuery = new ThirdQuery();
            thirdQuery.setHouseId(houseId);
            thirdQuery.setAuthId(authId);
            switch (taskCode.getType()) {
                case TASK_CODE_GITEE  -> {
                    storeHouse = giteeService.findStoreHouse(thirdQuery);
                }
                case TASK_CODE_GITHUB -> {
                    storeHouse = gitHubService.findStoreHouse(thirdQuery);
                }
                case TASK_CODE_GITLAB -> {
                    storeHouse = gitLabService.findStoreHouse(thirdQuery);
                }
                case TASK_CODE_XCODE -> {
                    storeHouse = xcodeService.findStoreHouse(thirdQuery);
                }
                default -> {
                }
            }
            if (!Objects.isNull(storeHouse)){
                taskCode.setCodeAddress(storeHouse.getHouseWebUrl());
                taskCode.setCodeName(storeHouse.getNameWithSpace());
                taskCode.setCodeBranch(storeHouse.getDefaultBranch());
            }
        }else {
            taskCode.setCodeAddress(taskCode.getCodeName());
        }

        taskCodeDao.updateCode(BeanMapper.map(taskCode, TaskCodeEntity.class));
    }

    //查询单个
    @Override
    public TaskCode findOneCode(String codeId) {
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(codeId);
        return BeanMapper.map(oneCodeEntity, TaskCode.class);
    }

    //查询所有
    @Override
    public List<TaskCode> findAllCode() {
        List<TaskCodeEntity> allCode = taskCodeDao.findAllCode();
        List<TaskCode> taskCodes = BeanMapper.mapList(allCode, TaskCode.class);
        if (taskCodes == null){
            return null;
        }
        for (TaskCode taskCode : taskCodes) {
            taskCode.setAuth(findAuth(taskCode.getAuthId()));
        }
        return taskCodes;
    }

    @Override
    public List<TaskCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(taskCodeDao.findAllCodeList(idList), TaskCode.class);
    }

    //获认证信息
    private Object findAuth(String id){
        AuthThird oneAuthServer = authServerServer.findOneAuthServer(id);
        if (oneAuthServer != null){
           return oneAuthServer;
        }
        return authServer.findOneAuth(id);
    }

}


