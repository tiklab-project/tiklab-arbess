package io.tiklab.arbess.task.test.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.task.test.model.MavenTest;
import io.tiklab.arbess.task.test.model.MavenTestQuery;

import java.util.List;

public interface MavenTestService {

     String creatMavenTest(MavenTest mavenTest) ;

     void updateMavenTest(MavenTest mavenTest) ;


     void deleteMavenTest(String testId) ;


     MavenTest findOneMavenTest(String testId) ;

     List<MavenTest> findAllMavenTest() ;


     List<MavenTest> findMavenTestList(MavenTestQuery testQuery);


     Pagination<MavenTest> findMavenTestPage(MavenTestQuery testQuery);
    
    
    
}
