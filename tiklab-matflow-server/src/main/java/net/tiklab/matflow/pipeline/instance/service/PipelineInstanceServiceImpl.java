package net.tiklab.matflow.pipeline.instance.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.matflow.pipeline.instance.dao.PipelineInstanceDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import net.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import net.tiklab.matflow.task.task.service.TasksInstanceService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private JoinTemplate joinTemplate;

    //运行时间
    private final Map<String, Integer> runTime = TasksExecServiceImpl.runTime;

    //实例id与实例
    private final Map<String,PipelineInstance> instanceIdOrInstance = PipelineExecServiceImpl.instanceIdOrInstance;

    private static final Logger logger = LoggerFactory.getLogger(PipelineInstanceServiceImpl.class);

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
            pipelineInstance.setFindNumber( findNumber + 1);
        }
        updateInstance(pipelineInstance);
        return pipelineInstance;
    }

    @Override
    public void deleteInstance(String instanceId) {
        PipelineInstance instance = findOneInstance(instanceId);
        String id = instance.getPipeline().getId();
        pipelineInstanceDao.deleteInstance(instanceId);
        tasksInstanceService.deleteAllInstanceInstance(instanceId);
        String fileAddress = PipelineUtil.findFileAddress(id,2);
        //删除对应日志
        PipelineUtil.deleteFile(new File(fileAddress+"/"+instanceId+"/"));
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
    public List<PipelineInstance> findAllInstance(String pipelineId) {
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
            PipelineInstance pipelineInstance = instanceIdOrInstance.get(id);
            if (pipelineInstance != null){
                Integer integer = runTime.get(id);
                pipelineInstance.setRunTime(integer);
                execInstanceList.remove(i);
                execInstanceList.add(pipelineInstance);
            }
        }

        // for (PipelineInstance instance : execInstanceList) {
        //     String id = instance.getInstanceId();
        //     PipelineInstance pipelineInstance = instanceIdOrInstance.get(id);
        //     if (pipelineInstance != null){
        //         instance = pipelineInstance;
        //         Integer integer = runTime.get(id);
        //         instance.setRunTime(integer);
        //     }
        // }

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
        joinTemplate.joinQuery(execInstanceList);
        for (int i = execInstanceList.size() -1 ; i >= 0; i--) {
            PipelineInstance instance = execInstanceList.get(i);
            String id = instance.getInstanceId();
            PipelineInstance pipelineInstance = instanceIdOrInstance.get(id);
            if (pipelineInstance != null){
                Integer integer = runTime.get(id);
                pipelineInstance.setRunTime(integer);
                execInstanceList.remove(i);
                execInstanceList.add(pipelineInstance);
            }
        }
        return PaginationBuilder.build(pagination,execInstanceList);
    }













}


























