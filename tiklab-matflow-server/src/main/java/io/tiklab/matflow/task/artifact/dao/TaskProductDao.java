package io.tiklab.matflow.task.artifact.dao;

import io.tiklab.matflow.task.artifact.entity.TaskArtifactEntity;
import io.tiklab.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskProductDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param taskArtifactEntity 推送制品
     * @return 推送制品id
     */
    public  String createProduct(TaskArtifactEntity taskArtifactEntity){

        return jpaTemplate.save(taskArtifactEntity, String.class);
    }

    /**
     * 删除推送制品
     * @param productId 推送制品id
     */
    public  void deleteProduct(String productId){
        jpaTemplate.delete(TaskArtifactEntity.class, productId);
    }

    /**
     * 更新推送制品
     * @param taskArtifactEntity 更新信息
     */
    public  void updateProduct(TaskArtifactEntity taskArtifactEntity){
        jpaTemplate.update(taskArtifactEntity);
    }

    /**
     * 查询单个推送制品信息
     * @param productId 推送制品id
     * @return 推送制品信息
     */
    public TaskArtifactEntity findOneProduct(String productId){
        return jpaTemplate.findOne(TaskArtifactEntity.class,productId);
    }

    /**
     * 查询所有推送制品
     * @return 推送制品集合
     */
    public List<TaskArtifactEntity> findAllProduct(){
        return jpaTemplate.findAll(TaskArtifactEntity.class);
    }


    public List<TaskArtifactEntity> findAllProductList(List<String> idList){
        return jpaTemplate.findList(TaskArtifactEntity.class,idList);
    }
    
}
