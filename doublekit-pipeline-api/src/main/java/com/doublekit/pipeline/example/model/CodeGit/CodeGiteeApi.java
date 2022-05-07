package com.doublekit.pipeline.example.model.CodeGit;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class CodeGiteeApi {


    public String getClient_id() {
        //第三方应用id
        return "84e4a3626d037a717fb4952f09abdf2624ce28a0beffb92cb4eac67479e05b34";
    }

    public String getCallback_uri() {
        //回调地址
        return "http%3A%2F%2F192.168.10.101%3A3004%2F%23%2Fhome%2Ftask%2Fconfig";
    }

    public String getClient_secret() {
        //第三方应用秘钥
        return "f38167d54948136b9ca4e4ca454573b3c3d485db575a91a0e784a04599a20054";
    }

    public String getCode() {
        //获取code
        return "https://gitee.com/oauth/authorize?client_id="+getClient_id()+"&redirect_uri="+getCallback_uri()+"&response_type=code";
    }

    /**
     * 获取accessToken
     * @param code 临时code
     * @return accessToken
     */
    public String getAccessToken(String code) {
        //获取accessToken
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id="+getClient_id()+"&redirect_uri="+getCallback_uri()+"&client_secret="+getClient_secret();
        // return "https://gitee.com/oauth/token";
    }

    /**
     * 获取所有仓库
     * @param accessToken 凭证
     * @return 获取地址
     */
    public String getAllStorehouse(String accessToken) {
        //获取所有仓库
        return "https://gitee.com/api/v5/user/repos?access_token="+accessToken+"&sort=full_name&page=1&per_page=20";
    }

    /**
     * 获取单个仓库信息
     * @param loginName 登录名
     * @param projectName 项目名
     * @param accessToken 凭证
     * @return 仓库信息url
     */
    public String getOneStorehouse(String loginName,String projectName , String accessToken) {
        //获取某个仓库
        return "https://gitee.com/api/v5/repos/"+loginName+"/"+projectName+"?access_token="+accessToken;
    }

    /**
     * 获取单个仓库信息
     * @param loginName 登录名
     * @param projectName 项目名
     * @param accessToken 凭证
     * @return 仓库信息url
     */
    public String getWarehouseBranch(String loginName,String projectName , String accessToken) {
        //获取分支
        return "https://gitee.com/api/v5/repos/"+loginName+"/"+projectName+"/branches?access_token="+accessToken;
    }

    /**
     * 获取新的token凭证
     * @param refreshToken 回调token
     * @return 地址
     */
    public String getRefreshToken(String refreshToken) {
        //获取新的token
        return "https://gitee.com/oauth/token?grant_type=refresh_token&refresh_token="+ refreshToken;
    }

    /**
     * 获取用户信息
     * @param accessToken 凭证
     * @return 用户信息
     */
    public String getUserMessage( String accessToken) {
        //获取授权用户的资料
        return "https://gitee.com/api/v5/user?access_token="+accessToken;
    }


}
