package com.tiklab.matflow.setting.view.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineViewEntity")
public class PipelineView {


    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="userId",desc="userId")
    private String userId;

    @ApiProperty(name="viewName",desc="viewName")
    private String viewName;

    //存放流水线id列表
    private List<String> list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
