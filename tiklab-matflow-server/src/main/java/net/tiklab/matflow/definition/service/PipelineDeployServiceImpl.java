package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineDeployDao;
import net.tiklab.matflow.definition.entity.PipelineDeployEntity;
import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.setting.service.PipelineAuthHostServer;
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
    PipelineAuthHostServer hostServer;

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
        if (pipelineDeploy.getAuthId() != null){
            pipelineDeploy.setAuth(hostServer.findOneAuthHost(pipelineDeploy.getAuthId()));
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
    @Override
    public void updateDeploy(PipelineDeploy pipelineDeploy) {
        String deployId = pipelineDeploy.getDeployId();
        if (pipelineDeploy.getAuthType() == 2){
            PipelineDeploy oneDeploy = findOneDeploy(deployId);
            pipelineDeploy.setAuthType(oneDeploy.getAuthType());
        }else {
            pipelineDeploy.setAuthId("");
            pipelineDeploy.setLocalAddress("");
            pipelineDeploy.setDeployAddress("");
            pipelineDeploy.setDeployOrder("");
            pipelineDeploy.setStartAddress("");
            pipelineDeploy.setStartOrder("");
        }
        pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }


}
























