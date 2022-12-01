package net.tiklab.matflow.definition.dao.task;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.task.PipelineScriptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineScriptDao {


    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineScriptEntity script信息
     * @return scriptId
     */
    public String createScript(PipelineScriptEntity pipelineScriptEntity){
        return jpaTemplate.save(pipelineScriptEntity,String.class);
    }

    /**
     * 删除script
     * @param scriptId scriptId
     */
    public void deleteScript(String scriptId){
        jpaTemplate.delete(PipelineScriptEntity.class,scriptId);
    }

    /**
     * 更新script
     * @param pipelineScriptEntity 更新信息
     */
    public void updateScript(PipelineScriptEntity pipelineScriptEntity){
        jpaTemplate.update(pipelineScriptEntity);
    }

    /**
     * 查询单个script信息
     * @param scriptId scriptId
     * @return script信息
     */
    public PipelineScriptEntity findOneScript(String scriptId){
        return jpaTemplate.findOne(PipelineScriptEntity.class,scriptId);
    }

    /**
     * 查询所有script信息
     * @return script信息集合
     */
    public List<PipelineScriptEntity> findAllScript(){
        return jpaTemplate.findAll(PipelineScriptEntity.class);
    }


    public List<PipelineScriptEntity> findAllScriptList(List<String> idList){
        return jpaTemplate.findList(PipelineScriptEntity.class,idList);
    }
    
    
}
