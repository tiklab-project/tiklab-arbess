package com.doublekit.pipeline.document.entity;


import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.dal.jpa.mapper.annotation.GeneratorValue;
import com.doublekit.dal.jpa.mapper.annotation.Id;
import com.doublekit.dal.jpa.mapper.annotation.Table;import com.doublekit.dal.jpa.mapper.annotation.Entity;

import java.io.Serializable;

@Entity
@Table(name="wiki_document_template")
public class DocumentTemplateEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;
    //名称
    @Column(name = "name",length = 32,notNull = true)
    private String name;
    //描述
    @Column(name = "description",length = 64,notNull = true)
    private String description;
    //详情
    @Column(name = "details",length = 32)
    private String details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
