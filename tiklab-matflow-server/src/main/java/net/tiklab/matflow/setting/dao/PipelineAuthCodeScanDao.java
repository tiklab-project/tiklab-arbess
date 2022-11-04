package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.setting.entity.PipelineAuthCodeScanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAuthCodeScanDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 服务认证
     * @param pipelineAuthCodeScanEntity 服务认证
     * @return 服务认证id
     */
    public  String createAuthCodeScan(PipelineAuthCodeScanEntity pipelineAuthCodeScanEntity){

        return jpaTemplate.save(pipelineAuthCodeScanEntity, String.class);
    }

    /**
     * 删除服务认证
     * @param authCodeScanId 服务认证id
     */
    public  void deleteAuthCodeScan(String authCodeScanId){
        jpaTemplate.delete(PipelineAuthCodeScanEntity.class, authCodeScanId);
    }

    /**
     * 更新服务认证
     * @param pipelineAuthCodeScanEntity 更新信息
     */
    public  void updateAuthCodeScan(PipelineAuthCodeScanEntity pipelineAuthCodeScanEntity){
        jpaTemplate.update(pipelineAuthCodeScanEntity);
    }

    /**
     * 查询单个服务认证信息
     * @param authCodeScanId 服务认证id
     * @return 服务认证信息
     */
    public PipelineAuthCodeScanEntity findOneAuthCodeScan(String authCodeScanId){
        return jpaTemplate.findOne(PipelineAuthCodeScanEntity.class,authCodeScanId);
    }

    /**
     * 查询所有服务认证
     * @return 服务认证集合
     */
    public List<PipelineAuthCodeScanEntity> findAllAuthCodeScan(){
        return jpaTemplate.findAll(PipelineAuthCodeScanEntity.class);
    }


    public List<PipelineAuthCodeScanEntity> findAllAuthCodeScanList(List<String> idList){
        return jpaTemplate.findList(PipelineAuthCodeScanEntity.class,idList);
    }
    
    
}
