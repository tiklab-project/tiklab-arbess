package io.tiklab.matflow.setting.service;


import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.setting.model.AuthHost;

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
    
}
