package com.doublekit.pipeline.implement.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.implement.dao.PipelineHistoryDao;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.model.PipelineHistory;
import com.doublekit.pipeline.implement.model.PipelineHistoryDetails;
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
    PipelineConfigureDao pipelineConfigureDao;

    //创建
    @Override
    public String createPipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        return pipelineHistoryDao.createPipelineHistory(pipelineHistoryEntity);
    }

    //删除
    @Override
    public void deletePipelineHistory(String id) {

        pipelineHistoryDao.deletePipelineHistory(id);

    }

    //修改
    @Override
    public void updatePipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        pipelineHistoryDao.updatePipelineHistory(pipelineHistoryEntity);

    }

    //查询单个
    @Override
    public PipelineHistory selectPipelineHistory(String id) {

        PipelineHistoryEntity pipelineHistoryEntity = pipelineHistoryDao.selectPipelineHistory(id);

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

    //获取最近一次的构建历史
    public PipelineHistory selectLastPipelineHistory(String pipelineId) {

        List<PipelineHistory> pipelineHistoryList = selectAllPipelineNameList(pipelineId);

        if (pipelineHistoryList.size() != 0){
            //将同一任务构建历史通过时间排序
            pipelineHistoryList.sort(new Comparator<PipelineHistory>() {
                @Override
                public int compare(PipelineHistory pipelineHistory1, PipelineHistory pipelineHistory2) {

                    return pipelineHistory1.getHistoryCreateTime().compareTo(pipelineHistory2.getHistoryCreateTime());
                }
            });

            String historyId = pipelineHistoryList.get(pipelineHistoryList.size() - 1).getHistoryId();

            return selectPipelineHistory(historyId);
        }

        return null;

    }

    @Override
    public List<PipelineHistory> selectPipelineHistoryList(List<String> idList) {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = pipelineHistoryDao.selectPipelineHistoryList(idList);

        return BeanMapper.mapList(pipelineHistoryEntityList, PipelineHistory.class);
    }

    /**
     * 根据流水线id查询所有构建信息
     * @param pipelineId 流水线id
     * @return 配置信息集合
     */
    @Override
    public List<PipelineHistoryDetails> selectAll(String pipelineId) {

        List<PipelineHistory> pipelineHistories = selectAllPipelineNameList(pipelineId);

        List<PipelineHistoryDetails> pipelineHistoryDetailsList = new ArrayList<>();

        for (PipelineHistory pipelineHistory : pipelineHistories) {

            PipelineHistoryDetails pipelineHistoryDetails = new PipelineHistoryDetails();

            //设置id
            if (pipelineHistory.getHistoryId() != null) {

                pipelineHistoryDetails.setId(pipelineHistory.getHistoryId());

            }

            //设置状态
            pipelineHistoryDetails.setStatus(pipelineHistory.getPipelineLog().getLogRunStatus());

            //设置创建构建时间
            if (pipelineHistory.getHistoryCreateTime() != null){

                pipelineHistoryDetails.setCreateStructureTime(pipelineHistory.getPipelineLog().getLogCreateTime());

            }

            //设置构建方式 (1:自动 2：手动)
            pipelineHistoryDetails.setStructureWay(pipelineHistory.getHistoryWay());

            //设置执行人
            if (pipelineHistory.getPipeline().getPipelineName() != null){

                pipelineHistoryDetails.setImplementor(pipelineHistory.getPipeline().getPipelineName());

            }

            //设置执行时长
            int ImplementTime = pipelineHistory.getPipelineLog().getLogCodeTime() + pipelineHistory.getPipelineLog().getLogDeployTime() + pipelineHistory.getPipelineLog().getLogPackTime();

            pipelineHistoryDetails.setImplementTime(ImplementTime);

            // 设置代码源
            PipelineConfigureEntity pipelineConfigureEntity = pipelineConfigureDao.selectPipelineConfigure(pipelineHistory.getPipelineConfigure().getConfigureId());

            if (pipelineConfigureEntity != null){

                pipelineHistoryDetails.setCodeSource(pipelineConfigureEntity.getConfigureCodeSource());

            }

            // 设置凭证
            if (pipelineHistory.getProof().getProofType() != null)

                pipelineHistoryDetails.setProof(pipelineHistory.getProof().getProofType());

            // 设置分支
            if (pipelineHistory.getHistoryBranch() != null){

                pipelineHistoryDetails.setBranch(pipelineHistory.getHistoryBranch());

            }

            pipelineHistoryDetailsList.add(pipelineHistoryDetails);

        }

        return pipelineHistoryDetailsList;
    }

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    private List<PipelineHistory> selectAllPipelineNameList(String pipelineId) {

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
}
