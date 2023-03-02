package net.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.dao.PipelineTasksDao;
import net.tiklab.matflow.task.task.entity.PipelineTasksEntity;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.PipelineTasks;
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
public class PipelineTasksServiceImpl implements PipelineTasksService {

    @Autowired
    PipelineTasksDao tasksDao;

    @Autowired
    PipelineTaskDispatchService dispatchService;

    @Override
    public String createTasksOrTask(Tasks tasks) throws ApplicationException {
        Pipeline pipeline = tasks.getPipeline();
        int type = pipeline.getType();
        String pipelineId = pipeline.getId();
        int taskType = tasks.getTaskType();
        findTypeTasks(pipelineId,taskType);
        int taskSort ;
        PipelineTasks pipelineTasks = new PipelineTasks(PipelineUtil.date(1), taskType);
        pipelineTasks.setPipelineId(pipelineId);
        //添加阶段id
        if (type == 2){
            pipelineTasks.setStagesId(tasks.getStagesId());
            taskSort = initSort(tasks.getStagesId(), tasks.getTaskSort(), taskType,type);
        }else {
            taskSort = initSort(pipelineId, tasks.getTaskSort(), taskType,type);
        }
        pipelineTasks.setTaskSort(taskSort);
        String tasksId = createTasks(pipelineTasks);
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
        List<PipelineTasks> list = finAllPipOrStageTasks(pipelineId,1);
        if (list.size() == 0 || taskType > 10){
            return ;
        }
        for (PipelineTasks tasks : list) {
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
        List<PipelineTasks> list = finAllPipOrStageTasks(id , type);
        if (list.size() == 0){
            return 1;
        }
        //插入的为代码源
        if (taskType < 10){
            for (PipelineTasks tasks : list) {
                tasks.setTaskSort(tasks.getTaskSort()+1);
                updateTasks(tasks);
            }
            return 1;
        }

        //更新顺序
        for (PipelineTasks tasks : list) {
            if (tasks.getTaskSort() < taskSort ){
                continue;
            }
            tasks.setTaskSort(tasks.getTaskSort()+1);
            updateTasks(tasks);
        }
        return taskSort;

    }

    @Override
    public void updateTasksTask(Tasks config) {
        String configId = config.getTaskId();
        Object values = config.getValues();
        int taskType = config.getTaskType();
        String object = JSON.toJSONString(values);
        Tasks tasks =JSON.parseObject(object, Tasks.class);
        //更新名称
        if (PipelineUtil.isNoNull(tasks.getName())) {
            PipelineTasks task = findOneTasks(configId);
            updateTasks(task);
            return;
        }
        //更新任务字段值
        dispatchService.updateDifferentTask(configId,taskType,values);
    }

    @Override
    public void deleteTasksOrTask(String tasksId) {
        PipelineTasks tasks = findOneTasks(tasksId);

        //删除
        dispatchService.deleteDifferentTask(tasksId,tasks.getTaskType());
        deleteTasks(tasksId);
        String id = tasks.getPipelineId();
        int type = 1;
        if (tasks.getStagesId() != null){
            type = 2;
            id = tasks.getStagesId();
        }
        List<PipelineTasks> list = finAllPipOrStageTasks(id,type);
        for (PipelineTasks pipelineTasks : list) {
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
        List<PipelineTasks> list = finAllPipOrStageTasks(pipelineId,pipelineType);
        if (list.size() == 0){
            return;
        }
        for (PipelineTasks tasks : list) {
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            dispatchService.deleteDifferentTask(configId,taskType);
            deleteTasks(configId);
        }
    }

    @Override
    public List<PipelineTasks> finAllPipOrStageTasks(String id,int pipelineType) {
        List<PipelineTasks> allTasks = findAllTasks();
        if (allTasks == null){
            return Collections.emptyList();
        }
        List<PipelineTasks> list = new ArrayList<>();
        for (PipelineTasks tasks : allTasks) {
            String pipelineId = tasks.getPipelineId();
            if (pipelineType == 2){
                pipelineId = tasks.getStagesId();
            }
            if (!id.equals(pipelineId)){
                continue;
            }
            list.add(tasks);
        }
        list.sort(Comparator.comparing(PipelineTasks::getTaskSort));
        return list;
    }

    @Override
    public List<Object> findAllTasksOrTask(String id, int type) {
        List<PipelineTasks> list = finAllPipOrStageTasks(id,type);
        List<Object> arrayList = new ArrayList<>();
        if (list.size() == 0){
            return null;
        }
        for (PipelineTasks tasks : list) {
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
        PipelineTasks tasks = findOneTasks(configId);
        int taskSort = tasks.getTaskSort();

        int taskType = tasks.getTaskType();
        return dispatchService.findOneDifferentTask(configId, taskType, taskSort,"tasksName");
    }

    @Override
    public List<String> validTasksMustField(String pipelineId , int type){
        List<PipelineTasks> list = finAllPipOrStageTasks(pipelineId,type);
        if (list == null || list.size() == 0){
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (PipelineTasks tasks : list) {
            String configId = tasks.getTaskId();
            int taskType = tasks.getTaskType();
            dispatchService.validDifferentTaskMastField(configId,taskType,stringList);
        }
        return stringList;
    }

    //创建
    private String createTasks(PipelineTasks tasks){
        PipelineTasksEntity tasksEntity = BeanMapper.map(tasks, PipelineTasksEntity.class);
        return tasksDao.createConfigure(tasksEntity);
    }

    //删除
    private void deleteTasks(String tasksId){
       tasksDao.deleteConfigure(tasksId);
    }

    //更新
    private void updateTasks(PipelineTasks tasks){
        PipelineTasksEntity tasksEntity = BeanMapper.map(tasks, PipelineTasksEntity.class);
        tasksDao.updateConfigure(tasksEntity);
    }

    //查询单个
    @Override
    public PipelineTasks findOneTasks(String tasksId){
        PipelineTasksEntity tasksEntity = tasksDao.findOneConfigure(tasksId);
        return BeanMapper.map(tasksEntity, PipelineTasks.class);
    }

    //查询所有
    private List<PipelineTasks> findAllTasks(){
        List<PipelineTasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, PipelineTasks.class);
    }


















}

















