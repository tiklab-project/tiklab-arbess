package io.tiklab.arbess.stages.service;

import io.tiklab.arbess.pipeline.instance.dao.PipelineInstanceDao;
import io.tiklab.arbess.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.stages.entity.StageInstanceEntity;
import io.tiklab.arbess.stages.model.StageInstance;
import io.tiklab.arbess.stages.model.StageInstanceQuery;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.stages.dao.StageInstanceDao;
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    PipelineInstanceDao pipelineInstanceDao;

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
            return new ArrayList<>();
        }
        stageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
       return stageInstanceList;
    }

    @Override
    public List<StageInstance> findOtherStageInstance(String mainStageId) {

        StageInstanceQuery stageInstanceQuery = new StageInstanceQuery();
        stageInstanceQuery.setParentId(mainStageId);
        List<StageInstance> stageInstanceList = findStageInstanceList(stageInstanceQuery);
        if (stageInstanceList == null || stageInstanceList.isEmpty()){
            return new ArrayList<>();
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
                otherInstance.setStageState(findTaskInstanceStatus(allTaskInstance));
                if (!Objects.equals(runLog.toString(),log)){
                    mainLog.append(runLog).append(log);
                }
                allTime = Math.max(allTime, otherInstance.getStageTime());
            }

            stageInstance.setRunLog(mainLog.toString());
            stageInstance.setStageTime(allTime);
            otherStageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
            stageInstance.setStageState(findStageInstanceStatus(otherStageInstanceList));
            stageInstance.setStageInstanceList(otherStageInstanceList);
        }
        // List<TaskInstance> list = tasksInstanceService.findStagePostRunMessage(instanceId);
        // StageInstance stageInstance = new StageInstance();
        // if (!Objects.equals(list.size(),0)){
        //     String status = findTaskInstanceStatus(list);
        //     stageInstance.setStageName("后置处理");
        //     stageInstance.setId("1");
        //
        //     AtomicInteger time = new AtomicInteger();
        //     list.forEach(taskInstance -> {time.set(time.get() + taskInstance.getRunTime());});
        //
        //     StageInstance otherStageInstance = new StageInstance();
        //     otherStageInstance.setStageName("后置任务");
        //     otherStageInstance.setId("11111");
        //     otherStageInstance.setStageState(status);
        //     otherStageInstance.setStageTime(time.get());
        //     otherStageInstance.setTaskInstanceList(list);
        //
        //     List<StageInstance> stageInstancesList = new ArrayList<>();
        //     stageInstancesList.add(otherStageInstance);
        //
        //     stageInstance.setStageState(status);
        //     stageInstance.setStageTime(time.get());
        //     stageInstance.setStageInstanceList(stageInstancesList);
        //     stageInstanceList.add(stageInstanceList.size(),stageInstance);
        // }else {
        //     stageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
        // }


        stageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));

        StageInstance stageInstance = stageInstanceList.get(stageInstanceList.size() - 1);
        PipelineInstanceEntity instance = pipelineInstanceDao.findOneInstance(stageInstance.getInstanceId());
        if (Objects.isNull(instance) || StringUtils.isEmpty(instance.getRunLog())){
            return stageInstanceList;
        }

        List<StageInstance> otherStageInstanceList = stageInstance.getStageInstanceList();
        StageInstance otherStageInstance = otherStageInstanceList.get(otherStageInstanceList.size() - 1);
        List<TaskInstance> taskInstanceList = otherStageInstance.getTaskInstanceList();
        TaskInstance taskInstance = taskInstanceList.get(taskInstanceList.size() - 1);
        taskInstance.setRunLog(taskInstance.getRunLog()+"\n\n" + instance.getRunLog());

        return stageInstanceList;
    }

    private String findTaskInstanceStatus(List<TaskInstance> list){
        String status;

        long successCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_SUCCESS)).count();

        long timeoutCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_TIMEOUT)).count();

        long errorCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_ERROR)).count();

        long haltCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_HALT)).count();

        long runCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_RUN)).count();

        long suspendCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getRunState(), PipelineFinal.RUN_SUSPEND)).count();

        if (haltCount != 0){
            status = PipelineFinal.RUN_HALT;
        }else if (timeoutCount != 0){
            status = PipelineFinal.RUN_TIMEOUT;
        } else if (errorCount != 0){
            status = PipelineFinal.RUN_ERROR;
        } else if (runCount != 0){
            status = PipelineFinal.RUN_RUN;
        } else if (suspendCount != 0){
            status = PipelineFinal.RUN_SUSPEND;
        } else {
            status = PipelineFinal.RUN_WAIT;
        }
        if (successCount == list.size()){
            status = PipelineFinal.RUN_SUCCESS;
        }
        return status;
    }
    private String findStageInstanceStatus(List<StageInstance> list){
        String status;

        long successCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_SUCCESS)).count();

        long errorCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_ERROR)).count();

        long timeoutCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_TIMEOUT)).count();

        long haltCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_HALT)).count();

        long runCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_RUN)).count();

        long suspendCount = list.stream().filter(taskInstance -> Objects.equals(taskInstance.getStageState(), PipelineFinal.RUN_SUSPEND)).count();

        if (haltCount != 0){
            status = PipelineFinal.RUN_HALT;
        }else if (timeoutCount != 0){
            status = PipelineFinal.RUN_TIMEOUT;
        }else if (errorCount != 0){
            status = PipelineFinal.RUN_ERROR;
        } else if (runCount != 0){
            status = PipelineFinal.RUN_RUN;
        }else if (suspendCount != 0){
            status = PipelineFinal.RUN_SUSPEND;
        } else {
            status = PipelineFinal.RUN_WAIT;
        }
        if (successCount == list.size()){
            status = PipelineFinal.RUN_SUCCESS;
        }
        return status;
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
            return new ArrayList<>();
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

































