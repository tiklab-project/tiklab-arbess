package io.thoughtware.arbess.task.test.dao;

import io.thoughtware.arbess.task.test.entity.MavenTestEntity;
import io.thoughtware.arbess.task.test.model.MavenTest;
import io.thoughtware.arbess.task.test.model.MavenTestQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
            return Collections.emptyList();
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
            return Collections.emptyList();
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
            return PaginationBuilder.build(mavenTestEntityPage,Collections.emptyList());
        }
        List<MavenTest> mavenTestList = BeanMapper.mapList(dataList, MavenTest.class);

        return PaginationBuilder.build(mavenTestEntityPage,mavenTestList);

    }
    
    
}
