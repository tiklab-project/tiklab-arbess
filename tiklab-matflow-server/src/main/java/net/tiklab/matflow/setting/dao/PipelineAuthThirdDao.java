package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthThirdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthThirdDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 第三方认证
     * @param pipelineAuthThirdEntity 第三方认证
     * @return 第三方认证id
     */
    public  String createAuthThird(PipelineAuthThirdEntity pipelineAuthThirdEntity){

        return jpaTemplate.save(pipelineAuthThirdEntity, String.class);
    }

    /**
     * 删除第三方认证
     * @param authThirdId 第三方认证id
     */
    public  void deleteAuthThird(String authThirdId){
        jpaTemplate.delete(PipelineAuthThirdEntity.class, authThirdId);
    }

    /**
     * 更新第三方认证
     * @param pipelineAuthThirdEntity 更新信息
     */
    public  void updateAuthThird(PipelineAuthThirdEntity pipelineAuthThirdEntity){
        jpaTemplate.update(pipelineAuthThirdEntity);
    }

    /**
     * 查询单个第三方认证信息
     * @param authThirdId 第三方认证id
     * @return 第三方认证信息
     */
    public PipelineAuthThirdEntity findOneAuthThird(String authThirdId){
        return jpaTemplate.findOne(PipelineAuthThirdEntity.class,authThirdId);
    }

    /**
     * 查询所有第三方认证
     * @return 第三方认证集合
     */
    public List<PipelineAuthThirdEntity> findAllAuthThird(){
        return jpaTemplate.findAll(PipelineAuthThirdEntity.class);
    }


    public List<PipelineAuthThirdEntity> findAllAuthThirdList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthThirdEntity.class,idList);
    }
    
}
