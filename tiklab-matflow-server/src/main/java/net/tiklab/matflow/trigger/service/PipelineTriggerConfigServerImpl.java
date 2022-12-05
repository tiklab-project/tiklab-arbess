package net.tiklab.matflow.trigger.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.trigger.dao.PipelineTriggerConfigDao;
import net.tiklab.matflow.trigger.entity.PipelineTriggerConfigEntity;
import net.tiklab.matflow.trigger.model.PipelineTime;
import net.tiklab.matflow.trigger.model.PipelineTriggerConfig;
import net.tiklab.matflow.trigger.server.PipelineTriggerConfigServer;
import net.tiklab.matflow.trigger.server.PipelineTriggerConfigTaskServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineTriggerConfigServerImpl implements PipelineTriggerConfigServer {

    @Autowired
    PipelineTriggerConfigDao triggerConfigDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineTriggerConfigTaskServer triggerConfigTaskServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
    @Override
    public String createConfig(PipelineTriggerConfig config) {
        config.setCreateTime(PipelineUntil.date(1));
        String triggerConfigId = createTriggerConfig(config);
        config.setConfigId(triggerConfigId);
        try {
            triggerConfigTaskServer.createTriggerConfig(config);
        }catch (ApplicationException e){
            deleteTriggerConfig(triggerConfigId);
        }
        return triggerConfigId;
    }

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineTriggerConfig> allTriggerConfig;
        allTriggerConfig = findAllTriggerConfig();
        if (pipelineId != null){
            allTriggerConfig = findAllTriggerConfig(pipelineId);
        }

        if (allTriggerConfig == null){
            return null;
        }
        List<PipelineTime> timeList = new ArrayList<>();
        for (PipelineTriggerConfig pipelineTriggerConfig : allTriggerConfig) {
            PipelineTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(pipelineTriggerConfig);
            if (triggerConfig == null){
                deleteTriggerConfig(pipelineTriggerConfig.getConfigId());
                continue;
            }
            timeList.add(triggerConfig);
        }
        timeList.sort(Comparator.comparing(PipelineTime::getWeekTime));

        return new ArrayList<>(timeList);
    }

    /**
     * 删除单个定时任务
     * @param pipelineId 流水线id
     * @param cron 表达式
     */
    public void deleteCronConfig(String pipelineId,String cron){
        List<PipelineTriggerConfig> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null){
            return;
        }
        for (PipelineTriggerConfig config : allTriggerConfig) {
            String configId = config.getConfigId();
            triggerConfigTaskServer.deleteCronConfig(pipelineId,configId,cron);
        }
    }

    /**
     * 查询单个任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public Object findOneConfig(String configId){
        PipelineTriggerConfig oneTriggerConfig = findOneTriggerConfig(configId);
        PipelineTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(oneTriggerConfig);
        if (triggerConfig == null){
            deleteTriggerConfig(configId);
            return null;
        }
        return triggerConfig;
    }


    @Override
    public void updateConfig(PipelineTriggerConfig config){
        triggerConfigTaskServer.updateTriggerConfig(config);
    }

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineTriggerConfig> findAllTriggerConfig(String pipelineId) {
        List<PipelineTriggerConfig> allTriggerConfig = findAllTriggerConfig();
        List<PipelineTriggerConfig> list = new ArrayList<>();

        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return null;
        }

        for (PipelineTriggerConfig pipelineTriggerConfig : allTriggerConfig) {
            Pipeline pipeline = pipelineTriggerConfig.getPipeline();
            if (!pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            list.add(pipelineTriggerConfig);
        }
        return list;
    }

    //创建
    public String createTriggerConfig(PipelineTriggerConfig pipelineTriggerConfig){
        PipelineTriggerConfigEntity configEntity = BeanMapper.map(pipelineTriggerConfig, PipelineTriggerConfigEntity.class);
        return triggerConfigDao.createTriggerConfig(configEntity);
    }

    //更新
    @Override
    public void updateTriggerConfig(PipelineTriggerConfig pipelineTriggerConfig) {
        triggerConfigTaskServer.updateTriggerConfig(pipelineTriggerConfig);
    }

    //查询单个
    @Override
    public PipelineTriggerConfig findOneTriggerConfig(String triggerConfigId) {
        PipelineTriggerConfigEntity triggerConfigEntity = triggerConfigDao.findOneTriggerConfig(triggerConfigId);
        PipelineTriggerConfig config = BeanMapper.map(triggerConfigEntity, PipelineTriggerConfig.class);
        joinTemplate.joinQuery(config);
        return config;
    }

    //删除
    @Override
    public void deleteTriggerConfig(String triggerConfigId) {
        PipelineTriggerConfig oneTriggerConfig = findOneTriggerConfig(triggerConfigId);
        triggerConfigTaskServer.deleteTriggerConfig(oneTriggerConfig);
        triggerConfigDao.deleteTriggerConfig(triggerConfigId);
    }

    //查询所有
    @Override
    public List<PipelineTriggerConfig> findAllTriggerConfig() {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfig(), PipelineTriggerConfig.class);
    }

    @Override
    public List<PipelineTriggerConfig> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfigList(idList), PipelineTriggerConfig.class);
    }

}
