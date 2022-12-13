package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineStagesTaskDao;
import net.tiklab.matflow.definition.entity.PipelineStagesTaskEntity;
import net.tiklab.matflow.definition.model.PipelineConfig;
import net.tiklab.matflow.definition.model.PipelineStagesTask;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.task.server.PipelineTaskCommonServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
@Exporter
public class PipelineStagesTaskServerImpl implements PipelineStagesTaskServer {

    @Autowired
    private PipelineStagesTaskDao stagesTaskDao;

    @Autowired
    private PipelineTaskCommonServer commonServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return stagesTaskId
     */
    @Override
    public String createStagesTasksTask(PipelineConfig config) {
        String stagesId = config.getStagesId();
        int taskType = config.getTaskType();
        int taskSort = initSort(stagesId,config.getTaskSort()) ;
        PipelineStagesTask stagesTask = new PipelineStagesTask(PipelineUntil.date(1),taskType,taskSort,stagesId);
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
        List<PipelineStagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks.size() == 0){
            return 1;
        }
        for (PipelineStagesTask stagesTask : allStagesTasks) {
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
    public void deleteStagesTasksTask(String configId) {
        PipelineStagesTask stagesTask = findOneStagesTask(configId);
        int taskType = stagesTask.getTaskType();
        commonServer.deleteTaskConfig(configId,taskType);
        int taskSort = stagesTask.getTaskSort();
        String stagesId = stagesTask.getStagesId();
        deleteStagesTask(configId);
        List<PipelineStagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks == null || allStagesTasks.size()==0){
            return;
        }
        for (PipelineStagesTask task : allStagesTasks) {
            int sort = task.getTaskSort();
            if (sort < taskSort){
                continue;
            }
            task.setTaskSort(task.getTaskSort()-1);
            updateStagesTask(task);
        }
    }

    /**
     * 删除所有配置及任务
     * @param stagesId 阶段id
     */
    @Override
    public void deleteAllStagesTasksTask(String stagesId) {
        List<PipelineStagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        if (allStagesTasks.size() == 0){
            return;
        }
        for (PipelineStagesTask stagesTask : allStagesTasks) {
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
    public void updateStagesTasksTask(PipelineConfig config) {
        Object values = config.getValues();
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        commonServer.updateTaskConfig(configId,taskType,values);
    }

    /**
     * 查询阶段配置
     * @param stagesId 阶段id
     * @return 配置
     */
    @Override
    public List<PipelineStagesTask> findAllStagesTasks(String stagesId) {
        List<PipelineStagesTask> allStagesTask = findAllStagesTask();
        if (allStagesTask == null){
            return Collections.emptyList();
        }
        List<PipelineStagesTask> list = new ArrayList<>();
        for (PipelineStagesTask stagesTask : allStagesTask) {
            String id = stagesTask.getStagesId();
            if (!id.equals(stagesId)){
                continue;
            }
            list.add(stagesTask);
        }
        list.sort(Comparator.comparing(PipelineStagesTask::getTaskSort));
        return list;
    }

    /**
     * 查询流水线所有配置及任务
     * @param stagesId 阶段id
     * @return 任务
     */
    @Override
    public List<Object> findAllStagesTasksTask(String stagesId) {
        List<PipelineStagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        List<Object> list = new ArrayList<>();
        allStagesTasks.sort(Comparator.comparing(PipelineStagesTask::getTaskSort));
        for (PipelineStagesTask stagesTask : allStagesTasks) {
            String configId = stagesTask.getConfigId();
            int taskSort = stagesTask.getTaskSort();
            int taskType = stagesTask.getTaskType();
            Object config = commonServer.findOneTaskConfig(configId, taskType, taskSort);
            list.add(config);
        }
        return list;
    }

    /**
     * 效验配置必填字段
     * @param stagesId 流水线id
     */
    @Override
    public void validAllConfig(String stagesId,List<String> list){
        List<PipelineStagesTask> allStagesTasks = findAllStagesTasks(stagesId);
        for (PipelineStagesTask stagesTask : allStagesTasks) {
            int taskType = stagesTask.getTaskType();
            String configId = stagesTask.getConfigId();
            commonServer.validTaskConfig(configId,taskType,list);
        }
    }

    //创建
    public String createStagesTask(PipelineStagesTask pipelineStagesTask) {
        PipelineStagesTaskEntity stagesTaskEntity = BeanMapper.map(pipelineStagesTask, PipelineStagesTaskEntity.class);
        return stagesTaskDao.createStagesTask(stagesTaskEntity);
    }

    //删除
    public void deleteStagesTask(String stagesTaskId) {
        stagesTaskDao.deleteStagesTask(stagesTaskId);
    }

    //更新
    public void updateStagesTask(PipelineStagesTask pipelineStagesTask) {
        PipelineStagesTaskEntity stagesTaskEntity = BeanMapper.map(pipelineStagesTask, PipelineStagesTaskEntity.class);
        stagesTaskDao.updateStagesTask(stagesTaskEntity);
    }

    //查询单个
    public PipelineStagesTask findOneStagesTask(String stagesTaskId) {
        PipelineStagesTaskEntity stagesTask = stagesTaskDao.findOneStagesTask(stagesTaskId);
        return BeanMapper.map(stagesTask, PipelineStagesTask.class);
    }

    //查询所有
    public List<PipelineStagesTask> findAllStagesTask() {
        List<PipelineStagesTaskEntity> allStagesTask = stagesTaskDao.findAllStagesTask();
        return BeanMapper.mapList(allStagesTask,PipelineStagesTask.class);
    }



}





















