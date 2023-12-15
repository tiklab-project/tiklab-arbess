package io.thoughtware.matflow.stages.service;

import io.thoughtware.matflow.stages.entity.StageInstanceEntity;
import io.thoughtware.matflow.stages.model.StageInstance;
import io.thoughtware.matflow.stages.model.StageInstanceQuery;
import io.thoughtware.matflow.support.util.PipelineFileUtil;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.matflow.stages.dao.StageInstanceDao;
import io.thoughtware.matflow.task.task.model.TaskInstance;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import io.thoughtware.security.logging.model.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public List<String> findAllStageInstanceLogs(String instanceId){
        List<StageInstance> allStageInstance = findAllMainStageInstance(instanceId);
        List<String> list = new ArrayList<>();
        for (StageInstance stageInstance : allStageInstance) {
            List<StageInstance> allOtherStageInstance = findAllOtherStageInstance(stageInstance.getId());
            for (StageInstance instance : allOtherStageInstance) {
                List<TaskInstance> allTaskInstance = tasksInstanceService.findAllStageInstance(instance.getId());
                for (TaskInstance taskInstance : allTaskInstance) {
                    String runLog = taskInstance.getLogAddress();
                    String readFile = PipelineFileUtil.readFile(runLog, 0);
                    if (Objects.isNull(readFile)){
                        continue;
                    }
                    list.add(readFile+ "\n");
                }
            }
        }
        return list;
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
        List<StageInstanceEntity> stageInstanceEntityList= stageInstanceDao.findMainStageInstance(instanceId);
        if (Objects.isNull(stageInstanceEntityList)){
            return null;
        }
        List<StageInstance> stageInstanceList = BeanMapper.mapList(stageInstanceEntityList, StageInstance.class);
        List<StageInstance> mainStageInstanceList = new ArrayList<>();

        // 主阶段实例
        for (StageInstance stageInstance : stageInstanceList) {
            StringBuilder mainLog = new StringBuilder();
            String stageInstanceId = stageInstance.getId();
            StageExecServiceImpl stageExecService = new StageExecServiceImpl();
            StageInstance instance = stageExecService.findStageInstance(stageInstanceId);
            if (Objects.isNull(instance)){
                instance = findOneStageInstance(stageInstanceId);
            }else {
                Integer integer = findStageRunTime(stageInstanceId);
                instance.setStageTime(integer);
            }

            // 从阶段实例
            List<StageInstanceEntity> otherStageInstanceEntityList =
                    stageInstanceDao.findOtherStageInstance(stageInstanceId);
            if (Objects.isNull(otherStageInstanceEntityList)){
                continue;
            }
            List<StageInstance> allStageInstance = BeanMapper.mapList(otherStageInstanceEntityList, StageInstance.class);
            String log = "\n";
            //并行阶段执行实例
            List<StageInstance> otherStageInstanceList = new ArrayList<>();
            for (StageInstance otherStageInstance : allStageInstance) {
                String otherStageInstanceId = otherStageInstance.getId();
                StageInstance otherInstance = stageExecService.findStageInstance(otherStageInstanceId);
                if (Objects.isNull(otherInstance)){
                    otherInstance = findOneStageInstance(otherStageInstanceId);
                }
                //获取从阶段具体任务执行实例
                List<TaskInstance> allTaskInstance =
                        tasksInstanceService.findAllStageInstance(otherStageInstanceId);
                otherInstance.setTaskInstanceList(allTaskInstance);
                StringBuilder runLog = new StringBuilder();
                String stageRunLog = tasksInstanceService.findStageRunLog(otherStageInstanceId);
                Integer stageRunTime = tasksInstanceService.findStageRunTime(otherStageInstanceId);
                tasksInstanceService.removeStageRunLog(otherStageInstanceId);
                tasksInstanceService.removeStageRunTime(otherStageInstanceId);
                runLog.append(stageRunLog).append(log);
                if (Objects.isNull(stageRunTime)){
                    stageRunTime = 0;
                }
                otherInstance.setStageTime(stageRunTime);
                otherInstance.setRunLog(runLog.toString());
                if (!Objects.equals(runLog.toString(),log)){
                    mainLog.append(runLog).append(log);
                }
                otherStageInstanceList.add(otherInstance);
            }
            instance.setRunLog(mainLog.toString());
            instance.setStageInstanceList(otherStageInstanceList);
            mainStageInstanceList.add(instance);
        }

        List<TaskInstance> list = tasksInstanceService.findStagePostRunMessage(instanceId);
        if (!Objects.equals(list.size(),0)){

            Map<String, Object> map = tasksInstanceService.findPostRunMessage(instanceId);
            StageInstance stageInstance = new StageInstance();
            stageInstance.setStageName("后置处理");
            stageInstance.setId("1");
            stageInstance.setRunLog((String) map.get("log"));
            stageInstance.setStageState((String) map.get("state"));
            stageInstance.setStageTime((Integer) map.get("time"));

            StageInstance otherStageInstance = new StageInstance();
            otherStageInstance.setStageName("后置任务");
            otherStageInstance.setId("11111");
            otherStageInstance.setRunLog((String) map.get("log"));
            otherStageInstance.setStageState((String) map.get("state"));
            otherStageInstance.setStageTime((Integer) map.get("time"));
            otherStageInstance.setTaskInstanceList(list);

            List<StageInstance> stageInstancesList = new ArrayList<>();
            stageInstancesList.add(otherStageInstance);

            stageInstance.setStageInstanceList(stageInstancesList);
            mainStageInstanceList.add(mainStageInstanceList.size(),stageInstance);
        }

        return mainStageInstanceList;
    }

    //运行时间
    private final static Map<String,Integer> stageRunTime = new HashMap<>();

    ExecutorService threadPool = Executors.newCachedThreadPool();

    public void stageRunTime(String stageInstanceId){
        stageRunTime.put(stageInstanceId,0);
        threadPool.submit(() -> {
            while (true){
                Thread.currentThread().setName(stageInstanceId);
                try {
                    int integer = stageRunTime.get(stageInstanceId);
                    Thread.sleep(1000);
                    integer = integer +1;
                    stageRunTime.put(stageInstanceId,integer);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }

            }
        });
    }

    public Integer findStageRunTime(String stageInstanceId){
        Integer integer = stageRunTime.get(stageInstanceId);
        if (Objects.isNull(integer)){
            return 0;
        }
        return integer;
    }

    public void removeStageRunTime(String stageInstanceId){
        stageRunTime.remove(stageInstanceId);
    }


    /**
     * 获取所有阶段实例
     * @return 阶段实例列表
     */
    private List<StageInstance> findAllStageInstance() {
        List<StageInstanceEntity> allStagesInstance = stageInstanceDao.findAllStagesInstance();
        return BeanMapper.mapList(allStagesInstance,StageInstance.class);
    }


    public List<StageInstance> findStageInstanceList(StageInstanceQuery query){
        List<StageInstanceEntity> stageInstanceList = stageInstanceDao.findStageInstanceList(query);
        if (stageInstanceList == null || stageInstanceList.size() == 0){
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
































