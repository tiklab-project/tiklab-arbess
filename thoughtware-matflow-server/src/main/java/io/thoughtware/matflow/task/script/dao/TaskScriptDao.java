package io.thoughtware.matflow.task.script.dao;

import io.thoughtware.matflow.task.script.entity.TaskScriptEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskScriptDao {


    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskScriptEntity script信息
     * @return scriptId
     */
    public String createScript(TaskScriptEntity taskScriptEntity){
        return jpaTemplate.save(taskScriptEntity,String.class);
    }

    /**
     * 删除script
     * @param scriptId scriptId
     */
    public void deleteScript(String scriptId){
        jpaTemplate.delete(TaskScriptEntity.class,scriptId);
    }

    /**
     * 更新script
     * @param taskScriptEntity 更新信息
     */
    public void updateScript(TaskScriptEntity taskScriptEntity){
        jpaTemplate.update(taskScriptEntity);
    }

    /**
     * 查询单个script信息
     * @param scriptId scriptId
     * @return script信息
     */
    public TaskScriptEntity findOneScript(String scriptId){
        return jpaTemplate.findOne(TaskScriptEntity.class,scriptId);
    }

    /**
     * 查询所有script信息
     * @return script信息集合
     */
    public List<TaskScriptEntity> findAllScript(){
        return jpaTemplate.findAll(TaskScriptEntity.class);
    }


    public List<TaskScriptEntity> findAllScriptList(List<String> idList){
        return jpaTemplate.findList(TaskScriptEntity.class,idList);
    }
    
    
}
