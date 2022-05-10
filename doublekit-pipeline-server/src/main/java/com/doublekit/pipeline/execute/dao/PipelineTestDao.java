package com.doublekit.pipeline.execute.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.execute.entity.PipelineTestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTestDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineTestEntity test信息
     * @return testId
     */
    public String createTest(PipelineTestEntity pipelineTestEntity){
        return jpaTemplate.save(pipelineTestEntity,String.class);
    }

    /**
     * 删除
     * @param testId testId
     */
    public void deleteTest(String testId){
        jpaTemplate.delete(PipelineTestEntity.class,testId);
    }

    /**
     * 更新test
     * @param pipelineTestEntity 更新信息
     */
    public void updateTest(PipelineTestEntity pipelineTestEntity){
        jpaTemplate.update(pipelineTestEntity);
    }

    /**
     * 查询单个test信息
     * @param testId testId
     * @return test信息
     */
    public PipelineTestEntity findOneTest(String testId){
        return jpaTemplate.findOne(PipelineTestEntity.class,testId);
    }

    /**
     * 查询所有test信息
     * @return test信息集合
     */
    public List<PipelineTestEntity> findAllTest(){
        return jpaTemplate.findAll(PipelineTestEntity.class);
    }

    public List<PipelineTestEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineTestEntity.class,idList);
    }


}
