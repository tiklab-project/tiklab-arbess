package com.doublekit.pipeline.doument.service;

import com.doublekit.pipeline.document.model.DocumentTemplate;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import com.doublekit.pipeline.document.service.DocumentTemplateService;
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
public class DocumentTemplateServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(DocumentTemplateServiceImplTest.class);

    @Autowired
    DocumentTemplateService documentTemplateService;

    static String id;

    @Test
    public void test01ForSaveDocumentTemplate() {
        DocumentTemplate documentTemplate = JMockit.mock(DocumentTemplate.class);

        id = documentTemplateService.createDocumentTemplate(documentTemplate);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateDocumentTemplate(){
        DocumentTemplate documentTemplate = JMockit.mock(DocumentTemplate.class);
        documentTemplate.setId(id);

        documentTemplateService.updateDocumentTemplate(documentTemplate);
    }

    @Test
    public void test03ForFindDocumentTemplate() {
        DocumentTemplate documentTemplate = documentTemplateService.findDocumentTemplate(id);

        assertNotNull(documentTemplate);
    }

    @Test
    public void test04ForFindAllDocumentTemplate() {
        List<DocumentTemplate> documentTemplateList = documentTemplateService.findAllDocumentTemplate();

        assertNotNull(documentTemplateList);
    }

    @Test
    public void test05ForDeleteDocumentTemplate(){
        documentTemplateService.deleteDocumentTemplate(id);
    }
}