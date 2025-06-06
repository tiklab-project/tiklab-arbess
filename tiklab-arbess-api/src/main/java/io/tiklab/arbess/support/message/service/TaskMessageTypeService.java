package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.model.TaskMessageType;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 任务消息类型服务接口
 */
@JoinProvider(model = TaskMessageType.class)
public interface TaskMessageTypeService {

    /**
     * 创建
     * @param taskMessageType message信息
     */
    String createMessageType(TaskMessageType taskMessageType) ;


    /**
     * 删除
     * @param id id
     */
    void deleteMessageType(String id) ;


    /**
     * 更新信息
     * @param taskMessageType 信息
     */
    void updateMessageType(TaskMessageType taskMessageType);

    /**
     * 查询单个信息
     * @param id id
     * @return message信息
     */
    @FindOne
    TaskMessageType findMessageType(String id) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<TaskMessageType> findAllMessageType() ;

    /**
     * 根据id列表查询信息
     * @param idList id列表
     * @return
     */
    @FindList
    List<TaskMessageType> findMessageTypeList(List<String> idList);


    /**
     * 根据查询条件查询信息
     * @param typeQuery 查询条件
     * @return message信息集合
     */
    List<TaskMessageType> findMessageTypeList(TaskMessageTypeQuery typeQuery);


    /**
     * 分页根据查询条件查询信息
     * @param typeQuery 查询条件
     * @return message信息集合
     */
    Pagination<TaskMessageType> findMessageTypePage(TaskMessageTypeQuery typeQuery);




    
    
}
