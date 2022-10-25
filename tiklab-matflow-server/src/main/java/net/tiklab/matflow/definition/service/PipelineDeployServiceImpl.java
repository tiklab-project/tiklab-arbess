package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineDeployDao;
import net.tiklab.matflow.definition.entity.PipelineDeployEntity;
import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.matflow.setting.service.ProofService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineDeployServiceImpl implements PipelineDeployService {

    @Autowired
    PipelineDeployDao pipelineDeployDao;

    @Autowired
    JoinTemplate joinTemplate;


    //创建
    @Override
    public String createDeploy(PipelineDeploy pipelineDeploy) {
        return pipelineDeployDao.createDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        pipelineDeployDao.deleteDeploy(deployId);
    }

    //查询单个
    @Override
    public PipelineDeploy findOneDeploy(String deployId) {
        PipelineDeploy pipelineDeploy = BeanMapper.map(pipelineDeployDao.findOneDeploy(deployId), PipelineDeploy.class);
        joinTemplate.joinQuery(pipelineDeploy);
        return pipelineDeploy;
    }

    //查询所有
    @Override
    public List<PipelineDeploy> findAllDeploy() {
        List<PipelineDeploy> pipelineDeploys = BeanMapper.mapList(pipelineDeployDao.findAllDeploy(), PipelineDeploy.class);
        joinTemplate.joinQuery(pipelineDeploys);
        return pipelineDeploys;
    }

    @Override
    public List<PipelineDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(pipelineDeployDao.findAllCodeList(idList), PipelineDeploy.class);
    }

    //修改
    @Override
    public void updateDeploy(PipelineDeploy pipelineDeploy) {
        pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }

}
