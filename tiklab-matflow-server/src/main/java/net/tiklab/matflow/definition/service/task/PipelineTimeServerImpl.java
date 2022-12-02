package net.tiklab.matflow.definition.service.task;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.dao.task.PipelineTimeDao;
import net.tiklab.matflow.definition.entity.task.PipelineTimeEntity;
import net.tiklab.matflow.definition.model.task.PipelineTime;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Exporter
public class PipelineTimeServerImpl implements PipelineTimeServer{


    @Autowired
    private PipelineTimeDao pipelineTimeDao;

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
    public String createTimeConfig(PipelineTime pipelineTime){
        List<Integer> timeList = pipelineTime.getTimeList();
        if (timeList == null || timeList.size() == 0){
            throw new ApplicationException(50001,"无法获取到执行时间");
        }
        String id = null;
        for (Integer integer : timeList) {
            pipelineTime.setDate(integer);
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
        List<PipelineTime> allTime = findAllTime();
        if (allTime == null){
            return null;
        }
        for (PipelineTime time : allTime) {
            if (time.getConfigId().equals(configId)){
                time.setTimeList(findAllDataConfig(configId));
                return time;
            }
        }
        return null;
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
        for (PipelineTime time : allTime) {
            if (time.getConfigId().equals(configId)){
                list.add(time.getDate());
            }
        }
        return list;
    }

    /**
     * 删除当前配置下的的所有任务
     * @param configId 配置id
     */
    @Override
    public void deleteAllTime(String configId){
        List<PipelineTime> timeConfig = findAllTimeConfig(configId);
        if (timeConfig == null){
            return;
        }
        for (PipelineTime pipelineTime : timeConfig) {
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
