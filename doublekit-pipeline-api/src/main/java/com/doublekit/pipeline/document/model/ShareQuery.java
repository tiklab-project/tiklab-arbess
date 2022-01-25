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
@CriteriaQuery(entityAlias = "ShareEntity")
public class ShareQuery  extends BaseModel {
        @ApiProperty(name ="orderParams",desc = "排序参数")
        @OrderField
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        @PageField
        private Page pageParam = new Page();

        @ApiProperty(name ="shareLink",desc = "分享链接")
        @QueryField(type = QueryTypeEnum.equal)
        private String shareLink;

        @ApiProperty(name="whetherAuthCode",desc="是否创建验证码  true  false")
        private java.lang.Boolean whetherAuthCode;

        @ApiProperty(name ="authCode",desc = "验证码")
        @QueryField(type = QueryTypeEnum.equal)
        private String authCode ;

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

        public String getShareLink() {
            return shareLink;
        }

        public ShareQuery setShareLink(String shareLink) {
            this.shareLink = shareLink;
            return this;
        }

        public Boolean getWhetherAuthCode() {
            return whetherAuthCode;
        }

        public void setWhetherAuthCode(Boolean whetherAuthCode) {
            this.whetherAuthCode = whetherAuthCode;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }
}