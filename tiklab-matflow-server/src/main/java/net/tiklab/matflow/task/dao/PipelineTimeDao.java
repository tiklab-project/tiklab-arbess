package net.tiklab.matflow.task.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.task.entity.PipelineTimeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTimeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineTimeEntity time信息
     * @return timeId
     */
    public String createTime(PipelineTimeEntity pipelineTimeEntity){
        return jpaTemplate.save(pipelineTimeEntity,String.class);
    }

    /**
     * 删除time
     * @param timeId timeId
     */
    public void deleteTime(String timeId){
        jpaTemplate.delete(PipelineTimeEntity.class,timeId);
    }

    /**
     * 更新time
     * @param pipelineTimeEntity 更新信息
     */
    public void updateTime(PipelineTimeEntity pipelineTimeEntity){
        jpaTemplate.update(pipelineTimeEntity);
    }

    /**
     * 查询单个time信息
     * @param timeId timeId
     * @return time信息
     */
    public PipelineTimeEntity findOneTime(String timeId){
        return jpaTemplate.findOne(PipelineTimeEntity.class,timeId);
    }

    /**
     * 查询所有time信息
     * @return time信息集合
     */
    public List<PipelineTimeEntity> findAllTime(){
        return jpaTemplate.findAll(PipelineTimeEntity.class);
    }


    public List<PipelineTimeEntity> findAllTimeList(List<String> idList){
        return jpaTemplate.findList(PipelineTimeEntity.class,idList);
    }
}
