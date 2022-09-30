package net.tiklab.pipeline.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.pipeline.definition.entity.PipelineBuildEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineBuildDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineBuildEntity build信息
     * @return buildId
     */
    public String createBuild(PipelineBuildEntity pipelineBuildEntity){
        return jpaTemplate.save(pipelineBuildEntity,String.class);
    }

    /**
     * 删除
     * @param buildId buildId
     */
    public void deleteBuild(String buildId){
        jpaTemplate.delete(PipelineBuildEntity.class,buildId);
    }

    /**
     * 更新build
     * @param pipelineBuildEntity 更新信息
     */
    public void updateBuild(PipelineBuildEntity pipelineBuildEntity){
        jpaTemplate.update(pipelineBuildEntity);
    }

    /**
     * 查询单个build信息
     * @param buildId buildId
     * @return build信息
     */
    public PipelineBuildEntity findOneBuild(String buildId){
        return jpaTemplate.findOne(PipelineBuildEntity.class,buildId);
    }

    /**
     * 查询所有build信息
     * @return build信息集合
     */
    public List<PipelineBuildEntity> findAllBuild(){
        return jpaTemplate.findAll(PipelineBuildEntity.class);
    }


    public List<PipelineBuildEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineBuildEntity.class,idList);
    }
}
