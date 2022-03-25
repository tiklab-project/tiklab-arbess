package com.doublekit.pipeline.example.model.CodeGit;

import java.util.List;

public class CodeGiteeApi {

    //应用名
     private String name;

     //分支
     private List<String> branch;

     //url
     private String url;

     //accessToken
     private  String accessToken;

     //refreshToken
     private  String refreshToken;


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

    public String getAccessToken(String code) {
        //获取accessToken
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id="+getClient_id()+"&redirect_uri="+getCallback_uri()+"&client_secret="+getClient_secret();
        // return "https://gitee.com/oauth/token";
    }

    public String getAllStorehouse(String accessToken) {
        //获取所有仓库
        return "https://gitee.com/api/v5/user/repos?access_token="+accessToken+"&sort=full_name&page=1&per_page=20";
    }
    public String getOneStorehouse(String projectName) {
        //获取某个仓库
        return "https://gitee.com/api/v5/repos/"+getName()+"/"+projectName+"?access_token="+getAccessToken();
    }

    public String getWarehouseBranch(String projectName) {
        //获取分支
        return "https://gitee.com/api/v5/repos/"+getName()+"/"+projectName+"/branches?access_token="+getAccessToken();
    }

    public String getToken() {
        //获取新的token
        return "https://gitee.com/oauth/token?grant_type=refresh_token&refresh_token="+getRefreshToken();
    }

    public String getUserMessage() {
        //获取授权用户的资料
        return "https://gitee.com/api/v5/user?access_token="+getAccessToken();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
