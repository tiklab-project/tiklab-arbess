package io.tiklab.arbess.task.test.dao;

import io.tiklab.arbess.task.test.entity.MavenTestEntity;
import io.tiklab.arbess.task.test.model.MavenTest;
import io.tiklab.arbess.task.test.model.MavenTestQuery;
import io.tiklab.toolkit.beans.BeanMapper;
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
public class MavenTestDao {


    @Autowired
    JpaTemplate jpaTemplate;

    public String creatMavenTest(MavenTest mavenTest) {
        MavenTestEntity testEntity = BeanMapper.map(mavenTest, MavenTestEntity.class);
        return jpaTemplate.save(testEntity,String.class);
    }

    public void updateMavenTest(MavenTest mavenTest) {
        MavenTestEntity testEntity = BeanMapper.map(mavenTest, MavenTestEntity.class);
        jpaTemplate.update(testEntity);
    }


    public void deleteMavenTest(String testId) {
        jpaTemplate.delete(MavenTestEntity.class,testId);
    }


    public MavenTest findOneMavenTest(String testId) {
        MavenTestEntity testEntity = jpaTemplate.findOne(MavenTestEntity.class, testId);

        return BeanMapper.map(testEntity,MavenTest.class);
    }


    public List<MavenTest> findAllMavenTest() {
        List<MavenTestEntity> testEntityList = jpaTemplate.findAll(MavenTestEntity.class);
        if (testEntityList == null || testEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(testEntityList,MavenTest.class);
    }

    public List<MavenTest> findMavenTestList(MavenTestQuery testQuery){
        QueryBuilders builders = QueryBuilders.createQuery(MavenTestEntity.class)
                .eq("pipelineId", testQuery.getPipelineId())
                .eq("testId",testQuery.getTestId());

        QueryCondition queryCondition = builders.orders(testQuery.getOrderParams()).get();

        List<MavenTestEntity> mavenTestEntityList = jpaTemplate.findList(queryCondition, MavenTestEntity.class);

        if (Objects.isNull(mavenTestEntityList) || mavenTestEntityList.isEmpty()){
            return new ArrayList<>();
        }

        return BeanMapper.mapList(mavenTestEntityList,MavenTest.class);

    }


    public Pagination<MavenTest> findMavenTestPage(MavenTestQuery testQuery){
        QueryBuilders builders = QueryBuilders.createQuery(MavenTestEntity.class)
                .eq("pipelineId", testQuery.getPipelineId())
                .eq("testId",testQuery.getTestId());

        QueryCondition queryCondition = builders.orders(testQuery.getOrderParams())
                .pagination(testQuery.getPageParam())
                .get();


        Pagination<MavenTestEntity> mavenTestEntityPage = jpaTemplate.findPage(queryCondition, MavenTestEntity.class);

        List<MavenTestEntity> dataList = mavenTestEntityPage.getDataList();

        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(mavenTestEntityPage,new ArrayList<>());
        }
        List<MavenTest> mavenTestList = BeanMapper.mapList(dataList, MavenTest.class);

        return PaginationBuilder.build(mavenTestEntityPage,mavenTestList);

    }
    
    
}
