package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.PipelineHistoryDao;
import com.doublekit.pipeline.instance.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineHistoryDetails;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineHistoryServiceImpl implements PipelineHistoryService{

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineHistoryDao pipelineHistoryDao;

    @Autowired
    PipelineLogService pipelineLogService;


    //创建
    @Override
    public String createPipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        return pipelineHistoryDao.createPipelineHistory(pipelineHistoryEntity);
    }

    //删除
    @Override
    public void deletePipelineHistory(String historyId) {

        if (historyId != null){

            PipelineHistory pipelineHistory = selectPipelineHistory(historyId);

            if (pipelineHistory !=null){
                // 删除对应的日志
                pipelineLogService.deletePipelineLog(pipelineHistory.getPipelineLog().getLogId());
            }

            //删除对应的历史
            pipelineHistoryDao.deletePipelineHistory(historyId);
        }

    }

    //根据流水线id删除全部历史
    @Override
    public void deleteAllPipelineHistory(String pipelineId) {

        List<PipelineHistory> pipelineHistoryList = selectAllPipelineIdList(pipelineId);

        if (pipelineHistoryList != null){

            for (PipelineHistory pipelineHistory : pipelineHistoryList) {

                deletePipelineHistory(pipelineHistory.getHistoryId());

            }
        }
    }

    //修改
    @Override
    public void updatePipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        pipelineHistoryDao.updatePipelineHistory(pipelineHistoryEntity);

    }

    //查询单个
    @Override
    public PipelineHistory selectPipelineHistory(String historyId) {

        PipelineHistoryEntity pipelineHistoryEntity = pipelineHistoryDao.selectPipelineHistory(historyId);
        PipelineHistory pipelineHistory = BeanMapper.map(pipelineHistoryEntity, PipelineHistory.class);
        joinTemplate.joinQuery(pipelineHistory);

        return pipelineHistory;
    }

    //查询所有
    @Override
    public List<PipelineHistory> selectAllPipelineHistory() {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = pipelineHistoryDao.selectAllPipelineHistory();
        List<PipelineHistory> pipelineHistoryList = BeanMapper.mapList(pipelineHistoryEntityList, PipelineHistory.class);
        joinTemplate.joinQuery(pipelineHistoryList);

        return pipelineHistoryList;
    }

    @Override
    public List<PipelineHistory> selectPipelineHistoryList(List<String> idList) {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = pipelineHistoryDao.selectPipelineHistoryList(idList);

        return BeanMapper.mapList(pipelineHistoryEntityList, PipelineHistory.class);
    }

    /**
     * 查询对应流水线id下的所有信息
     * @param pipelineId 流水线id
     * @return 信息集合
     */
    @Override
    public List<PipelineHistoryDetails> selectAll(String pipelineId) {

        List<PipelineHistory> pipelineHistories = selectAllPipelineIdList(pipelineId);

        List<PipelineHistoryDetails> pipelineHistoryDetailsList = new ArrayList<>();

        //获取PipelineHistoryDetails对应的数据
        for (PipelineHistory pipelineHistory : pipelineHistories) {

            PipelineHistoryDetails pipelineHistoryDetails = new PipelineHistoryDetails();

            //获取id
            if (pipelineHistory.getHistoryId() != null) {

                pipelineHistoryDetails.setHistoryId(pipelineHistory.getHistoryId());

            }

            pipelineHistoryDetails.setHistoryNumber(pipelineHistory.getHistoryNumber());


            //获取状态
            pipelineHistoryDetails.setStatus(pipelineHistory.getPipelineLog().getLogRunStatus());

            //获取创建构建时间
            if (pipelineHistory.getHistoryCreateTime() != null){

                pipelineHistoryDetails.setCreateStructureTime(pipelineHistory.getHistoryCreateTime());

            }

            //获取构建方式 (1:自动 2：手动)
            pipelineHistoryDetails.setStructureWay(pipelineHistory.getHistoryWay());

            //获取执行人
            if (pipelineHistory.getPipeline().getPipelineName() != null){

                pipelineHistoryDetails.setImplementor(pipelineHistory.getPipeline().getPipelineCreateUser());

            }

            //获取执行时长
            int ImplementTime = pipelineHistory.getPipelineLog().getLogCodeTime() + pipelineHistory.getPipelineLog().getLogDeployTime() + pipelineHistory.getPipelineLog().getLogPackTime();

            pipelineHistoryDetails.setImplementTime(ImplementTime);

            // 获取代码源
            String configureCodeSource = pipelineHistory.getPipelineConfigure().getConfigureCodeSource();

            if (configureCodeSource != null){

                pipelineHistoryDetails.setCodeSource(configureCodeSource);

            }

            // 获取凭证类型
            Proof proof = pipelineHistory.getProof();
            if (proof != null){
                pipelineHistoryDetails.setProof(proof.getProofType());
            }

            // 获取分支
            if (pipelineHistory.getHistoryBranch() != null){

                pipelineHistoryDetails.setBranch(pipelineHistory.getHistoryBranch());
            }

            pipelineHistoryDetailsList.add(pipelineHistoryDetails);
        }

        //将同一任务历史详情通过时间降序排序
        pipelineHistoryDetailsList.sort(Comparator.comparing(PipelineHistoryDetails::getCreateStructureTime,Comparator.reverseOrder()));

        return pipelineHistoryDetailsList;
    }

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    @Override
    public List<PipelineHistory> selectAllPipelineIdList(String pipelineId) {

        List<PipelineHistory> pipelineHistoryList = new ArrayList<>();

        List<PipelineHistory> pipelineHistories = selectAllPipelineHistory();
            //遍历出属于同一任务的历史记录
            for (PipelineHistory pipelineHistory : pipelineHistories) {
                if (pipelineHistory.getPipeline().getPipelineId().equals(pipelineId)){
                    pipelineHistoryList.add(pipelineHistory);
                }
            }

            return pipelineHistoryList;

    }

    /**
     * 根据流水线id获取最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 构建历史
     */
    @Override
    public PipelineHistory selectLastPipelineHistory(String pipelineId) {

        List<PipelineHistory> pipelineHistoryList = selectAllPipelineIdList(pipelineId);

        if (pipelineHistoryList.size() != 0){
            //将同一任务构建历史通过时间排序
            pipelineHistoryList.sort(Comparator.comparing(PipelineHistory::getHistoryCreateTime));
            String historyId = pipelineHistoryList.get(pipelineHistoryList.size() - 1).getHistoryId();

            return selectPipelineHistory(historyId);
        }

        return null;
    }

    /**
     * 创建历史表
     * @param pipelineHistory 历史信息
     * @return 流水线历史id
     */
    @Override
    public String foundPipelineHistory(PipelineHistory pipelineHistory) {

        String logId = pipelineHistory.getPipelineLog().getLogId();

        if (logId != null){
            //获取日志id
            pipelineHistory.setPipelineLog(pipelineLogService.selectPipelineLog(logId));
        }
        //获取分支
        String configureBranch = pipelineHistory.getPipelineConfigure().getConfigureBranch();
        if (configureBranch == null){
            configureBranch="master";
        }
        pipelineHistory.setHistoryBranch(configureBranch);
        //设置构建方式（1：自动  2：手动）
        pipelineHistory.setHistoryWay(1);

        return createPipelineHistory(pipelineHistory);
    }

    //查询日志
    @Override
    public PipelineLog selectHistoryLog(String historyId) {

        PipelineHistory pipelineHistory = selectPipelineHistory(historyId);

        if (pipelineHistory != null){
            String logId = pipelineHistory.getPipelineLog().getLogId();
            pipelineLogService.selectPipelineLog(logId);

            return  pipelineLogService.selectPipelineLog(logId);
        }
        return null;
    }

    //删除历史以及日志
    public void deleteHistoryLog(String HistoryId){

        PipelineHistory pipelineHistory = selectPipelineHistory(HistoryId);
        //判断是否存在历史
        if (pipelineHistory != null){
            String logId = pipelineHistory.getPipelineLog().getLogId();
            if (logId != null){
                pipelineLogService.deletePipelineLog(logId);
            }
        }

       deletePipelineHistory(HistoryId);
    }
}
