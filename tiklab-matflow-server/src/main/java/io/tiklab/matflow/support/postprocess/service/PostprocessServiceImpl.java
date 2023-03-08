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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PostprocessServiceImpl implements PostprocessService {

    @Autowired
    private PostprocessDao postprocessDao;

    @Autowired
    private TasksService tasksService;

    @Override
    public String createPostTask(Postprocess postprocess) {
        PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
        postprocessEntity.setCreateTime(PipelineUtil.date(1));
        String postId = postprocessDao.createPost(postprocessEntity);
        Tasks tasks = new Tasks();
        tasks.setValues(postprocess.getValues());
        tasks.setTaskSort(1);
        tasks.setTaskType(postprocess.getTaskType());
        tasks.setPostprocessId(postId);
        tasksService.createTasksOrTask(tasks);
        return postId;
    }


    public List<Postprocess> findAllPipelinePostTask(String pipelineId){
        List<Postprocess> allPost = findAllPost();

        if (allPost.isEmpty()){
           return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : allPost) {
            String id = postprocess.getPipelineId();
            if (id == null || !id.equals(pipelineId)){
                continue;
            }
            String postprocessId = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(postprocessId);
            postprocess.setTask(tasks);
            list.add(postprocess);
        }
        return list;
    }

    @Override
    public  List<Postprocess> findAllTaskPostTask(String taskId) {
        List<Postprocess> allPost = findAllPost();

        if (allPost.isEmpty()){
            return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : allPost) {
            String id = postprocess.getTaskId();
            if (id == null || !id.equals(taskId)){
                continue;
            }
            String id1 = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(id1);
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
        task.setValues(values);
        tasksService.updateTasksTask(task);
    }

    @Override
    public Postprocess findOnePost(String postId) {
        PostprocessEntity postprocessEntity = postprocessDao.findOnePost(postId);
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
