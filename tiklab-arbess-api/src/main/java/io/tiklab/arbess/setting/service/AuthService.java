package io.tiklab.arbess.setting.service;


import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.Auth;

import java.util.List;

/**
 * 流水线基本认证服务接口
 */
@JoinProvider(model = Auth.class)
public interface AuthService {

    /**
     * 创建流水线基本认证
     * @param auth 流水线基本认证
     * @return 流水线基本认证id
     */
    String createAuth(Auth auth);

    /**
     * 删除流水线基本认证
     * @param authId 流水线基本认证id
     */
    void deleteAuth(String authId);

    /**
     * 更新基本认证信息
     * @param auth 信息
     */
    void updateAuth(Auth auth);

    /**
     * 查询基本认证信息
     * @param authId id
     * @return 信息
     */
    @FindOne
    Auth findOneAuth(String authId);


    /**
     * 查询所有流水线基本认证
     * @return 流水线基本认证列表
     */
    @FindAll
    List<Auth> findAllAuth();


    @FindList
    List<Auth> findAllAuthList(List<String> idList);


    Integer findAuthNumber();
    
}
