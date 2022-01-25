package com.doublekit.pipeline.document.service;

import com.doublekit.common.page.Pagination;

import com.doublekit.pipeline.document.model.DocumentAttach;
import com.doublekit.pipeline.document.model.DocumentAttachQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* DocumentAttachService
*/
public interface DocumentAttachService {

    /**
    * 创建
    * @param documentAttach
    * @return
    */
    String createDocumentAttach(@NotNull @Valid DocumentAttach documentAttach);

    /**
    * 更新
    * @param documentAttach
    */
    void updateDocumentAttach(@NotNull @Valid DocumentAttach documentAttach);

    /**
    * 删除
    * @param id
    */
    void deleteDocumentAttach(@NotNull String id);

    DocumentAttach findOne(@NotNull String id);

    List<DocumentAttach> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    DocumentAttach findDocumentAttach(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<DocumentAttach> findAllDocumentAttach();

    /**
    * 查询列表
    * @param documentAttachQuery
    * @return
    */
    List<DocumentAttach> findDocumentAttachList(DocumentAttachQuery documentAttachQuery);

    /**
    * 按分页查询
    * @param documentAttachQuery
    * @return
    */
    Pagination<DocumentAttach> findDocumentAttachPage(DocumentAttachQuery documentAttachQuery);

}