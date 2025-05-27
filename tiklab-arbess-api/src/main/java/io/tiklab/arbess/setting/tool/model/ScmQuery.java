package io.tiklab.arbess.setting.tool.model;


import io.tiklab.core.BaseModel;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;


/**
 * 流水线环境配置模型
 */
//@ApiModel
public class ScmQuery extends BaseModel {

    //@ApiProperty(name = "scmType",desc = "类型")
    private String scmType;

    //@ApiProperty(name = "scmType",desc = "名称")
    private String name;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScmType() {
        return scmType;
    }

    public void setScmType(String scmType) {
        this.scmType = scmType;
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
}
