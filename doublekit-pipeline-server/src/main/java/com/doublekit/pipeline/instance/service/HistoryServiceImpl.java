package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.HistoryDao;
import com.doublekit.pipeline.instance.entity.HistoryEntity;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.instance.model.HistoryDetails;
import com.doublekit.pipeline.instance.model.Log;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    HistoryDao historyDao;

    @Autowired
    LogService logService;


    //创建
    @Override
    public String createHistory(History history) {

        HistoryEntity historyEntity = BeanMapper.map(history, HistoryEntity.class);

        return historyDao.createHistory(historyEntity);
    }

    //删除
    @Override
    public void deleteHistory(String historyId) {

        if (historyId != null){
            History history = findOneHistory(historyId);
            if (history !=null){
                // 删除对应的日志
                logService.deleteLog(history.getPipelineLog().getLogId());
            }
            //删除对应的历史
            historyDao.deleteHistory(historyId);
        }

    }

    //根据流水线id删除全部历史
    @Override
    public void deleteAllHistory(String pipelineId) {
        List<History> historyList = findAllPipelineIdList(pipelineId);
        if (historyList != null){
            for (History history : historyList) {
                deleteHistory(history.getHistoryId());
            }
        }
    }

    //修改
    @Override
    public void updateHistory(History history) {

        HistoryEntity historyEntity = BeanMapper.map(history, HistoryEntity.class);

        historyDao.updateHistory(historyEntity);

    }

    //查询单个
    @Override
    public History findOneHistory(String historyId) {

        HistoryEntity historyEntity = historyDao.findOneHistory(historyId);
        History history = BeanMapper.map(historyEntity, History.class);
        joinTemplate.joinQuery(history);

        return history;
    }

    //查询所有
    @Override
    public List<History> findAllHistory() {

        List<HistoryEntity> historyEntityList = historyDao.findAllHistory();
        List<History> historyList = BeanMapper.mapList(historyEntityList, History.class);
        joinTemplate.joinQuery(historyList);

        return historyList;
    }

    @Override
    public List<History> findHistoryList(List<String> idList) {

        List<HistoryEntity> historyEntityList = historyDao.findHistoryList(idList);

        return BeanMapper.mapList(historyEntityList, History.class);
    }

    /**
     * 查询对应流水线id下的所有信息
     * @param pipelineId 流水线id
     * @return 信息集合
     */
    @Override
    public List<HistoryDetails> findAll(String pipelineId) {

        List<History> pipelineHistories = findAllPipelineIdList(pipelineId);

        List<HistoryDetails> historyDetailsList = new ArrayList<>();

        //获取PipelineHistoryDetails对应的数据
        for (History history : pipelineHistories) {

            HistoryDetails historyDetails = new HistoryDetails();
            //获取id
            if (history.getHistoryId() != null) {
                historyDetails.setHistoryId(history.getHistoryId());
            }

            historyDetails.setHistoryNumber(history.getHistoryNumber());
            //获取状态
            historyDetails.setStatus(history.getPipelineLog().getLogRunStatus());
            //获取创建构建时间
            if (history.getHistoryCreateTime() != null){
                historyDetails.setCreateStructureTime(history.getHistoryCreateTime());
            }

            //获取构建方式 (1:自动 2：手动)
            historyDetails.setStructureWay(history.getHistoryWay());
            //获取执行人
            if (history.getPipeline().getPipelineName() != null){
                historyDetails.setImplementor(history.getPipeline().getPipelineCreateUser());
            }
            //获取执行时长
            int ImplementTime = history.getPipelineLog().getLogCodeTime()
                    + history.getPipelineLog().getLogDeployTime()
                    + history.getPipelineLog().getLogPackTime()
                    + history.getPipelineLog().getLogTestTime();
            historyDetails.setImplementTime(ImplementTime);
            // 获取代码源
            int configureCodeSource = history.getConfigure().getConfigureCodeSource();
            if (configureCodeSource != 0){
                historyDetails.setCodeSource(configureCodeSource);
            }
            Proof proof = history.getProof();
            if (proof != null){
                historyDetails.setProof(proof.getProofType());
            }
            if (history.getHistoryBranch() != null){
                historyDetails.setBranch(history.getHistoryBranch());
            }
            historyDetailsList.add(historyDetails);
        }

        //将同一任务历史详情通过时间降序排序
        historyDetailsList.sort(Comparator.comparing(HistoryDetails::getCreateStructureTime,Comparator.reverseOrder()));

        return historyDetailsList;
    }

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    @Override
    public List<History> findAllPipelineIdList(String pipelineId) {

        List<History> historyList = new ArrayList<>();

        List<History> pipelineHistories = findAllHistory();
            //遍历出属于同一任务的历史记录
            for (History history : pipelineHistories) {
                if (history.getPipeline().getPipelineId().equals(pipelineId)){
                    historyList.add(history);
                }
            }

            return historyList;

    }

    /**
     * 根据流水线id获取最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 构建历史
     */
    @Override
    public History findLastPipelineHistory(String pipelineId) {

        List<History> historyList = findAllPipelineIdList(pipelineId);

        if (historyList.size() != 0){
            //将同一任务构建历史通过时间排序
            historyList.sort(Comparator.comparing(History::getHistoryCreateTime));
            String historyId = historyList.get(historyList.size() - 1).getHistoryId();

            return findOneHistory(historyId);
        }

        return null;
    }

    /**
     * 完善历史信息
     * @param history 历史信息
     */
    @Override
    public void perfectHistory(History history) {

        String logId = history.getPipelineLog().getLogId();

        if (logId != null){
            //获取日志id
            history.setPipelineLog(logService.findOneLog(logId));
        }
        //获取分支
        String configureBranch = history.getConfigure().getConfigureBranch();
        if (configureBranch == null){
            configureBranch="master";
        }
        history.setHistoryBranch(configureBranch);
        //设置构建方式（1：自动  2：手动）
        history.setHistoryWay(1);

        createHistory(history);
    }

    //查询日志
    @Override
    public Log findHistoryLog(String historyId) {

        History history = findOneHistory(historyId);

        if (history != null){
            String logId = history.getPipelineLog().getLogId();
            return  logService.findOneLog(logId);
        }
        return null;
    }

    //删除历史以及日志
    public void deleteHistoryLog(String HistoryId){

        History history = findOneHistory(HistoryId);
        //判断是否存在历史
        if (history != null){
            String logId = history.getPipelineLog().getLogId();
            if (logId != null){
                logService.deleteLog(logId);
            }
        }

       deleteHistory(HistoryId);
    }
}
