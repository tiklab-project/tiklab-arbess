package net.tiklab.matflow.orther.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.entity.PipelineThirdAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineThirdAddressDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineThirdAuthEntity 第三方授权
     * @return 第三方授权id
     */
    public  String createAuthorize(PipelineThirdAddressEntity pipelineThirdAuthEntity){

        return jpaTemplate.save(pipelineThirdAuthEntity, String.class);
    }

    /**
     * 删除第三方授权
     * @param authorizeId 第三方授权id
     */
    public  void deleteAuthorize(String authorizeId){
        jpaTemplate.delete(PipelineThirdAddressEntity.class, authorizeId);
    }

    /**
     * 更新第三方授权
     * @param pipelineThirdAuthEntity 更新信息
     */
    public  void updateAuthorize(PipelineThirdAddressEntity pipelineThirdAuthEntity){
        jpaTemplate.update(pipelineThirdAuthEntity);
    }

    /**
     * 查询单个第三方授权信息
     * @param authorizeId 第三方授权id
     * @return 第三方授权信息
     */
    public PipelineThirdAddressEntity findOneAuthorize(String authorizeId){
        return jpaTemplate.findOne(PipelineThirdAddressEntity.class,authorizeId);
    }

    /**
     * 查询所有第三方授权
     * @return 第三方授权集合
     */
    public List<PipelineThirdAddressEntity> findAllAuthorize(){
        return jpaTemplate.findAll(PipelineThirdAddressEntity.class);
    }


    public List<PipelineThirdAddressEntity> findAllAuthorizeList(List<String> idList){
        return jpaTemplate.findList(PipelineThirdAddressEntity.class,idList);
    }


}
