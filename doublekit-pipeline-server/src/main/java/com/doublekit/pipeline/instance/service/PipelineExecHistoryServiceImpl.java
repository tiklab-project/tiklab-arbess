package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.PipelineExecHistoryDao;
import com.doublekit.pipeline.instance.entity.PipelineExecHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecHistoryDetails;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineExecHistoryServiceImpl implements PipelineExecHistoryService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;


    //创建
    @Override
    public String createHistory(PipelineExecHistory pipelineExecHistory) {

        PipelineExecHistoryEntity pipelineExecHistoryEntity = BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class);

        return pipelineExecHistoryDao.createHistory(pipelineExecHistoryEntity);
    }

    //删除
    @Override
    public void deleteHistory(String historyId) {

        if (historyId != null){
            PipelineExecHistory pipelineExecHistory = findOneHistory(historyId);
            if (pipelineExecHistory !=null){
                // 删除对应的日志
                pipelineExecLogService.deleteLog(pipelineExecHistory.getPipelineLog().getLogId());
            }
            //删除对应的历史
            pipelineExecHistoryDao.deleteHistory(historyId);
        }

    }

    //根据流水线id删除全部历史
    @Override
    public void deleteAllHistory(String pipelineId) {
        List<PipelineExecHistory> pipelineExecHistoryList = findAllPipelineIdList(pipelineId);
        if (pipelineExecHistoryList != null){
            for (PipelineExecHistory pipelineExecHistory : pipelineExecHistoryList) {
                deleteHistory(pipelineExecHistory.getHistoryId());
            }
        }
    }

    //修改
    @Override
    public void updateHistory(PipelineExecHistory pipelineExecHistory) {

        PipelineExecHistoryEntity pipelineExecHistoryEntity = BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class);

        pipelineExecHistoryDao.updateHistory(pipelineExecHistoryEntity);

    }

    //查询单个
    @Override
    public PipelineExecHistory findOneHistory(String historyId) {

        PipelineExecHistoryEntity pipelineExecHistoryEntity = pipelineExecHistoryDao.findOneHistory(historyId);
        PipelineExecHistory pipelineExecHistory = BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistory);

        return pipelineExecHistory;
    }

    //查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory() {

        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findAllHistory();
        List<PipelineExecHistory> pipelineExecHistoryList = BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistoryList);

        return pipelineExecHistoryList;
    }

    @Override
    public List<PipelineExecHistory> findHistoryList(List<String> idList) {

        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findHistoryList(idList);

        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    /**
     * 查询对应流水线id下的所有信息
     * @param pipelineId 流水线id
     * @return 信息集合
     */
    @Override
    public List<PipelineExecHistoryDetails> findAll(String pipelineId) {

        List<PipelineExecHistory> pipelineHistories = findAllPipelineIdList(pipelineId);

        List<PipelineExecHistoryDetails> pipelineExecHistoryDetailsList = new ArrayList<>();

        //获取PipelineHistoryDetails对应的数据
        for (PipelineExecHistory pipelineExecHistory : pipelineHistories) {

            PipelineExecHistoryDetails pipelineExecHistoryDetails = new PipelineExecHistoryDetails();
            //获取id
            if (pipelineExecHistory.getHistoryId() != null) {
                pipelineExecHistoryDetails.setHistoryId(pipelineExecHistory.getHistoryId());
            }

            pipelineExecHistoryDetails.setHistoryNumber(pipelineExecHistory.getHistoryNumber());
            //获取状态
            pipelineExecHistoryDetails.setStatus(pipelineExecHistory.getPipelineLog().getLogRunStatus());
            //获取创建构建时间
            if (pipelineExecHistory.getHistoryCreateTime() != null){
                pipelineExecHistoryDetails.setCreateStructureTime(pipelineExecHistory.getHistoryCreateTime());
            }

            //获取构建方式 (1:自动 2：手动)
            pipelineExecHistoryDetails.setStructureWay(pipelineExecHistory.getHistoryWay());
            //获取执行人
            if (pipelineExecHistory.getPipeline().getPipelineName() != null){
                pipelineExecHistoryDetails.setImplementor(pipelineExecHistory.getPipeline().getPipelineCreateUser());
            }
            //获取执行时长
            int ImplementTime = pipelineExecHistory.getPipelineLog().getLogCodeTime()
                    + pipelineExecHistory.getPipelineLog().getLogDeployTime()
                    + pipelineExecHistory.getPipelineLog().getLogPackTime()
                    + pipelineExecHistory.getPipelineLog().getLogTestTime();
            pipelineExecHistoryDetails.setImplementTime(ImplementTime);
            // 获取代码源
            int configureCodeSource = pipelineExecHistory.getConfigure().getConfigureCodeSource();
            if (configureCodeSource != 0){
                pipelineExecHistoryDetails.setCodeSource(configureCodeSource);
            }
            Proof proof = pipelineExecHistory.getProof();
            if (proof != null){
                pipelineExecHistoryDetails.setProof(proof.getProofType());
            }
            if (pipelineExecHistory.getHistoryBranch() != null){
                pipelineExecHistoryDetails.setBranch(pipelineExecHistory.getHistoryBranch());
            }
            pipelineExecHistoryDetailsList.add(pipelineExecHistoryDetails);
        }

        //将同一任务历史详情通过时间降序排序
        pipelineExecHistoryDetailsList.sort(Comparator.comparing(PipelineExecHistoryDetails::getCreateStructureTime,Comparator.reverseOrder()));

        return pipelineExecHistoryDetailsList;
    }

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    @Override
    public List<PipelineExecHistory> findAllPipelineIdList(String pipelineId) {

        List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

        List<PipelineExecHistory> pipelineHistories = findAllHistory();
            //遍历出属于同一任务的历史记录
            for (PipelineExecHistory pipelineExecHistory : pipelineHistories) {
                if (pipelineExecHistory.getPipeline().getPipelineId().equals(pipelineId)){
                    pipelineExecHistoryList.add(pipelineExecHistory);
                }
            }

            return pipelineExecHistoryList;

    }

    /**
     * 根据流水线id获取最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 构建历史
     */
    @Override
    public PipelineExecHistory findLastPipelineHistory(String pipelineId) {

        List<PipelineExecHistory> pipelineExecHistoryList = findAllPipelineIdList(pipelineId);

        if (pipelineExecHistoryList.size() != 0){
            //将同一任务构建历史通过时间排序
            pipelineExecHistoryList.sort(Comparator.comparing(PipelineExecHistory::getHistoryCreateTime));
            String historyId = pipelineExecHistoryList.get(pipelineExecHistoryList.size() - 1).getHistoryId();

            return findOneHistory(historyId);
        }

        return null;
    }

    /**
     * 完善历史信息
     * @param pipelineExecHistory 历史信息
     */
    @Override
    public void perfectHistory(PipelineExecHistory pipelineExecHistory) {

        String logId = pipelineExecHistory.getPipelineLog().getLogId();

        if (logId != null){
            //获取日志id
            pipelineExecHistory.setPipelineLog(pipelineExecLogService.findOneLog(logId));
        }
        //获取分支
        String configureBranch = pipelineExecHistory.getConfigure().getConfigureBranch();
        if (configureBranch == null){
            configureBranch="master";
        }
        pipelineExecHistory.setHistoryBranch(configureBranch);
        //设置构建方式（1：自动  2：手动）
        pipelineExecHistory.setHistoryWay(1);

        createHistory(pipelineExecHistory);
    }

    //查询日志
    @Override
    public PipelineExecLog findHistoryLog(String historyId) {

        PipelineExecHistory pipelineExecHistory = findOneHistory(historyId);

        if (pipelineExecHistory != null){
            String logId = pipelineExecHistory.getPipelineLog().getLogId();
            return  pipelineExecLogService.findOneLog(logId);
        }
        return null;
    }

    //删除历史以及日志
    public void deleteHistoryLog(String HistoryId){

        PipelineExecHistory pipelineExecHistory = findOneHistory(HistoryId);
        //判断是否存在历史
        if (pipelineExecHistory != null){
            String logId = pipelineExecHistory.getPipelineLog().getLogId();
            if (logId != null){
                pipelineExecLogService.deleteLog(logId);
            }
        }

       deleteHistory(HistoryId);
    }
}
