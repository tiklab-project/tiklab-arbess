package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthOtherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthOtherDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 服务认证
     * @param pipelineAuthOtherEntity 服务认证
     * @return 服务认证id
     */
    public  String createAuthOther(PipelineAuthOtherEntity pipelineAuthOtherEntity){

        return jpaTemplate.save(pipelineAuthOtherEntity, String.class);
    }

    /**
     * 删除服务认证
     * @param authOtherId 服务认证id
     */
    public  void deleteAuthOther(String authOtherId){
        jpaTemplate.delete(PipelineAuthOtherEntity.class, authOtherId);
    }

    /**
     * 更新服务认证
     * @param pipelineAuthOtherEntity 更新信息
     */
    public  void updateAuthOther(PipelineAuthOtherEntity pipelineAuthOtherEntity){
        jpaTemplate.update(pipelineAuthOtherEntity);
    }

    /**
     * 查询单个服务认证信息
     * @param authOtherId 服务认证id
     * @return 服务认证信息
     */
    public PipelineAuthOtherEntity findOneAuthOther(String authOtherId){
        return jpaTemplate.findOne(PipelineAuthOtherEntity.class,authOtherId);
    }

    /**
     * 查询所有服务认证
     * @return 服务认证集合
     */
    public List<PipelineAuthOtherEntity> findAllAuthOther(){
        return jpaTemplate.findAll(PipelineAuthOtherEntity.class);
    }


    public List<PipelineAuthOtherEntity> findAllAuthOtherList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthOtherEntity.class,idList);
    }
    
    
}
