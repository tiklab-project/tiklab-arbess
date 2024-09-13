package io.thoughtware.arbess.task.code.dao;


import io.thoughtware.arbess.task.code.entity.TaskCodeEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskCodeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskCodeEntity code信息
     * @return codeId
     */
    public String createCode(TaskCodeEntity taskCodeEntity){
      return jpaTemplate.save(taskCodeEntity,String.class);
    }

    /**
     * 删除code
     * @param codeId codeId
     */
    public void deleteCode(String codeId){
        jpaTemplate.delete(TaskCodeEntity.class,codeId);
    }

    /**
     * 更新code
     * @param taskCodeEntity 更新信息
     */
    public void updateCode(TaskCodeEntity taskCodeEntity){
        jpaTemplate.update(taskCodeEntity);
    }

    /**
     * 查询单个code信息
     * @param codeId codeId
     * @return code信息
     */
    public TaskCodeEntity findOneCode(String codeId){
       return jpaTemplate.findOne(TaskCodeEntity.class,codeId);
    }

    /**
     * 查询所有code信息
     * @return code信息集合
     */
    public List<TaskCodeEntity> findAllCode(){
        return jpaTemplate.findAll(TaskCodeEntity.class);
    }

    public List<TaskCodeEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(TaskCodeEntity.class,idList);
    }


}
