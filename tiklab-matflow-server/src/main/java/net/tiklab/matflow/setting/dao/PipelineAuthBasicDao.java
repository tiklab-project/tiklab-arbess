package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthBasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PipelineAuthBasicDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 基本认证
     * @param pipelineAuthBasicEntity 基本认证
     * @return 基本认证id
     */
    public  String createAuthBasic(PipelineAuthBasicEntity pipelineAuthBasicEntity){
        return jpaTemplate.save(pipelineAuthBasicEntity, String.class);
    }

    /**
     * 删除基本认证
     * @param authBasicId 基本认证id
     */
    public  void deleteAuthBasic(String authBasicId){
        jpaTemplate.delete(PipelineAuthBasicEntity.class, authBasicId);
    }

    /**
     * 更新基本认证
     * @param pipelineAuthBasicEntity 更新信息
     */
    public  void updateAuthBasic(PipelineAuthBasicEntity pipelineAuthBasicEntity){
        jpaTemplate.update(pipelineAuthBasicEntity);
    }

    /**
     * 查询单个基本认证信息
     * @param authBasicId 基本认证id
     * @return 基本认证信息
     */
    public PipelineAuthBasicEntity findOneAuthBasic(String authBasicId){
        return jpaTemplate.findOne(PipelineAuthBasicEntity.class,authBasicId);
    }

    /**
     * 查询所有基本认证
     * @return 基本认证集合
     */
    public List<PipelineAuthBasicEntity> findAllAuthBasic(){
        return jpaTemplate.findAll(PipelineAuthBasicEntity.class);
    }


    public List<PipelineAuthBasicEntity> findAllAuthBasicList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthBasicEntity.class,idList);
    }
    
}
