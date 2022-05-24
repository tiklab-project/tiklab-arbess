package com.doublekit.pipeline.execute.model.CodeGit;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class CodeGitHubApi {

    public String getClient_ID() {
        return  "cf93e472f1ffe9521474";
    }

    public String getClient_Secret() {
        return "a9fb450fe0d746b71c06f18992487458938da9a1";
    }

    public String getCallback_URL() {
        return "http://192.168.10.23:3004/#/home/task/config";
    }

    //获取code （get）
    public String getCode(){
        return "https://github.com/login/oauth/authorize?client_id=" + getClient_ID()+"&scope=repo admin:org_hook admin:repo_hook user admin:org admin:org_hook notifications codespace";
    }

    //获取accessToken
    public String getAccessToken(String code){ return "https://github.com/login/oauth/access_token";}

    //获取用户信息
    public String getUser(){
        return "https://api.github.com/user";
    }

    //获取所有仓库
    public String getAllStorehouse(){ return "https://api.github.com/user/repos";}

    //获取仓库下所有分支
    public String getBranch(String username,String houseName){return "https://api.github.com/repos/"+username+"/"+houseName+"/branches";}
}
