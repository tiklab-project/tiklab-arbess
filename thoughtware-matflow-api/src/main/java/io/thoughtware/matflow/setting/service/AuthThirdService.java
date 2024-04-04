package io.thoughtware.matflow.setting.service;


import io.thoughtware.matflow.setting.model.AuthThirdQuery;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.matflow.setting.model.AuthThird;

import java.util.List;

/**
 * 流水线第三方认证服务接口
 */
@JoinProvider(model = AuthThird.class)
public interface AuthThirdService {

    /**
     * 创建流水线第三方授权
     * @param authThird 流水线第三方授权
     * @return 流水线第三方授权id
     */
    String createAuthServer(AuthThird authThird);

    /**
     * 删除流水线第三方授权
     * @param authServerId 流水线第三方授权id
     */
    void deleteAuthServer(String authServerId);

    /**
     * 更新第三方授权信息
     * @param authThird 信息
     */
    void updateAuthServer(AuthThird authThird);

    /**
     * 查询第三方授权信息
     * @param authServerId id
     * @return 信息
     */
    @FindOne
    AuthThird findOneAuthServer(String authServerId);

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型 1. gitee 2. github 3.sonar 4.nexus
     * @return 认证信息
     */
    List<AuthThird> findAllAuthServerList(String type);


    List<AuthThird> findAuthServerList(AuthThirdQuery thirdQuery);

    List<AuthThird> findAllAuthServerList();

    /**
     * 查询所有流水线第三方授权
     * @return 流水线第三方授权列表
     */
    @FindAll
    List<AuthThird> findAllAuthServer();


    @FindList
    List<AuthThird> findAllAuthServerList(List<String> idList);


    Integer findAuthServerNumber();
    
    
}
