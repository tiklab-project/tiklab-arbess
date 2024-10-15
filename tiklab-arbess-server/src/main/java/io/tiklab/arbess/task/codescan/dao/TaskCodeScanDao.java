package io.tiklab.arbess.task.codescan.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.arbess.task.codescan.entity.TaskCodeScanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskCodeScanDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param taskCodeScanEntity 代码扫描
     * @return 代码扫描id
     */
    public  String createCodeScan(TaskCodeScanEntity taskCodeScanEntity){

        return jpaTemplate.save(taskCodeScanEntity, String.class);
    }

    /**
     * 删除代码扫描
     * @param CodeScanId 代码扫描id
     */
    public  void deleteCodeScan(String CodeScanId){
        jpaTemplate.delete(TaskCodeScanEntity.class, CodeScanId);
    }

    /**
     * 更新代码扫描
     * @param taskCodeScanEntity 更新信息
     */
    public  void updateCodeScan(TaskCodeScanEntity taskCodeScanEntity){
        jpaTemplate.update(taskCodeScanEntity);
    }

    /**
     * 查询单个代码扫描信息
     * @param CodeScanId 代码扫描id
     * @return 代码扫描信息
     */
    public TaskCodeScanEntity findOneCodeScan(String CodeScanId){
        return jpaTemplate.findOne(TaskCodeScanEntity.class,CodeScanId);
    }

    /**
     * 查询所有代码扫描
     * @return 代码扫描集合
     */
    public List<TaskCodeScanEntity> findAllCodeScan(){
        return jpaTemplate.findAll(TaskCodeScanEntity.class);
    }


    public List<TaskCodeScanEntity> findAllCodeScanList(List<String> idList){
        return jpaTemplate.findList(TaskCodeScanEntity.class,idList);
    }
    
    
}
