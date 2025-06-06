package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Configuration
public class TaskVariable implements TiklabApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskVariable.class);

    @Autowired
    VariableService variableService;

    @Override
    public void run(){
        logger.info("update pipeline variable......");
        updateVariable();
        logger.info("update pipeline variable completed!");
    }


    public void updateVariable(){

        List<Variable> allVariable = variableService.findAllVariable();

        for (Variable variable : allVariable) {
            String pipelineId = variable.getPipelineId();
            if (!StringUtils.isEmpty(pipelineId)){
                continue;
            }

            if (variable.getType() != 1){
                continue;
            }

            String taskId = variable.getTaskId();
            variable.setPipelineId(taskId);
            variable.setTaskId(" ");

            variableService.updateVariable(variable);
        }
    }

}




