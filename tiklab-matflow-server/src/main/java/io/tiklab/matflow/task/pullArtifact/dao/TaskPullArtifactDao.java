package io.tiklab.matflow.task.pullArtifact.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.matflow.task.pullArtifact.entity.TaskPullArtifactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskPullArtifactDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param taskPullArtifactEntity 推送制品
     * @return 推送制品id
     */
    public  String createProduct(TaskPullArtifactEntity taskPullArtifactEntity){

        return jpaTemplate.save(taskPullArtifactEntity, String.class);
    }

    /**
     * 删除推送制品
     * @param productId 推送制品id
     */
    public  void deleteProduct(String productId){
        jpaTemplate.delete(TaskPullArtifactEntity.class, productId);
    }

    /**
     * 更新推送制品
     * @param taskPullArtifactEntity 更新信息
     */
    public  void updateProduct(TaskPullArtifactEntity taskPullArtifactEntity){
        jpaTemplate.update(taskPullArtifactEntity);
    }

    /**
     * 查询单个推送制品信息
     * @param productId 推送制品id
     * @return 推送制品信息
     */
    public TaskPullArtifactEntity findOneProduct(String productId){
        return jpaTemplate.findOne(TaskPullArtifactEntity.class,productId);
    }

    /**
     * 查询所有推送制品
     * @return 推送制品集合
     */
    public List<TaskPullArtifactEntity> findAllProduct(){
        return jpaTemplate.findAll(TaskPullArtifactEntity.class);
    }


    public List<TaskPullArtifactEntity> findAllProductList(List<String> idList){
        return jpaTemplate.findList(TaskPullArtifactEntity.class,idList);
    }
    
}
