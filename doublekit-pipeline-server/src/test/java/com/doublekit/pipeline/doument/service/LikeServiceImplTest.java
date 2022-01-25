package com.doublekit.pipeline.doument.service;

import com.doublekit.pipeline.document.model.Like;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import com.doublekit.pipeline.document.service.LikeService;
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
public class LikeServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(LikeServiceImplTest.class);

    @Autowired
    LikeService likeService;

    static String id;

    @Test
    public void test01ForSaveLike() {
        Like like = JMockit.mock(Like.class);

        id = likeService.createLike(like);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateLike(){
        Like like = JMockit.mock(Like.class);
        like.setId(id);

        likeService.updateLike(like);
    }

    @Test
    public void test03ForFindLike() {
        Like like = likeService.findLike(id);

        assertNotNull(like);
    }

    @Test
    public void test04ForFindAllLike() {
        List<Like> likeList = likeService.findAllLike();

        assertNotNull(likeList);
    }

    @Test
    public void test05ForDeleteLike(){
        likeService.deleteLike(id);
    }
}