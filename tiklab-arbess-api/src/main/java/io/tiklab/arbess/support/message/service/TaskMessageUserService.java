package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.model.TaskMessageUserQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.support.message.model.TaskMessageUser;

import java.util.List;

/**
 * 消息接收人服务接口
 */
@JoinProvider(model = TaskMessageUser.class)
public interface TaskMessageUserService {

    /**
     * 创建
     * @param taskMessageUser message信息
     * @return messageId
     */
    String createMessageUser(TaskMessageUser taskMessageUser) ;


    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessageUser(String messageId) ;


    /**
     * 更新信息
     * @param taskMessageUser 信息
     */
    void updateMessageUser(TaskMessageUser taskMessageUser);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    TaskMessageUser findMessageUser(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<TaskMessageUser> findAllMessageUser();


    @FindList
    List<TaskMessageUser> findMessageUserList(List<String> idList);

    /**
     * 根据查询条件查询信息
     * @param userQuery 查询条件
     * @return message信息集合
     */
    List<TaskMessageUser> findMessageUserList(TaskMessageUserQuery userQuery);


    /**
     * 分页根据查询条件查询信息
     * @param userQuery 查询条件
     * @return message信息集合
     */
    Pagination<TaskMessageUser> findMessageUserPage(TaskMessageUserQuery userQuery);



}
