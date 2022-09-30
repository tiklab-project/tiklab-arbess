package net.tiklab.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.pipeline.definition.dao.PipelineDeployDao;
import net.tiklab.pipeline.definition.entity.PipelineDeployEntity;
import net.tiklab.pipeline.definition.model.PipelineDeploy;
import net.tiklab.pipeline.setting.model.Proof;
import net.tiklab.pipeline.setting.service.ProofService;
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
    ProofService proofService;

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

    //根据流水线id查询配置
    @Override
    public PipelineDeploy findDeploy(String pipelineId) {
        List<PipelineDeploy> allBuild = findAllDeploy();
        if (allBuild != null){
            for (PipelineDeploy pipelineDeploy : allBuild) {
                if (pipelineDeploy.getPipeline() == null){
                    continue;
                }
                if (pipelineDeploy.getPipeline().getPipelineId().equals(pipelineId)){
                    joinTemplate.joinQuery(pipelineDeploy);
                    return pipelineDeploy;
                }
            }
        }
        return null;
    }

    @Override
    public void updateDeploy(PipelineDeploy pipelineDeploy, String pipelineId) {
        PipelineDeploy deploy = findDeploy(pipelineId);
        if (deploy == null){
            if (pipelineDeploy.getSort() != 0){
                createDeploy(pipelineDeploy);
            }
        }else {
            if (pipelineDeploy.getSort() == 0){
                deleteDeploy(deploy.getDeployId());
            }else {
                pipelineDeploy.setDeployId(deploy.getDeployId());
                updateDeploy(pipelineDeploy);
            }
        }
    }

    //查询单个
    @Override
    public PipelineDeploy findOneDeploy(String deployId) {
        PipelineDeployEntity oneDeploy = pipelineDeployDao.findOneDeploy(deployId);
        PipelineDeploy pipelineDeploy = BeanMapper.map(pipelineDeployDao.findOneDeploy(deployId), PipelineDeploy.class);
        if (oneDeploy.getProofId() != null){
            Proof oneProof = proofService.findOneProof(oneDeploy.getProofId());
            pipelineDeploy.setProof(oneProof);
        }
        return pipelineDeploy;
    }

    //查询所有
    @Override
    public List<PipelineDeploy> findAllDeploy() {
        return BeanMapper.mapList(pipelineDeployDao.findAllDeploy(), PipelineDeploy.class);
    }

    @Override
    public List<PipelineDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(pipelineDeployDao.findAllCodeList(idList), PipelineDeploy.class);
    }

    //修改
    private void updateDeploy(PipelineDeploy pipelineDeploy) {
        pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }

}
