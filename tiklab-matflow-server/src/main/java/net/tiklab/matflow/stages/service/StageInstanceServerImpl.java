package net.tiklab.matflow.stages.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.stages.dao.StageInstanceDao;
import net.tiklab.matflow.stages.entity.StageInstanceEntity;
import net.tiklab.matflow.stages.model.StageInstance;
import net.tiklab.matflow.task.task.model.TaskInstance;
import net.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import net.tiklab.matflow.task.task.service.TasksInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public void deleteAllStageInstanceInstance(String instanceId) {
        List<StageInstance> allStageInstance = findAllMainStageInstance(instanceId);
        for (StageInstance stageInstance : allStageInstance) {
            String id = stageInstance.getId();
            deleteStageInstance(id);
            tasksInstanceService.deleteAllStageInstance(id);
        }
    }

    @Override
    public void deleteAllStageInstance(String stageId) {
        List<StageInstance> allStageInstance = findAllStageInstance(stageId);
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
            if (!id.equals(instanceId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }

    @Override
    public List<StageInstance> findAllStageInstance(String stageId) {
        List<StageInstance> allStageInstance = findAllStageInstance();
        if (allStageInstance == null || allStageInstance.size() == 0){
            return Collections.emptyList();
        }
        List<StageInstance> list = new ArrayList<>();
        for (StageInstance instance : allStageInstance) {
            String id = instance.getParentId();
            if (!id.equals(stageId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }

    @Override
    public List<StageInstance> findStageExecInstance(String instanceId){
        List<StageInstance> stageInstanceList = findAllMainStageInstance(instanceId);
        for (StageInstance stageInstance : stageInstanceList) {
            String stageId = stageInstance.getId();
            StageInstance instance = stageInstanceMap.get(stageId);
            if (instance == null){
                instance = findOneStageInstance(stageId);
            }else {
                Integer integer = runTime.get(stageId);
                instance.setStageTime(integer);
            }
            List<StageInstance> allStageInstance = findAllStageInstance(stageId);
            //并行阶段执行实例
            for (StageInstance otherStageInstance : allStageInstance) {
                String otherStageInstanceId = otherStageInstance.getId();
                StageInstance otherInstance = stageInstanceMap.get(otherStageInstanceId);
                if (otherInstance == null){
                    otherInstance = findOneStageInstance(otherStageInstanceId);
                }else {
                    Integer integer = runTime.get(otherStageInstanceId);
                    instance.setStageTime(integer);
                }
                //获取任务执行实例
                List<TaskInstance> allTaskInstance =
                        tasksInstanceService.findAllStageInstance(otherStageInstanceId);
                otherInstance.setTaskInstanceList(allTaskInstance);
            }
            instance.setStageInstanceList(allStageInstance);
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

    /**
     * 删除阶段实例
     * @param stageInstanceId 阶段实例id
     */
    private void deleteStageInstance(String stageInstanceId) {
        stageInstanceDao.deleteStagesInstance(stageInstanceId);
    }


}

































