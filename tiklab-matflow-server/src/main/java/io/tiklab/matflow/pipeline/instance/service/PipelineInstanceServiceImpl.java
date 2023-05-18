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
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (instance == null){
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
    public PipelineInstance findLastInstance(String pipelineId){
        PipelineInstanceEntity instanceEntity = pipelineInstanceDao.findLastInstance(pipelineId);
        String createTime = instanceEntity.getCreateTime();
        Date date = PipelineUtil.StringChengeDate(createTime);
        String dateTime = PipelineUtil.findDateTime(date, 7);
        if (Objects.isNull(dateTime)){
            return null;
        }
        PipelineInstance pipelineInstance = BeanMapper.map(instanceEntity, PipelineInstance.class);
        joinTemplate.joinQuery(pipelineInstance);
        return pipelineInstance;
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
        List<PipelineInstance> instanceList = new ArrayList<>();
        for (PipelineInstance instance : execInstanceList) {
            String id = instance.getInstanceId();
            //判断内存中是否存在该实例
            PipelineExecServiceImpl pipelineExecService = new PipelineExecServiceImpl();
            PipelineInstance pipelineInstance = pipelineExecService.findPipelineInstance(id);
            String time;
            if (Objects.isNull(pipelineInstance)){
                instanceList.add(instance);
                time = PipelineUtil.formatDateTime(instance.getRunTime());
                instance.setRunTimeDate(time);
            }else {
                Integer integer = findPipelineRunTime(id);
                pipelineInstance.setRunTime(integer);
                instanceList.add(pipelineInstance);
                time = PipelineUtil.formatDateTime(pipelineInstance.getRunTime());
                pipelineInstance.setRunTimeDate(time);
            }
        }
        joinTemplate.joinQuery(instanceList);
        return PaginationBuilder.build(pagination, instanceList);
    }

    @Override
    public Pagination<PipelineInstance> findUserInstance(PipelineInstanceQuery pipelineInstanceQuery){
        String loginId = LoginContext.getLoginId();
        List<Pipeline> userPipeline = authorityService.findUserPipeline(loginId);
        pipelineInstanceQuery.setPipelineList(userPipeline);
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findAllPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> execInstanceList = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        List<PipelineInstance> instanceList = new ArrayList<>();
        for (PipelineInstance instance : execInstanceList) {
            PipelineExecServiceImpl pipelineExecService = new PipelineExecServiceImpl();
            String id = instance.getInstanceId();
            PipelineInstance pipelineInstance = pipelineExecService.findPipelineInstance(id);
            String time;
            if (Objects.isNull(pipelineInstance)){
                instanceList.add(instance);
                time = PipelineUtil.formatDateTime(instance.getRunTime());
                instance.setRunTimeDate(time);
            }else {
                Integer integer = findPipelineRunTime(id);
                pipelineInstance.setRunTime(integer);
                time = PipelineUtil.formatDateTime(pipelineInstance.getRunTime());
                pipelineInstance.setRunTimeDate(time);
                instanceList.add(pipelineInstance);
            }
        }
        joinTemplate.joinQuery(instanceList);
        return PaginationBuilder.build(pagination,instanceList);
    }

    public ExecutorService executorService = Executors.newCachedThreadPool();

    //运行时间
    public  final Map<String,Integer> pipelineRunTime = new HashMap<>();

    public void pipelineRunTime(String instanceId){
        pipelineRunTime.put(instanceId,0);
        executorService.submit(() -> {
            while (true){
                Thread.currentThread().setName(instanceId);
                int integer = pipelineRunTime.get(instanceId);
                try {
                    Thread.sleep(1000);
                    integer = integer +1;
                    pipelineRunTime.put(instanceId,integer);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }
            }
        });
    }

    public Integer findPipelineRunTime(String instanceId){
        Integer integer = pipelineRunTime.get(instanceId);
        if (Objects.isNull(integer)){
            return 0;
        }
        return integer;
    }

    public void removePipelineRunTime(String instanceId){
        pipelineRunTime.remove(instanceId);
    }










}


























