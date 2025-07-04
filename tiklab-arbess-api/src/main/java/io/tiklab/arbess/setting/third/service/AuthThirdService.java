package io.tiklab.arbess.setting.third.service;


import io.tiklab.arbess.setting.third.model.AuthThirdQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.third.model.AuthThird;

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


    AuthThird findOneAuthServerNoQuery(String authServerId);

    /**
     * 查询流水线第三方授权
     * @param thirdQuery 查询条件
     * @return 流水线第三方授权列表
     */
    List<AuthThird> findAuthServerList(AuthThirdQuery thirdQuery);

    /**
     * 分页查询流水线第三方授权
     * @param thirdQuery 查询条件
     * @return 分页流水线第三方授权
     */
    Pagination<AuthThird> findAuthServerPage(AuthThirdQuery thirdQuery);

    /**
     * 查询所有流水线第三方授权
     * @return 流水线第三方授权列表
     */
    @FindAll
    List<AuthThird> findAllAuthServer();


    /**
     * 根据ID列表批量查询流水线第三方授权
     * @param idList 流水线第三方授权ID列表
     * @return 流水线第三方授权列表
     */
    @FindList
    List<AuthThird> findAllAuthServerList(List<String> idList);

    /**
     * 查询流水线第三方授权数量
     * @return 流水线第三方授权数量
     */
    Integer findAuthServerNumber();
    
    
}
