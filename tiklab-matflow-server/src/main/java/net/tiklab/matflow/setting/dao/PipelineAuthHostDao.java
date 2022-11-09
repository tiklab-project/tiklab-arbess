package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.entity.PipelineAuthHostEntity;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthHostDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 主机认证
     * @param pipelineAuthHostEntity 主机认证
     * @return 主机认证id
     */
    public  String createAuthHost(PipelineAuthHostEntity pipelineAuthHostEntity){
        pipelineAuthHostEntity.setCreateTime(PipelineUntil.date());
        pipelineAuthHostEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(pipelineAuthHostEntity, String.class);
    }

    /**
     * 删除主机认证
     * @param authHostId 主机认证id
     */
    public  void deleteAuthHost(String authHostId){
        jpaTemplate.delete(PipelineAuthHostEntity.class, authHostId);
    }

    /**
     * 更新主机认证
     * @param pipelineAuthHostEntity 更新信息
     */
    public  void updateAuthHost(PipelineAuthHostEntity pipelineAuthHostEntity){
        jpaTemplate.update(pipelineAuthHostEntity);
    }

    /**
     * 查询单个主机认证信息
     * @param authHostId 主机认证id
     * @return 主机认证信息
     */
    public PipelineAuthHostEntity findOneAuthHost(String authHostId){
        return jpaTemplate.findOne(PipelineAuthHostEntity.class,authHostId);
    }

    /**
     * 查询所有主机认证
     * @return 主机认证集合
     */
    public List<PipelineAuthHostEntity> findAllAuthHost(){
        return jpaTemplate.findAll(PipelineAuthHostEntity.class);
    }


    public List<PipelineAuthHostEntity> findAllAuthHostList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthHostEntity.class,idList);
    }
    
}
