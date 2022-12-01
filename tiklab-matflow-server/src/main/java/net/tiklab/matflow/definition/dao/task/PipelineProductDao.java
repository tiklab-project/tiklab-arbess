package net.tiklab.matflow.definition.dao.task;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.task.PipelineProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineProductDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineProductEntity 推送制品
     * @return 推送制品id
     */
    public  String createProduct(PipelineProductEntity pipelineProductEntity){

        return jpaTemplate.save(pipelineProductEntity, String.class);
    }

    /**
     * 删除推送制品
     * @param productId 推送制品id
     */
    public  void deleteProduct(String productId){
        jpaTemplate.delete(PipelineProductEntity.class, productId);
    }

    /**
     * 更新推送制品
     * @param pipelineProductEntity 更新信息
     */
    public  void updateProduct(PipelineProductEntity pipelineProductEntity){
        jpaTemplate.update(pipelineProductEntity);
    }

    /**
     * 查询单个推送制品信息
     * @param productId 推送制品id
     * @return 推送制品信息
     */
    public PipelineProductEntity findOneProduct(String productId){
        return jpaTemplate.findOne(PipelineProductEntity.class,productId);
    }

    /**
     * 查询所有推送制品
     * @return 推送制品集合
     */
    public List<PipelineProductEntity> findAllProduct(){
        return jpaTemplate.findAll(PipelineProductEntity.class);
    }


    public List<PipelineProductEntity> findAllProductList(List<String> idList){
        return jpaTemplate.findList(PipelineProductEntity.class,idList);
    }
    
}
