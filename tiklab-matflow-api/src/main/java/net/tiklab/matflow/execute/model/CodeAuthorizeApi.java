package net.tiklab.matflow.execute.model;


import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@ApiModel
@Join
@Component
public class CodeAuthorizeApi {

    //第三方应用id
    @Value("${git.github.client.id}")
    private String githubClientId;

    @Value("${git.github.client.secret}")
    private String githubClientSecret;

    //回调地址
    @Value("${git.github.callback.url}")
    private String githubCallbackUri;

    //第三方应用id
    @Value("${git.gitee.client.id}")
    private String giteeClientId;

    @Value("${git.gitee.client.secret}")
    private String giteeClientSecret;

    //回调地址
    @Value("${git.gitee.callback.url}")
    private String giteeCallbackUri;


    //获取code （get）
    public String getCode(Integer type) {
        return switch (type) {
            case 2 ->
                    "https://gitee.com/oauth/authorize?client_id=" + giteeClientId + "&redirect_uri=" + giteeCallbackUri + "&response_type=code";
            case 3 ->
                    "https://github.com/login/oauth/authorize?client_id=" + githubClientId + "&callback_url=" + githubCallbackUri + "&scope=repo repo admin:org_hook user ";
            default -> null;
        };
    }

    //获取accessToken
    public String getAccessToken(String code, Integer type) {
        return switch (type) {
            case 2 -> "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + giteeClientId + "&redirect_uri=" + giteeCallbackUri + "&client_secret=" + giteeClientSecret;
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

    public String getGithubClientId() {
        return githubClientId;
    }

    public void setGithubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }

    public void setGithubClientSecret(String githubClientSecret) {
        this.githubClientSecret = githubClientSecret;
    }

    public String getGithubCallbackUri() {
        return githubCallbackUri;
    }

    public void setGithubCallbackUri(String githubCallbackUri) {
        this.githubCallbackUri = githubCallbackUri;
    }

    public String getGiteeClientId() {
        return giteeClientId;
    }

    public void setGiteeClientId(String giteeClientId) {
        this.giteeClientId = giteeClientId;
    }

    public String getGiteeClientSecret() {
        return giteeClientSecret;
    }

    public void setGiteeClientSecret(String giteeClientSecret) {
        this.giteeClientSecret = giteeClientSecret;
    }

    public String getGiteeCallbackUri() {
        return giteeCallbackUri;
    }

    public void setGiteeCallbackUri(String giteeCallbackUri) {
        this.giteeCallbackUri = giteeCallbackUri;
    }
}