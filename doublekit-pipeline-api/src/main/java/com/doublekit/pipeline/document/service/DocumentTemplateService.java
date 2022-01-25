package com.doublekit.pipeline.document.service;

import com.doublekit.common.page.Pagination;

import com.doublekit.pipeline.document.model.DocumentTemplate;
import com.doublekit.pipeline.document.model.DocumentTemplateQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* DocumentTemplateService
*/
public interface DocumentTemplateService {

    /**
    * 创建
    * @param documentTemplate
    * @return
    */
    String createDocumentTemplate(@NotNull @Valid DocumentTemplate documentTemplate);

    /**
    * 更新
    * @param documentTemplate
    */
    void updateDocumentTemplate(@NotNull @Valid DocumentTemplate documentTemplate);

    /**
    * 删除
    * @param id
    */
    void deleteDocumentTemplate(@NotNull String id);

    DocumentTemplate findOne(@NotNull String id);

    List<DocumentTemplate> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    DocumentTemplate findDocumentTemplate(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<DocumentTemplate> findAllDocumentTemplate();

    /**
    * 查询列表
    * @param documentTemplateQuery
    * @return
    */
    List<DocumentTemplate> findDocumentTemplateList(DocumentTemplateQuery documentTemplateQuery);

    /**
    * 按分页查询
    * @param documentTemplateQuery
    * @return
    */
    Pagination<DocumentTemplate> findDocumentTemplatePage(DocumentTemplateQuery documentTemplateQuery);

}