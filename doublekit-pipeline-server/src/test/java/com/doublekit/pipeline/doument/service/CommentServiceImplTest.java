package com.doublekit.pipeline.doument.service;

import com.doublekit.pipeline.document.model.Comment;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import com.doublekit.pipeline.document.service.CommentService;
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
public class CommentServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImplTest.class);

    @Autowired
    CommentService commentService;

    static String id;

    @Test
    public void test01ForSaveComment() {
        Comment comment = JMockit.mock(Comment.class);

        id = commentService.createComment(comment);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateComment(){
        Comment comment = JMockit.mock(Comment.class);
        comment.setId(id);

        commentService.updateComment(comment);
    }

    @Test
    public void test03ForFindComment() {
        Comment comment = commentService.findComment(id);

        assertNotNull(comment);
    }

    @Test
    public void test04ForFindAllComment() {
        List<Comment> commentList = commentService.findAllComment();

        assertNotNull(commentList);
    }

    @Test
    public void test05ForDeleteComment(){
        commentService.deleteComment(id);
    }
}