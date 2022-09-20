package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.service.MatFlowDeployService;
import net.tiklab.matflow.definition.dao.MatFlowDeployDao;
import net.tiklab.matflow.definition.entity.MatFlowDeployEntity;
import net.tiklab.matflow.definition.model.MatFlowDeploy;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.matflow.setting.service.ProofService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MatFlowDeployServiceImpl implements MatFlowDeployService {

    @Autowired
    MatFlowDeployDao matFlowDeployDao;

    @Autowired
    ProofService proofService;

    //创建
    @Override
    public String createDeploy(MatFlowDeploy matFlowDeploy) {
        return matFlowDeployDao.createDeploy(BeanMapper.map(matFlowDeploy, MatFlowDeployEntity.class));
    }

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        matFlowDeployDao.deleteDeploy(deployId);
    }

    //修改
    @Override
    public void updateDeploy(MatFlowDeploy matFlowDeploy) {
        matFlowDeployDao.updateDeploy(BeanMapper.map(matFlowDeploy, MatFlowDeployEntity.class));
    }

    //查询单个
    @Override
    public MatFlowDeploy findOneDeploy(String deployId) {
        MatFlowDeployEntity oneDeploy = matFlowDeployDao.findOneDeploy(deployId);
        MatFlowDeploy matFlowDeploy = BeanMapper.map(matFlowDeployDao.findOneDeploy(deployId), MatFlowDeploy.class);
        if (oneDeploy.getProofId() != null){
            Proof oneProof = proofService.findOneProof(oneDeploy.getProofId());
            matFlowDeploy.setProof(oneProof);
        }
        return matFlowDeploy;
    }

    //查询所有
    @Override
    public List<MatFlowDeploy> findAllDeploy() {
        return BeanMapper.mapList(matFlowDeployDao.findAllDeploy(), MatFlowDeploy.class);
    }

    @Override
    public List<MatFlowDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(matFlowDeployDao.findAllCodeList(idList), MatFlowDeploy.class);
    }



}
