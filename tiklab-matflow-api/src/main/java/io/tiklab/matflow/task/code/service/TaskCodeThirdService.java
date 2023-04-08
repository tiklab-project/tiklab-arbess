package io.tiklab.matflow.task.code.service;


import io.tiklab.matflow.setting.model.AuthThird;

import java.io.IOException;
import java.util.List;

/**
 * 源码第三方服务接口
 */
public interface TaskCodeThirdService {

    /**
     * 获取code
     * @return 获取code的Url
     */
    String findCode(AuthThird authThird);

    /**
     * 根据code获取AccessToken
     * @param authThird code
     */
    String findAccessToken(AuthThird authThird)  throws IOException ;

    /**
     * 获取用户授权的第三方信息
     * @param userId 用户id
     * @return 第三方信息
     */
    AuthThird findUserAuthThird(String userId);

    /**
     * 异移除用户授权的第三方信息
     * @param userId 用户id
     */
    void removeUserAuthThird(String userId);

    /**
     * 获取授权的accessToken
     * @param authId 认证id
     * @param accessToken 旧的accessToken
     * @return accessToken
     */
    String findUserAuthThirdToken(String authId , String accessToken);

    /**
     * 移除授权的accessToken
     * @param accessToken 旧的accessToken
     */
    void removeUserAuthThirdToken(String accessToken);


    /**
     * 获取用户所有仓库
     * @param authId 凭证
     * @return 厂库信息
     */
    List<String> findAllStorehouse(String authId);

    /**
     * 获取仓库所有分支
     * @param authId 凭证ID
     * @param houseName 仓库名
     * @return 所有分支
     */
    List<String> findBranch(String authId,String houseName);

    /**
     * 获取仓库克隆地址
     * @param proofId 凭证ID
     * @param houseName 仓库名称
     * @return 克隆地址
     */
    String getHouseUrl(String proofId,String houseName,int type);


    /**
     * 获取回调地址
     * @param callbackUrl 地址
     * @return 回调地址
     */
    String callbackUrl(String callbackUrl);



}


















