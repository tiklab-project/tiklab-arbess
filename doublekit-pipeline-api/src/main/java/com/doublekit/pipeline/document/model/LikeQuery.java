package com.doublekit.pipeline.document.model;

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
@CriteriaQuery(entityAlias = "LikeEntity")
public class LikeQuery extends BaseModel {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

        @ApiProperty(name ="toWhomId",desc = "id")
        @QueryField(type = QueryTypeEnum.like)
        private String toWhomId;

        @ApiProperty(name ="likeType",desc = "点赞类型")
        @QueryField(type = QueryTypeEnum.like)
        private String likeType;

        @ApiProperty(name ="likeUser",desc = "点赞人")
        @QueryField(type = QueryTypeEnum.like)
        private String likeUser;

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

        public String getToWhomId() {
            return toWhomId;
        }

        public void setToWhomId(String toWhomId) {
            this.toWhomId = toWhomId;
        }

        public String getLikeType() {
                return likeType;
            }

            public void setLikeType(String likeType) {
                this.likeType = likeType;
            }

        public String getLikeUser() {
            return likeUser;
        }

        public void setLikeUser(String likeUser) {
            this.likeUser = likeUser;
        }
}