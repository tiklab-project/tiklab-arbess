package io.tiklab.arbess.setting.third.model;


import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

/**
 * 流水线第三方模型
 */

/**
 * @pi.model:io.tiklab.matflow.setting.model.AuthThird
 * @desc:
 */
//@ApiModel
@Join
@Mapper
public class AuthThirdQuery {

    //@ApiProperty(name = "type",desc="类型 1. gitee 2. github 3.sonar 4.nexus" )
    private String type;

    //@ApiProperty(name = "authType",desc="认证类型 1.用户名密码 2. 通用认证")
    private int authType;

    //@ApiProperty(name = "name",desc="服务名称")
    private String name;

    //@ApiProperty(name = "createTime")
    private String createTime ;

    private String serverAddress;

    private String usrId;

    //@ApiProperty(name = "authPublic",desc="是否公开")
    private int authPublic;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getType() {
        return type;
    }

    public AuthThirdQuery setType(String type) {
        this.type = type;
        return this;
    }

    public int getAuthType() {
        return authType;
    }

    public AuthThirdQuery setAuthType(int authType) {
        this.authType = authType;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuthThirdQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public AuthThirdQuery setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public AuthThirdQuery setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public String getUsrId() {
        return usrId;
    }

    public AuthThirdQuery setUsrId(String usrId) {
        this.usrId = usrId;
        return this;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public AuthThirdQuery setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public AuthThirdQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public AuthThirdQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}


























