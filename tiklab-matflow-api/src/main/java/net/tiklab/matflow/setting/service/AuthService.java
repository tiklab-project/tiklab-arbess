package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.Auth;

import java.util.List;

@JoinProvider(model = Auth.class)
public interface AuthService {

    /**
     * 创建流水线基本授权
     * @param auth 流水线基本授权
     * @return 流水线基本授权id
     */
    String createAuth(Auth auth);

    /**
     * 删除流水线基本授权
     * @param authId 流水线基本授权id
     */
    void deleteAuth(String authId);

    /**
     * 更新基本授权信息
     * @param auth 信息
     */
    void updateAuth(Auth auth);

    /**
     * 查询基本授权信息
     * @param authId id
     * @return 信息
     */
    @FindOne
    Auth findOneAuth(String authId);


    /**
     * 查询所有流水线基本授权
     * @return 流水线基本授权列表
     */
    @FindAll
    List<Auth> findAllAuth();


    @FindList
    List<Auth> findAllAuthList(List<String> idList);
    
}
