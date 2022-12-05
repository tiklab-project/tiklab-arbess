package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineStagesDao;
import net.tiklab.matflow.definition.entity.PipelineStagesEntity;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineStagesServerImpl implements PipelineStagesServer {


    @Autowired
    PipelineStagesDao stagesDao;


    /**
     * 创建阶段
     * @param stages 阶段信息
     * @return 阶段id
     */
    @Override
    public String createStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    /**
     * 更新阶段
     * @param stages 阶段信息
     */
    @Override
    public void updateStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    /**
     * 删除阶段
     * @param stageId 阶段id
     */
    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    /**
     * 查询单个阶段
     * @param stageId 阶段id
     * @return 阶段信息
     */
    @Override
    public PipelineStages findOneStages(String stageId) {
        PipelineStagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, PipelineStages.class);
    }

    /**
     * 查询所有阶段
     * @return 阶段信息集合
     */
    @Override
    public List<PipelineStages> findAllStages() {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStages();
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

    @Override
    public List<PipelineStages> findAllStagesList(List<String> idList) {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }
}
