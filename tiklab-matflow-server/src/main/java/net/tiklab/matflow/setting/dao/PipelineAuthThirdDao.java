package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.entity.PipelineAuthThirdEntity;
import net.tiklab.utils.context.LoginContext;
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
    public  String createAuthServer(PipelineAuthThirdEntity pipelineAuthThirdEntity){
        pipelineAuthThirdEntity.setCreateTime(PipelineUntil.date(1));
        pipelineAuthThirdEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(pipelineAuthThirdEntity, String.class);
    }

    /**
     * 删除第三方认证
     * @param authServerId 第三方认证id
     */
    public  void deleteAuthServer(String authServerId){
        jpaTemplate.delete(PipelineAuthThirdEntity.class, authServerId);
    }

    /**
     * 更新第三方认证
     * @param pipelineAuthThirdEntity 更新信息
     */
    public  void updateAuthServer(PipelineAuthThirdEntity pipelineAuthThirdEntity){
        jpaTemplate.update(pipelineAuthThirdEntity);
    }

    /**
     * 查询单个第三方认证信息
     * @param authServerId 第三方认证id
     * @return 第三方认证信息
     */
    public PipelineAuthThirdEntity findOneAuthServer(String authServerId){
        return jpaTemplate.findOne(PipelineAuthThirdEntity.class,authServerId);
    }

    /**
     * 查询所有第三方认证
     * @return 第三方认证集合
     */
    public List<PipelineAuthThirdEntity> findAllAuthServer(){
        return jpaTemplate.findAll(PipelineAuthThirdEntity.class);
    }


    public List<PipelineAuthThirdEntity> findAllAuthServerList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthThirdEntity.class,idList);
    }
    
}
