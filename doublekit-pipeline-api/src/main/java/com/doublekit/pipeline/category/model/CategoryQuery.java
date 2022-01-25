package com.doublekit.pipeline.category.model;

import com.doublekit.common.BaseModel;
import com.doublekit.dal.jpa.criteria.annotation.*;
import com.doublekit.dal.jpa.criteria.model.Order;
import com.doublekit.dal.jpa.criteria.model.OrderBuilders;
import com.doublekit.common.page.Page;
import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.dal.jpa.criteria.annotation.QueryTypeEnum;

import java.util.List;

@ApiModel
@CriteriaQuery(entityAlias = "CategoryEntity")

public class CategoryQuery extends BaseModel {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryId",desc = "空间id")
        @QueryField(type = QueryTypeEnum.equal)
        private String repositoryId;

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

        public String getRepositoryId() {
            return repositoryId;
        }

        public void setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
        }
}