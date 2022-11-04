package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthCodeDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 第三方认证
     * @param pipelineAuthCodeEntity 第三方认证
     * @return 第三方认证id
     */
    public  String createAuthCode(PipelineAuthCodeEntity pipelineAuthCodeEntity){

        return jpaTemplate.save(pipelineAuthCodeEntity, String.class);
    }

    /**
     * 删除第三方认证
     * @param authCodeId 第三方认证id
     */
    public  void deleteAuthCode(String authCodeId){
        jpaTemplate.delete(PipelineAuthCodeEntity.class, authCodeId);
    }

    /**
     * 更新第三方认证
     * @param pipelineAuthCodeEntity 更新信息
     */
    public  void updateAuthCode(PipelineAuthCodeEntity pipelineAuthCodeEntity){
        jpaTemplate.update(pipelineAuthCodeEntity);
    }

    /**
     * 查询单个第三方认证信息
     * @param authCodeId 第三方认证id
     * @return 第三方认证信息
     */
    public PipelineAuthCodeEntity findOneAuthCode(String authCodeId){
        return jpaTemplate.findOne(PipelineAuthCodeEntity.class,authCodeId);
    }

    /**
     * 查询所有第三方认证
     * @return 第三方认证集合
     */
    public List<PipelineAuthCodeEntity> findAllAuthCode(){
        return jpaTemplate.findAll(PipelineAuthCodeEntity.class);
    }


    public List<PipelineAuthCodeEntity> findAllAuthCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthCodeEntity.class,idList);
    }
    
}
