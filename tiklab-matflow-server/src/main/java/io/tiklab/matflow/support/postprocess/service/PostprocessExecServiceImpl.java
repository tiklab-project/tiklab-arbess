package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostprocessExecServiceImpl implements PostprocessExecService{

    @Autowired
    PostprocessService postprocessService;

    @Autowired
    TasksExecService tasksExecService;


    @Override
    public boolean execPipelinePostTask(Pipeline pipeline , boolean execStatus){
        String pipelineId = pipeline.getId();
        List<Tasks> pipelinePostTask = postprocessService.findAllPipelinePostTask(pipelineId);
        for (Tasks tasks : pipelinePostTask) {
            int taskType = tasks.getTaskType();
            boolean b;
            if (taskType == 61){
                 b = tasksExecService.execSendMessageTask(pipeline, tasks, execStatus, true);
            }else {
                b = tasksExecService.execTask(pipelineId, tasks.getTaskType(), tasks.getTaskId());
            }
            if (!b){
                return b;
            }
        }
        return true;
    }

    @Override
    public boolean execTaskPostTask(Pipeline pipeline , String taskId,boolean execStatus){
        String pipelineId = pipeline.getId();
        List<Tasks> pipelinePostTask = postprocessService.findAllTaskPostTask(taskId);
        for (Tasks tasks : pipelinePostTask) {
            boolean b;
            int taskType = tasks.getTaskType();
            if (taskType == 61){
                b = tasksExecService.execSendMessageTask(pipeline, tasks, execStatus, false);
            }else {
                b = tasksExecService.execTask(pipelineId, tasks.getTaskType(), tasks.getTaskId());
            }
            if (!b){
                return b;
            }
        }
        return true;
    }

    public void stopTaskPostTask(){

    }



}



































