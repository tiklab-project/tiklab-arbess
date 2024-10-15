package io.tiklab.arbess.task.test.service;

import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.test.dao.MavenTestDao;
import io.tiklab.arbess.task.test.model.MavenTest;
import io.tiklab.arbess.task.test.model.MavenTestQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.eam.common.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MavenTestServiceImpl implements MavenTestService {

    @Autowired
    MavenTestDao mavenTestDao;

    @Override
    public String creatMavenTest(MavenTest mavenTest) {
        mavenTest.setCreateTime(PipelineUtil.date(1));
        mavenTest.setUserId(LoginContext.getLoginId());
        return mavenTestDao.creatMavenTest(mavenTest);
    }

    @Override
    public void updateMavenTest(MavenTest mavenTest) {
        mavenTestDao.updateMavenTest(mavenTest);
    }

    @Override
    public void deleteMavenTest(String testId) {
        MavenTestQuery mavenTestQuery = new MavenTestQuery();
        mavenTestQuery.setTestId(testId);
        List<MavenTest> mavenTestList = findMavenTestList(mavenTestQuery);
        for (MavenTest mavenTest : mavenTestList) {
            mavenTestDao.deleteMavenTest(mavenTest.getId());
        }
        mavenTestDao.deleteMavenTest(testId);
    }

    @Override
    public MavenTest findOneMavenTest(String testId) {
        return mavenTestDao.findOneMavenTest(testId);
    }

    @Override
    public List<MavenTest> findAllMavenTest() {
        return mavenTestDao.findAllMavenTest();
    }


    @Override
    public List<MavenTest> findMavenTestList(MavenTestQuery testQuery){
        return mavenTestDao.findMavenTestList(testQuery);
    }

    @Override
    public Pagination<MavenTest> findMavenTestPage(MavenTestQuery testQuery){
        return mavenTestDao.findMavenTestPage(testQuery);
    }



}
