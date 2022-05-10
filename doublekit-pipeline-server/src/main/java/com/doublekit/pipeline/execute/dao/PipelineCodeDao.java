package com.doublekit.pipeline.execute.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.execute.entity.PipelineCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineCodeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineCodeEntity code信息
     * @return codeId
     */
    public String createCode(PipelineCodeEntity pipelineCodeEntity){
      return jpaTemplate.save(pipelineCodeEntity,String.class);
    }

    /**
     * 删除code
     * @param codeId codeId
     */
    public void deleteCode(String codeId){
        jpaTemplate.delete(PipelineCodeEntity.class,codeId);
    }

    /**
     * 更新code
     * @param pipelineCodeEntity 更新信息
     */
    public void updateCode(PipelineCodeEntity pipelineCodeEntity){
        jpaTemplate.update(pipelineCodeEntity);
    }

    /**
     * 查询单个code信息
     * @param codeId codeId
     * @return code信息
     */
    public PipelineCodeEntity findOneCode(String codeId){
       return jpaTemplate.findOne(PipelineCodeEntity.class,codeId);
    }

    /**
     * 查询所有code信息
     * @return code信息集合
     */
    public List<PipelineCodeEntity> findAllCode(){
        return jpaTemplate.findAll(PipelineCodeEntity.class);
    }

    public List<PipelineCodeEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineCodeEntity.class,idList);
    }


}
