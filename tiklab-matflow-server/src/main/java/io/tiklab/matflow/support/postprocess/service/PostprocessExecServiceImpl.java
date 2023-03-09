package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.postprocess.model.Postprocess;
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


    public void createPostInstance(String pipelineId){
        List<Postprocess> postprocessList = postprocessService.findAllPipelinePostTask(pipelineId);
        if (postprocessList.size() == 0){
            return;
        }
        for (Postprocess postprocess : postprocessList) {
            String postprocessId = postprocess.getPostprocessId();
            Tasks task = postprocess.getTask();


            tasksExecService.createTaskExecInstance(task,"",3,"");


        }
    }






    @Override
    public boolean execPipelinePostTask(Pipeline pipeline , boolean execStatus){
        String pipelineId = pipeline.getId();
        List<Postprocess> postprocessList = postprocessService.findAllPipelinePostTask(pipelineId);
        for (Postprocess postprocess : postprocessList) {
            int taskType = postprocess.getTaskType();
            Tasks task = postprocess.getTask();
            boolean b;
            if (taskType == 61){
                 b = tasksExecService.execSendMessageTask(pipeline, task, execStatus, true);
            }else {
                b = tasksExecService.execTask(pipelineId, taskType, task.getTaskId());
            }
            if (!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean execTaskPostTask(Pipeline pipeline , String taskId,boolean execStatus){
        String pipelineId = pipeline.getId();
        List<Postprocess> postprocessList = postprocessService.findAllTaskPostTask(taskId);
        for (Postprocess postprocess : postprocessList) {
            boolean b;
            int taskType = postprocess.getTaskType();
            Tasks task = postprocess.getTask();
            if (taskType == 61){
                b = tasksExecService.execSendMessageTask(pipeline, task, execStatus, false);
            }else {
                b = tasksExecService.execTask(pipelineId, taskType, task.getTaskId());
            }
            if (!b){
                return false;
            }
        }
        return true;
    }



    public void stopTaskPostTask(){

    }



}



































