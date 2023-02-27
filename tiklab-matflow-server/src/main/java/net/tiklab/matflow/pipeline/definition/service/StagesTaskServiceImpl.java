package net.tiklab.matflow.pipeline.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.pipeline.definition.dao.StagesTaskDao;
import net.tiklab.matflow.pipeline.definition.entity.StagesTaskEntity;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.pipeline.definition.model.StagesTask;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.service.PipelineTaskDispatchService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
@Exporter
public class StagesTaskServiceImpl implements StagesTaskService {

    @Autowired
    private StagesTaskDao stagesTaskDao;

    @Autowired
    private PipelineTaskDispatchService commonServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return stagesTaskId
     */
    @Override
    public String createStagesTasksTask(Tasks config) {
        String stagesId = config.getStagesId();
        int taskType = config.getTaskType();
        int taskSort = initSort(stagesId,config.getTaskSort()) ;
        StagesTask stagesTask =
                new StagesTask(PipelineUtil.date(1),taskType,taskSort,stagesId);
        String name = commonServer.initName(taskType);
        stagesTask.setName(name);
        String taskId = createStagesTask(stagesTask);
        commonServer.createTaskConfig(taskId,taskType);
        return taskId;
    }

    /**
     * 创建时配置顺序
     * @param stagesId 阶段id
     * @param taskSort 顺序
     * @return 顺序
     */
    private Integer initSort(String stagesId,int taskSort){
        List<StagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks.size() == 0){
            return 1;
        }
        for (StagesTask stagesTask : allStagesTasks) {
            int sort = stagesTask.getTaskSort();
            if (sort < taskSort){
                continue;
            }
            stagesTask.setTaskSort(stagesTask.getTaskSort()+1);
            updateStagesTask(stagesTask);
        }
        return taskSort;
    }

    /**
     * 删除配置及任务
     * @param configId Id
     */
    @Override
    public boolean deleteStagesTasksTask(String configId) {
        StagesTask stagesTask = findOneStagesTask(configId);
        int taskType = stagesTask.getTaskType();
        //删除任务
        commonServer.deleteTaskConfig(configId,taskType);
        int taskSort = stagesTask.getTaskSort();
        String stagesId = stagesTask.getStagesId();

        //删除阶段
        stagesTaskDao.deleteStagesTask(configId);

        // this.deleteStagesTask(configId);
        List<StagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks == null || allStagesTasks.size()==0){
            return false;
        }
        //更新顺序
        for (StagesTask task : allStagesTasks) {
            int sort = task.getTaskSort();
            if (sort < taskSort){
                continue;
            }
            task.setTaskSort(task.getTaskSort()-1);
            updateStagesTask(task);
        }
        return true;
    }

    /**
     * 删除所有配置及任务
     * @param stagesId 阶段id
     */
    @Override
    public void deleteAllStagesTasksTask(String stagesId) {
        List<StagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks.size() == 0){
            return;
        }
        for (StagesTask stagesTask : allStagesTasks) {
            int taskType = stagesTask.getTaskType();
            String configId = stagesTask.getConfigId();
            commonServer.deleteTaskConfig(configId,taskType);
            deleteStagesTask(configId);
        }
    }

    /**
     * 更新配置及任务
     * @param config 配置
     */
    @Override
    public void updateStagesTasksTask(Tasks config) {
        Object values = config.getValues();
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        String object = JSON.toJSONString(values);
        Tasks tasks =JSON.parseObject(object, Tasks.class);
        if (PipelineUtil.isNoNull(tasks.getName())) {
            StagesTask task = findOneStagesTask(configId);
            task.setName(tasks.getName());
            updateStagesTask(task);
            return;
        }
        commonServer.updateTaskConfig(configId,taskType,values);
    }

    /**
     * 查询阶段配置
     * @param stagesId 阶段id
     * @return 配置
     */
    @Override
    public List<StagesTask> findAllStagesTasks(String stagesId) {
        List<StagesTask> allStagesTask = findAllStagesTask();
        if (allStagesTask == null){
            return Collections.emptyList();
        }
        List<StagesTask> list = new ArrayList<>();
        for (StagesTask stagesTask : allStagesTask) {
            String id = stagesTask.getStagesId();
            if (!id.equals(stagesId)){
                continue;
            }
            list.add(stagesTask);
        }
        list.sort(Comparator.comparing(StagesTask::getTaskSort));
        return list;
    }

    /**
     * 查询流水线所有配置及任务
     * @param stagesId 阶段id
     * @return 任务
     */
    @Override
    public List<Object> findAllStagesTasksTask(String stagesId) {
        List<StagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        List<Object> list = new ArrayList<>();
        allStagesTasks.sort(Comparator.comparing(StagesTask::getTaskSort));
        for (StagesTask stagesTask : allStagesTasks) {
            String configId = stagesTask.getConfigId();
            int taskSort = stagesTask.getTaskSort();
            int taskType = stagesTask.getTaskType();
            String taskName = stagesTask.getName();
            Object config = commonServer.findOneTaskConfig(configId, taskType, taskSort,taskName);
            list.add(config);
        }
        return list;
    }

    /**
     * 获取配置详情
     * @param configId 配置id
     * @return 详情
     */
    public Object findOneStagesTasksTask(String configId){
        StagesTask stagesTask = findOneStagesTask(configId);
        int taskSort = stagesTask.getTaskSort();
        int taskType = stagesTask.getTaskType();
        String taskName = stagesTask.getName();
        return commonServer.findOneTaskConfig(configId, taskType, taskSort,taskName);
    }

    /**
     * 效验配置必填字段
     * @param stagesId 流水线id
     */
    @Override
    public void validAllConfig(String stagesId,List<String> list){
        List<StagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        for (StagesTask stagesTask : allStagesTasks) {
            int taskType = stagesTask.getTaskType();
            String configId = stagesTask.getConfigId();
            commonServer.validTaskConfig(configId,taskType,list);
        }
    }

    //创建
    public String createStagesTask(StagesTask stagesTask) {
        StagesTaskEntity stagesTaskEntity = BeanMapper.map(stagesTask, StagesTaskEntity.class);
        return stagesTaskDao.createStagesTask(stagesTaskEntity);
    }

    //删除
    public void deleteStagesTask(String stagesTaskId) {
        stagesTaskDao.deleteStagesTask(stagesTaskId);
    }

    //更新
    public void updateStagesTask(StagesTask stagesTask) {
        StagesTaskEntity stagesTaskEntity = BeanMapper.map(stagesTask, StagesTaskEntity.class);
        stagesTaskDao.updateStagesTask(stagesTaskEntity);
    }

    //查询单个
    public StagesTask findOneStagesTask(String stagesTaskId) {
        StagesTaskEntity stagesTask = stagesTaskDao.findOneStagesTask(stagesTaskId);
        return BeanMapper.map(stagesTask, StagesTask.class);
    }

    //查询所有
    public List<StagesTask> findAllStagesTask() {
        List<StagesTaskEntity> allStagesTask = stagesTaskDao.findAllStagesTask();
        return BeanMapper.mapList(allStagesTask, StagesTask.class);
    }



}





















