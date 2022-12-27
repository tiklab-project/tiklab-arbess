package net.tiklab.matflow.task.server;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.dao.PipelineDeployDao;
import net.tiklab.matflow.task.entity.PipelineDeployEntity;
import net.tiklab.matflow.task.model.PipelineDeploy;
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


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteDeployConfig(String configId){
        PipelineDeploy oneDeployConfig = findOneDeployConfig(configId);
        deleteDeploy(oneDeployConfig.getDeployId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public PipelineDeploy findOneDeployConfig(String configId){
        List<PipelineDeploy> allDeploy = findAllDeploy();
        if (allDeploy == null){
            return null;
        }
        for (PipelineDeploy pipelineDeploy : allDeploy) {
            if (pipelineDeploy.getConfigId().equals(configId)){
                return findOneDeploy(pipelineDeploy.getDeployId());
            }
        }
        return null;
    }
    

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        pipelineDeployDao.deleteDeploy(deployId);
    }

    //查询单个
    @Override
    public PipelineDeploy findOneDeploy(String deployId) {
        PipelineDeployEntity oneDeploy = pipelineDeployDao.findOneDeploy(deployId);
        PipelineDeploy pipelineDeploy = BeanMapper.map(oneDeploy, PipelineDeploy.class);
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
        int authType = pipelineDeploy.getAuthType();
        String deployId = pipelineDeploy.getDeployId();

        if (authType == 0){
            PipelineDeploy oneDeploy = findOneDeploy(deployId);
            pipelineDeploy.setAuthType(oneDeploy.getAuthType());
            pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
            return;
        }

        if (authType == 2){
            pipelineDeploy.setAuthId("");
            pipelineDeploy.setAuthId("");
            pipelineDeploy.setLocalAddress("");
            pipelineDeploy.setDeployAddress("");
            pipelineDeploy.setDeployOrder("");
            pipelineDeploy.setStartAddress("");
        }
        pipelineDeploy.setStartOrder("");

        pipelineDeployDao.updateDeploy(BeanMapper.map(pipelineDeploy, PipelineDeployEntity.class));
    }


}
























