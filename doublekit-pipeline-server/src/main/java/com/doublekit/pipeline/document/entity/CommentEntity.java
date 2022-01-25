package com.doublekit.pipeline.document.entity;

import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.dal.jpa.mapper.annotation.GeneratorValue;
import com.doublekit.dal.jpa.mapper.annotation.Id;
import com.doublekit.dal.jpa.mapper.annotation.Table;import com.doublekit.dal.jpa.mapper.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="wiki_comment")
public class CommentEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //文档id
    @Column(name = "document_id",length = 32,notNull = true)
    private String documentId;

    //上一级评论id
    @Column(name = "parent_comment_id")
    private String parentCommentId;

    //第一层评论id
    @Column(name = "first_one_comment_id")
    private String firstOneCommentId;

    //内容
    @Column(name = "details")
    private String details;

    //用户
    @Column(name = "user")
    private String user;

    //对谁的评论
    @Column(name = "aim_at_user")
    private String aimAtUser;

    //创建时间
    @Column(name = "create_time")
    private Date createTime;

    //更新时间
    @Column(name = "update_time")
    private Date updateTime;
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

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFirstOneCommentId() {
        return firstOneCommentId;
    }

    public void setFirstOneCommentId(String firstOneCommentId) {
        this.firstOneCommentId = firstOneCommentId;
    }

    public String getAimAtUser() {
        return aimAtUser;
    }

    public void setAimAtUser(String aimAtUser) {
        this.aimAtUser = aimAtUser;
    }

}
