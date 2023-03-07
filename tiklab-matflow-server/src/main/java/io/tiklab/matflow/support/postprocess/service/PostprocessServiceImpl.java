package io.tiklab.matflow.support.postprocess.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.matflow.support.postprocess.dao.PostprocessDao;
import io.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
import io.tiklab.matflow.support.postprocess.model.Postprocess;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.message.model.TaskMessageType;
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

    /**
     * 创建后置配置及任务
     * @param postprocess message信息
     * @return 配置id
     */
    @Override
    public String createPostTask(Postprocess postprocess) {
        PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
        postprocessEntity.setCreateTime(PipelineUtil.date(1));
        String postId = postprocessDao.createPost(postprocessEntity);

        String object = JSON.toJSONString(postprocess.getValues());
        TaskMessageType message = JSON.parseObject(object, TaskMessageType.class);
        Tasks tasks = new Tasks();
        tasks.setValues(message);
        tasks.setTaskSort(1);
        tasks.setTaskType(postprocess.getTaskType());
        tasks.setPostprocessId(postId);
        tasksService.createTasksOrTask(tasks);
        return postId;
    }


    public List<Tasks> findAllPipelinePostTask(String pipelineId){
        List<Postprocess> allPost = findAllPost();

        if (allPost.isEmpty()){
           return Collections.emptyList();
        }
        List<Tasks> list = new ArrayList<>();
        for (Postprocess postprocess : allPost) {
            String id = postprocess.getPipelineId();
            if (id == null || !id.equals(pipelineId)){
                continue;
            }
            String id1 = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(id1);
            list.add(tasks);
        }
        return list;
    }

    @Override
    public List<Tasks> findAllTaskPostTask(String taskId) {
        List<Postprocess> allPost = findAllPost();

        if (allPost.isEmpty()){
            return Collections.emptyList();
        }
        List<Tasks> list = new ArrayList<>();
        for (Postprocess postprocess : allPost) {
            String id = postprocess.getTaskId();
            if (id == null || !id.equals(taskId)){
                continue;
            }
            String id1 = postprocess.getPostprocessId();
            Tasks tasks = tasksService.findOnePostTaskOrTask(id1);
            list.add(tasks);
        }
        return list;
    }

    /**
     * 删除配置及任务
     * @param postId messageId
     */
    @Override
    public void deletePostTask(String postId) {
        Tasks postTask = tasksService.findOnePostTask(postId);
        String taskId = postTask.getTaskId();
        tasksService.deleteTasksOrTask(taskId);
        postprocessDao.deletePost(postId);
    }

    /**
     * 根据任务id查询后置配置
     * @param taskId 流水线id
     * @return 配置
     */
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
        list.sort(Comparator.comparing(Postprocess::getCreateTime));
        return list;
    }

    @Override
    public void updatePostTask(Postprocess postprocess) {
        Object values = postprocess.getValues();
        String taskId = postprocess.getTaskId();
        Tasks tasks = new Tasks();
        tasks.setTaskId(taskId);
        tasks.setValues(values);
        tasksService.updateTasksTask(tasks);
    }

    //查询单个
    @Override
    public Postprocess findOnePost(String postId) {
        PostprocessEntity postprocessEntity = postprocessDao.findOnePost(postId);
        return BeanMapper.map(postprocessEntity, Postprocess.class);

    }

    //查询所有
    @Override
    public List<Postprocess> findAllPost() {
        return BeanMapper.mapList(postprocessDao.findAllPost(), Postprocess.class);
    }

    @Override
    public List<Postprocess> findAllPostList(List<String> idList) {
        return BeanMapper.mapList(postprocessDao.findAllPostList(idList), Postprocess.class);
    }



}
