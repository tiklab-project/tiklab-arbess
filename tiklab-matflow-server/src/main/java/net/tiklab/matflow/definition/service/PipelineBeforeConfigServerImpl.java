package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineBeforeConfigDao;
import net.tiklab.matflow.definition.entity.PipelineBeforeConfigEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineBeforeConfig;
import net.tiklab.matflow.definition.model.task.PipelineTime;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineBeforeConfigServerImpl implements PipelineBeforeConfigServer{

    @Autowired
    PipelineBeforeConfigDao beforeConfigDao;


    @Autowired
    PipelineBeforeConfigTaskServer beforeConfigTaskServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
    @Override
    public String createConfig(PipelineBeforeConfig config){
        String beforeConfigId = createBeforeConfig(config);
        config.setConfigId(beforeConfigId);
        beforeConfigTaskServer.createBeforeConfig(config);
        return beforeConfigId;
    }

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineBeforeConfig> allBeforeConfig = findAllBeforeConfig(pipelineId);
        if (allBeforeConfig == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (PipelineBeforeConfig pipelineBeforeConfig : allBeforeConfig) {
            PipelineTime beforeConfig = beforeConfigTaskServer.findBeforeConfig(pipelineBeforeConfig);
            list.add(beforeConfig);
        }
        return list;
    }

    /**
     * 查询单个任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public Object findOneConfig(String configId){
        PipelineBeforeConfig oneBeforeConfig = findOneBeforeConfig(configId);
        return beforeConfigTaskServer.findBeforeConfig(oneBeforeConfig);
    }


    @Override
    public void updateConfig(PipelineBeforeConfig config){
        beforeConfigTaskServer.updateBeforeConfig(config);
    }

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineBeforeConfig> findAllBeforeConfig(String pipelineId) {
        List<PipelineBeforeConfig> allBeforeConfig = findAllBeforeConfig();
        if (allBeforeConfig == null){
            return null;
        }
        List<PipelineBeforeConfig> list = new ArrayList<>();
        for (PipelineBeforeConfig pipelineBeforeConfig : allBeforeConfig) {
            Pipeline pipeline = pipelineBeforeConfig.getPipeline();
            if (!pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            list.add(pipelineBeforeConfig);
        }
        return list;
    }

    //创建
    public String createBeforeConfig(PipelineBeforeConfig pipelineBeforeConfig){
        PipelineBeforeConfigEntity configEntity = BeanMapper.map(pipelineBeforeConfig, PipelineBeforeConfigEntity.class);
        return beforeConfigDao.createBeforeConfig(configEntity);
    }

    //更新
    @Override
    public void updateBeforeConfig(PipelineBeforeConfig pipelineBeforeConfig) {
        beforeConfigTaskServer.updateBeforeConfig(pipelineBeforeConfig);
    }

    //查询单个
    @Override
    public PipelineBeforeConfig findOneBeforeConfig(String beforeConfigId) {
        PipelineBeforeConfigEntity beforeConfigEntity = beforeConfigDao.findOneBeforeConfig(beforeConfigId);
        return BeanMapper.map(beforeConfigEntity,PipelineBeforeConfig.class);
    }

    //删除
    @Override
    public void deleteBeforeConfig(String beforeConfigId) {
        PipelineBeforeConfig oneBeforeConfig = findOneBeforeConfig(beforeConfigId);
        beforeConfigTaskServer.deleteBeforeConfig(oneBeforeConfig);
        beforeConfigDao.deleteBeforeConfig(beforeConfigId);
    }

    //查询所有
    @Override
    public List<PipelineBeforeConfig> findAllBeforeConfig() {
        return BeanMapper.mapList(beforeConfigDao.findAllBeforeConfig(), PipelineBeforeConfig.class);
    }

    @Override
    public List<PipelineBeforeConfig> findAllBeforeConfigList(List<String> idList) {
        return BeanMapper.mapList(beforeConfigDao.findAllBeforeConfigList(idList), PipelineBeforeConfig.class);
    }

}
