package io.tiklab.arbess.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.Group;
import io.tiklab.arbess.setting.model.GroupQuery;

import java.util.List;

/**
 * 主机组服务接口
 */
@JoinProvider(model = Group.class)
public interface GroupService {

    /**
     * 创建主机组
     * @param group 主机组对象
     * @return 主机组ID
     */
    String createGroup(Group group);

    /**
     * 更新主机组
     * @param group 主机组对象
     */
    void updateGroup(Group group);

    /**
     * 删除主机组
     * @param groupId 主机组ID
     */
    void deleteGroup(String groupId);

    /**
     * 查询主机组
     * @param groupId 主机组ID
     * @return 主机组对象
     */
    @FindOne
    Group findOneGroup(String groupId);

    /**
     * 查询所有主机组
     * @return 主机组列表
     */
    @FindAll
    List<Group> findAllGroup();

    /**
     * 查询主机组列表
     * @param groupQuery 查询条件
     * @return 主机组列表
     */
    List<Group> findGroupList(GroupQuery groupQuery);

    /**
     * 分页查询主机组
     * @param groupQuery 查询条件
     * @return 主机组分页结果
     */
    Pagination<Group> findGroupPage(GroupQuery groupQuery);

    /**
     * 根据ID列表批量查询主机组
     * @param idList 主机组ID列表
     * @return 主机组列表
     */
    @FindList
    List<Group> findAllGroupList(List<String> idList);

    /**
     * 查询主机组数量
     * @return 主机组数量
     */
    Integer findGroupNumber();
    
    
}
