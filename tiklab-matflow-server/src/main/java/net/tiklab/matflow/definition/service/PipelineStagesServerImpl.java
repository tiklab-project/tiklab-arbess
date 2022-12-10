package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.dao.PipelineStagesDao;
import net.tiklab.matflow.definition.entity.PipelineStagesEntity;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineStagesServerImpl implements PipelineStagesServer {


    @Autowired
    PipelineStagesDao stagesDao;

    @Autowired
    PipelineStagesTaskServer stagesTaskServer;


    /**
     * 创建阶段及关联任务
     * @param config 阶段信息
     * @return 阶段id
     */
    @Override
    public String createStagesTask(PipelineConfig config){
        String stagesId = config.getStagesId();
        String pipelineId = config.getPipeline().getId();
        int taskType = config.getTaskType();
        int taskSort = config.getTaskSort();

        //存在阶段id并且插入的不是源码，直接创建配置及任务
        if (PipelineUntil.isNoNull(stagesId) && taskType > 10){
            stagesTaskServer.createStagesTasksTask(config);
            return stagesId;
        }

        PipelineStages pipelineStages = new PipelineStages(PipelineUntil.date(1),pipelineId,taskSort);
        pipelineStages.setCode(false);
        //如果是源码
        if (taskType < 10){
            //判断是否已经存在代码源
            findTypeTasks(pipelineId);
            pipelineStages.setCode(true);
        }
        //获取顺序
        int initStage = initStage(pipelineId,taskSort,taskType);
        //初始化信息
        pipelineStages.setTaskStage(initStage);
        stagesId = createStages(pipelineStages);
        config.setStagesId(stagesId);
        stagesTaskServer.createStagesTasksTask(config);
        return stagesId;
    }

    /**
     * 判断是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId) throws ApplicationException {
        List<PipelineStages> allStage = findAllStage(pipelineId);
        if (allStage == null || allStage.size() == 0){
            return;
        }
        String stagesId = allStage.get(0).getStagesId();
        List<PipelineStagesTask> list = stagesTaskServer.findAllStagesTasks(stagesId);
        for (PipelineStagesTask stagesTask : list) {
            int type = stagesTask.getTaskType();
            if (type > 10){
                continue;
            }
            throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
        }
    }

    /**
     * 阶段顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @param taskType 类型
     * @return 顺序
     */
    private Integer initStage(String pipelineId,int taskSort,int taskType){
        List<PipelineStages> allStagesConfig = findAllStage(pipelineId);
        if (allStagesConfig.size() == 0){
            return taskSort;
        }

        if (taskType < 10){
            for (PipelineStages stages : allStagesConfig) {
                stages.setTaskStage(stages.getTaskStage()+1);
                updateStages(stages);
            }
            return 1;
        }

        for (PipelineStages stages : allStagesConfig) {
            int stage = stages.getTaskStage();
            if (stage < taskSort){
                continue;
            }
            stages.setTaskStage(stages.getTaskStage()+1);
            updateStages(stages);
        }
        return taskSort;
    }

    /**
     * 查询流水线所有阶段
     * @param pipelineId 流水线id
     * @return 阶段集合
     */
    @Override
    public List<PipelineStages> findAllStage(String pipelineId){
        List<PipelineStages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : allStages) {
            String id = stages.getPipeline().getId();
            if (!id.equals(pipelineId)){
                continue;
            }
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
        return list;
    }

    /**
     * 根据流水线id查询所有阶段及任务
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineStages> findAllStagesTasks(String pipelineId){
        List<PipelineStages> allStages = findAllStage(pipelineId);
        if (allStages == null || allStages.size() == 0){
            return null;
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : allStages) {
            List<Object> allStagesConfig = stagesTaskServer.findAllStagesTasksTask(stages.getStagesId());
            stages.setTaskValues(allStagesConfig);
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
        return list;
    }

    /**
     * 删除阶段任务
     * @param configId 配置id
     */
    @Override
    public void deleteStagesTask(String configId){
        PipelineStagesTask stagesTask = stagesTaskServer.findOneStagesTask(configId);
        stagesTaskServer.deleteStagesTasksTask(configId);
        String stagesId = stagesTask.getStagesId();
        List<PipelineStagesTask> allStagesTasks = stagesTaskServer.findAllStagesTasks(stagesId);
        PipelineStages stages = findOneStages(stagesId);
        //删除配置与任务后阶段为空是删除阶段
        if (allStagesTasks == null ||allStagesTasks.size() == 0){
            deleteStages(stagesId);
        }
        String id = stages.getPipeline().getId();
        //删除阶段后更新阶段顺序
        List<PipelineStages> allStage = findAllStage(id);
        if (allStage == null || allStage.size() == 0){
            return;
        }
        for (PipelineStages pipelineStages : allStage) {
            int taskStage = pipelineStages.getTaskStage();
            if (taskStage > stages.getTaskStage()){
                pipelineStages.setTaskStage(pipelineStages.getTaskStage()-1);
                updateStages(pipelineStages);
            }
        }

    }

    /**
     * 更新配置及任务
     * @param config 配置id
     */
    @Override
    public void updateStagesTask(PipelineConfig config){
        stagesTaskServer.updateStagesTasksTask(config);
    }

    /**
     *  删除流水线所有阶段
     * @param pipelineId 流水线id
     */
    @Override
    public void  deleteAllStagesTask(String pipelineId){
       List<PipelineStages> allStage = findAllStage(pipelineId);
       if (allStage.size() == 0){
           return;
       }
       for (PipelineStages stages : allStage) {
           String stagesId = stages.getStagesId();
           List<PipelineStagesTask> allStagesTasks = stagesTaskServer.findAllStagesTasks(stagesId);
           if (allStagesTasks.size() != 0){
               deleteStages(stagesId);
               stagesTaskServer.deleteAllStagesTasksTask(stagesId);
           }
       }
   }

    /**
     * 根据阶段id查询所有任务配置
     * @param stagesId 阶段id
     * @return 任务配置
     */
    @Override
    public List<PipelineStagesTask> findAllStagesTask(String stagesId){
       return stagesTaskServer.findAllStagesTasks(stagesId);
    }

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    @Override
    public List<String> validAllConfig(String pipelineId){
        List<PipelineStages> allStage = findAllStage(pipelineId);
        if (allStage == null || allStage.size() == 0){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (PipelineStages stages : allStage) {
            String stagesId = stages.getStagesId();
            stagesTaskServer.validAllConfig(stagesId,list);
        }
        return list;
    }

    //创建阶段
    @Override
    public String createStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    //更新阶段
    @Override
    public void updateStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    //删除阶段
    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    //查询单个阶段
    @Override
    public PipelineStages findOneStages(String stageId) {
        PipelineStagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, PipelineStages.class);
    }

    //查询所有阶段
    @Override
    public List<PipelineStages> findAllStages() {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStages();
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

    @Override
    public List<PipelineStages> findAllStagesList(List<String> idList) {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

}
