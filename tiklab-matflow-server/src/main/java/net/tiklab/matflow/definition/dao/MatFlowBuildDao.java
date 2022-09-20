package net.tiklab.matflow.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.MatFlowBuildEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowBuildDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param matFlowBuildEntity build信息
     * @return buildId
     */
    public String createBuild(MatFlowBuildEntity matFlowBuildEntity){
        return jpaTemplate.save(matFlowBuildEntity,String.class);
    }

    /**
     * 删除
     * @param buildId buildId
     */
    public void deleteBuild(String buildId){
        jpaTemplate.delete(MatFlowBuildEntity.class,buildId);
    }

    /**
     * 更新build
     * @param matFlowBuildEntity 更新信息
     */
    public void updateBuild(MatFlowBuildEntity matFlowBuildEntity){
        jpaTemplate.update(matFlowBuildEntity);
    }

    /**
     * 查询单个build信息
     * @param buildId buildId
     * @return build信息
     */
    public MatFlowBuildEntity findOneBuild(String buildId){
        return jpaTemplate.findOne(MatFlowBuildEntity.class,buildId);
    }

    /**
     * 查询所有build信息
     * @return build信息集合
     */
    public List<MatFlowBuildEntity> findAllBuild(){
        return jpaTemplate.findAll(MatFlowBuildEntity.class);
    }


    public List<MatFlowBuildEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(MatFlowBuildEntity.class,idList);
    }
}
