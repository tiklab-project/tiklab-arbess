package io.tiklab.arbess.starter.task;


import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.setting.auth.model.Auth;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.code.dao.TaskCodeDao;
import io.tiklab.arbess.task.code.entity.TaskCodeEntity;
import io.tiklab.arbess.task.code.service.TaskCodeService;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TaskInitAuth implements TiklabApplicationRunner {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    TasksService tasksService;

    @Autowired
    StageService stageService;

    @Autowired
    TaskCodeService taskCodeService;

    @Autowired
    TaskCodeDao taskCodeDao;

    @Autowired
    AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("The update auth.....");
        updatePipelineAuth();
        logger.info("The update auth end.");

    }


    private void updatePipelineAuth(){

        List<TaskCodeEntity> codeEntityList = taskCodeDao.findAllCode();
        codeEntityList.forEach(codeEntity -> {
            String authType = codeEntity.getAuthType();
            if (!StringUtils.isEmpty(authType)){
                return;
            }
            String authId = codeEntity.getAuthId();
            codeEntity.setAuthType(PipelineFinal.AUTH_NONE);
            if (!StringUtils.isEmpty(authId)){
                Auth auth = authService.findOneAuth(authId);
                if (!Objects.isNull(auth)){
                    int authPublic = auth.getAuthPublic();
                    if (authPublic == 1){
                        codeEntity.setAuthType(PipelineFinal.AUTH_USER_PASS);
                        codeEntity.setUsername(auth.getUsername());
                        codeEntity.setPassword(auth.getPassword());
                    }else {
                        codeEntity.setAuthType(PipelineFinal.AUTH_PRI_KEY);
                        codeEntity.setPriKey(auth.getPrivateKey());
                    }
                }
            }
            taskCodeDao.updateCode(codeEntity);
        });
    }


}

















