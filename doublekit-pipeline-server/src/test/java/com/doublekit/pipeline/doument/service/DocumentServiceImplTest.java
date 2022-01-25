package com.doublekit.pipeline.doument.service;

import com.doublekit.pipeline.document.model.Document;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import com.doublekit.pipeline.document.service.DocumentService;
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
public class DocumentServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(DocumentServiceImplTest.class);

    @Autowired
    DocumentService documentService;

    static String id;

    @Test
    public void test01ForSaveDocument() {
        Document document = JMockit.mock(Document.class);

        id = documentService.createDocument(document);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateDocument(){
        Document document = JMockit.mock(Document.class);
        document.setId(id);

        documentService.updateDocument(document);
    }

    @Test
    public void test03ForFindDocument() {
        Document document = documentService.findDocument(id,null);

        assertNotNull(document);
    }

    @Test
    public void test04ForFindAllDocument() {
        List<Document> documentList = documentService.findAllDocument();

        assertNotNull(documentList);
    }

    @Test
    public void test05ForDeleteDocument(){
        documentService.deleteDocument(id);
    }
}