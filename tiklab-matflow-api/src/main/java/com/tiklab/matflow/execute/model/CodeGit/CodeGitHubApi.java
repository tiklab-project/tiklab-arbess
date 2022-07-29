package com.tiklab.matflow.execute.model.CodeGit;


import com.tiklab.join.annotation.Join;
import com.tiklab.postlink.annotation.ApiModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@ApiModel
@Join
@Component
public class CodeGitHubApi {

    //第三方应用id
    @Value("${gitHubClientId}")
    private String clientId;

    @Value("${gitHubClientSecret}")
    private String clientSecret;

    //回调地址
    @Value("${gitHubCallbackUrl}")
    private String callbackUri;

    //获取code （get）
    public String getCode(){
        return "https://github.com/login/oauth/authorize?client_id=" + getClientId() +"&callback_url="+getClientSecret() +"&scope=repo admin:org_hook user ";
    }

    //获取accessToken
    public String getAccessToken(String code){ return "https://github.com/login/oauth/access_token";}

    //获取用户信息
    public String getUser(){
        return "https://api.github.com/user";
    }

    //获取单个仓库
    public String getOneHouse(String name,String house){
        return "https://api.github.com/repos/"+name+"/"+house;
    }

    //获取所有仓库
    public String getAllStorehouse(){ return "https://api.github.com/user/repos";}

    //获取仓库下所有分支
    public String getBranch(String username,String houseName){return "https://api.github.com/repos/"+username+"/"+houseName+"/branches";}


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCallbackUri() {
        return callbackUri;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }
}
