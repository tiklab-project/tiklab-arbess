package com.doublekit.pipeline.doument.service;

import com.doublekit.pipeline.document.model.Share;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import com.doublekit.pipeline.document.service.ShareService;
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
public class ShareServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(ShareServiceImplTest.class);

    @Autowired
    ShareService shareService;

    static String id;

    @Test
    public void test01ForSaveShare() {
        Share share = JMockit.mock(Share.class);

        id = shareService.createShare(share);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateShare(){
        Share share = JMockit.mock(Share.class);
        share.setId(id);

        shareService.updateShare(share);
    }

    @Test
    public void test03ForFindShare() {
        Share share = shareService.findShare(id);

        assertNotNull(share);
    }

    @Test
    public void test04ForFindAllShare() {
        List<Share> shareList = shareService.findAllShare();

        assertNotNull(shareList);
    }

    @Test
    public void test05ForDeleteShare(){
        shareService.deleteShare(id);
    }
}