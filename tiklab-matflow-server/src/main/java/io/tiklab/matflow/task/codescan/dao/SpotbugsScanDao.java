package io.tiklab.matflow.task.codescan.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.task.code.model.SpotbugsBugQuery;
import io.tiklab.matflow.task.code.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.codescan.entity.SpotbugsScanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Spotbugs代码扫描数据操作
 * @author zcamy
 */
@Repository
public class SpotbugsScanDao {
    
    
    @Autowired
    JpaTemplate jpaTemplate;
    
    public String creatSpotbugs(SpotbugsBugSummary bugSummary) {
        SpotbugsScanEntity scanEntity = BeanMapper.map(bugSummary, SpotbugsScanEntity.class);
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateSpotbugs(SpotbugsBugSummary bugSummary) {
        SpotbugsScanEntity scanEntity = BeanMapper.map(bugSummary, SpotbugsScanEntity.class);
        jpaTemplate.update(scanEntity);
    }

    
    public void deleteSpotbugs(String bugId) {
        jpaTemplate.delete(String.class,bugId);
    }

    
    public SpotbugsBugSummary findOneSpotbugs(String bugId) {
        SpotbugsScanEntity scanEntity = jpaTemplate.findOne(SpotbugsScanEntity.class, bugId);

        return BeanMapper.map(scanEntity,SpotbugsBugSummary.class);
    }

    
    public List<SpotbugsBugSummary> findAllSpotbugs() {
        List<SpotbugsScanEntity> scanEntityList = jpaTemplate.findAll(SpotbugsScanEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SpotbugsBugSummary.class);
    }

    
    public List<SpotbugsBugSummary> findSpotbugsList(SpotbugsBugQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SpotbugsScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders
                .get();
        List<SpotbugsScanEntity> scanEntityList = jpaTemplate.findList(queryCondition, SpotbugsScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SpotbugsBugSummary.class);
    }
    
    
    
}
