package io.tiklab.arbess.task.codescan.dao;

import io.tiklab.arbess.task.codescan.model.SpotbugsBugQuery;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.task.codescan.entity.SpotbugsScanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        jpaTemplate.delete(SpotbugsScanEntity.class,bugId);
    }

    
    public SpotbugsBugSummary findOneSpotbugs(String bugId) {
        SpotbugsScanEntity scanEntity = jpaTemplate.findOne(SpotbugsScanEntity.class, bugId);

        return BeanMapper.map(scanEntity,SpotbugsBugSummary.class);
    }

    
    public List<SpotbugsBugSummary> findAllSpotbugs() {
        List<SpotbugsScanEntity> scanEntityList = jpaTemplate.findAll(SpotbugsScanEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(scanEntityList,SpotbugsBugSummary.class);
    }

    
    public List<SpotbugsBugSummary> findSpotbugsList(SpotbugsBugQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SpotbugsScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.orders(bugQuery.getOrderParams())
                .get();
        List<SpotbugsScanEntity> scanEntityList = jpaTemplate.findList(queryCondition, SpotbugsScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(scanEntityList,SpotbugsBugSummary.class);
    }

    public Pagination<SpotbugsBugSummary> findSpotbugsPage(SpotbugsBugQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SpotbugsScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.pagination(bugQuery.getPageParam())
                .orders(bugQuery.getOrderParams())
                .get();
        Pagination<SpotbugsScanEntity> scanEntityPagination = jpaTemplate.findPage(queryCondition, SpotbugsScanEntity.class);
        List<SpotbugsScanEntity> dataList = scanEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(scanEntityPagination,new ArrayList<>());
        }

        List<SpotbugsBugSummary> summaryList = BeanMapper.mapList(dataList, SpotbugsBugSummary.class);
        return PaginationBuilder.build(scanEntityPagination,summaryList);
    }
    
}
