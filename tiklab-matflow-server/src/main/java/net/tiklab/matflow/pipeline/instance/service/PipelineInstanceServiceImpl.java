package net.tiklab.matflow.pipeline.instance.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.matflow.stages.model.PipelineStages;
import net.tiklab.matflow.stages.service.PipelineStagesService;
import net.tiklab.matflow.pipeline.execute.model.TaskRunLog;
import net.tiklab.matflow.pipeline.instance.dao.PipelineInstanceDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import net.tiklab.matflow.pipeline.instance.model.*;
import net.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 流水线实例服务
 */
@Service
@Exporter
public class PipelineInstanceServiceImpl implements PipelineInstanceService {

    @Autowired
    private PipelineInstanceDao pipelineInstanceDao;

    @Autowired
    PipelineAuthorityService authorityService;

    @Autowired
    private TaskInstanceLogService taskInstanceLogService;

    @Autowired
    private PipelineStagesService stagesServer;

    @Autowired
    private JoinTemplate joinTemplate;

    //运行时间
    Map<String, Integer> runTime = PipelineExecServiceImpl.runTime;

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
        pipelineInstance.setRunStatus(30);
        String instanceId = createInstance(pipelineInstance);

        //初始化基本信息
        pipelineInstance.setSort(1);
        pipelineInstance.setInstanceId(instanceId);

        //构建次数
        PipelineInstance latelyInstance = findLastInstance(pipelineId);
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
        taskInstanceLogService.deleteInstanceLog(instanceId);
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
    public Pagination<PipelineInstance> findUserAllInstance(PipelineInstanceQuery pipelineInstanceQuery){
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findAllPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);

    }

    @Override
    public Pagination<PipelineInstance> findUserRunPageInstance(PipelineInstanceQuery pipelineInstanceQuery){
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findUserRunPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

    @Override
    public PipelineInstance findLatelyInstance(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLatelyInstance(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public PipelineInstance findLastInstance(String pipelineId){

        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLastInstance(pipelineId);

        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public PipelineInstance findRunInstance(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findRunInstance(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineInstance> findInstanceList(List<String> idList) {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findInstanceList(idList);
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    @Override
    public TaskRunLog findAll(String instanceId){
        PipelineInstance instance = findOneInstance(instanceId);

        if (instance == null){
            return null;
        }

        TaskRunLog execRunLog = new TaskRunLog();
        execRunLog.setName(String.valueOf(instance.getFindNumber()));
        Pipeline pipeline = instance.getPipeline();
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        List<TaskRunLog> runLogList = new ArrayList<>();

        if (pipelineType == 1){
            List<TaskInstanceLog> allLog = taskInstanceLogService.findAllLog(instanceId);
            for (TaskInstanceLog execLog : allLog) {
                TaskRunLog runLog =  initLog(execLog);
                runLogList.add(runLog);
            }
            Map<String, Object> timeState = findTimeState(runLogList);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }

        if (pipelineType == 2){
            //多阶段
            List<PipelineStages> stagesMainStage = stagesServer.findAllMainStage(pipelineId);
            for (PipelineStages stages : stagesMainStage) {

                String stagesId = stages.getStagesId();
                //并行阶段
                List<TaskRunLog> logList= new ArrayList<>();
                List<PipelineStages> allMainStage = stagesServer.findOtherStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {

                    String stagesStagesId = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<TaskInstanceLog> allStagesLog = taskInstanceLogService.findAllStagesLog(instanceId, stagesStagesId);
                    List<TaskRunLog> logs = new ArrayList<>();
                    for (TaskInstanceLog log : allStagesLog) {
                        TaskRunLog runLogs =  initLog(log);
                        logs.add(runLogs);
                    }
                    TaskRunLog taskRunLog = initRunLog(logs, pipelineStages);
                    logList.add(taskRunLog);
                }
                TaskRunLog runLog = initRunLog(logList, stages);
                runLogList.add(runLog);
            }

            //添加消息阶段
            List<TaskInstanceLog> allLog = taskInstanceLogService.findAllLog(instanceId);
            allLog.removeIf(pipelineExecLog -> PipelineUtil.isNoNull(pipelineExecLog.getStagesId()));
            if (allLog.size() == 0){
                execRunLog.setRunLogList(runLogList);
                return execRunLog;
            }

            List<TaskRunLog> logs = new ArrayList<>();
            for (TaskInstanceLog taskInstanceLog : allLog) {
                TaskRunLog log =  initLog(taskInstanceLog);
                logs.add(log);
            }
            PipelineStages stages = new PipelineStages();
            stages.setStagesName("后置任务");
            stages.setStagesId("后置任务");
            TaskRunLog runLog = initRunLog(logs, stages);
            runLogList.add(runLog);
        }
        execRunLog.setRunLogList(runLogList);
        return execRunLog;
    }

    public TaskRunLog initLog(TaskInstanceLog log){
        TaskRunLog runLog = new TaskRunLog();
        runLog.setId(log.getLogId());
        runLog.setState(log.getRunState());
        runLog.setType(log.getTaskType());
        runLog.setTime(log.getRunTime());
        runLog.setName(log.getTaskName());
        String logAddress = log.getLogAddress();
        runLog.setRunLog(PipelineUtil.readFile(logAddress,500));
        return runLog;
    }

    public TaskRunLog initRunLog(List<TaskRunLog> logs, PipelineStages pipelineStages){
        TaskRunLog taskRunLog = new TaskRunLog();
        taskRunLog.setName(pipelineStages.getStagesName());
        taskRunLog.setRunLogList(logs);
        taskRunLog.setName(pipelineStages.getStagesName());
        taskRunLog.setId(pipelineStages.getStagesId());
        Map<String, Object> timeState = findTimeState(logs);
        taskRunLog.setState((Integer) timeState.get("state"));
        taskRunLog.setTime((Integer) timeState.get("time"));
        taskRunLog.setRunLog((String) timeState.get("runLog"));
        return taskRunLog;
    }

    public Map<String,Object> findTimeState(List<TaskRunLog> logs){
        int time = 0;
        int state = 0;
        int runState = 0;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (TaskRunLog log : logs) {
            time = time + log.getTime();
            runLog.append(log.getRunLog());
            state = state + log.getState();
            if (log.getState() == 1){
                runState = 1;
            }
            if (log.getState() == 20 && runState != 1){
                runState = 20;
            }
        }

        if (runState == 0 ){
            runState = state/logs.size();
        }

        map.put("time",time);
        map.put("state",runState);
        map.put("runLog", runLog.toString());
        return map;
    }

    @Override
    public Pagination<PipelineInstance> findPageInstance(PipelineInstanceQuery pipelineInstanceQuery){
        if (pipelineInstanceQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findPageInstance(pipelineInstanceQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        if (pipelineExecHistories == null){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

    @Override
    public Pagination<PipelineInstance> findUserAllHistory(PipelineInstanceQuery pipelineHistoryQuery){
        String id = LoginContext.getLoginId();
        List<Pipeline> allPipeline = authorityService.findUserPipeline(id);
        if (allPipeline.isEmpty()){
            return null;
        }
        if (!PipelineUtil.isNoNull(pipelineHistoryQuery.getPipelineId())){
            pipelineHistoryQuery.setPipelineList(allPipeline);
        }
        return findUserAllInstance(pipelineHistoryQuery);
    }


    /**
     * 获取正在运行的流水线
     * @param pipelineHistoryQuery 分页
     * @return 流水线信息
     */
    @Override
    public Pagination<PipelineInstance> findUserRunPageHistory(PipelineInstanceQuery pipelineHistoryQuery){
        List<Pipeline> userPipeline = authorityService.findUserPipeline(LoginContext.getLoginId());
        if (userPipeline.isEmpty()){
            return null;
        }
        pipelineHistoryQuery.setPipelineList(userPipeline);
        Pagination<PipelineInstance> pageHistory =
                findUserAllInstance(pipelineHistoryQuery);

        List<PipelineInstance> dataList = pageHistory.getDataList();
        if (dataList.isEmpty()){
            return null;
        }
        //判断是否有正在运行的历史
        int historyStatus = dataList.get(0).getRunStatus();
        if (historyStatus != 30){
            return pageHistory;
        }
        for (PipelineInstance history : dataList) {
            String historyId = history.getInstanceId();
            int status = history.getRunStatus();
            if (status != 30){
                continue;
            }
            int time = 0;
            //获取正在运行的历史的时间
            List<TaskInstanceLog> allLog = taskInstanceLogService.findAllLog(historyId);
            for (TaskInstanceLog log : allLog) {
                Integer integer = runTime.get(log.getLogId());
                if (integer != null){
                    time = time + integer;
                }
            }
            history.setRunTime(time);
        }
        pageHistory.setDataList(dataList);
        return pageHistory;
    }











}


























