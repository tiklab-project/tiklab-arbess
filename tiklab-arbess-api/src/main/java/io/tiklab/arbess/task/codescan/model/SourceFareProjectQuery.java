package io.tiklab.arbess.task.codescan.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

public class SourceFareProjectQuery {

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();

    @ApiProperty(name ="userId",desc = "用户id")
    private String  userId;

    @ApiProperty(name ="name",desc = "项目名字")
    private String  name;

    @ApiProperty(name ="scanWay",desc = "扫描方式")
    private String scanWay;


    @ApiProperty(name ="scanSchemeId",desc = "扫描方案")
    private String scanSchemeId;

    @ApiProperty(name ="findType",desc = "查询的类型，所有（viewable）、自己创建的（oneself）、收藏（collect）")
    private String findType;

    private String authId;

    public String getAuthId() {
        return authId;
    }

    public SourceFareProjectQuery setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScanWay() {
        return scanWay;
    }

    public void setScanWay(String scanWay) {
        this.scanWay = scanWay;
    }

    public String getScanSchemeId() {
        return scanSchemeId;
    }

    public void setScanSchemeId(String scanSchemeId) {
        this.scanSchemeId = scanSchemeId;
    }

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
    }
}
