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

    @Autowired
    MatFlowConfigureService matFlowConfigureService;


    //创建
    @Override
    public String createDeploy(MatFlowDeploy matFlowDeploy) {
        return matFlowDeployDao.createDeploy(BeanMapper.map(matFlowDeploy, MatFlowDeployEntity.class));
    }

    //创建配置
    @Override
    public String createConfigure(String matFlowId,int taskType, MatFlowDeploy matFlowDeploy) {
        matFlowDeploy.setType(taskType);
        MatFlowConfigure matFlowConfigure = new MatFlowConfigure();
        matFlowConfigure.setTaskAlias("部署");
        if (matFlowDeploy.getDeployAlias() != null){
            matFlowConfigure.setTaskAlias(matFlowDeploy.getDeployAlias());
        }
        matFlowDeploy.setType(taskType);
        String deployId = createDeploy(matFlowDeploy);
        matFlowConfigure.setTaskId(deployId);
        matFlowConfigure.setTaskType(taskType);
        matFlowConfigure.setTaskSort(matFlowDeploy.getSort());
        matFlowConfigureService.createTask(matFlowConfigure,matFlowId);
        return deployId;
    }

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        matFlowDeployDao.deleteDeploy(deployId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType >30 && taskType < 40){
            deleteDeploy(taskId);
        }
    }

    //修改
    @Override
    public void updateDeploy(MatFlowDeploy matFlowDeploy) {
        matFlowDeployDao.updateDeploy(BeanMapper.map(matFlowDeploy, MatFlowDeployEntity.class));
    }

    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure) {
        MatFlow matFlow = matFlowExecConfigure.getMatFlow();
        MatFlowDeploy matFlowDeploy = matFlowExecConfigure.getMatFlowDeploy();
        MatFlowConfigure oneConfigure = matFlowConfigureService.findOneConfigure(matFlow.getMatflowId(), 40);

        //判断新配置是否删除了测试配置
        if (oneConfigure != null && matFlowDeploy.getType() == 0){
            deleteDeploy(oneConfigure.getTaskId());
            matFlowConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            return;
        }
        //判断是否有部署配置
        if (matFlowDeploy.getType() == 0){
            return;
        }
        //初始化
        if (oneConfigure == null ){
            oneConfigure = new MatFlowConfigure();
        }
        oneConfigure.setTaskSort(matFlowDeploy.getSort());
        oneConfigure.setTaskAlias(matFlowDeploy.getDeployAlias());
        oneConfigure.setView(matFlowExecConfigure.getView());
        oneConfigure.setMatFlow(matFlow);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(matFlowDeploy.getType());

        //存在部署配置，更新或者创建
        if (matFlowDeploy.getDeployId() != null){
            updateDeploy(matFlowDeploy);
            matFlowConfigureService.updateConfigure(oneConfigure);
        }else {
            String testId = createDeploy(matFlowDeploy);
            oneConfigure.setTaskId(testId);
            matFlowConfigureService.createConfigure(oneConfigure);
        }

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

    @Override
    public List<Object> findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list) {
        if (matFlowConfigure.getTaskType() > 30){
            MatFlowDeploy oneDeploy =findOneDeploy(matFlowConfigure.getTaskId());
            if (oneDeploy.getProof() != null){
                Proof proof = proofService.findOneProof(oneDeploy.getProof().getProofId());
                oneDeploy.setProof(proof);
            }
            list.add(oneDeploy);
        }
        return list;
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
