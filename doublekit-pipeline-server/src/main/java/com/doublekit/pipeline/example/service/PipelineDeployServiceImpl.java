package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.dao.PipelineDeployDao;
import com.doublekit.pipeline.example.entity.PipelineDeployEntity;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineDeployServiceImpl implements PipelineDeployService {


    @Autowired
    PipelineDeployDao pipelineDeployDao;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;


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
        pipelineDeploy.setType(taskType);
        String deployId = createDeploy(pipelineDeploy);
        pipelineConfigure.setTaskId(deployId);
        pipelineConfigure.setTaskType(taskType);
        pipelineConfigure.setTaskSort(pipelineDeploy.getSort());
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
    public void updateTask(Map<String, Object> map) {
        String pipelineId = map.get("pipelineId").toString();
        PipelineDeploy pipelineDeploy = (PipelineDeploy) map.get("pipelineDeploy");
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipelineId, 40);
        if (oneConfigure != null){
            if (pipelineDeploy.getType() != 0){
                updateDeploy(pipelineDeploy);
                oneConfigure.setTaskSort(pipelineDeploy.getSort());
                pipelineConfigureService.updateConfigure(oneConfigure);
            }else {
                pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipelineId);
            }
        }
        if (oneConfigure == null && pipelineDeploy.getType() != 0){
            createConfigure(pipelineId,pipelineDeploy.getType(),pipelineDeploy);
        }
    }

    //查询单个
    @Override
    public PipelineDeploy findOneDeploy(String deployId) {
        return BeanMapper.map(pipelineDeployDao.findOneDeploy(deployId),PipelineDeploy.class);
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
