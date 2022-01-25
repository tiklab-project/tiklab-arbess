package com.doublekit.pipeline.document.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.pipeline.document.entity.DocumentEntity;
import com.doublekit.pipeline.document.model.DocumentQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DocumentDao
 */
@Repository
public class DocumentDao{

    private static Logger logger = LoggerFactory.getLogger(DocumentDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param documentEntity
     * @return
     */
    public String createDocument(DocumentEntity documentEntity) {
        return jpaTemplate.save(documentEntity,String.class);
    }

    /**
     * 更新
     * @param documentEntity
     */
    public void updateDocument(DocumentEntity documentEntity){
        jpaTemplate.update(documentEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteDocument(String id){
        jpaTemplate.delete(DocumentEntity.class,id);
    }

    public void deleteDocument(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public DocumentEntity findDocument(String id){
        return jpaTemplate.findOne(DocumentEntity.class,id);
    }

    /**
    * findAllDocument
    * @return
    */
    public List<DocumentEntity> findAllDocument() {
        return jpaTemplate.findAll(DocumentEntity.class);
    }

    public List<DocumentEntity> findDocumentList(List<String> idList) {
        return jpaTemplate.findList(DocumentEntity.class,idList);
    }

    public List<DocumentEntity> findDocumentList(DocumentQuery documentQuery) {
        return jpaTemplate.findList(documentQuery, DocumentEntity.class);
    }

    public Pagination<DocumentEntity> findDocumentPage(DocumentQuery documentQuery) {
        return jpaTemplate.findPage(documentQuery, DocumentEntity.class);
    }
}