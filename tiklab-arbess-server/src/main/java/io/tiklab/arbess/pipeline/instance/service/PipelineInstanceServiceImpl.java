package io.tiklab.arbess.pipeline.instance.service;

import io.tiklab.arbess.home.service.PipelineHomeService;
import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.instance.dao.PipelineInstanceDao;
import io.tiklab.arbess.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.arbess.stages.service.StageInstanceServer;
import io.tiklab.arbess.support.authority.service.PipelineAuthorityService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.tiklab.arbess.support.util.util.PipelineFinal.PIPELINE_RUN_KEY;

/**
 * 流水线实例服务
 */
@Service
@Exporter
public class PipelineInstanceServiceImpl implements PipelineInstanceService {

    @Autowired
    PipelineInstanceDao pipelineInstanceDao;

    @Autowired
    PipelineAuthorityService authorityService;

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    StageInstanceServer stageInstanceServer;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    @Autowired
    PipelineHomeService homeService;

    // 任务示例ID -- 运行时间
    public final static Map<String,Integer> runTimeMap = new HashMap<>();

    //任务线程池
    public final ExecutorService timeThreadPool = Executors.newCachedThreadPool();

    @Override
    public String createInstance(PipelineInstance pipelineInstance) {
        return pipelineInstanceDao.createInstance(BeanMapper.map(pipelineInstance, PipelineInstanceEntity.class));
    }

    @Override
    public void deleteAllInstance(String pipelineId) {
        List<PipelineInstance> allInstance = findPipelineAllInstance(pipelineId);
        if (Objects.isNull(allInstance)){
            return;
        }
        for (PipelineInstance instance : allInstance) {
            Pipeline pipeline = instance.getPipeline();
            if (Objects.isNull(pipeline)){
                deleteInstance(instance.getInstanceId());
            }
            if (instance.getPipeline().getId().equals(pipelineId)){
                deleteInstance(instance.getInstanceId());
            }
        }
    }

    @Override
    public PipelineInstance initializeInstance(PipelineRunMsg runMsg) {
        String date = PipelineUtil.date(1);
        String pipelineId = runMsg.getPipelineId();
        String userId = runMsg.getUserId();
        PipelineInstance pipelineInstance = new PipelineInstance(date,runMsg.getRunWay(),userId,pipelineId);
        pipelineInstance.setRunStatus(PipelineFinal.RUN_RUN);
        if (!StringUtils.isEmpty(runMsg.getRunStatus())){
            pipelineInstance.setRunStatus(runMsg.getRunStatus());
        }
        String instanceId = createInstance(pipelineInstance);
        pipelineInstance.setInstanceId(instanceId);

        //构建次数
        PipelineInstance latelyInstance = findLatelyInstance(pipelineId);
        pipelineInstance.setFindNumber(1);
        if (latelyInstance != null){
            int findNumber = latelyInstance.getFindNumber();
            pipelineInstance.setFindNumber(findNumber + 1);
        }
        updateInstance(pipelineInstance);
        return pipelineInstance;
    }

    @Override
    public void deleteInstance(String instanceId) {
        PipelineInstance instance = findOneInstanceNoQuery(instanceId);
        if (Objects.isNull(instance)){
            return;
        }
        Pipeline pipeline = instance.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
            tasksInstanceService.deleteAllInstanceInstance(instanceId);
        }else {
            stageInstanceServer.deleteAllMainStageInstance(instanceId);
        }
        pipelineInstanceDao.deleteInstance(instanceId);

        String fileAddress = utilService.findPipelineDefaultAddress(pipeline.getId()+"/"+instanceId,2);
        //删除对应日志
        PipelineFileUtil.deleteFile(new File(fileAddress));

        // 删除对应制品信息
        TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
        taskBuildProductQuery.setInstanceId(instance.getInstanceId());
        List<TaskBuildProduct> buildProductList = taskBuildProductService.findBuildProductList(taskBuildProductQuery);
        for (TaskBuildProduct taskBuildProduct : buildProductList) {
            taskBuildProductService.deleteBuildProduct(taskBuildProduct.getId());
        }
    }

    @Override
    public void updateInstance(PipelineInstance pipelineInstance) {
        pipelineInstanceDao.updateInstance(BeanMapper.map(pipelineInstance, PipelineInstanceEntity.class));
    }

    @Override
    public PipelineInstance findOneInstance(String instanceId) {
        PipelineInstanceEntity pipelineInstanceEntity = pipelineInstanceDao.findOneInstance(instanceId);
        PipelineInstance instance = BeanMapper.map(pipelineInstanceEntity, PipelineInstance.class);
        joinTemplate.joinQuery(instance);
        return instance;
    }

    public PipelineInstance findOneInstanceNoQuery(String instanceId) {
        PipelineInstanceEntity pipelineInstanceEntity = pipelineInstanceDao.findOneInstance(instanceId);
        return BeanMapper.map(pipelineInstanceEntity, PipelineInstance.class);
        // joinTemplate.joinQuery(instance);
    }

    @Override
    public List<PipelineInstance> findAllInstance() {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findAllInstance();
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    @Override
    public List<PipelineInstance> findPipelineAllInstance(String pipelineId) {
        List<PipelineInstanceEntity> list = pipelineInstanceDao.findAllInstance(pipelineId);
        if (Objects.isNull(list)){
            return Collections.emptyList();
        }
        List<PipelineInstance> allInstance = BeanMapper.mapList(list, PipelineInstance.class);
        allInstance.sort(Comparator.comparing(PipelineInstance::getCreateTime,Comparator.reverseOrder()));
        return allInstance;
    }

    @Override
    public List<PipelineInstance> findUserPipelineInstance(String userId,Integer limit){
        List<PipelineInstanceEntity> instanceEntityList = pipelineInstanceDao.findUserPipelineInstance(userId, limit);
        if (instanceEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(instanceEntityList,PipelineInstance.class);
    }

    @Override
    public PipelineInstance findLatelyInstance(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLatelyInstance(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.isEmpty()){
            return null;
        }
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineInstance> findInstanceList(List<String> idList) {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findInstanceList(idList);
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    @Override
    public String findRunInstanceId(String pipelineId){
        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
        pipelineInstanceQuery.setPipelineId(pipelineId);
        List<PipelineInstance> pipelineInstanceList = this.findPipelineInstanceList(pipelineInstanceQuery);
        if (pipelineInstanceList.isEmpty()){
            return null;
        }
        PipelineInstance pipelineInstance = pipelineInstanceList.get(0);
        return pipelineInstance.getInstanceId();
    }

    @Override
    public List<PipelineInstance> findPipelineInstanceList(PipelineInstanceQuery pipelineInstanceQuery){
        List<PipelineInstanceEntity> instanceList = pipelineInstanceDao.findInstanceList(pipelineInstanceQuery);
        if (instanceList == null || instanceList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(instanceList,PipelineInstance.class);

    }

    @Override
    public Pagination<PipelineInstance> findPipelineInstance(PipelineInstanceQuery pipelineInstanceQuery){
        if (StringUtils.isEmpty(pipelineInstanceQuery.getPipelineId())){
            return null;
        }
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> execInstanceList= BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        for (PipelineInstance instance : execInstanceList) {
            String time;
            if (instance.getRunStatus().equals(PipelineFinal.RUN_RUN)){
                time = PipelineUtil.formatDateTime(findInstanceRuntime(instance.getInstanceId()));
            } else {
                time = PipelineUtil.formatDateTime(instance.getRunTime());
            }
            String id = instance.getPipeline().getId();
            Boolean permissions = homeService.findPermissions(id, PIPELINE_RUN_KEY);
            instance.setExec(permissions);
            instance.setRunTimeDate(time);
        }
        joinTemplate.joinQuery(execInstanceList);
        return PaginationBuilder.build(pagination, execInstanceList);
    }

    @Override
    public Pagination<PipelineInstance> findUserInstance(PipelineInstanceQuery pipelineInstanceQuery){
        String loginId = LoginContext.getLoginId();
        String[] userPipeline = authorityService.findUserPipelineIdString(loginId);
        pipelineInstanceQuery.setIds(userPipeline);
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findAllPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> execInstanceList = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        for (PipelineInstance instance : execInstanceList) {
            String time;
            if (instance.getRunStatus().equals(PipelineFinal.RUN_RUN)){
                time = PipelineUtil.formatDateTime(findInstanceRuntime(instance.getInstanceId()));
            } else {
                time = PipelineUtil.formatDateTime(instance.getRunTime());
            }
            instance.setRunTimeDate(time);
            String id = instance.getPipeline().getId();
            Boolean permissions = homeService.findPermissions(id, PIPELINE_RUN_KEY);
            instance.setExec(permissions);
        }
        joinTemplate.joinQuery(execInstanceList);
        return PaginationBuilder.build(pagination,execInstanceList);
    }

    @Override
    public List<PipelineInstance> findInstanceByTime(String pipelineId,String[] queryTime){
       List<PipelineInstanceEntity> instanceEntityList = pipelineInstanceDao.findInstanceByTime(pipelineId,queryTime);
       if (instanceEntityList.isEmpty()){
           return Collections.emptyList();
       }
       return BeanMapper.mapList(instanceEntityList,PipelineInstance.class);
    }

    @Override
    public List<PipelineInstance> findInstanceByTime(String[] queryTime){
        List<PipelineInstanceEntity> instanceEntityList = pipelineInstanceDao.findInstanceByTime(queryTime);
        if (instanceEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(instanceEntityList,PipelineInstance.class);
    }

    @Override
    public int findInstanceRuntime(String instanceId){
        Integer i = runTimeMap.get(instanceId);
        if (Objects.isNull(i)){
            return 1;
        }
        return i;
    }

    @Override
    public void instanceRuntime(String instanceId){
        timeThreadPool.submit(() -> {
            while (true){
                Thread.currentThread().setName(instanceId);
                int integer = findInstanceRuntime(instanceId);
                try {
                    Thread.sleep(1000);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }
                integer = integer +1;
                runTimeMap.put(instanceId,integer);
            }
        });
    }

    @Override
    public void stopThread(String threadName){
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        if (Objects.equals(lstThreads.length,0)){
            return;
        }
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!PipelineUtil.isNoNull(nm) ||!nm.equals(threadName)) {
                continue;
            }
            runTimeMap.remove(threadName);
            lstThreads[i].stop();
        }
    }



}


























