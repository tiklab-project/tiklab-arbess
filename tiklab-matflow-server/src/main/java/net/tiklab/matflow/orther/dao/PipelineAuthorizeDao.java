package net.tiklab.matflow.orther.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.entity.PipelineAuthorizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthorizeDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineAuthorizeEntity 授权
     * @return 授权id
     */
    public  String createAuthorize(PipelineAuthorizeEntity pipelineAuthorizeEntity){

        return jpaTemplate.save(pipelineAuthorizeEntity, String.class);
    }

    /**
     * 删除授权
     * @param authorizeId 授权id
     */
    public  void deleteAuthorize(String authorizeId){
        jpaTemplate.delete(PipelineAuthorizeEntity.class, authorizeId);
    }

    /**
     * 更新授权
     * @param pipelineAuthorizeEntity 更新信息
     */
    public  void updateAuthorize(PipelineAuthorizeEntity pipelineAuthorizeEntity){
        jpaTemplate.update(pipelineAuthorizeEntity);
    }

    /**
     * 查询单个授权信息
     * @param authorizeId 授权id
     * @return 授权信息
     */
    public PipelineAuthorizeEntity findOneAuthorize(String authorizeId){
        return jpaTemplate.findOne(PipelineAuthorizeEntity.class,authorizeId);
    }

    /**
     * 查询所有授权
     * @return 授权集合
     */
    public List<PipelineAuthorizeEntity> findAllAuthorize(){
        return jpaTemplate.findAll(PipelineAuthorizeEntity.class);
    }


    public List<PipelineAuthorizeEntity> findAllAuthorizeList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthorizeEntity.class,idList);
    }


}
