package net.tiklab.matflow.task.product.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.task.product.entity.TaskProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskProductDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param taskProductEntity 推送制品
     * @return 推送制品id
     */
    public  String createProduct(TaskProductEntity taskProductEntity){

        return jpaTemplate.save(taskProductEntity, String.class);
    }

    /**
     * 删除推送制品
     * @param productId 推送制品id
     */
    public  void deleteProduct(String productId){
        jpaTemplate.delete(TaskProductEntity.class, productId);
    }

    /**
     * 更新推送制品
     * @param taskProductEntity 更新信息
     */
    public  void updateProduct(TaskProductEntity taskProductEntity){
        jpaTemplate.update(taskProductEntity);
    }

    /**
     * 查询单个推送制品信息
     * @param productId 推送制品id
     * @return 推送制品信息
     */
    public TaskProductEntity findOneProduct(String productId){
        return jpaTemplate.findOne(TaskProductEntity.class,productId);
    }

    /**
     * 查询所有推送制品
     * @return 推送制品集合
     */
    public List<TaskProductEntity> findAllProduct(){
        return jpaTemplate.findAll(TaskProductEntity.class);
    }


    public List<TaskProductEntity> findAllProductList(List<String> idList){
        return jpaTemplate.findList(TaskProductEntity.class,idList);
    }
    
}
