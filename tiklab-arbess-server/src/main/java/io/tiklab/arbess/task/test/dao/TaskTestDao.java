package io.tiklab.arbess.task.test.dao;


import io.tiklab.arbess.task.test.entity.TaskTestEntity;
import io.tiklab.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskTestDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskTestEntity test信息
     * @return testId
     */
    public String createTest(TaskTestEntity taskTestEntity){
        return jpaTemplate.save(taskTestEntity,String.class);
    }

    /**
     * 删除
     * @param testId testId
     */
    public void deleteTest(String testId){
        jpaTemplate.delete(TaskTestEntity.class,testId);
    }

    /**
     * 更新test
     * @param taskTestEntity 更新信息
     */
    public void updateTest(TaskTestEntity taskTestEntity){
        jpaTemplate.update(taskTestEntity);
    }

    /**
     * 查询单个test信息
     * @param testId testId
     * @return test信息
     */
    public TaskTestEntity findOneTest(String testId){
        return jpaTemplate.findOne(TaskTestEntity.class,testId);
    }

    /**
     * 查询所有test信息
     * @return test信息集合
     */
    public List<TaskTestEntity> findAllTest(){
        return jpaTemplate.findAll(TaskTestEntity.class);
    }

    public List<TaskTestEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(TaskTestEntity.class,idList);
    }


}
