package com.doublekit.pipeline.repository.service;

import com.doublekit.pipeline.repository.model.Repository;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@Rollback(false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryServiceImplTest.class);

    @Autowired
    RepositoryService repositoryService;

    static String id;

    @Test
    public void test01ForSaveRepository() {
        Repository repository = JMockit.mock(Repository.class);

        id = repositoryService.createRepository(repository);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRepository(){
        Repository repository = JMockit.mock(Repository.class);
        repository.setId(id);

        repositoryService.updateRepository(repository);
    }

    @Test
    public void test03ForFindRepository() {
        Repository repository = repositoryService.findRepository(id);

        assertNotNull(repository);
    }

    @Test
    public void test04ForFindAllRepository() {
        List<Repository> repositoryList = repositoryService.findAllRepository();

        assertNotNull(repositoryList);
    }

    @Test
    public void test05ForDeleteRepository(){
        repositoryService.deleteRepository(id);
    }
}