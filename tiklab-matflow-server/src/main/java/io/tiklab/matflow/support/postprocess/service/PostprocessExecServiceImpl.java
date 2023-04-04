package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.postprocess.model.Postprocess;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksExecService;
import io.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostprocessExecServiceImpl implements PostprocessExecService{

    @Autowired
    private PostprocessService postprocessService;

    @Autowired
    private TasksExecService tasksExecService;

    @Autowired
    private PostprocessInstanceService postInstanceService;


    //后置任务id与后置任务实例id之间的关系
    Map<String , String> postIdOrPostInstanceId = new HashMap<>();

    @Override
    public void createPipelinePostInstance(String pipelineId, String instanceId){
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,2) + instanceId;
        List<Postprocess> postprocessList = postprocessService.findAllPipelinePostTask(pipelineId);

        if (postprocessList.isEmpty()){
            return;
        }
        for (Postprocess postprocess : postprocessList) {
            String postprocessId = postprocess.getPostprocessId();
            Tasks task = postprocess.getTask();
            PostprocessInstance postInstance = new PostprocessInstance();
            postInstance.setInstanceId(instanceId);
            String postInstanceId = postInstanceService.createPostInstance(postInstance);
            tasksExecService.createTaskExecInstance(task,postInstanceId,3,fileAddress);
            postIdOrPostInstanceId.put(postprocessId,postInstanceId);
        }
    }

    @Override
    public void createTaskPostInstance(String pipelineId, String instanceId, String taskId){
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,2) + instanceId;
        List<Postprocess> postprocessList = postprocessService.findAllTaskPostTask(taskId);

        if (postprocessList.isEmpty()){
            return;
        }
        for (Postprocess postprocess : postprocessList) {
            String postprocessId = postprocess.getPostprocessId();
            PostprocessInstance postInstance = new PostprocessInstance();
            TasksExecServiceImpl tasksExecService1 = new TasksExecServiceImpl();
            String taskInstanceId = tasksExecService1.findTaskInstanceId(taskId);
            postInstance.setTaskInstanceId(taskInstanceId);
            String postInstanceId = postInstanceService.createPostInstance(postInstance);
            fileAddress = fileAddress + "/" + taskInstanceId + "/" +  postInstanceId;
            Tasks task = postprocess.getTask();
            tasksExecService.createTaskExecInstance(task,postInstanceId,3,fileAddress);
            postIdOrPostInstanceId.put(postprocessId,postInstanceId);
        }
    }

    @Override
    public boolean execPipelinePost(Pipeline pipeline, boolean execStatus){
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
    public boolean execTaskPostTask(Pipeline pipeline, String taskId,boolean execStatus){
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



































