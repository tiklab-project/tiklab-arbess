package net.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.dao.TasksDao;
import net.tiklab.matflow.task.task.entity.TasksEntity;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.model.TaskFacade;
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

    @Override
    public String createTasksOrTask(TaskFacade taskFacade) throws ApplicationException {
        Pipeline pipeline = taskFacade.getPipeline();
        int type = pipeline.getType();
        String pipelineId = pipeline.getId();
        int taskType = taskFacade.getTaskType();
        findTypeTasks(pipelineId,taskType);
        int taskSort ;
        Tasks tasks = new Tasks(PipelineUtil.date(1), taskType);

        //添加阶段id
        if (type == 2){
            tasks.setStagesId(taskFacade.getStagesId());
            taskSort = initSort(taskFacade.getStagesId(), taskFacade.getTaskSort(), taskType,type);
        }else {
            tasks.setPipelineId(pipelineId);
            taskSort = initSort(pipelineId, taskFacade.getTaskSort(), taskType,type);
        }
        tasks.setTaskSort(taskSort);

        String taskName = dispatchService.initDifferentTaskName(taskType);
        tasks.setTaskName(taskName);

        String tasksId = createTasks(tasks);
        //创建任务
        dispatchService.createDifferentTask(tasksId,taskType);
        return tasksId;
    }

    /**
     * 判断是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId,int taskType)
            throws ApplicationException {
        List<Tasks> list = finAllPipOrStages(pipelineId,1);
        if (list.size() == 0 || taskType > 10){
            return ;
        }
        for (Tasks tasks : list) {
            int type = tasks.getTaskType();
            if (type  > 10){
                continue;
            }
            throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
        }
    }

    /**
     * 初始化配置顺序
     * @param id id
     * @param taskSort 插入顺序
     * @param taskType 任务类型
     * @param type 流水线类型
     * @return 顺序
     */
    private Integer initSort(String id, int taskSort,int taskType,int type){
        List<Tasks> list = finAllPipOrStages(id , type);
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
    public void updateTasksTask(TaskFacade config) {
        String configId = config.getTaskId();
        Object values = config.getValues();
        int taskType = config.getTaskType();
        String object = JSON.toJSONString(values);
        TaskFacade taskFacade =JSON.parseObject(object, TaskFacade.class);
        //更新名称
        if (PipelineUtil.isNoNull(taskFacade.getName())) {
            Tasks task = findOneTasks(configId);
            updateTasks(task);
            return;
        }
        //更新任务字段值
        dispatchService.updateDifferentTask(configId,taskType,values);
    }

    @Override
    public void deleteTasksOrTask(String tasksId) {
        Tasks tasks = findOneTasks(tasksId);

        //删除
        dispatchService.deleteDifferentTask(tasksId,tasks.getTaskType());
        deleteTasks(tasksId);
        String id = tasks.getPipelineId();
        int type = 1;
        if (tasks.getStagesId() != null){
            type = 2;
            id = tasks.getStagesId();
        }
        List<Tasks> list = finAllPipOrStages(id,type);
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
    public void deleteAllTasksOrTask(String pipelineId,int pipelineType) {
        List<Tasks> list = finAllPipOrStages(pipelineId,pipelineType);
        if (list.size() == 0){
            return;
        }
        for (Tasks tasks : list) {
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            dispatchService.deleteDifferentTask(configId,taskType);
            deleteTasks(configId);
        }
    }

    @Override
    public List<Tasks> finAllPipOrStages(String id, int pipelineType) {
        List<Tasks> allTasks = findAllTasks();
        if (allTasks == null){
            return Collections.emptyList();
        }
        List<Tasks> list = new ArrayList<>();
        for (Tasks tasks : allTasks) {
            if (pipelineType == 1){
                String pipelineId = tasks.getPipelineId();
                if (!id.equals(pipelineId)){
                    continue;
                }
                list.add(tasks);
            }
            if (pipelineType == 2){
                String stagesId = tasks.getStagesId();
                if (!id.equals(stagesId)){
                    continue;
                }
                list.add(tasks);
            }
        }
        list.sort(Comparator.comparing(Tasks::getTaskSort));
        return list;
    }

    @Override
    public List<Object> findAllTasksOrTask(String id, int type) {
        List<Tasks> list = finAllPipOrStages(id,type);
        List<Object> arrayList = new ArrayList<>();
        if (list.size() == 0){
            return null;
        }
        for (Tasks tasks : list) {
            int taskSort = tasks.getTaskSort();
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            if (configId == null){
                continue;
            }
            //查询任务
            Object config = dispatchService.findOneDifferentTask(configId, taskType, taskSort," ");
            arrayList.add(config);
        }
        return arrayList;
    }

    @Override
    public Object findOneTasksTask(String configId){
        Tasks tasks = findOneTasks(configId);
        int taskSort = tasks.getTaskSort();

        int taskType = tasks.getTaskType();
        return dispatchService.findOneDifferentTask(configId, taskType, taskSort,"tasksName");
    }

    @Override
    public List<String> validTasksMustField(String pipelineId , int type){
        List<Tasks> list = finAllPipOrStages(pipelineId,type);
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

    //创建
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

















