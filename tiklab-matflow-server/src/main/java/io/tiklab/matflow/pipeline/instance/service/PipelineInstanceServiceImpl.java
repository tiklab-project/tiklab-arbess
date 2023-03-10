package io.tiklab.matflow.pipeline.instance.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.matflow.pipeline.instance.dao.PipelineInstanceDao;
import io.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.stages.service.StageInstanceServer;
import io.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 流水线实例服务
 */
@Service
@Exporter
public class PipelineInstanceServiceImpl implements PipelineInstanceService {

    @Autowired
    private PipelineInstanceDao pipelineInstanceDao;

    @Autowired
    private PipelineAuthorityService authorityService;

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private StageInstanceServer stageInstanceServer;

    @Autowired
    private JoinTemplate joinTemplate;

    //运行时间
    private final Map<String, Integer> runTime = TasksExecServiceImpl.runTime;

    //实例id与实例
    private final Map<String,PipelineInstance> instanceIdOrInstance = PipelineExecServiceImpl.instanceIdOrInstance;

    @Override
    public String createInstance(PipelineInstance pipelineInstance) {
        return pipelineInstanceDao.createInstance(BeanMapper.map(pipelineInstance, PipelineInstanceEntity.class));
    }

    @Override
    public void deleteAllInstance(String pipelineId) {
        List<PipelineInstance> allInstance = findAllInstance();
        if (allInstance == null){
            return;
        }
        for (PipelineInstance instance : allInstance) {
            Pipeline pipeline = instance.getPipeline();
            if (pipeline == null){
                deleteInstance(instance.getInstanceId());
            }
            if (instance.getPipeline().getId().equals(pipelineId)){
                deleteInstance(instance.getInstanceId());
            }
        }
    }

    @Override
    public PipelineInstance initializeInstance(String pipelineId , int startWAy) {
        String loginId = LoginContext.getLoginId();
        String date = PipelineUtil.date(1);

        PipelineInstance pipelineInstance = new PipelineInstance(date,startWAy,loginId,pipelineId);
        pipelineInstance.setRunStatus(PipelineFinal.RUN_RUN);
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

    public PipelineInstance findPipelineExecInstance(String pipelineId){
        List<PipelineInstance> allInstance = findPipelineAllInstance(pipelineId);
        if (allInstance == null){
            return null;
        }
        for (PipelineInstance instance : allInstance) {
            String runStatus = instance.getRunStatus();
            if (runStatus.equals(PipelineFinal.RUN_RUN)){
                continue;
            }
            return instance;
        }
        return null;
    }

    @Override
    public void deleteInstance(String instanceId) {
        PipelineInstance instance = findOneInstance(instanceId);
        Pipeline pipeline = instance.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
            tasksInstanceService.deleteAllInstanceInstance(instanceId);
        }else {
            stageInstanceServer.deleteAllMainStageInstance(instanceId);
        }
        pipelineInstanceDao.deleteInstance(instanceId);

        String fileAddress = PipelineUtil.findFileAddress(instanceId,2);
        //删除对应日志
        PipelineUtil.deleteFile(new File(fileAddress));
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

    @Override
    public List<PipelineInstance> findAllInstance() {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findAllInstance();
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    @Override
    public List<PipelineInstance> findPipelineAllInstance(String pipelineId) {
        List<PipelineInstanceEntity> list = pipelineInstanceDao.findAllInstance(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineInstance> allInstance = BeanMapper.mapList(list, PipelineInstance.class);
        allInstance.sort(Comparator.comparing(PipelineInstance::getCreateTime,Comparator.reverseOrder()));
        return allInstance;
    }

    @Override
    public PipelineInstance findLatelyInstance(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLatelyInstance(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        PipelineInstance instance = pipelineExecHistories.get(0);
        joinTemplate.joinQuery(instance);
        return instance;
    }

    @Override
    public List<PipelineInstance> findInstanceList(List<String> idList) {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findInstanceList(idList);
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    @Override
    public Pagination<PipelineInstance> findPipelineInstance(PipelineInstanceQuery pipelineInstanceQuery){
        if (pipelineInstanceQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> execInstanceList= BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        int size = execInstanceList.size();
        for (int i = size -1 ; i >= 0; i--) {
            PipelineInstance instance = execInstanceList.get(i);
            String id = instance.getInstanceId();
            //判断内存中是否存在该实例
            PipelineInstance pipelineInstance = instanceIdOrInstance.get(id);
            if (pipelineInstance == null){
               continue;
            }
            Integer integer = runTime.get(id);
            pipelineInstance.setRunTime(integer);
            execInstanceList.remove(i);
            execInstanceList.add(i,pipelineInstance);
        }
        joinTemplate.joinQuery(execInstanceList);
        return PaginationBuilder.build(pagination,execInstanceList);
    }

    @Override
    public Pagination<PipelineInstance> findUserInstance(PipelineInstanceQuery pipelineInstanceQuery){
        String loginId = LoginContext.getLoginId();
        List<Pipeline> userPipeline = authorityService.findUserPipeline(loginId);
        pipelineInstanceQuery.setPipelineList(userPipeline);
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findAllPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> execInstanceList = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);

        for (int i = execInstanceList.size() -1 ; i >= 0; i--) {
            PipelineInstance instance = execInstanceList.get(i);
            String id = instance.getInstanceId();
            PipelineInstance pipelineInstance = instanceIdOrInstance.get(id);
            if (pipelineInstance != null){
                Integer integer = runTime.get(id);
                pipelineInstance.setRunTime(integer);
                execInstanceList.remove(i);
                execInstanceList.add(i,pipelineInstance);
            }
        }
        joinTemplate.joinQuery(execInstanceList);
        return PaginationBuilder.build(pagination,execInstanceList);
    }













}


























