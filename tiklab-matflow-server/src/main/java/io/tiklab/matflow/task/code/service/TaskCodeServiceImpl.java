package io.tiklab.matflow.task.code.service;

import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.task.code.dao.TaskCodeDao;
import io.tiklab.matflow.task.code.entity.TaskCodeEntity;
import io.tiklab.matflow.task.code.model.TaskCode;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.setting.service.AuthService;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    TaskCodeThirdService taskCodeThirdService;

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(TaskCode taskCode) {
        return taskCodeDao.createCode(BeanMapper.map(taskCode, TaskCodeEntity.class));
    }

    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteCodeConfig(String configId){
        TaskCode oneCodeConfig = findOneCodeConfig(configId);
        deleteCode(oneCodeConfig.getTaskId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskCode findOneCodeConfig(String configId){
        List<TaskCode> allCode = findAllCode();
        if (allCode == null){
            return null;
        }
        for (TaskCode taskCode : allCode) {
            if (!PipelineUtil.isNoNull(taskCode.getTaskId())){
                continue;
            }
            if (taskCode.getTaskId().equals(configId)){
                return taskCode;
            }
        }
        return null;
    }


    //删除
    @Override
    public void deleteCode(String codeId) {
        taskCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(TaskCode taskCode) {

        switch (taskCode.getType()) {
            case 2, 3 -> {
                if (!PipelineUtil.isNoNull(taskCode.getCodeName())){
                    break;
                }
                TaskCode oneCode = findOneCode(taskCode.getTaskId());
                String authId = oneCode.getAuthId();
                String houseUrl = taskCodeThirdService.getHouseUrl(authId, taskCode.getCodeName(), taskCode.getType());
                taskCode.setCodeAddress(houseUrl);
            }
            default -> taskCode.setCodeAddress(taskCode.getCodeName());
        }

        taskCodeDao.updateCode(BeanMapper.map(taskCode, TaskCodeEntity.class));
    }

    //查询单个
    @Override
    public TaskCode findOneCode(String codeId) {
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(codeId);
        TaskCode taskCode = BeanMapper.map(oneCodeEntity, TaskCode.class);
        taskCode.setAuth(findAuth(taskCode.getAuthId()));
        return taskCode;
    }

    //查询所有
    @Override
    public List<TaskCode> findAllCode() {
        List<TaskCode> taskCodes = BeanMapper.mapList(taskCodeDao.findAllCode(), TaskCode.class);
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


