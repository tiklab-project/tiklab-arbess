package net.tiklab.matflow.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.MatFlowTestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowTestDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param matFlowTestEntity test信息
     * @return testId
     */
    public String createTest(MatFlowTestEntity matFlowTestEntity){
        return jpaTemplate.save(matFlowTestEntity,String.class);
    }

    /**
     * 删除
     * @param testId testId
     */
    public void deleteTest(String testId){
        jpaTemplate.delete(MatFlowTestEntity.class,testId);
    }

    /**
     * 更新test
     * @param matFlowTestEntity 更新信息
     */
    public void updateTest(MatFlowTestEntity matFlowTestEntity){
        jpaTemplate.update(matFlowTestEntity);
    }

    /**
     * 查询单个test信息
     * @param testId testId
     * @return test信息
     */
    public MatFlowTestEntity findOneTest(String testId){
        return jpaTemplate.findOne(MatFlowTestEntity.class,testId);
    }

    /**
     * 查询所有test信息
     * @return test信息集合
     */
    public List<MatFlowTestEntity> findAllTest(){
        return jpaTemplate.findAll(MatFlowTestEntity.class);
    }

    public List<MatFlowTestEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(MatFlowTestEntity.class,idList);
    }


}
