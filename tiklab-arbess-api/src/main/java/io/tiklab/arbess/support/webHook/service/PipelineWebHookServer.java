package io.tiklab.arbess.support.webHook.service;

import io.tiklab.arbess.support.webHook.model.WebHook;
import io.tiklab.arbess.support.webHook.model.WebHookQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

@Join
@Mapper
public interface PipelineWebHookServer {


    void execWebHook(String key);

    /**
     * 创建WebHook
     * @param webHook WebHook信息
     * @return id
     */
    String createWebHook(WebHook webHook);


    /**
     * 删除WebHook
     * @param id WebHook id
     */
    void deleteWebHook(String id);


    /**
     * 更新WebHook
     * @param webHook WebHook信息
     */
    void updateWebHook(WebHook webHook);


    /**
     * 更新WebHook
     * @param pipelineId 流水线id
     * @return WebHook信息
     */
    WebHook findPipelineWebHook(String pipelineId);


    /**
     * 查询WebHook
     * @param id WebHook id
     * @return WebHook信息
     */
    WebHook findWebHook(String id);


    /**
     * 查询所有WebHook
     * @return WebHook信息
     */
    List<WebHook> findAllWebHook();


    /**
     * 条件查询WebHook列表
     * @param webHookQuery 查询条件
     * @return WebHook信息
     */
    List<WebHook> findWebHookList(WebHookQuery webHookQuery);


    /**
     * 根据id列表查询WebHook列表
     * @param idList id列表
     * @return WebHook信息
     */
    List<WebHook> findWebHookList(List<String> idList);


    /**
     * 分页条件查询WebHook列表
     * @param webHookQuery 查询条件
     * @return 分页信息
     */
    Pagination<WebHook> findWebHookPage(WebHookQuery webHookQuery);


}












