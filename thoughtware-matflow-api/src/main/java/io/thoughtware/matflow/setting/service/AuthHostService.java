package io.thoughtware.matflow.setting.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthHostQuery;

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
     * 查询单个类型所有配置
     * @param type 类型
     * @return 配置
     */
    List<AuthHost> findAllAuthHostList(String type);

    /**
     * 查询所有流水线主机授权
     * @return 流水线主机授权列表
     */
    @FindAll
    List<AuthHost> findAllAuthHost();


    @FindList
    List<AuthHost> findAllAuthHostList(List<String> idList);


    Pagination<AuthHost> findAuthHostPage(AuthHostQuery hostQuery);
    
}
