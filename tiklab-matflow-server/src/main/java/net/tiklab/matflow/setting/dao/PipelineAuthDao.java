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
     * 创建动态
     * @param pipelineAuthEntity 认证
     * @return 认证id
     */
    public  String createAuth(PipelineAuthEntity pipelineAuthEntity){

        return jpaTemplate.save(pipelineAuthEntity, String.class);
    }

    /**
     * 删除认证
     * @param AuthId 认证id
     */
    public  void deleteAuth(String AuthId){
        jpaTemplate.delete(PipelineAuthEntity.class, AuthId);
    }

    /**
     * 更新认证
     * @param pipelineAuthEntity 更新信息
     */
    public  void updateAuth(PipelineAuthEntity pipelineAuthEntity){
        jpaTemplate.update(pipelineAuthEntity);
    }

    /**
     * 查询单个认证信息
     * @param AuthId 认证id
     * @return 认证信息
     */
    public PipelineAuthEntity findOneAuth(String AuthId){
        return jpaTemplate.findOne(PipelineAuthEntity.class,AuthId);
    }

    /**
     * 查询所有认证
     * @return 认证集合
     */
    public List<PipelineAuthEntity> findAllAuth(){
        return jpaTemplate.findAll(PipelineAuthEntity.class);
    }


    public List<PipelineAuthEntity> findAllAuthList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthEntity.class,idList);
    }


}
