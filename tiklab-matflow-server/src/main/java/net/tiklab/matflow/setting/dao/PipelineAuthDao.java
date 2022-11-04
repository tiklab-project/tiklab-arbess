package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PipelineAuthDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 基本认证
     * @param pipelineAuthEntity 基本认证
     * @return 基本认证id
     */
    public  String createAuth(PipelineAuthEntity pipelineAuthEntity){
        return jpaTemplate.save(pipelineAuthEntity, String.class);
    }

    /**
     * 删除基本认证
     * @param authId 基本认证id
     */
    public  void deleteAuth(String authId){
        jpaTemplate.delete(PipelineAuthEntity.class, authId);
    }

    /**
     * 更新基本认证
     * @param pipelineAuthEntity 更新信息
     */
    public  void updateAuth(PipelineAuthEntity pipelineAuthEntity){
        jpaTemplate.update(pipelineAuthEntity);
    }

    /**
     * 查询单个基本认证信息
     * @param authId 基本认证id
     * @return 基本认证信息
     */
    public PipelineAuthEntity findOneAuth(String authId){
        return jpaTemplate.findOne(PipelineAuthEntity.class,authId);
    }

    /**
     * 查询所有基本认证
     * @return 基本认证集合
     */
    public List<PipelineAuthEntity> findAllAuth(){
        return jpaTemplate.findAll(PipelineAuthEntity.class);
    }


    public List<PipelineAuthEntity> findAllAuthList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthEntity.class,idList);
    }
    
}
