package com.doublekit.pipeline.repository.model;

import com.doublekit.common.BaseModel;
import com.doublekit.common.page.Page;
import com.doublekit.dal.jpa.criteria.annotation.*;
import com.doublekit.dal.jpa.criteria.model.Order;
import com.doublekit.dal.jpa.criteria.model.OrderBuilders;
import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.dal.jpa.criteria.annotation.QueryTypeEnum;

import java.util.List;

@ApiModel
@CriteriaQuery(entityAlias = "RepositoryEntity")
public class RepositoryQuery extends BaseModel {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

        @ApiProperty(name ="name",desc = "名字，模糊匹配")
        @QueryField(type = QueryTypeEnum.like)
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}