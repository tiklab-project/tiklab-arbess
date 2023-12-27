package io.thoughtware.matflow.support.postprocess.service;

import io.thoughtware.matflow.support.postprocess.model.Postprocess;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.support.postprocess.dao.PostprocessDao;
import io.thoughtware.matflow.support.postprocess.entity.PostprocessEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class PostprocessServiceImpl implements PostprocessService {

    @Autowired
    PostprocessDao postprocessDao;

    @Autowired
    TasksService tasksService;

    @Override
    public String createPostTask(Postprocess postprocess) {
        if (Objects.isNull(postprocess.getTaskType())){
            throw new ApplicationException("taskType不能为空！");
        }

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

        // 设置默认名称
        if (Objects.isNull(postprocess.getPostName())){
            String name = tasksService.initDifferentTaskName(postprocess.getTaskType());
            postprocess.setPostName(name);
        }

        PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
        postprocessEntity.setCreateTime(PipelineUtil.date(1));
        String postId = postprocessDao.createPost(postprocessEntity);
        Tasks tasks = new Tasks();
        tasks.setTaskSort(1);
        tasks.setTaskType(postprocess.getTaskType());
        tasks.setPostprocessId(postId);
        tasks.setValues(postprocess.getValues());
        tasksService.createTasksOrTask(tasks);
        return postId;
    }

    @Override
    public List<Postprocess> findAllPipelinePostTask(String pipelineId){
        List<PostprocessEntity> allPostEntity = postprocessDao.findPipelinePost(pipelineId);
        List<Postprocess> postprocessList = BeanMapper.mapList(allPostEntity, Postprocess.class);
        if (postprocessList.isEmpty()){
           return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : postprocessList) {
            String postprocessId = postprocess.getPostId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(postprocessId);
            postprocess.setTask(tasks);
            String taskType = tasks.getTaskType();
            postprocess.setTaskType(taskType);
            list.add(postprocess);
        }
        return list;
    }

    @Override
    public void clonePostTask(String pipelineId,String clonePipelineId){
        List<PostprocessEntity> allPostEntity = postprocessDao.findPipelinePost(pipelineId);
        List<Postprocess> postprocessList = BeanMapper.mapList(allPostEntity, Postprocess.class);
        for (Postprocess postprocess : postprocessList) {
            postprocess.setPipelineId(clonePipelineId);
            PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
            String clonePostId = postprocessDao.createPost(postprocessEntity);
            tasksService.clonePostTasks(postprocess.getPostId(),clonePostId);
        }
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
            String id = postprocess.getPostId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(id);
            String taskType = tasks.getTaskType();
            postprocess.setTaskType(taskType);
            postprocess.setTask(tasks);
            list.add(postprocess);
        }
        postprocessList.sort(Comparator.comparing(Postprocess::getCreateTime).reversed());
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
        String postprocessId = postprocess.getPostId();
        Postprocess onePost = findOnePost(postprocessId);
        onePost.setPostName(postprocess.getPostName());
        postprocessDao.updatePost(BeanMapper.map(onePost,PostprocessEntity.class));

        Tasks task = tasksService.findOnePostTask(postprocessId);
        Object values = postprocess.getValues();
        task.setTask(values);
        task.setValues(values);
        task.setTaskType(postprocess.getTaskType());
        tasksService.updateTasksTask(task);
    }

    public void updatePost(Postprocess postprocess){
        postprocessDao.updatePost(BeanMapper.map(postprocess,PostprocessEntity.class));
    }

    @Override
    public Postprocess findOnePostOrTask(String postprocessId) {
        Postprocess postprocess = findOnePost(postprocessId);
        String id = postprocess.getPostId();
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
