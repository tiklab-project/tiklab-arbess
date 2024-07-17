package io.thoughtware.matflow.support.postprocess.service;

import io.thoughtware.matflow.pipeline.execute.model.PipelineDetails;
import io.thoughtware.matflow.support.postprocess.model.Postprocess;
import io.thoughtware.matflow.support.postprocess.model.PostprocessInstance;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.task.task.model.TaskExecMessage;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksExecService;
import io.thoughtware.matflow.task.task.service.TasksExecServiceImpl;
import io.thoughtware.matflow.task.task.service.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostprocessExecServiceImpl implements PostprocessExecService{

    @Autowired
    PostprocessService postprocessService;

    @Autowired
    TasksExecService tasksExecService;

    @Autowired
    PostprocessInstanceService postInstanceService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    TasksService tasksService;

    private static final Logger logger = LoggerFactory.getLogger(PostprocessExecServiceImpl.class);


    //后置任务id与后置任务实例id之间的关系
    public static Map<String , String> postIdOrPostInstanceId = new HashMap<>();

    //后置实例id与后置任务实例之间的关系
    public static Map<String , PostprocessInstance> postInstanceIdOrPostInstance = new HashMap<>();


    private final static Map<String , List<String>> pipelineIdOrPostInstanceId = new HashMap<>();

    @Override
    public List<Postprocess> createPipelinePostInstance(String pipelineId, String instanceId){
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2) + instanceId;
        List<Postprocess> postprocessList = postprocessService.findAllPipelinePostTask(pipelineId);
        if (postprocessList.isEmpty()){
            return Collections.emptyList();
        }
        for (Postprocess postprocess : postprocessList) {

            PostprocessInstance postInstance = new PostprocessInstance();
            postInstance.setInstanceId(instanceId);
            postInstance.setPostState(PipelineFinal.RUN_HALT);
            String postInstanceId = postInstanceService.createPostInstance(postInstance);

            Tasks task = tasksService.findOnePostTaskOrTask(postprocess.getPostId());
            fileAddress = fileAddress + "/" +  postInstanceId;
            String taskInstanceId = tasksExecService.createTaskExecInstance(task, postInstanceId, 3, fileAddress);
            task.setInstanceId(taskInstanceId);

            postInstance.setTaskInstanceId(taskInstanceId);

            postprocess.setTask(task);
            postprocess.setValues(task);
            postprocess.setInstanceId(postInstanceId);
        }
        return postprocessList;
    }

    @Override
    public boolean execPipelinePost(PipelineDetails pipelineDetails){
        String pipelineId = pipelineDetails.getPipelineId();
        List<Postprocess> postprocessList = postprocessService.findAllPipelinePostTask(pipelineId);
        boolean state = true;

        if (postprocessList.isEmpty()){
            return true;
        }

        for (Postprocess postprocess : postprocessList) {
            if (state){
                updatePostInstanceCache(postprocess.getPostId());
                String taskType = postprocess.getTaskType();
                Tasks task = postprocess.getTask();
                boolean b;
                String postprocessId = postprocess.getPostId();
                if (taskType.equals("61")|| taskType.equals("message")){
                    // taskExecMessage.setTasks(task);
                    // taskExecMessage.setExecPipeline(true);
                    // b = tasksExecService.execSendMessageTask(taskExecMessage);
                }else {
                    b = tasksExecService.execTask(pipelineId, taskType, task.getTaskId());
                }
                // // removePostInstanceCache(postprocessId,b);
                // if (!b){
                //     state = false;
                // }
            }
        }
        return state;
    }



    public boolean execTaskPostTask(TaskExecMessage taskExecMessage){
        String pipelineId = taskExecMessage.getPipeline().getId();
        List<Postprocess> postprocessList = postprocessService.findAllTaskPostTask(taskExecMessage.getTaskId());

        if (postprocessList.isEmpty()){
            return true;
        }

        logger.warn("执行任务{}的后置任务......",taskExecMessage.getTaskName());

        boolean state = true;
        for (Postprocess postprocess : postprocessList) {
            boolean b;
            String postprocessId = postprocess.getPostId();
            if (state){
                updatePostInstanceCache(postprocessId);
                String taskType = postprocess.getTaskType();
                Tasks task = postprocess.getTask();
                if (taskType.equals("61")|| taskType.equals("message")){
                    taskExecMessage.setTasks(task);
                    taskExecMessage.setExecPipeline(false);
                    b = tasksExecService.execSendMessageTask(taskExecMessage);
                }else {
                    b = tasksExecService.execTask(pipelineId, taskType, task.getTaskId());
                }
                removePostInstanceCache(postprocessId,b);
                if (!b){
                    state = false;
                }
            }
            String postInstanceId = findPostInstanceId(postprocessId);
            postIdOrPostInstanceId.remove(postprocessId);
            postInstanceIdOrPostInstance.remove(postInstanceId);
        }
        return state;
    }

    public String findPostInstanceId(String postId){
        return postIdOrPostInstanceId.get(postId);
    }

    public PostprocessInstance findPostInstance(String postInstanceId){
        return postInstanceIdOrPostInstance.get(postInstanceId);
    }

    public void updatePostInstanceCache(String postId){
        String postInstanceId = findPostInstanceId(postId);
        // postInstanceService.postInstanceRunTime(postInstanceId);
        PostprocessInstance postInstance = findPostInstance(postInstanceId);
        postInstance.setPostState(PipelineFinal.RUN_RUN);
        postInstanceService.updatePostInstance(postInstance);
        postInstanceIdOrPostInstance.put(postInstanceId,postInstance);
    }

    public void removePostInstanceCache(String postId,boolean runState){
        String postInstanceId = findPostInstanceId(postId);
        PostprocessInstance postInstance = findPostInstance(postInstanceId);
        // Integer runTime = postInstanceService.findPostInstanceRunTime(postInstanceId);
        // postInstance.setPostTime(runTime);
        if (runState){
            postInstance.setPostState(PipelineFinal.RUN_SUCCESS);
        }else {
            postInstance.setPostState(PipelineFinal.RUN_ERROR);
        }
        postInstanceService.updatePostInstance(postInstance);
        postInstanceIdOrPostInstance.remove(postInstanceId);
        postIdOrPostInstanceId.remove(postId);
        // postInstanceService.removePostInstanceRunTime(postInstanceId);
    }

    private void updatePipelineOrPostInstanceCache(String pipelineId,String postInstanceId){
        List<String> stringList = findPipelineOrPostInstanceCache(pipelineId);
        stringList.add(postInstanceId);
        pipelineIdOrPostInstanceId.put(postInstanceId,stringList);
    }

    private List<String> findPipelineOrPostInstanceCache(String pipelineId){
        List<String> list = pipelineIdOrPostInstanceId.get(pipelineId);
        if (Objects.isNull(list)){
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void stopTaskPostTask(String pipelineId){
        List<String> postInstanceCache = findPipelineOrPostInstanceCache(pipelineId);
        for (String s : postInstanceCache) {
            // Integer instanceRunTime = postInstanceService.findPostInstanceRunTime(s);
            // if (!Objects.equals(instanceRunTime,0)){
            //     PostprocessInstance postInstance = findPostInstance(s);
            //     postInstance.setPostTime(instanceRunTime);
            //     postInstanceService.updatePostInstance(postInstance);
            //     tasksExecService.stopThread(s);
            // }
            postIdOrPostInstanceId.remove(s);
            postInstanceIdOrPostInstance.remove(s);
        }
    }






}



































