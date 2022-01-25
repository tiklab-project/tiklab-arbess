package com.doublekit.pipeline.document.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.pipeline.document.entity.DocumentTemplateEntity;
import com.doublekit.pipeline.document.model.DocumentTemplateQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DocumentTemplateDao
 */
@Repository
public class DocumentTemplateDao{

    private static Logger logger = LoggerFactory.getLogger(DocumentTemplateDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param documentTemplateEntity
     * @return
     */
    public String createDocumentTemplate(DocumentTemplateEntity documentTemplateEntity) {
        return jpaTemplate.save(documentTemplateEntity,String.class);
    }

    /**
     * 更新
     * @param documentTemplateEntity
     */
    public void updateDocumentTemplate(DocumentTemplateEntity documentTemplateEntity){
        jpaTemplate.update(documentTemplateEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteDocumentTemplate(String id){
        jpaTemplate.delete(DocumentTemplateEntity.class,id);
    }

    public void deleteDocumentTemplate(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public DocumentTemplateEntity findDocumentTemplate(String id){
        return jpaTemplate.findOne(DocumentTemplateEntity.class,id);
    }

    /**
    * findAllDocumentTemplate
    * @return
    */
    public List<DocumentTemplateEntity> findAllDocumentTemplate() {
        return jpaTemplate.findAll(DocumentTemplateEntity.class);
    }

    public List<DocumentTemplateEntity> findDocumentTemplateList(List<String> idList) {
        return jpaTemplate.findList(DocumentTemplateEntity.class,idList);
    }

    public List<DocumentTemplateEntity> findDocumentTemplateList(DocumentTemplateQuery documentTemplateQuery) {
        return jpaTemplate.findList(documentTemplateQuery, DocumentTemplateEntity.class);
    }

    public Pagination<DocumentTemplateEntity> findDocumentTemplatePage(DocumentTemplateQuery documentTemplateQuery) {
        return jpaTemplate.findPage(documentTemplateQuery, DocumentTemplateEntity.class);
    }
}