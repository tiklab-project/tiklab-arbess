package com.doublekit.pipeline.document.entity;

import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.dal.jpa.mapper.annotation.GeneratorValue;
import com.doublekit.dal.jpa.mapper.annotation.Id;
import com.doublekit.dal.jpa.mapper.annotation.Table;import com.doublekit.dal.jpa.mapper.annotation.Entity;

import java.io.Serializable;

@Entity
@Table(name="wiki_document_attach")
public class DocumentAttachEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "document_id",length = 32,notNull = true)
    private String documentId;

    @Column(name = "attachment",length = 256,notNull = true)
    private String attachment;

    @Column(name = "sort",length = 4)
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
