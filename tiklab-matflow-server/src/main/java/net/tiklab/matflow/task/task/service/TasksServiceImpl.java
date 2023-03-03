package net.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.dao.TasksDao;
import net.tiklab.matflow.task.task.entity.TasksEntity;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class TasksServiceImpl implements TasksService {

    @Autowired
    TasksDao tasksDao;

    @Autowired
    TaskDispatchService dispatchService;

    /**
     * 初始化配置顺序
     * @param id id
     * @param taskSort 插入顺序
     * @param taskType 任务类型
     * @param type 1.流水线id 2.阶段id
     * @return 顺序
     */
    private Integer initSort(String id, int taskSort,int taskType,int type){
        List<Tasks> list ;
        if (type == 1){
            list = finAllPipelineTask(id);
        }else {
            list = finAllStageTask(id);
        }
        if (list.size() == 0){
            return 1;
        }
        //插入的为代码源
        if (taskType < 10){
            for (Tasks tasks : list) {
                tasks.setTaskSort(tasks.getTaskSort()+1);
                updateTasks(tasks);
            }
            return 1;
        }

        //更新顺序
        for (Tasks tasks : list) {
            if (tasks.getTaskSort() < taskSort ){
                continue;
            }
            tasks.setTaskSort(tasks.getTaskSort()+1);
            updateTasks(tasks);
        }
        return taskSort;

    }

    @Override
    public String createTasksOrTask(Tasks tasks) throws ApplicationException {

        int sort;
        int taskSort = tasks.getTaskSort();
        String stageId = tasks.getStageId();
        int taskType = tasks.getTaskType();
        //判断多任务是否存在代码源
        if (tasks.getPipelineId() != null && taskType < 10){
            findCode(tasks.getPipelineId());
        }
        if (stageId != null){
            sort = initSort(stageId, taskSort, taskType,2);
        }else {
            sort = initSort(tasks.getPipelineId(), taskSort, taskType,1);
        }
        tasks.setTaskSort(sort);
        String taskName = dispatchService.initDifferentTaskName(taskType);
        tasks.setTaskName(taskName);
        tasks.setCreateTime(PipelineUtil.date(1));

        String tasksId = createTasks(tasks);
        //创建任务
        dispatchService.createDifferentTask(tasksId,taskType,tasks.getValues());
        return tasksId;
    }

    private void findCode(String pipelineId){
        List<Tasks> tasks = finAllPipelineTask(pipelineId);
        if (tasks == null){
            return;
        }
        for (Tasks task : tasks) {
            int taskType = task.getTaskType();
            if (taskType < 10){
                throw new ApplicationException(50001,"代码源已存在");
            }
        }
    }

    @Override
    public void updateTasksTask(Tasks tasks) {
        String taskId = tasks.getTaskId();
        Object values = tasks.getValues();
        Tasks task = findOneTasks(taskId);
        int taskType = task.getTaskType();
        //更新任务字段值
        dispatchService.updateDifferentTask(taskId,taskType,values);
    }

    @Override
    public void updateTaskName(Tasks tasks) {
        Object values = tasks.getValues();
        String object = JSON.toJSONString(values);
        Tasks tasks1 = JSON.parseObject(object, Tasks.class);
        String taskId = tasks.getTaskId();
        Tasks task = findOneTasks(taskId);
        task.setTaskName(tasks1.getTaskName());
        updateTasks(task);
    }

    @Override
    public void deleteTasksOrTask(String tasksId) {
        Tasks tasks = findOneTasks(tasksId);

        //删除
        dispatchService.deleteDifferentTask(tasksId, tasks.getTaskType());
        deleteTasks(tasksId);

        List<Tasks> list;
        if (tasks.getStageId() != null){
            list = finAllStageTask(tasks.getStageId());
        }else {
            list = finAllPipelineTask(tasks.getPipelineId());
        }
        //更新顺序
        for (Tasks pipelineTasks : list) {
            int sort = pipelineTasks.getTaskSort();
            if (sort == 1 || tasks.getTaskSort() > sort){
                continue;
            }
            pipelineTasks.setTaskSort(sort-1);
            updateTasks(pipelineTasks);
        }
    }

    @Override
    public void deleteAllTasksOrTask(String id,int type) {
        List<Tasks> list;
        if (type == 1){
            list = finAllPipelineTask(id);
        }else {
            list = finAllStageTask(id);
        }

        if (list.size() == 0){
            return;
        }
        //循环删除任务
        for (Tasks tasks : list) {
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            dispatchService.deleteDifferentTask(configId,taskType);
            deleteTasks(configId);
        }
    }
    
    @Override
    public List<Tasks> finAllPipelineTask(String pipelineId) {
        List<Tasks> allTasks = findAllTasks();
        if (allTasks == null){
            return Collections.emptyList();
        }
        List<Tasks> list = new ArrayList<>();
        for (Tasks tasks : allTasks) {
            String id = tasks.getPipelineId();
            if (id == null || !id.equals(pipelineId)){
                continue;
            }
            list.add(tasks);
        }
        list.sort(Comparator.comparing(Tasks::getTaskSort));
        return list;
    }
    
    @Override
    public List<Tasks> finAllStageTask(String stageId) {
        List<Tasks> allTasks = findAllTasks();
        if (allTasks == null){
            return Collections.emptyList();
        }
        List<Tasks> list = new ArrayList<>();
        for (Tasks tasks : allTasks) {
            String id = tasks.getStageId();
            if (id == null || !id.equals(stageId)){
                continue;
            }
            list.add(tasks);
        }
        list.sort(Comparator.comparing(Tasks::getTaskSort));
        return list;
    }

    @Override
    public Tasks findOnePostTask(String postId) {
        return null;
    }

    @Override
    public Tasks findOnePostTaskOrTask(String postId) {
        return null;
    }

    @Override
    public List<Tasks> finAllPipelineTaskOrTask(String pipelineId) {
        List<Tasks> tasks = finAllPipelineTask(pipelineId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks task : tasks) {
            String taskId = task.getTaskId();
            int taskType = task.getTaskType();
            Object differentTask = dispatchService.findOneDifferentTask(taskId, taskType, 2, "");
            task.setTask(differentTask);
        }
        return tasks;
    }

    @Override
    public List<Tasks> finAllStageTaskOrTask(String stageId) {
        List<Tasks> tasks = finAllStageTask(stageId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks task : tasks) {
            int taskType = task.getTaskType();
            String taskId = task.getTaskId();
            Object differentTask = dispatchService.findOneDifferentTask(taskId, taskType, 2, "");
            task.setTask(differentTask);
        }
        return tasks;
    }

    @Override
    public Object findOneTasksTask(String configId){
        Tasks tasks = findOneTasks(configId);
        int taskSort = tasks.getTaskSort();

        int taskType = tasks.getTaskType();
        return dispatchService.findOneDifferentTask(configId, taskType, taskSort,"tasksName");
    }

    @Override
    public List<String> validTasksMustField(String id , int type){
        List<Tasks> list ;
        if (type == 1){
            list = finAllPipelineTask(id);
        }else {
            list = finAllStageTask(id);
        }
        if (list == null || list.size() == 0){
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (Tasks tasks : list) {
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            dispatchService.validDifferentTaskMastField(configId,taskType,stringList);
        }
        return stringList;
    }

    /**
     *
     * @param tasks
     * @return
     */
    private String createTasks(Tasks tasks){
        TasksEntity tasksEntity = BeanMapper.map(tasks, TasksEntity.class);
        return tasksDao.createConfigure(tasksEntity);
    }

    //删除
    private void deleteTasks(String tasksId){
       tasksDao.deleteConfigure(tasksId);
    }

    //更新
    private void updateTasks(Tasks tasks){
        TasksEntity tasksEntity = BeanMapper.map(tasks, TasksEntity.class);
        tasksDao.updateConfigure(tasksEntity);
    }

    //查询单个
    @Override
    public Tasks findOneTasks(String tasksId){
        TasksEntity tasksEntity = tasksDao.findOneConfigure(tasksId);
        return BeanMapper.map(tasksEntity, Tasks.class);
    }

    //查询所有
    private List<Tasks> findAllTasks(){
        List<TasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, Tasks.class);
    }


















}

















