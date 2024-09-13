package io.thoughtware.arbess.task.build.dao;


import io.thoughtware.arbess.task.build.entity.TaskBuildEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskBuildDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskBuildEntity build信息
     * @return buildId
     */
    public String createBuild(TaskBuildEntity taskBuildEntity){
        return jpaTemplate.save(taskBuildEntity,String.class);
    }

    /**
     * 删除
     * @param buildId buildId
     */
    public void deleteBuild(String buildId){
        jpaTemplate.delete(TaskBuildEntity.class,buildId);
    }

    /**
     * 更新build
     * @param taskBuildEntity 更新信息
     */
    public void updateBuild(TaskBuildEntity taskBuildEntity){
        jpaTemplate.update(taskBuildEntity);
    }

    /**
     * 查询单个build信息
     * @param buildId buildId
     * @return build信息
     */
    public TaskBuildEntity findOneBuild(String buildId){
        return jpaTemplate.findOne(TaskBuildEntity.class,buildId);
    }

    /**
     * 查询所有build信息
     * @return build信息集合
     */
    public List<TaskBuildEntity> findAllBuild(){
        return jpaTemplate.findAll(TaskBuildEntity.class);
    }


    public List<TaskBuildEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(TaskBuildEntity.class,idList);
    }
}
