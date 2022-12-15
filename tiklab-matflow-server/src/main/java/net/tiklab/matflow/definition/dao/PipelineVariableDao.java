package net.tiklab.matflow.definition.dao;

import net.tiklab.beans.BeanMapper;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineVariableEntity;
import net.tiklab.matflow.definition.model.PipelineVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineVariableDao {

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建变量
     * @param variable 变量信息
     * @return 变量id
     */
    public String createVariable(PipelineVariable variable){
        PipelineVariableEntity variableEntity = BeanMapper.map(variable, PipelineVariableEntity.class);
        return jpaTemplate.save(variableEntity, String.class);
    }

    /**
     * 删除变量
     * @param varId 变量id
     */
    public void deleteVariable(String varId){
        jpaTemplate.delete(PipelineVariableEntity.class,varId);
    }

    /**
     * 更新变量
     * @param variable 变量信息
     */
    public void updateVariable(PipelineVariable variable){
        PipelineVariableEntity variableEntity = BeanMapper.map(variable, PipelineVariableEntity.class);
        jpaTemplate.update(variableEntity);
    }

    /**
     * 查询单个变量
     * @param varId 变量id
     * @return 变量信息
     */
    public PipelineVariable findOneVariable(String varId){
        PipelineVariableEntity variableEntity = jpaTemplate.findOne(PipelineVariableEntity.class, varId);
        return BeanMapper.map(variableEntity,PipelineVariable.class);
    }

    /**
     * 查询所有变量
     * @return 变量集合
     */
    public List<PipelineVariable> findAllVariable(){
        List<PipelineVariableEntity> variableEntity = jpaTemplate.findAll(PipelineVariableEntity.class);
        return BeanMapper.mapList(variableEntity,PipelineVariable.class);
    }


}






























