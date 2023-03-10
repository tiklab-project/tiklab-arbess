package io.tiklab.matflow.stages.service;

import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.stages.dao.StageInstanceDao;
import io.tiklab.matflow.stages.entity.StageInstanceEntity;
import io.tiklab.matflow.stages.model.StageInstance;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 阶段执行实例服务
 */
@Service
public class StageInstanceServerImpl implements StageInstanceServer{

    @Autowired
    private StageInstanceDao stageInstanceDao;


    @Autowired
    private TasksInstanceService tasksInstanceService;

    //任务实例id与任务实例
    private final Map<String , StageInstance> stageInstanceMap =
            StageExecServiceImpl.stageInstanceIdOrStageInstance;

    //阶段运行时间
    private final Map<String, Integer> runTime = TasksExecServiceImpl.runTime;

    @Override
    public String createStageInstance(StageInstance stageInstance) {
        StageInstanceEntity instanceEntity =
                BeanMapper.map(stageInstance, StageInstanceEntity.class);
        return stageInstanceDao.createStagesInstance(instanceEntity);
    }

    @Override
    public void deleteAllMainStageInstance(String instanceId) {
        List<StageInstance> allStageInstance = findAllMainStageInstance(instanceId);
        for (StageInstance stageInstance : allStageInstance) {
            String id = stageInstance.getId();

            List<StageInstance> allOtherStageInstance = findAllOtherStageInstance(id);
            for (StageInstance instance : allOtherStageInstance) {
                String otherStageId = instance.getId();
                tasksInstanceService.deleteAllStageInstance(otherStageId);
                deleteStageInstance(otherStageId);
            }
            deleteStageInstance(id);
        }
    }

    @Override
    public void deleteAllStageInstance(String stageId) {
        List<StageInstance> allStageInstance = findAllOtherStageInstance(stageId);
        for (StageInstance stageInstance : allStageInstance) {
            String id = stageInstance.getId();
            deleteStageInstance(id);
        }
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
    public List<StageInstance> findAllMainStageInstance(String instanceId) {
        List<StageInstance> allStageInstance = findAllStageInstance();
        if (allStageInstance == null || allStageInstance.size() == 0){
            return Collections.emptyList();
        }
        List<StageInstance> list = new ArrayList<>();
        for (StageInstance instance : allStageInstance) {
            String id = instance.getInstanceId();
            if (id == null || !id.equals(instanceId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }

    @Override
    public List<StageInstance> findAllOtherStageInstance(String mainStageId) {
        List<StageInstance> allStageInstance = findAllStageInstance();
        if (allStageInstance == null || allStageInstance.size() == 0){
            return Collections.emptyList();
        }
        List<StageInstance> list = new ArrayList<>();
        for (StageInstance instance : allStageInstance) {
            String id = instance.getParentId();
            if (id == null || !id.equals(mainStageId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }

    @Override
    public List<StageInstance> findStageExecInstance(String instanceId){
        List<StageInstance> stageInstanceList = findAllMainStageInstance(instanceId);

        int size1 = stageInstanceList.size();
        for (int i = size1-1; i >= 0; i--) {
            String stageId = stageInstanceList.get(i).getId();
            StageInstance instance = stageInstanceMap.get(stageId);
            if (instance == null){
                instance = findOneStageInstance(stageId);
            }else {
                Integer integer = runTime.get(stageId);
                if (integer == null){
                    integer = 0;
                }
                instance.setStageTime(integer);
            }
            List<StageInstance> allStageInstance = findAllOtherStageInstance(stageId);
            String log = "\n\n";
            //并行阶段执行实例
            int size = allStageInstance.size();
            for (int j = size - 1; j >= 0; j--) {
                String otherStageInstanceId = allStageInstance.get(j).getId();
                StageInstance otherInstance = stageInstanceMap.get(otherStageInstanceId);
                if (otherInstance == null){
                    otherInstance = findOneStageInstance(otherStageInstanceId);
                }

                allStageInstance.remove(j);
                //获取任务执行实例
                List<TaskInstance> allTaskInstance =
                        tasksInstanceService.findAllStageInstance(otherStageInstanceId);
                otherInstance.setTaskInstanceList(allTaskInstance);

                //阶段日志
                int time = 0;
                StringBuilder runLog = new StringBuilder();
                for (TaskInstance taskInstance : allTaskInstance) {
                    String instanceRunLog = taskInstance.getRunLog();
                    if (!PipelineUtil.isNoNull(instanceRunLog)){
                        continue;
                    }
                    runLog.append(instanceRunLog).append(log);
                    time = time + taskInstance.getRunTime();
                }
                otherInstance.setStageTime(time);

                otherInstance.setRunLog(runLog.toString());
                allStageInstance.add(j,otherInstance);
                //排序
                allStageInstance.sort(Comparator.comparing(StageInstance::getStageSort));
            }
            stageInstanceList.remove(i);
            StringBuilder runLog = new StringBuilder();
            for (StageInstance stageInstance : allStageInstance) {
                if (!PipelineUtil.isNoNull(stageInstance.getRunLog())){
                    continue;
                }
                runLog.append(stageInstance.getRunLog()).append(log);
            }

            instance.setRunLog(runLog.toString());
            instance.setStageInstanceList(allStageInstance);
            stageInstanceList.add(i,instance);
        }
        stageInstanceList.sort(Comparator.comparing(StageInstance::getStageSort));
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

    /**
     * 删除阶段实例
     * @param stageInstanceId 阶段实例id
     */
    private void deleteStageInstance(String stageInstanceId) {
        stageInstanceDao.deleteStagesInstance(stageInstanceId);
    }


}

































