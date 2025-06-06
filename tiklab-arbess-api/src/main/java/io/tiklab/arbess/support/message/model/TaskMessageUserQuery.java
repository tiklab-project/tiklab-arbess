package io.tiklab.arbess.support.message.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

/**
 * 任务消息接收人模型
 */
public class TaskMessageUserQuery {

    // 消息ID
    private String messageId;

    // 用户ID
    private String userId;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

    public String getUserId() {
        return userId;
    }

    public TaskMessageUserQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public TaskMessageUserQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public TaskMessageUserQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public TaskMessageUserQuery setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }
}
