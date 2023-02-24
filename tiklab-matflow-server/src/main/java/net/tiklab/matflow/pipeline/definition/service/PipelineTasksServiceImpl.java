package net.tiklab.matflow.pipeline.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.dao.PipelineTasksDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineTasksEntity;
import net.tiklab.matflow.pipeline.definition.model.PipelineConfig;
import net.tiklab.matflow.pipeline.definition.model.PipelineTasks;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.common.service.PipelineTaskCommonService;
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
    PipelineTaskCommonService commonServer;


    /**
     * 创建配置及任务
     * @param config 配置信息
     * @return 配置id
     */
    @Override
    public String createTasksTask(PipelineConfig config) throws ApplicationException {
        String pipelineId = config.getPipeline().getId();

        int taskType = config.getTaskType();
        findTypeTasks(pipelineId,taskType);
        int taskSort = initSort(pipelineId, config.getTaskSort(), taskType);
        PipelineTasks tasks = new PipelineTasks(PipelineUntil.date(1),taskType,taskSort,pipelineId);
        String name = commonServer.initName(taskType);
        tasks.setName(name);
        String tasksId = createTasks(tasks);
        commonServer.createTaskConfig(tasksId,taskType);
        return tasksId;
    }

    /**
     * 判断是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId,int taskType) throws ApplicationException {
        List<PipelineTasks> list = finAllTasks(pipelineId);
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
     * @param pipelineId 流水线id
     * @param taskSort 插入顺序
     * @param taskType 类型
     * @return 顺序
     */
    private Integer initSort(String pipelineId, int taskSort,int taskType){
        List<PipelineTasks> list = finAllTasks(pipelineId);
        if (list.size() == 0){
            return taskSort;
        }
        //插入的为代码源
        if (taskType < 10){
            for (PipelineTasks tasks : list) {
                tasks.setTaskSort(tasks.getTaskSort()+1);
                updateTasks(tasks);
            }
            return 1;
        }

        for (PipelineTasks tasks : list) {
            if (tasks.getTaskSort() < taskSort ){
                continue;
            }
            tasks.setTaskSort(tasks.getTaskSort()+1);
            updateTasks(tasks);
        }
        return taskSort;

    }

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    @Override
    public void updateTasksTask(PipelineConfig config) {
        String configId = config.getConfigId();
        Object values = config.getValues();
        int taskType = config.getTaskType();
        String object = JSON.toJSONString(values);
        PipelineConfig pipelineConfig =JSON.parseObject(object, PipelineConfig.class);
        if (PipelineUntil.isNoNull(pipelineConfig.getName())) {
            PipelineTasks task = findOneTasks(configId);
            task.setName(pipelineConfig.getName());
            updateTasks(task);
            return;
        }
        commonServer.updateTaskConfig(configId,taskType,values);
    }

    /**
     * 删除配置及任务
     * @param tasksId 配置id
     */
    @Override
    public void deleteTasksTask(String tasksId) {
        PipelineTasks tasks = findOneTasks(tasksId);
        int taskType = tasks.getTaskType();
        //删除
        commonServer.deleteTaskConfig(tasksId,taskType);
        deleteTasks(tasksId);
        //更新顺序
        int taskSort = tasks.getTaskSort();
        String id = tasks.getPipeline().getId();
        List<PipelineTasks> list = finAllTasks(id);
        for (PipelineTasks pipelineTasks : list) {
            int sort = pipelineTasks.getTaskSort();

            if (sort == 1 || taskSort > sort){
                continue;
            }

            pipelineTasks.setTaskSort(pipelineTasks.getTaskSort()-1);
            updateTasks(pipelineTasks);
        }
    }

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    @Override
    public void deleteAllTasksTask(String pipelineId) {
        List<PipelineTasks> list = finAllTasks(pipelineId);
        if (list.size() == 0){
            return;
        }
        for (PipelineTasks tasks : list) {
            String configId = tasks.getConfigId();
            int taskType = tasks.getTaskType();
            commonServer.deleteTaskConfig(configId,taskType);
            deleteTasks(configId);
        }
    }

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineTasks> finAllTasks(String pipelineId) {
        List<PipelineTasks> allTasks = findAllTasks();
        if (allTasks == null){
            return Collections.emptyList();
        }
        List<PipelineTasks> list = new ArrayList<>();
        for (PipelineTasks tasks : allTasks) {
            String id = tasks.getPipeline().getId();
            if (!id.equals(pipelineId)){
                continue;
            }
            list.add(tasks);
        }
        list.sort(Comparator.comparing(PipelineTasks::getTaskSort));
        return list;
    }

    /**
     * 查询所有任务
     * @param pipelineId 流水线id
     * @return 任务集合
     */
    @Override
    public List<Object> findAllTasksTask(String pipelineId) {
        List<PipelineTasks> list = finAllTasks(pipelineId);
        List<Object> arrayList = new ArrayList<>();
        if (list.size() == 0){
            return null;
        }
        for (PipelineTasks tasks : list) {
            int taskSort = tasks.getTaskSort();
            String configId = tasks.getConfigId();
            String tasksName = tasks.getName();
            int taskType = tasks.getTaskType();
            if (configId == null){
                continue;
            }
            Object config = commonServer.findOneTaskConfig(configId, taskType, taskSort,tasksName);
            arrayList.add(config);
        }
        return arrayList;
    }

    /**
     * 获取配置详情
     * @param configId 配置id
     * @return 详情
     */
    public Object findOneTasksTask(String configId){
        PipelineTasks tasks = findOneTasks(configId);
        int taskSort = tasks.getTaskSort();
        String tasksName = tasks.getName();
        int taskType = tasks.getTaskType();
        return commonServer.findOneTaskConfig(configId, taskType, taskSort,tasksName);
    }

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    @Override
    public List<String> validAllConfig(String pipelineId){
        List<PipelineTasks> list = finAllTasks(pipelineId);
        if (list == null || list.size() == 0){
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (PipelineTasks tasks : list) {
            String configId = tasks.getConfigId();
            int taskType = tasks.getTaskType();
            commonServer.validTaskConfig(configId,taskType,stringList);
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
    private PipelineTasks findOneTasks(String tasksId){
        PipelineTasksEntity tasksEntity = tasksDao.findOneConfigure(tasksId);
        return BeanMapper.map(tasksEntity, PipelineTasks.class);
    }

    //查询所有
    private List<PipelineTasks> findAllTasks(){
        List<PipelineTasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, PipelineTasks.class);
    }


















}

















