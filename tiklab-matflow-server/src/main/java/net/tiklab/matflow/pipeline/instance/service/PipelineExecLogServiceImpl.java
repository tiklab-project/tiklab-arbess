package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.pipeline.instance.dao.PipelineExecLogDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineExecLogEntity;
import net.tiklab.matflow.pipeline.instance.model.PipelineExecLog;
import net.tiklab.matflow.pipeline.instance.service.PipelineExecLogService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineExecLogServiceImpl implements PipelineExecLogService {

    @Autowired
    PipelineExecLogDao pipelineExecLogDao;

    //创建
    @Override
    public String createLog(PipelineExecLog pipelineExecLog) {
        PipelineExecLogEntity logEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);
        return pipelineExecLogDao.createLog(logEntity);
    }

    //删除
    public void deleteLog(String logId) {
        pipelineExecLogDao.deleteLog(logId);
    }

    //更新
    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {
        pipelineExecLogDao.updateLog(BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class));
    }

    //查询单个
    @Override
    public PipelineExecLog findOneLog(String logId){
        PipelineExecLogEntity execLogEntity = pipelineExecLogDao.findOne(logId);
        return BeanMapper.map(execLogEntity,PipelineExecLog.class);
    }

    /**
     * 删除历史对应的所有日志
     * @param historyId 历史id
     */
    @Override
    public void deleteHistoryLog(String historyId) {
        pipelineExecLogDao.deleteAllLog(historyId);
    }

    /**
     * 查询历史对应的日志
     * @param historyId 历史id
     * @return 日志集合
     */
    @Override
    public List<PipelineExecLog> findAllLog(String historyId){
        List<PipelineExecLogEntity> pipelineExecLogList = pipelineExecLogDao.findAllLog(historyId);
        List<PipelineExecLog> list = BeanMapper.mapList(pipelineExecLogList, PipelineExecLog.class);
        if (list == null){
           return null;
        }
        list.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
        return list;
    }

    /**
     * 根据阶段查询对应日志
     * @param stagesId 阶段id
     * @return 日志
     */
    public List<PipelineExecLog> findAllStagesLog(String historyId,String stagesId){
        List<PipelineExecLogEntity> pipelineExecLogList = pipelineExecLogDao.findAllStagesLog(historyId,stagesId);
        return BeanMapper.mapList(pipelineExecLogList, PipelineExecLog.class);
    }

    //查询所有
    @Override
    public List<PipelineExecLog> findAllLog() {
        List<PipelineExecLogEntity> allLog = pipelineExecLogDao.findAllLog();
        return BeanMapper.mapList(allLog, PipelineExecLog.class);
    }

    @Override
    public List<PipelineExecLog> findAllLogList(List<String> idList) {
        List<PipelineExecLogEntity> pipelineLogList = pipelineExecLogDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, PipelineExecLog.class);
    }


}
