package io.thoughtware.matflow.task.code.service;

import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.matflow.task.code.model.TaskCode;
import io.thoughtware.matflow.task.code.model.XcodeBranch;
import io.thoughtware.matflow.task.code.model.XcodeRepository;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.matflow.task.code.dao.TaskCodeDao;
import io.thoughtware.matflow.task.code.entity.TaskCodeEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    TaskCodeXcodeService taskCodeXcodeService;

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
    public void deleteCodeConfig(String taskId){
        deleteCode(taskId);
    }


    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskCode findOneCodeConfig(String configId,String taskType){
        TaskCodeEntity oneCodeEntity = taskCodeDao.findOneCode(configId);
        TaskCode taskCode = BeanMapper.map(oneCodeEntity, TaskCode.class);
        String authId = taskCode.getAuthId();

        if (Objects.isNull(authId)){
            return taskCode;
        }

        taskCode.setAuth(findAuth(taskCode.getAuthId()));
        if (Objects.isNull(oneCodeEntity.getXcodeId())){
            return taskCode;
        }

        String xcodeId = oneCodeEntity.getXcodeId();
        XcodeRepository repository = taskCodeXcodeService.findRepository(authId, xcodeId);
        taskCode.setRepository(repository);
        // 查询不到仓库
        if (Objects.isNull(repository)){
            return taskCode;
        }else {
            taskCode.setCodeName(repository.getName());
        }

        // 查询不到分支
        String codeBranch = oneCodeEntity.getBranchId();
        if (Objects.isNull(codeBranch)){
            return taskCode;
        }
        XcodeBranch branch = taskCodeXcodeService.findOneBranch(authId, repository.getRpyId(), codeBranch);
        if (Objects.isNull(branch)){
            branch = new XcodeBranch();
            branch.setBranchName(PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
            branch.setBranchId(PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        }
        taskCode.setBranch(branch);
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
        switch (taskCode.getType()) {
            case "2", "3","gitee","github" -> {
                if (!PipelineUtil.isNoNull(taskCode.getCodeName())){
                    break;
                }
                String houseUrl = taskCodeThirdService.getHouseUrl(authId, taskCode.getCodeName(), taskCode.getType());
                taskCode.setCodeAddress(houseUrl);
            }
            case "xcode" -> {
                XcodeRepository repository = oneCode.getRepository();
                if (!Objects.isNull(repository)){
                    taskCode.setCodeAddress(repository.getFullPath());
                }
            }
            default -> taskCode.setCodeAddress(taskCode.getCodeName());
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

