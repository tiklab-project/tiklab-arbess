package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.support.postprocess.dao.PostprocessDao;
import io.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
import io.tiklab.matflow.support.postprocess.model.Postprocess;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class PostprocessServiceImpl implements PostprocessService {

    @Autowired
    private PostprocessDao postprocessDao;

    @Autowired
    private TasksService tasksService;

    @Override
    public String createPostTask(Postprocess postprocess) {
        String pipelineId = postprocess.getPipelineId();
        if (Objects.isNull(pipelineId)){
            List<PostprocessEntity> taskPost = postprocessDao.findTaskPost(postprocess.getTaskId());
            int size = taskPost.size();
            postprocess.setTaskSort(size);
        }else {
            List<PostprocessEntity> taskPost = postprocessDao.findPipelinePost(pipelineId);
            int size = taskPost.size();
            postprocess.setTaskSort(size);
        }
        PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
        postprocessEntity.setCreateTime(PipelineUtil.date(1));
        String postId = postprocessDao.createPost(postprocessEntity);
        Tasks tasks = new Tasks();
        tasks.setTask(postprocess.getValues());
        tasks.setTaskSort(1);
        tasks.setTaskType(postprocess.getTaskType());
        tasks.setPostprocessId(postId);
        tasksService.createTasksOrTask(tasks);
        return postId;
    }

    public List<Postprocess> findAllPipelinePostTask(String pipelineId){
        List<PostprocessEntity> allPostEntity = postprocessDao.findPipelinePost(pipelineId);
        List<Postprocess> postprocessList = BeanMapper.mapList(allPostEntity, Postprocess.class);
        if (postprocessList.isEmpty()){
           return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : postprocessList) {
            String postprocessId = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(postprocessId);
            postprocess.setTask(tasks);
            String taskType = tasks.getTaskType();
            postprocess.setTaskType(taskType);
            list.add(postprocess);
        }
        return list;
    }

    @Override
    public  List<Postprocess> findAllTaskPostTask(String taskId) {
        List<PostprocessEntity> allPostEntity = postprocessDao.findTaskPost(taskId);
        List<Postprocess> postprocessList = BeanMapper.mapList(allPostEntity, Postprocess.class);
        if (postprocessList.isEmpty()){
            return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : postprocessList) {
            String id = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(id);
            String taskType = tasks.getTaskType();
            postprocess.setTaskType(taskType);
            postprocess.setTask(tasks);
            list.add(postprocess);
        }
        return list;
    }

    @Override
    public void deletePostTask(String postprocessId) {
        Tasks postTask = tasksService.findOnePostTask(postprocessId);
        String taskId = postTask.getTaskId();
        tasksService.deleteTasksOrTask(taskId);
        postprocessDao.deletePost(postprocessId);
    }

    @Override
    public List<Postprocess> findAllPost(String taskId) {
        List<Postprocess> allPostprocess = findAllPost();
        if (allPostprocess == null){
            return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : allPostprocess) {
            String postTaskId = postprocess.getTaskId();
            if (!postTaskId.equals(taskId)){
                continue;
            }
            list.add(postprocess);
        }
        list.sort(Comparator.comparing(Postprocess::getCreateTime).reversed());
        return list;
    }

    @Override
    public void updatePostTask(Postprocess postprocess) {
        String postprocessId = postprocess.getPostprocessId();
        Tasks task = tasksService.findOnePostTask(postprocessId);
        Object values = postprocess.getValues();
        task.setTask(values);
        task.setValues(values);
        task.setTaskType(postprocess.getTaskType());
        tasksService.updateTasksTask(task);
    }

    @Override
    public Postprocess findOnePostOrTask(String postprocessId) {
        Postprocess postprocess = findOnePost(postprocessId);
        String id = postprocess.getPostprocessId();
        Tasks taskOrTask = tasksService.findOnePostTaskOrTask(id);
        postprocess.setTask(taskOrTask);
        postprocess.setTaskType(taskOrTask.getTaskType());
        return postprocess;
    }

    @Override
    public Postprocess findOnePost(String postprocessId) {
        PostprocessEntity postprocessEntity = postprocessDao.findOnePost(postprocessId);
        return BeanMapper.map(postprocessEntity, Postprocess.class);
    }

    @Override
    public List<Postprocess> findAllPost() {
        return BeanMapper.mapList(postprocessDao.findAllPost(), Postprocess.class);
    }

    @Override
    public List<Postprocess> findAllPostList(List<String> idList) {
        return BeanMapper.mapList(postprocessDao.findAllPostList(idList), Postprocess.class);
    }



}
