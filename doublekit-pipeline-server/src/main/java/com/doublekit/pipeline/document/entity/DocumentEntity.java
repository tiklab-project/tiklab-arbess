package com.doublekit.pipeline.document.entity;


import com.doublekit.dal.jpa.mapper.annotation.*;

import java.io.Serializable;

@Entity
@Table(name="wiki_document")
public class DocumentEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //名称
    @Column(name = "name",length = 64,notNull = true)
    private String name;
    //知识库类别
    @Column(name = "type_id",length = 32,notNull = true)
    private String typeId="text";
    //知识库id
    @Column(name = "repository_id",length = 32,notNull = true)
    private String repositoryId;

    //目录id
    @Column(name = "category_id",length = 32)
    private String categoryId;

    //管理员
    @Column(name = "master",length = 32,notNull = true)
    private String master;

    //内容
    @Column(name = "details")
    private String details;

    private java.lang.String formatType="document";


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

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
