package com.doublekit.pipeline.document.entity;

import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.dal.jpa.mapper.annotation.GeneratorValue;
import com.doublekit.dal.jpa.mapper.annotation.Id;
import com.doublekit.dal.jpa.mapper.annotation.Table;import com.doublekit.dal.jpa.mapper.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="wiki_share")
public class ShareEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //文档或者评论的id
    @Column(name = "document_id",length = 64,notNull = true)
    private String documentId;

    //分享链接
    @Column(name = "share_link")
    private String shareLink;

    //验证码
    @Column(name = "auth_code")
    private String authCode;

    //创建时间
    @Column(name = "create_time")
    private Date createTime;

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

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
