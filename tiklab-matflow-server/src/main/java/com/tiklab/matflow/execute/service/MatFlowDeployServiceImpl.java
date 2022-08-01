package com.tiklab.matflow.execute.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.execute.dao.MatFlowDeployDao;
import com.tiklab.matflow.execute.entity.MatFlowDeployEntity;
import com.tiklab.matflow.execute.model.MatFlowDeploy;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.service.ProofService;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
