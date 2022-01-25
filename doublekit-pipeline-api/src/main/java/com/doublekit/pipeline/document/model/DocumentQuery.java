package com.doublekit.pipeline.document.model;

import com.doublekit.common.page.Page;
import com.doublekit.dal.jpa.criteria.annotation.*;
import com.doublekit.dal.jpa.criteria.model.Order;
import com.doublekit.dal.jpa.criteria.model.OrderBuilders;
import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.dal.jpa.criteria.annotation.QueryTypeEnum;

import java.io.Serializable;
import java.util.List;

@ApiModel
@CriteriaQuery(entityAlias = "DocumentEntity")
public class DocumentQuery implements Serializable {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

        @ApiProperty(name ="categoryId",desc = "目录id")
        @QueryField(type = QueryTypeEnum.equal)
        private String categoryId;

        @ApiProperty(name ="repositoryId",desc = "空间id")
        @QueryField(type = QueryTypeEnum.equal)
        private String repositoryId;

        @ApiProperty(name ="name",desc = "文档名字")
        @QueryField(type = QueryTypeEnum.like)
        private String name;

        @ApiProperty(name ="id",desc = "文档id")
        @QueryField(type = QueryTypeEnum.equal)
        private String id;

        public List<Order> getOrderParams() {
            return orderParams;
        }

        public void setOrderParams(List<Order> orderParams) {
            this.orderParams = orderParams;
        }

        public Page getPageParam() {
            return pageParam;
        }

        public void setPageParam(Page pageParam) {
            this.pageParam = pageParam;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public DocumentQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
}