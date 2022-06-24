package com.doublekit.pipeline.execute.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.execute.dao.PipelineDeployDao;
import com.doublekit.pipeline.execute.entity.PipelineDeployEntity;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.instance.service.PipelineActionService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineDeployServiceImpl implements PipelineDeployService {


    @Autowired
    PipelineDeployDao pipelineDeployDao;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineActionService pipelineActionService;

    //创建
    @Override
    public String createDeploy(PipelineDeploy pipelineDeploy) {
        return pipelineDeployDao.createDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }

    //创建配置
    @Override
    public String createConfigure(String pipelineId,int taskType, PipelineDeploy pipelineDeploy) {
        pipelineDeploy.setType(taskType);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskAlias("部署");
        if (pipelineDeploy.getDeployAlias() != null){
            pipelineConfigure.setTaskAlias(pipelineDeploy.getDeployAlias());
        }
        pipelineDeploy.setType(taskType);
        String deployId = createDeploy(pipelineDeploy);
        pipelineConfigure.setTaskId(deployId);
        pipelineConfigure.setTaskType(taskType);
        pipelineConfigure.setTaskSort(pipelineDeploy.getSort());
        //pipelineConfigure.setView(pipelineDeploy.getView());
        pipelineConfigureService.createTask(pipelineConfigure,pipelineId);
        return deployId;
    }

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        pipelineDeployDao.deleteDeploy(deployId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType >30 && taskType < 40){
            deleteDeploy(taskId);
        }
    }

    //修改
    @Override
    public void updateDeploy(PipelineDeploy pipelineDeploy) {
        pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy,PipelineDeployEntity.class));
    }

    @Override
    public void updateTask(PipelineExecConfigure pipelineExecConfigure) {
        Pipeline pipeline = pipelineExecConfigure.getPipeline();
        PipelineDeploy pipelineDeploy = pipelineExecConfigure.getPipelineDeploy();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipeline.getPipelineId(), 40);

        //if (oneConfigure != null){
        //    if (pipelineDeploy.getType() != 0){
        //        updateDeploy(pipelineDeploy);
        //        oneConfigure.setTaskSort(pipelineDeploy.getSort());
        //        oneConfigure.setTaskAlias(pipelineDeploy.getDeployAlias());
        //        //oneConfigure.setView(pipelineDeploy.getView());
        //        pipelineConfigureService.updateConfigure(oneConfigure);
        //    }else {
        //        pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipeline.getPipelineId());
        //    }
        //}
        //if (oneConfigure == null && pipelineDeploy.getType() != 0){
        //    createConfigure(pipeline.getPipelineId(),pipelineDeploy.getType(),pipelineDeploy);
        //}

        //判断新配置是否删除了测试配置
        if (oneConfigure != null && pipelineDeploy.getType() == 0){
            deleteDeploy(oneConfigure.getTaskId());
            pipelineConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            return;
        }

        //判断是否有部署配置
        if (pipelineDeploy.getType() == 0){
            return;
        }

        //初始化
        if (oneConfigure == null ){
            oneConfigure = new PipelineConfigure();
        }
        oneConfigure.setTaskSort(pipelineDeploy.getSort());
        oneConfigure.setTaskAlias(pipelineDeploy.getDeployAlias());
        oneConfigure.setView(pipelineExecConfigure.getView());
        oneConfigure.setPipeline(pipeline);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(pipelineDeploy.getType());

        //存在部署配置，更新或者创建
        if (pipelineDeploy.getDeployId() != null){
            updateDeploy(pipelineDeploy);
        }else {
            String testId = createDeploy(pipelineDeploy);
            oneConfigure.setTaskId(testId);
            pipelineConfigureService.createConfigure(oneConfigure);
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

    @Override
    public List<Object> findOneTask(PipelineConfigure pipelineConfigure, List<Object> list) {
        if (pipelineConfigure.getTaskType() > 30){
            PipelineDeploy oneDeploy =findOneDeploy(pipelineConfigure.getTaskId());
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
    public List<PipelineDeploy> findAllDeploy() {
        return BeanMapper.mapList(pipelineDeployDao.findAllDeploy(),PipelineDeploy.class);
    }

    @Override
    public List<PipelineDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(pipelineDeployDao.findAllCodeList(idList),PipelineDeploy.class);
    }



}
