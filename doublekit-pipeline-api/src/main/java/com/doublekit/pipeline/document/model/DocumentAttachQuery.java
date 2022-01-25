package com.doublekit.pipeline.document.model;

import com.doublekit.common.BaseModel;
import com.doublekit.dal.jpa.criteria.annotation.*;
import com.doublekit.dal.jpa.criteria.model.Order;
import com.doublekit.dal.jpa.criteria.model.OrderBuilders;
import com.doublekit.common.page.Page;
import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;

import java.util.List;

@ApiModel
@CriteriaQuery(entityAlias = "DocumentAttachEntity")
public class DocumentAttachQuery extends BaseModel {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

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
}