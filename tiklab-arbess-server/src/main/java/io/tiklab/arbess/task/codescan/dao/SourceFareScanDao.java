package io.tiklab.arbess.task.codescan.dao;

import io.tiklab.arbess.task.codescan.entity.SourceFareScanEntity;
import io.tiklab.arbess.task.codescan.model.SourceFareScanQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class SourceFareScanDao {
    
    @Autowired
    JpaTemplate jpaTemplate;

    public String creatSourceFareScan(SourceFareScanEntity scanEntity) {
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateSourceFareScan(SourceFareScanEntity scanEntity) {
        jpaTemplate.update(scanEntity);
    }


    public void deleteSourceFareScan(String bugId) {
        jpaTemplate.delete(SourceFareScanEntity.class,bugId);
    }


    public SourceFareScanEntity findSourceFareScan(String bugId) {
        return jpaTemplate.findOne(SourceFareScanEntity.class, bugId);
    }


    public List<SourceFareScanEntity> findAllSourceFareScan() {
        List<SourceFareScanEntity> scanEntityList = jpaTemplate.findAll(SourceFareScanEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return scanEntityList;
    }


    public List<SourceFareScanEntity> findSourceFareScanList(SourceFareScanQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SourceFareScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.orders(bugQuery.getOrderParams())
                .get();
        List<SourceFareScanEntity> scanEntityList = jpaTemplate.findList(queryCondition, SourceFareScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return scanEntityList;
    }

    public List<SourceFareScanEntity> findSourceFareScanList(List<String> idList) {
        List<SourceFareScanEntity> scanEntityList = jpaTemplate.findList(idList, SourceFareScanEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return scanEntityList;
    }

    public Pagination<SourceFareScanEntity> findSourceFareScanPage(SourceFareScanQuery bugQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(SourceFareScanEntity.class)
                .eq("pipelineId", bugQuery.getPipelineId());
        QueryCondition queryCondition = queryBuilders.pagination(bugQuery.getPageParam())
                .orders(bugQuery.getOrderParams())
                .get();
        Pagination<SourceFareScanEntity> scanEntityPagination = jpaTemplate.findPage(queryCondition, SourceFareScanEntity.class);
        List<SourceFareScanEntity> dataList = scanEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(scanEntityPagination,new ArrayList<>());
        }

        return PaginationBuilder.build(scanEntityPagination,dataList);
    }
    
}
