package net.tiklab.matflow.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.MatFlowCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowCodeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param matFlowCodeEntity code信息
     * @return codeId
     */
    public String createCode(MatFlowCodeEntity matFlowCodeEntity){
      return jpaTemplate.save(matFlowCodeEntity,String.class);
    }

    /**
     * 删除code
     * @param codeId codeId
     */
    public void deleteCode(String codeId){
        jpaTemplate.delete(MatFlowCodeEntity.class,codeId);
    }

    /**
     * 更新code
     * @param matFlowCodeEntity 更新信息
     */
    public void updateCode(MatFlowCodeEntity matFlowCodeEntity){
        jpaTemplate.update(matFlowCodeEntity);
    }

    /**
     * 查询单个code信息
     * @param codeId codeId
     * @return code信息
     */
    public MatFlowCodeEntity findOneCode(String codeId){
       return jpaTemplate.findOne(MatFlowCodeEntity.class,codeId);
    }

    /**
     * 查询所有code信息
     * @return code信息集合
     */
    public List<MatFlowCodeEntity> findAllCode(){
        return jpaTemplate.findAll(MatFlowCodeEntity.class);
    }

    public List<MatFlowCodeEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(MatFlowCodeEntity.class,idList);
    }


}
