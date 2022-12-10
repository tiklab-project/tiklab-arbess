package net.tiklab.matflow.task.server;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.PipelineTime;

import java.util.List;

@JoinProvider(model = PipelineTime.class)
public interface PipelineTimeServer {

    /**
     * 创建
     * @param pipelineTime time信息
     * @return timeId
     */
    String createTime(PipelineTime pipelineTime) ;


    /**
     * 创建所有关联时间信息
     * @param pipelineTime 信息
     */
    String createTimeConfig(PipelineTime pipelineTime,String pipelineId);


    /**
     * 根据配置id查询消息类型
     * @param configId 配置id
     * @return 消息
     */
    PipelineTime findTimeConfig(String configId);


    /**
     * 根据配置查询所有任务
     * @param configId 配置id
     * @return 任务集合
     */
    List<PipelineTime> findAllTimeConfig(String configId);


    /**
     * 根据类型查询定时任务
     * @param configId 配置id
     * @param cron 表达式
     * @return 配置
     */
    PipelineTime fondCronConfig(String configId,String cron);


    /**
     * 根据配置获取所有时间
     * @param configId 配置id
     * @return 时间集合
     */
    List<Integer> findAllDataConfig(String configId);

    /**
     * 删除当前配置下的的所有任务
     * @param configId 配置id
     */
    void deleteAllTime(String configId,String pipelineId);


    /**
     * 周期任务更新执行时间
     * @param timeId 任务id
     */
    void deleteCronTime(String pipelineId,String timeId);

    /**
     * 删除
     * @param timeId timeId
     */
    void deleteTime(String timeId) ;


    /**
     * 更新信息
     * @param pipelineTime 信息
     */
    void updateTime(PipelineTime pipelineTime);

    /**
     * 查询单个信息
     * @param timeId pipelineTimeId
     * @return time信息
     */
    @FindOne
    PipelineTime findOneTime(String timeId) ;

    /**
     * 查询所有信息
     * @return time信息集合
     */
    @FindAll
    List<PipelineTime> findAllTime() ;

    @FindList
    List<PipelineTime> findAllTimeList(List<String> idList);
    
    
}
