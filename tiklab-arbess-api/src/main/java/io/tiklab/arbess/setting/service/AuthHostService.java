package io.tiklab.arbess.setting.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.AuthHost;
import io.tiklab.arbess.setting.model.AuthHostQuery;

import java.util.List;

/**
 * 流水线主机认证服务接口
 */
@JoinProvider(model = AuthHost.class)
public interface AuthHostService {

    /**
     * 创建流水线主机授权
     * @param authHost 流水线主机授权
     * @return 流水线主机授权id
     */
    String createAuthHost(AuthHost authHost);

    /**
     * 删除流水线主机授权
     * @param authHostId 流水线主机授权id
     */
    void deleteAuthHost(String authHostId);

    /**
     * 更新主机授权信息
     * @param authHost 信息
     */
    @FindOne
    void updateAuthHost(AuthHost authHost);

    /**
     * 查询主机授权信息
     * @param authHostId id
     * @return 信息
     */
    AuthHost findOneAuthHost(String authHostId);


    /**
     * 查询流水线主机授权
     * @param hostQuery 查询条件
     * @return 流水线主机授权列表
     */
    List<AuthHost> findAuthHostList(AuthHostQuery hostQuery);

    /**
     * 查询所有流水线主机授权
     * @return 流水线主机授权列表
     */
    @FindAll
    List<AuthHost> findAllAuthHost();

    /**
     * 根据ID列表批量查询流水线主机授权
     * @param idList 流水线主机授权ID列表
     * @return 流水线主机授权列表
     */
    @FindList
    List<AuthHost> findAllAuthHostList(List<String> idList);

    /**
     * 分页查询流水线主机授权
     * @param hostQuery 查询条件
     * @return 流水线主机授权分页结果
     */
    Pagination<AuthHost> findAuthHostPage(AuthHostQuery hostQuery);

    /**
     * 查询流水线主机授权数量
     * @return 流水线主机授权数量
     */
    Integer findHostNumber();
    
}
