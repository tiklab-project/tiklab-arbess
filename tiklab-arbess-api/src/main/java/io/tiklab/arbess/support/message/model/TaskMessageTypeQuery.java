package io.tiklab.arbess.support.message.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

/**
 * 任务消息类型模型
 */
public class TaskMessageTypeQuery {


    //@ApiProperty(name = "taskId",desc = "任务id")
    private String messageId;

    //@ApiProperty(name="taskType",desc="消息类型")
    private String sendType;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

    public Page getPageParam() {
        return pageParam;
    }

    public TaskMessageTypeQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public TaskMessageTypeQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public TaskMessageTypeQuery setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getSendType() {
        return sendType;
    }

    public TaskMessageTypeQuery setSendType(String sendType) {
        this.sendType = sendType;
        return this;
    }
}
