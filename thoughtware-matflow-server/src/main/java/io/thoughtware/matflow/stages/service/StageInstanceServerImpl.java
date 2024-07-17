package io.thoughtware.matflow.stages.service;

import io.thoughtware.matflow.stages.entity.StageInstanceEntity;
import io.thoughtware.matflow.stages.model.StageInstance;
import io.thoughtware.matflow.stages.model.StageInstanceQuery;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.matflow.stages.dao.StageInstanceDao;
import io.thoughtware.matflow.task.task.model.TaskInstance;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阶段执行实例服务
 */
@Service
public class StageInstanceServerImpl implements StageInstanceServer{

    @Autowired
    StageInstanceDao stageInstanceDao;

    @Autowired
    TasksInstanceService tasksInstanceService;

    private static final Logger logger = LoggerFactory.getLogger(StageInstanceServerImpl.class);

    @Override
    public String createStageInstance(StageInstance stageInstance) {
        StageInstanceEntity instanceEntity =
                BeanMapper.map(stageInstance, StageInstanceEntity.class);
        return stageInstanceDao.createStagesInstance(instanceEntity);
    }

    @Override
    public void deleteAllMainStageInstance(String instanceId) {
        List<StageInstance> allStageInstance = findMainStageInstance(instanceId);
        for (StageInstance stageInstance : allStageInstance) {
            String id = stageInstance.getId();

            List<StageInstance> allOtherStageInstance = findOtherStageInstance(id);
            for (StageInstance instance : allOtherStageInstance) {
                String otherStageId = instance.getId();
                tasksInstanceService.deleteAllStageInstance(otherStageId);
                deleteStageInstance(otherStageId);
            }
            deleteStageInstance(id);
        }
    }

    @Override
    public List<String> findAllStageInstanceLogs(String instanceId){
        List<StageInstance> allStageInstance = findMainStageInstance(instanceId);
        List<String> list = new ArrayList<>();
        allStageInstance.forEach(stageInstance -> {
            List<StageInstance> allOtherStageInstance = findOtherStageInstance(stageInstance.getId());
            allOtherStageInstance.forEach(si -> {
                List<TaskInstance> allTaskInstance = tasksInstanceService.findAllStageInstance(si.getId());
                allTaskInstance.forEach(taskInstance -> {
                    String runLog = taskInstance.getLogAddress();
                    String readFile = PipelineFileUtil.readFile(runLog, 0);
                    list.add(readFile+ "\n");
                });
            });
        });
        return list;
    }

    @Override
    public void updateStageInstance(StageInstance stageInstance) {
        StageInstanceEntity instanceEntity =
                BeanMapper.map(stageInstance, StageInstanceEntity.class);
        stageInstanceDao.updateStagesInstance(instanceEntity);
    }

    @Override
    public StageInstance findOneStageInstance(String stageInstanceId) {
        StageInstanceEntity stagesInstanceEntity = stageInstanceDao.findOneStagesInstance(stageInstanceId);
        return BeanMapper.map(stagesInstanceEntity, StageInstance.class);
    }

    @Override
    public List<StageInstance> findMainStageInstance(String instanceId) {
        StageInstanceQuery stageInstanceQuery = new StageInstanceQuery();
        stageInstanceQuery.setInstanceId(instanceId);
        List<StageInstance> stageInstanceList = findStageInstanceList(stageInstanceQuery);
        if (stageInstanceList == null || stageInstanceList.isEmpty()){
            return Collections.emptyList();
        }
       return stageInstanceList;
    }

    @Override
    public List<StageInstance> findOtherStageInstance(String mainStageId) {

        StageInstanceQuery stageInstanceQuery = new StageInstanceQuery();
        stageInstanceQuery.setParentId(mainStageId);
        List<StageInstance> stageInstanceList = findStageInstanceList(stageInstanceQuery);
        if (stageInstanceList == null || stageInstanceList.isEmpty()){
            return Collections.emptyList();
        }
        return stageInstanceList;
    }

    @Override
    public List<StageInstance> findStageExecInstance(String instanceId){

        List<StageInstance> stageInstanceList = findMainStageInstance(instanceId);
        // 主阶段实例
        for (StageInstance stageInstance : stageInstanceList) {
            StringBuilder mainLog = new StringBuilder();
            int allTime = 0;
            String stageInstanceId = stageInstance.getId();

            // 从阶段实例
            List<StageInstance> otherStageInstanceList = findOtherStageInstance(stageInstanceId);
            String log = "\n";
            //并行阶段执行实例
            for (StageInstance otherInstance : otherStageInstanceList) {
                String otherStageInstanceId = otherInstance.getId();

                // 阶段任务
                List<TaskInstance> allTaskInstance = tasksInstanceService.findAllStageInstance(otherStageInstanceId);
                otherInstance.setTaskInstanceList(allTaskInstance);

                // 阶段日志,时间
                StringBuilder runLog = new StringBuilder();
                AtomicInteger stageRunTime = new AtomicInteger();
                allTaskInstance.forEach(taskInstance -> {
                    runLog.append(taskInstance.getRunLog());
                    stageRunTime.set(stageRunTime.get() + taskInstance.getRunTime());
                });
                otherInstance.setStageTime(stageRunTime.get());
                otherInstance.setRunLog(runLog.toString());
                if (!Objects.equals(runLog.toString(),log)){
                    mainLog.append(runLog).append(log);
                    allTime = allTime + otherInstance.getStageTime();
                }
            }
            stageInstance.setRunLog(mainLog.toString());
            stageInstance.setStageTime(allTime);
            otherStageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
            stageInstance.setStageInstanceList(otherStageInstanceList);
        }
        List<TaskInstance> list = tasksInstanceService.findStagePostRunMessage(instanceId);
        StageInstance stageInstance = new StageInstance();
        if (!Objects.equals(list.size(),0)){
            String status = PipelineFinal.RUN_RUN;
            stageInstance.setStageName("后置处理");
            stageInstance.setId("1");
            int i = 0;
            AtomicInteger time = new AtomicInteger();

            for (TaskInstance taskInstance : list) {
                // taskInstance.setTaskName();
                String runState = taskInstance.getRunState();
                switch (runState) {
                    case PipelineFinal.RUN_ERROR -> {status = PipelineFinal.RUN_ERROR;}
                    case PipelineFinal.RUN_RUN -> {status = PipelineFinal.RUN_RUN;}
                    case PipelineFinal.RUN_HALT ->{status = PipelineFinal.RUN_HALT;}
                    case PipelineFinal.RUN_WAIT  ->{status = PipelineFinal.RUN_WAIT;}
                    case PipelineFinal.RUN_SUCCESS ->{i++;}
                }
            }

            list.forEach(taskInstance -> {
                time.set(time.get() + taskInstance.getRunTime());
            });

            if (i == list.size()){
                status = PipelineFinal.RUN_SUCCESS;
            }

            StageInstance otherStageInstance = new StageInstance();
            otherStageInstance.setStageName("后置任务");
            otherStageInstance.setId("11111");
            otherStageInstance.setStageState(status);
            otherStageInstance.setStageTime(time.get());
            otherStageInstance.setTaskInstanceList(list);

            List<StageInstance> stageInstancesList = new ArrayList<>();
            stageInstancesList.add(otherStageInstance);

            stageInstance.setStageState(status);
            stageInstance.setStageTime(time.get());
            stageInstance.setStageInstanceList(stageInstancesList);
            stageInstanceList.add(stageInstanceList.size(),stageInstance);
        }else {
            stageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
        }
        return stageInstanceList;
    }


    /**
     * 获取所有阶段实例
     * @return 阶段实例列表
     */
    private List<StageInstance> findAllStageInstance() {
        List<StageInstanceEntity> allStagesInstance = stageInstanceDao.findAllStagesInstance();
        return BeanMapper.mapList(allStagesInstance,StageInstance.class);
    }

    @Override
    public List<StageInstance> findStageInstanceList(StageInstanceQuery query){
        List<StageInstanceEntity> stageInstanceList = stageInstanceDao.findStageInstanceList(query);
        if (stageInstanceList == null || stageInstanceList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(stageInstanceList,StageInstance.class);
    }

    /**
     * 删除阶段实例
     * @param stageInstanceId 阶段实例id
     */
    private void deleteStageInstance(String stageInstanceId) {
        stageInstanceDao.deleteStagesInstance(stageInstanceId);
    }


}

































