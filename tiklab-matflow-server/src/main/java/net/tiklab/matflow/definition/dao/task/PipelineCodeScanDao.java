package net.tiklab.matflow.definition.dao.task;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.task.PipelineCodeScanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineCodeScanDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineCodeScanEntity 代码扫描
     * @return 代码扫描id
     */
    public  String createCodeScan(PipelineCodeScanEntity pipelineCodeScanEntity){

        return jpaTemplate.save(pipelineCodeScanEntity, String.class);
    }

    /**
     * 删除代码扫描
     * @param CodeScanId 代码扫描id
     */
    public  void deleteCodeScan(String CodeScanId){
        jpaTemplate.delete(PipelineCodeScanEntity.class, CodeScanId);
    }

    /**
     * 更新代码扫描
     * @param pipelineCodeScanEntity 更新信息
     */
    public  void updateCodeScan(PipelineCodeScanEntity pipelineCodeScanEntity){
        jpaTemplate.update(pipelineCodeScanEntity);
    }

    /**
     * 查询单个代码扫描信息
     * @param CodeScanId 代码扫描id
     * @return 代码扫描信息
     */
    public PipelineCodeScanEntity findOneCodeScan(String CodeScanId){
        return jpaTemplate.findOne(PipelineCodeScanEntity.class,CodeScanId);
    }

    /**
     * 查询所有代码扫描
     * @return 代码扫描集合
     */
    public List<PipelineCodeScanEntity> findAllCodeScan(){
        return jpaTemplate.findAll(PipelineCodeScanEntity.class);
    }


    public List<PipelineCodeScanEntity> findAllCodeScanList(List<String> idList){
        return jpaTemplate.findList(PipelineCodeScanEntity.class,idList);
    }
    
    
}
