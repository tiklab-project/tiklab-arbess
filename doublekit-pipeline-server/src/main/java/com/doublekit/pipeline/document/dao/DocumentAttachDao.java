package com.doublekit.pipeline.document.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.pipeline.document.entity.DocumentAttachEntity;
import com.doublekit.pipeline.document.model.DocumentAttachQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DocumentAttachDao
 */
@Repository
public class DocumentAttachDao{

    private static Logger logger = LoggerFactory.getLogger(DocumentAttachDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param documentAttachEntity
     * @return
     */
    public String createDocumentAttach(DocumentAttachEntity documentAttachEntity) {
        return jpaTemplate.save(documentAttachEntity,String.class);
    }

    /**
     * 更新
     * @param documentAttachEntity
     */
    public void updateDocumentAttach(DocumentAttachEntity documentAttachEntity){
        jpaTemplate.update(documentAttachEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteDocumentAttach(String id){
        jpaTemplate.delete(DocumentAttachEntity.class,id);
    }

    public void deleteDocumentAttach(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public DocumentAttachEntity findDocumentAttach(String id){
        return jpaTemplate.findOne(DocumentAttachEntity.class,id);
    }

    /**
    * findAllDocumentAttach
    * @return
    */
    public List<DocumentAttachEntity> findAllDocumentAttach() {
        return jpaTemplate.findAll(DocumentAttachEntity.class);
    }

    public List<DocumentAttachEntity> findDocumentAttachList(List<String> idList) {
        return jpaTemplate.findList(DocumentAttachEntity.class,idList);
    }

    public List<DocumentAttachEntity> findDocumentAttachList(DocumentAttachQuery documentAttachQuery) {
        return jpaTemplate.findList(documentAttachQuery, DocumentAttachEntity.class);
    }

    public Pagination<DocumentAttachEntity> findDocumentAttachPage(DocumentAttachQuery documentAttachQuery) {
        return jpaTemplate.findPage(documentAttachQuery, DocumentAttachEntity.class);
    }
}