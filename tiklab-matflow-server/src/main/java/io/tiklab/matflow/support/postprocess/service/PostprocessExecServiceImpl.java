package io.tiklab.matflow.support.postprocess.service;

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


    public boolean execPipelinePostTask(String pipelineId){
        List<Tasks> pipelinePostTask = postprocessService.findAllPipelinePostTask(pipelineId);
        for (Tasks tasks : pipelinePostTask) {
            boolean b = tasksExecService.execTask(pipelineId, tasks.getTaskType(), tasks.getTaskId());
            if (!b){
                return b;
            }
        }
        return true;
    }



    public boolean execTaskPostTask(String pipelineId,String taskId){
        List<Tasks> pipelinePostTask = postprocessService.findAllTaskPostTask(taskId);
        for (Tasks tasks : pipelinePostTask) {
            boolean b = tasksExecService.execTask(pipelineId, tasks.getTaskType(), tasks.getTaskId());
            if (!b){
                return b;
            }
        }
        return true;
    }

    public void stopTaskPostTask(){

    }



}
