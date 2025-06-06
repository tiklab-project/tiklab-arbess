package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageType;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.arbess.support.message.service.TaskMessageTypeService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.trigger.model.Trigger;
import io.tiklab.arbess.support.trigger.model.TriggerJob;
import io.tiklab.arbess.support.trigger.model.TriggerQuery;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.arbess.support.trigger.service.CronUtils;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.trigger.service.TriggerTimeService;
import io.tiklab.arbess.support.variable.controller.VariableController;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import io.tiklab.todotask.todo.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;

@Configuration
public class TaskPipelineMessage implements TiklabApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskPipelineMessage.class);

    @Autowired
    TaskMessageService taskMessageService;

    @Autowired
    TasksService tasksService;

    @Autowired
    PostprocessService postprocessService;

    @Autowired
    TaskMessageTypeService taskMessageTypeService;


    @Override
    public void run(){
        logger.info("update pipeline message......");
        updateMessage();
        logger.info("update pipeline message completed!");
    }


    public void updateMessage(){

        List<Tasks> allTasks = tasksService.findAllTasks();
        for (Tasks tasks : allTasks) {
            String postprocessId = tasks.getPostprocessId();
            if (StringUtils.isEmpty(postprocessId)){
                continue;
            }

            String taskType = tasks.getTaskType();
            if (!taskType.equals("message")){
                continue;
            }

            Postprocess postprocess = postprocessService.findOnePost(postprocessId);

            if (Objects.isNull(postprocess)){
                continue;
            }

            String taskId = tasks.getTaskId();

            TaskMessage message = taskMessageService.findTaskMessage(taskId);
            if (!Objects.isNull(message)){
                continue;
            }

            TaskMessageTypeQuery taskMessageTypeQuery = new TaskMessageTypeQuery();
            taskMessageTypeQuery.setMessageId(taskId);
            List<TaskMessageType> messageTypeList = taskMessageTypeService.findMessageTypeList(taskMessageTypeQuery);
            if (messageTypeList.isEmpty()){
                continue;
            }

            TaskMessage taskMessage = new TaskMessage();
            taskMessage.setName(postprocess.getPostName());
            taskMessage.setPipelineId(postprocess.getPipelineId());
            taskMessage.setNoticeType(1);
            taskMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
            taskMessage.setId(taskId);
            taskMessageService.createTaskMessage(taskMessage);

        }
    }

}




