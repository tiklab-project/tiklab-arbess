package net.tiklab.matflow.trigger.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.trigger.dao.PipelineTimeDao;
import net.tiklab.matflow.trigger.entity.PipelineTimeEntity;
import net.tiklab.matflow.trigger.model.PipelineTime;
import net.tiklab.matflow.trigger.model.PipelineTimeServer;
import net.tiklab.matflow.trigger.service.quartz.PipelineRunJob;
import net.tiklab.matflow.trigger.service.quartz.PipelineJob;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@Exporter
public class PipelineTimeServerImpl implements PipelineTimeServer {


    @Autowired
    private PipelineTimeDao pipelineTimeDao;

    @Autowired
    PipelineJob manager;

    //创建
    @Override
    public String createTime(PipelineTime pipelineTime) {
        PipelineTimeEntity pipelineTimeEntity = BeanMapper.map(pipelineTime, PipelineTimeEntity.class);
        return pipelineTimeDao.createTime(pipelineTimeEntity);
    }


    /**
     * 创建所有关联时间信息
     * @param pipelineTime 信息
     */
    @Override
    public String createTimeConfig(PipelineTime pipelineTime,String pipelineId){
        List<Integer> timeList = pipelineTime.getTimeList();
        if (timeList == null || timeList.size() == 0){
            throw new ApplicationException(50001,"无法获取到执行时间");
        }
        String time = pipelineTime.getTime();
        String id = null;
        for (Integer integer : timeList) {
            int week = PipelineUntil.week();
            pipelineTime.setDate(integer);
            if (week == integer){
                integer = 0;
            }
            String cron = PipelineCronUtils.weekCron(time, integer);
            pipelineTime.setCron(cron);
            manager.addJob(pipelineId, PipelineRunJob.class,cron);
            id = createTime(pipelineTime);
        }
        return id;
    }

    /**
     * 根据配置id查询消息类型
     * @param configId 配置id
     * @return 消息
     */
    @Override
    public PipelineTime findTimeConfig(String configId){
        List<PipelineTime> allTime = findAllTimeConfig(configId);
        if (allTime == null || allTime.size() == 0){
            return null;
        }

        //获取时间
        List<Integer> allDataConfig = findAllDataConfig(configId);

        Integer integer = allDataConfig.get(0);
        PipelineTime time = findOneConfig(configId, integer);
        time.setTimeList(allDataConfig);
        //获取具体时间
        Map<String, String> map = PipelineCronUtils.cronWeek(time.getCron());
        time.setCron(map.get("cron"));
        time.setTime(map.get("time"));
        return time;
    }

    /**
     * 根据配置查询所有任务
     * @param configId 配置id
     * @return 任务集合
     */
    @Override
    public List<PipelineTime> findAllTimeConfig(String configId){
        List<PipelineTime> allTime = findAllTime();
        if (allTime == null){
            return null;
        }
        List<PipelineTime> list = new ArrayList<>();
        for (PipelineTime time : allTime) {
            if (time.getConfigId().equals(configId)){
                list.add(time);
            }
        }
        return list;
    }

    public PipelineTime findOneConfig(String configId,int day){
        List<PipelineTime> allTime = findAllTime();
        if (allTime == null){
            return null;
        }
        for (PipelineTime pipelineTime : allTime) {
            String timeConfigId = pipelineTime.getConfigId();
            int date = pipelineTime.getDate();
            if (timeConfigId.equals(configId) && date == day){
                return pipelineTime;
            }
        }
        return null;
    }

    /**
     * 根据配置获取所有时间
     * @param configId 配置id
     * @return 时间集合
     */
    @Override
    public List<Integer> findAllDataConfig(String configId){
        List<PipelineTime> allTime = findAllTime();
        if (allTime == null){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        List<Integer> afterList = new ArrayList<>();
        for (PipelineTime time : allTime) {
            int date = time.getDate();
            if (time.getConfigId().equals(configId) && date != 0){
                int week = PipelineUntil.week();
                if (date > week){
                    list.add(date);
                }else {
                    afterList.add(date);
                }
            }
            Collections.sort(list);
            Collections.sort(afterList);
        }
        list.addAll(afterList);
        return list;
    }

    /**
     * 删除当前配置下的的所有任务
     * @param configId 配置id
     */
    @Override
    public void deleteAllTime(String configId,String pipelineId){
        List<PipelineTime> timeConfig = findAllTimeConfig(configId);
        if (timeConfig == null){
            return;
        }
        for (PipelineTime pipelineTime : timeConfig) {
            String cron = pipelineTime.getCron();
            manager.removeJob(pipelineId, cron);
            deleteTime(pipelineTime.getTimeId());
        }
    }


    //删除
    @Override
    public void deleteTime(String timeId) {
        pipelineTimeDao.deleteTime(timeId);
    }

    //更新
    @Override
    public void updateTime(PipelineTime pipelineTime) {
        PipelineTimeEntity pipelineTimeEntity = BeanMapper.map(pipelineTime, PipelineTimeEntity.class);
        pipelineTimeDao.updateTime(pipelineTimeEntity);
    }

    //查询单个
    @Override
    public PipelineTime findOneTime(String timeId) {
        PipelineTimeEntity timeEntity = pipelineTimeDao.findOneTime(timeId);
        return BeanMapper.map(timeEntity,PipelineTime.class);

    }

    //查询所有
    @Override
    public List<PipelineTime> findAllTime() {
        return BeanMapper.mapList(pipelineTimeDao.findAllTime(), PipelineTime.class);
    }

    @Override
    public List<PipelineTime> findAllTimeList(List<String> idList) {
        return BeanMapper.mapList(pipelineTimeDao.findAllTimeList(idList), PipelineTime.class);
    }


}
