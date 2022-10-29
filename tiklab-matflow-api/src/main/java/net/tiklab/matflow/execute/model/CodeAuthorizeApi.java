package net.tiklab.matflow.execute.model;


import net.tiklab.matflow.orther.model.PipelineAuthorize;
import org.springframework.stereotype.Service;

@Service
public class CodeAuthorizeApi {

    //获取code （get）
    public  String getCode(PipelineAuthorize oneAuthorize, int type) {
        return switch (type) {
            case 2 ->
                    "https://gitee.com/oauth/authorize?client_id=" + oneAuthorize.getClientId() + "&redirect_uri=" + oneAuthorize.getCallbackUrl() + "&response_type=code";
            case 3 ->
                    "https://github.com/login/oauth/authorize?client_id=" + oneAuthorize.getClientId() + "&callback_url=" + oneAuthorize.getCallbackUrl() + "&scope=repo repo admin:org_hook user ";
            default -> null;
        };
    }

    //获取accessToken
    public String getAccessToken(PipelineAuthorize oneAuthorize,String code, Integer type) {
        return switch (type) {
            case 2 -> "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + oneAuthorize.getClientId() + "&redirect_uri=" + oneAuthorize.getCallbackUrl() + "&client_secret=" + oneAuthorize.getClientSecret();
            case 3 -> "https://github.com/login/oauth/access_token";
            default -> null;
        };
    }

    //获取用户信息
    public String getUser (String accessToken ,int type) {

        return switch (type) {
            case 2 -> "https://gitee.com/api/v5/user?access_token="+accessToken;
            case 3 ->  "https://api.github.com/user";
            default -> null;
        };
    }

    //获取单个仓库
    public String getOneHouse (String username, String houseName, String accessToken,int type){
        return switch (type) {
            case 2 -> "https://gitee.com/api/v5/repos/"+username+"/"+houseName+"?access_token="+accessToken;
            case 3 -> "https://api.github.com/repos/" + username + "/" + houseName;
            default -> null;
        };
    }

    //获取所有仓库
    public String getAllStorehouse (String accessToken,int type) {
        return switch (type) {
            case 2 -> "https://gitee.com/api/v5/user/repos?access_token="+accessToken+"&sort=full_name&page=1&per_page=20";
            case 3 -> "https://api.github.com/user/repos";
            default -> null;
        };
    }

    //获取仓库下所有分支
    public String getBranch (String username, String houseName, String accessToken,int type){
        return switch (type) {
            case 2 -> "https://gitee.com/api/v5/repos/"+username+"/"+houseName+"/branches?access_token="+accessToken;
            case 3 ->  "https://api.github.com/repos/" + username + "/" + houseName + "/branches";
            default -> null;
        };
    }

    /**
     * 获取新的token凭证
     * @param refreshToken 回调token
     * @return 地址
     */
    public String findRefreshToken(String refreshToken) {
        //获取新的token
        return "https://gitee.com/oauth/token?grant_type=refresh_token&refresh_token="+ refreshToken;
    }

}