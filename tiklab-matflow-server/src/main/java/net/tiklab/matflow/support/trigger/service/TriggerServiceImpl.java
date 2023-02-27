package net.tiklab.matflow.support.trigger.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.support.trigger.dao.TriggerDao;
import net.tiklab.matflow.support.trigger.entity.TriggerEntity;
import net.tiklab.matflow.support.trigger.model.TriggerTime;
import net.tiklab.matflow.support.trigger.model.Trigger;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TriggerDao triggerConfigDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    TriggerTaskService triggerConfigTaskServer;


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
    @Override
    public String createConfig(Trigger config) {
        config.setCreateTime(PipelineUtil.date(1));
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
        List<Trigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null){
            return null;
        }
        List<TriggerTime> triggerTimeList = new ArrayList<>();
        for (Trigger trigger : allTriggerConfig) {
            TriggerTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(trigger);
            if (triggerConfig == null){
                deleteTriggerConfig(trigger.getConfigId());
                continue;
            }
            triggerTimeList.add(triggerConfig);
        }
        triggerTimeList.sort(Comparator.comparing(TriggerTime::getWeekTime));

        return new ArrayList<>(triggerTimeList);
    }

    /**
     * 删除流水线所有定时任务
     * @param pipelineId 流水线id
     */
    public void deleteAllConfig(String pipelineId){
        List<Trigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return;
        }
        for (Trigger config : allTriggerConfig) {
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
        List<Trigger> allTriggerConfig = findAllTriggerConfig(pipelineId);
        if (allTriggerConfig == null){
            return;
        }
        for (Trigger config : allTriggerConfig) {
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
        Trigger oneTriggerConfig = findOneTriggerConfig(configId);
        TriggerTime triggerConfig = triggerConfigTaskServer.findTriggerConfig(oneTriggerConfig);
        if (triggerConfig == null){
            deleteTriggerConfig(configId);
            return null;
        }
        return triggerConfig;
    }

    @Override
    public void updateConfig(Trigger config){
        String configId = config.getConfigId();
        if (configId == null){
            configId = createConfig(config);
        }
        config.setConfigId(configId);
        triggerConfigTaskServer.updateTriggerConfig(config);
    }

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<Trigger> findAllTriggerConfig(String pipelineId) {
        List<Trigger> allTriggerConfig = findAllTriggerConfig();
        List<Trigger> list = new ArrayList<>();

        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return null;
        }

        for (Trigger trigger : allTriggerConfig) {
            Pipeline pipeline = trigger.getPipeline();
            if (!pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(trigger);
        }
        return list;
    }

    //创建
    public String createTriggerConfig(Trigger trigger){
        TriggerEntity configEntity = BeanMapper.map(trigger, TriggerEntity.class);
        return triggerConfigDao.createTriggerConfig(configEntity);
    }

    //更新
    @Override
    public void updateTriggerConfig(Trigger trigger) {
        triggerConfigTaskServer.updateTriggerConfig(trigger);
    }

    //查询单个
    @Override
    public Trigger findOneTriggerConfig(String triggerId) {
        TriggerEntity triggerConfigEntity = triggerConfigDao.findOneTriggerConfig(triggerId);
        Trigger config = BeanMapper.map(triggerConfigEntity, Trigger.class);
        joinTemplate.joinQuery(config);
        return config;
    }

    //删除
    @Override
    public void deleteTriggerConfig(String triggerId) {
        Trigger oneTriggerConfig = findOneTriggerConfig(triggerId);
        triggerConfigTaskServer.deleteTriggerConfig(oneTriggerConfig);
        triggerConfigDao.deleteTriggerConfig(triggerId);
    }

    //查询所有
    @Override
    public List<Trigger> findAllTriggerConfig() {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfig(), Trigger.class);
    }

    @Override
    public List<Trigger> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfigList(idList), Trigger.class);
    }

}
