package net.tiklab.matflow.trigger.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.trigger.dao.PipelineTriggerDao;
import net.tiklab.matflow.trigger.entity.PipelineTriggerEntity;
import net.tiklab.matflow.task.PipelineTime;
import net.tiklab.matflow.trigger.model.PipelineTrigger;
import net.tiklab.matflow.trigger.server.PipelineTriggerServer;
import net.tiklab.matflow.trigger.server.PipelineTriggerTaskServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineTriggerServerImpl implements PipelineTriggerServer {

    @Autowired
    PipelineTriggerDao triggerConfigDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineTriggerTaskServer triggerConfigTaskServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
    @Override
    public String createConfig(PipelineTrigger config) {
        config.setCreateTime(PipelineUntil.date(1));
        String triggerId = createTriggerConfig(config);
        config.setConfigId(triggerId);
        try {
            triggerConfigTaskServer.createTriggerConfig(config);
        }catch (ApplicationException e){
            deleteTriggerConfig(triggerId);
        }
        return triggerId;
    }

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineTrigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null){
            return null;
        }
        List<PipelineTime> timeList = new ArrayList<>();
        for (PipelineTrigger pipelineTrigger : allTriggerConfig) {
            PipelineTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(pipelineTrigger);
            if (triggerConfig == null){
                deleteTriggerConfig(pipelineTrigger.getConfigId());
                continue;
            }
            timeList.add(triggerConfig);
        }
        timeList.sort(Comparator.comparing(PipelineTime::getWeekTime));

        return new ArrayList<>(timeList);
    }

    /**
     * 删除流水线所有定时任务
     * @param pipelineId 流水线id
     */
    public void deleteAllConfig(String pipelineId){
        List<PipelineTrigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return;
        }
        for (PipelineTrigger config : allTriggerConfig) {
            triggerConfigTaskServer.deleteTriggerConfig(config);
            deleteTriggerConfig(config.getConfigId());
        }
    }

    /**
     * 删除单个定时任务
     * @param pipelineId 流水线id
     * @param cron 表达式
     */
    @Override
    public void deleteCronConfig(String pipelineId,String cron){
        List<PipelineTrigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null){
            return;
        }
        for (PipelineTrigger config : allTriggerConfig) {
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
        PipelineTrigger oneTriggerConfig = findOneTriggerConfig(configId);
        PipelineTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(oneTriggerConfig);
        if (triggerConfig == null){
            deleteTriggerConfig(configId);
            return null;
        }
        return triggerConfig;
    }

    @Override
    public void updateConfig(PipelineTrigger config){
        triggerConfigTaskServer.updateTriggerConfig(config);
    }

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineTrigger> findAllTriggerConfig(String pipelineId) {
        List<PipelineTrigger> allTriggerConfig = findAllTriggerConfig();
        List<PipelineTrigger> list = new ArrayList<>();

        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return null;
        }

        for (PipelineTrigger pipelineTrigger : allTriggerConfig) {
            Pipeline pipeline = pipelineTrigger.getPipeline();
            if (!pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(pipelineTrigger);
        }
        return list;
    }

    //创建
    public String createTriggerConfig(PipelineTrigger pipelineTrigger){
        PipelineTriggerEntity configEntity = BeanMapper.map(pipelineTrigger, PipelineTriggerEntity.class);
        return triggerConfigDao.createTriggerConfig(configEntity);
    }

    //更新
    @Override
    public void updateTriggerConfig(PipelineTrigger pipelineTrigger) {
        triggerConfigTaskServer.updateTriggerConfig(pipelineTrigger);
    }

    //查询单个
    @Override
    public PipelineTrigger findOneTriggerConfig(String triggerId) {
        PipelineTriggerEntity triggerConfigEntity = triggerConfigDao.findOneTriggerConfig(triggerId);
        PipelineTrigger config = BeanMapper.map(triggerConfigEntity, PipelineTrigger.class);
        joinTemplate.joinQuery(config);
        return config;
    }

    //删除
    @Override
    public void deleteTriggerConfig(String triggerId) {
        PipelineTrigger oneTriggerConfig = findOneTriggerConfig(triggerId);
        triggerConfigTaskServer.deleteTriggerConfig(oneTriggerConfig);
        triggerConfigDao.deleteTriggerConfig(triggerId);
    }

    //查询所有
    @Override
    public List<PipelineTrigger> findAllTriggerConfig() {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfig(), PipelineTrigger.class);
    }

    @Override
    public List<PipelineTrigger> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfigList(idList), PipelineTrigger.class);
    }

}
