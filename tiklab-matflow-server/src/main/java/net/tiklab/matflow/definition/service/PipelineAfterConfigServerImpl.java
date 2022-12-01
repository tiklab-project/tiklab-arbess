package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineAfterConfigDao;
import net.tiklab.matflow.definition.entity.PipelineAfterConfigEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAfterConfigServerImpl implements PipelineAfterConfigServer {

    @Autowired
    private PipelineAfterConfigDao pipelineAfterConfigDao;

    @Autowired
    PipelineAfterConfigTaskServer afterConfigTaskServer;

    //创建
    @Override
    public String createAfterConfig(PipelineAfterConfig pipelineAfterConfig) {
        Pipeline pipeline = pipelineAfterConfig.getPipeline();
        List<PipelineAfterConfig> allAfterConfig = findAllAfterConfig(pipeline.getPipelineId());
        pipelineAfterConfig.setTaskSort(1);
        if (allAfterConfig != null){
            pipelineAfterConfig.setTaskSort(allAfterConfig.size()+1);
        }
        PipelineAfterConfigEntity pipelineAfterConfigEntity = BeanMapper.map(pipelineAfterConfig, PipelineAfterConfigEntity.class);
        String afterConfigId = pipelineAfterConfigDao.createAfterConfig(pipelineAfterConfigEntity);
        pipelineAfterConfig.setConfigId(afterConfigId);
        afterConfigTaskServer.updateConfig(pipelineAfterConfig);
        return afterConfigId;
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineAfterConfig> allAfterConfig = findAllAfterConfig(pipelineId);
        if (allAfterConfig == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (PipelineAfterConfig pipelineAfterConfig : allAfterConfig) {
            Object config = afterConfigTaskServer.findOneConfig(pipelineAfterConfig);
            list.add(config);
        }
        return list;
    }

    //删除
    @Override
    public void deleteAfterConfig(String afterConfigId) {
        PipelineAfterConfig oneAfterConfig = findOneAfterConfig(afterConfigId);
        afterConfigTaskServer.deleteConfig(oneAfterConfig);
        pipelineAfterConfigDao.deleteAfterConfig(afterConfigId);
    }

    //更新
    @Override
    public void updateAfterConfig(PipelineAfterConfig pipelineAfterConfig) {
        afterConfigTaskServer.updateConfig(pipelineAfterConfig);
    }

    //查询单个
    @Override
    public PipelineAfterConfig findOneAfterConfig(String afterConfigId) {
        PipelineAfterConfigEntity afterConfigEntity = pipelineAfterConfigDao.findOneAfterConfig(afterConfigId);
        return BeanMapper.map(afterConfigEntity,PipelineAfterConfig.class);

    }

    /**
     * 根据流水线id查询后置配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineAfterConfig> findAllAfterConfig(String pipelineId) {
        List<PipelineAfterConfig> allAfterConfig = findAllAfterConfig();
        if (allAfterConfig == null){
            return null;
        }
        List<PipelineAfterConfig> list = new ArrayList<>();
        for (PipelineAfterConfig pipelineAfterConfig : allAfterConfig) {
            Pipeline pipeline = pipelineAfterConfig.getPipeline();
            if (!pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            list.add(pipelineAfterConfig);
        }
        return list;
    }

    //查询所有
    @Override
    public List<PipelineAfterConfig> findAllAfterConfig() {
        return BeanMapper.mapList(pipelineAfterConfigDao.findAllAfterConfig(), PipelineAfterConfig.class);
    }

    @Override
    public List<PipelineAfterConfig> findAllAfterConfigList(List<String> idList) {
        return BeanMapper.mapList(pipelineAfterConfigDao.findAllAfterConfigList(idList), PipelineAfterConfig.class);
    }



}
