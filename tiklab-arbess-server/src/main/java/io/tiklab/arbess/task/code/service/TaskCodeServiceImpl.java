package io.tiklab.arbess.task.code.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.service.AuthService;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.arbess.task.code.model.*;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.code.dao.TaskCodeDao;
import io.tiklab.arbess.task.code.entity.TaskCodeEntity;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.arbess.task.code.model.TaskCode;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

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

    @Override
    public String createCode(TaskCode taskCode) {
        return taskCodeDao.createCode(BeanMapper.map(taskCode, TaskCodeEntity.class));
    }

    @Override
    public TaskCode findCodeByAuth(String taskId){
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(taskId);
        TaskCode taskCode = BeanMapper.map(oneCodeEntity, TaskCode.class);
        String authId = taskCode.getAuthId();

        if (Objects.isNull(authId)){
            return taskCode;
        }
        taskCode.setAuth(findAuth(taskCode.getAuthId()));
        return taskCode;
    }

    @Override
    public Boolean codeValid(String taskType,TaskCode code){
        switch (taskType) {
            case TASK_CODE_GITEE, TASK_CODE_GITLAB, TASK_CODE_GITHUB -> {
                if (StringUtils.isEmpty(code.getHouseId())) {
                    return false;
                }
                return !StringUtils.isEmpty(code.getAuthId());
            }
            case TASK_CODE_XCODE -> {
                if (StringUtils.isEmpty(code.getHouseId())) {
                    return false;
                }
                return !StringUtils.isEmpty(code.getAuthId());
            }
            case TASK_CODE_SVN -> {
                // String svnFile = code.getSvnFile();
                // return !StringUtils.isEmpty(svnFile);
                return true;
            }
            default -> {
                String codeAddress = code.getCodeAddress();
                return !StringUtils.isEmpty(codeAddress);
            }
        }
    }

    @Override
    public void deleteCode(String codeId) {
        taskCodeDao.deleteCode(codeId);
    }

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

    @Override
    public TaskCode findOneCode(String codeId) {
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(codeId);
        return BeanMapper.map(oneCodeEntity, TaskCode.class);
    }

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


