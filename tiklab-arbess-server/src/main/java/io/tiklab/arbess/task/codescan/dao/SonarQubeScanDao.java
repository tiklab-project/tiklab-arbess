package io.tiklab.arbess.task.codescan.dao;

import io.tiklab.arbess.task.codescan.entity.SonarQubeScanEntity;
import io.tiklab.arbess.task.codescan.model.SonarQubeScanQuery;
import io.tiklab.arbess.task.codescan.model.SonarQubeScan;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class SonarQubeScanDao {
    
    @Autowired
    JpaTemplate jpaTemplate;

    public String creatSonarQubeScan(SonarQubeScanEntity scanEntity) {
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateSonarQubeScan(SonarQubeScanEntity scanEntity) {
        jpaTemplate.update(scanEntity);
    }


    public void deleteSonarQubeScan(String bugId) {
        jpaTemplate.delete(SonarQubeScanEntity.class,bugId);
    }


    public SonarQubeScanEntity findSonarQubeScan(String bugId) {
        return jpaTemplate.findOne(SonarQubeScanEntity.class, bugId);
    }


    public List<SonarQubeScanEntity> findAllSonarQubeScan() {
        List<SonarQubeScanEntity> scanEntityList = jpaTemplate.findAll(SonarQubeScanEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return scanEntityList;
    }


    public List<SonarQubeScanEntity> findSonarQubeScanList(SonarQubeScanQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SonarQubeScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.orders(bugQuery.getOrderParams())
                .get();
        List<SonarQubeScanEntity> scanEntityList = jpaTemplate.findList(queryCondition, SonarQubeScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return scanEntityList;
    }

    public List<SonarQubeScanEntity> findSonarQubeScanList(List<String> idList) {
        List<SonarQubeScanEntity> scanEntityList = jpaTemplate.findList(idList, SonarQubeScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return scanEntityList;
    }

    public Pagination<SonarQubeScanEntity> findSonarQubeScanPage(SonarQubeScanQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SonarQubeScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.pagination(bugQuery.getPageParam())
                .orders(bugQuery.getOrderParams())
                .get();
        Pagination<SonarQubeScanEntity> scanEntityPagination = jpaTemplate.findPage(queryCondition, SonarQubeScanEntity.class);
        List<SonarQubeScanEntity> dataList = scanEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(scanEntityPagination,Collections.emptyList());
        }

        return PaginationBuilder.build(scanEntityPagination,dataList);
    }
    
}
