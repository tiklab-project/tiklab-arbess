package io.tiklab.arbess.support.postprocess.service;

import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksExecService;
import io.tiklab.arbess.task.task.service.TasksService;
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
            task.setTaskSort(postprocess.getTaskSort());
            fileAddress = fileAddress + "/" +  postInstanceId;
            String taskInstanceId = tasksExecService.createTaskExecInstance(task, postInstanceId, 3, fileAddress);
            task.setInstanceId(taskInstanceId);

            postInstance.setTaskInstanceId(taskInstanceId);
            task.setTaskSort(postprocess.getTaskSort());
            postprocess.setTask(task);
            postprocess.setValues(task);
            postprocess.setInstanceId(postInstanceId);
        }
        return postprocessList;
    }


    public String findPostInstanceId(String postId){
        return postIdOrPostInstanceId.get(postId);
    }

    public PostprocessInstance findPostInstance(String postInstanceId){
        return postInstanceIdOrPostInstance.get(postInstanceId);
    }


    private List<String> findPipelineOrPostInstanceCache(String pipelineId){
        List<String> list = pipelineIdOrPostInstanceId.get(pipelineId);
        if (Objects.isNull(list)){
            return new ArrayList<>();
        }
        return list;
    }

}



































