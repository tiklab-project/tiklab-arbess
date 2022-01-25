package com.doublekit.pipeline.document.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.common.BaseModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
public class Share extends BaseModel{

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="documentId",desc="文档ID",required = true)
    private java.lang.String documentId;

    @ApiProperty(name="shareLink",desc="分享链接")
    private java.lang.String shareLink;

    @ApiProperty(name="authCode",desc="验证码")
    private java.lang.String authCode;

    @ApiProperty(name="createTime",desc="创建时间")
    private java.util.Date createTime;

    @ApiProperty(name="whetherAuthCode",desc="是否创建验证码  true  false")
    private java.lang.Boolean whetherAuthCode;

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

    public Boolean getWhetherAuthCode() {
        return whetherAuthCode;
    }

    public void setWhetherAuthCode(Boolean whetherAuthCode) {
        this.whetherAuthCode = whetherAuthCode;
    }
}
