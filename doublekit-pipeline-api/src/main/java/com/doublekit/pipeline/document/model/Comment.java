package com.doublekit.pipeline.document.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.common.BaseModel;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.user.user.model.User;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel
@Join
public class Comment extends BaseModel{

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="document",desc="文档",eg="@selectOne")
    @Mappings({
            @Mapping(source = "document.id",target = "documentId")
    })
    @JoinQuery(key = "id")
    private Document document;

    @ApiProperty(name="parentCommentId",desc="父评论id")
    private java.lang.String parentCommentId;

    @ApiProperty(name="details",desc="内容")
    private java.lang.String details;

    @ApiProperty(name="user",desc="用户",eg="@selectOne")
    @Mappings({
            @Mapping(source = "user.id",target = "user")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="createTime",desc="创建时间")
    private java.util.Date createTime;

    @ApiProperty(name="updateTime",desc="更新时间")
    private java.util.Date updateTime;

    @ApiProperty(name="aimAtUser",desc="对谁的评论",eg="@selectOne")
    @Mappings({
            @Mapping(source = "aimAtUser.id",target = "aimAtUser")
    })
    @JoinQuery(key = "id")
    private User aimAtUser;

    //第一层评论id
    @ApiProperty(name="firstOneCommentId",desc="第一层评论id")
    private java.lang.String firstOneCommentId;

    @ApiProperty(name="likenumInt",desc="点赞数")
    private java.lang.Integer likenumInt;

    private List<Comment> commentList;

    private List<String> likeUserList;

    @ApiProperty(name="isLike",desc="查询人是否点赞")
    private  java.lang.String isLike;


    public java.lang.String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public java.lang.String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(java.lang.String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    public java.lang.String getDetails() {
        return details;
    }

    public void setDetails(java.lang.String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public User getAimAtUser() {
        return aimAtUser;
    }

    public void setAimAtUser(User aimAtUser) {
        this.aimAtUser = aimAtUser;
    }

    public String getFirstOneCommentId() {
        return firstOneCommentId;
    }

    public void setFirstOneCommentId(String firstOneCommentId) {
        this.firstOneCommentId = firstOneCommentId;
    }

    public Integer getLikenumInt() {
        return likenumInt;
    }

    public void setLikenumInt(Integer likenumInt) {
        this.likenumInt = likenumInt;
    }

    public List<String> getLikeUserList() {
        return likeUserList;
    }

    public void setLikeUserList(List<String> likeUserList) {
        this.likeUserList = likeUserList;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}
