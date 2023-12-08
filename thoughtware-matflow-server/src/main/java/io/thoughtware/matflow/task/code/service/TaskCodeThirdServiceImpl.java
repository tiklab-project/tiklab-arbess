package io.thoughtware.matflow.task.code.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.model.AuthThirdQuery;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * gitee , github第三方实现
 */

@Service
@Exporter
@EnableScheduling
public class TaskCodeThirdServiceImpl implements TaskCodeThirdService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AuthThirdService serverServer;

    public static Map<String, AuthThird> userMapToken = new HashMap<>();

    public static Map<String, Long> userAuthThirdTime = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeThirdServiceImpl.class);

    @Override
    public String findCode(AuthThird authThird){
        String callbackUrl = authThird.getCallbackUrl().trim();
        String encode = URLEncoder.encode(callbackUrl(callbackUrl), StandardCharsets.UTF_8);
        authThird.setCallbackUrl(encode);
        return getCode(authThird);
    }
    
    @Override
    public String findAccessToken(AuthThird authThird) throws IOException , ApplicationException {
        String loginId = LoginContext.getLoginId();
        String type = authThird.getType();
        if (!PipelineUtil.isNoNull(authThird.getCode())){
            throw new ApplicationException("code不能为空。");
        }
        String callbackUrl = authThird.getCallbackUrl();
        String encode = URLEncoder.encode(callbackUrl(callbackUrl), StandardCharsets.UTF_8);
        authThird.setCallbackUrl(encode);

        String accessTokenUrl = getAccessToken(authThird);
        Map<String, String> map;
        switch (type) {
            case "2" ,"gitee" -> map = giteeAccessToken(accessTokenUrl);
            case "3","github" -> map = gitHubAccessToken(authThird,accessTokenUrl);
            default -> { return null; }
        }
        String accessToken = map.get("accessToken");
        if (accessToken == null){
            throw new ApplicationException(50001,"授权失败，accessToken为空。");
        }
        logger.info("授权成功：" + accessToken + " time: "+ PipelineUtil.date(1));

        authThird.setAccessToken(accessToken);
        authThird.setRefreshToken(map.get("refreshToken"));
        String username = findMessage(accessToken, type);
        authThird.setUsername(username);
        authThird.setAuthType(2);
        userMapToken.put(loginId, authThird);

        //过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long date = calendar.getTime().getTime();
        userAuthThirdTime.put(accessToken,date);

        if (map.get("state") != null && map.get("state").equals("0")){
            return null;
        }
        if (type.equals("2") || type.equals("gitee")){
            username = username+"的gitee的授权";
        }
        if (type.equals("3") || type.equals("github")){
            username = username+"的github的授权";
        }
        return username;
    }

    @Override
    public AuthThird findUserAuthThird(String userId){
       return userMapToken.get(userId);
    }

    @Override
    public void removeUserAuthThird(String userId){
        userMapToken.remove(userId);
    }

    @Override
    public String findUserAuthThirdToken(String authId , String accessToken){
        Long date= userAuthThirdTime.get(accessToken);
        if (Objects.isNull(date)){
            return updateAuthorization(authId);
        }
        long time = new Date().getTime();
        if (date - 20 > time){
            return accessToken;
        }
        return updateAuthorization(authId);
    }

    @Override
    public void removeUserAuthThirdToken(String accessToken){
        userAuthThirdTime.remove(accessToken);
    }

    private Map<String, String> giteeAccessToken(String accessTokenUrl) throws IOException {
        String post = request(accessTokenUrl);
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(post);
        map.put("accessToken", jsonObject.getString("access_token"));
        map.put("refreshToken", jsonObject.getString("refresh_token"));
        return map;
    }

    private Map<String, String> gitHubAccessToken(AuthThird authThird, String accessTokenUrl) throws ApplicationException{
        Map<String, String> map = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", authThird.getClientId().trim());
        paramMap.add("client_secret", authThird.getClientSecret().trim());
        paramMap.add("callback_url", authThird.getCallbackUrl().trim());
        paramMap.add("code",authThird.getCode());
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<JSONObject> response;
        try {
             response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, JSONObject.class);
        }catch (ResourceAccessException e){
            boolean timedOut = Objects.requireNonNull(e.getMessage()).contains("Read timed out");
            boolean connectOut = Objects.requireNonNull(e.getMessage()).contains("Connect timed out");
            if (timedOut || connectOut){
                throw new ApplicationException(50001,"连接超时，请重新授权。");
            }
            logger. info(e.getMessage());
            throw new RuntimeException();
        }
        JSONObject body = response.getBody();
        if (body != null){
            map.put("accessToken",body.getString("access_token")) ;
        }
        return map;
    }
    
    @Override
    public List<String> findAllStorehouse(String authId) {
        AuthThird authCode = serverServer.findOneAuthServer(authId);
        if (Objects.isNull(authCode)){
            return null;
        }
        String type = authCode.getType();
        String accessToken = authCode.getAccessToken();
        String allStorehouseAddress = getAllStorehouse(accessToken,type);
        List<String> list = new ArrayList<>();
        try {
            switch (type) {
                case  "2" , "gitee" -> list = giteeAllStorehouse(allStorehouseAddress);
                case "3" , "github" -> list = gitHubAllStorehouse(accessToken, allStorehouseAddress);
            }
        }catch (HttpClientErrorException e){
            throw new ApplicationException(50001,"获取仓库信息失败，该应用无权限，请为该应用赋予读取仓库(projects)权限。");
        }

        return list;
    }

    private List<String> giteeAllStorehouse(String allStorehouseAddress) throws HttpClientErrorException {
        List<String> storehouseList = new ArrayList<>();
        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器StringHttpMessageConverter，并设置utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> returnBody = restTemplate.getForEntity(allStorehouseAddress, String.class, JSONObject.class);
        String body = returnBody.getBody();
        JSONArray allStorehouseJson = JSON.parseArray(body);
        if (allStorehouseJson == null){
            return null;
        }
        for (int i = 0; i < allStorehouseJson.size(); i++) {
            JSONObject storehouse=allStorehouseJson.getJSONObject(i);
            storehouseList.add(storehouse.getString("full_name"));
        }
        return storehouseList;
    }

    private  List<String> gitHubAllStorehouse(String accessToken, String allStorehouseAddress) throws HttpClientErrorException {
        List<String> list = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Authorization", "token"+" "+accessToken);

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> forEntity = restTemplate.exchange(allStorehouseAddress, HttpMethod.GET, entity, String.class);
        String body = forEntity.getBody();
        if (body == null){
            return Collections.emptyList();
        }
        JSONArray allStorehouseJson = JSON.parseArray(body);
        for (int i = 0; i < allStorehouseJson.size(); i++) {
            JSONObject storehouse=allStorehouseJson.getJSONObject(i);
            list.add(storehouse.getString("full_name"));
        }
        return list;
    }
    
    @Override
    public List<String> findBranch(String authId,String houseName) {
        AuthThird authCode = serverServer.findOneAuthServer(authId);
        if (Objects.isNull(authCode)){
            return null;
        }
        String accessToken = authCode.getAccessToken();
        if (!PipelineUtil.isNoNull(houseName) ){
            throw new ApplicationException(5000,"仓库名称为空。");
        }
        if (houseName.split("/").length < 1){
            return null;
        }
        String house = houseName.split("/")[1];
        String name = houseName.split("/")[0];
        String type = authCode.getType();
        String url = getBranch(name,house,accessToken,type);
        return switch (type) {
            case "2" , "gitee" -> giteeBranch(url);
            case "3" , "github" -> gitHubBranch(accessToken, url);
            default -> null;
        };

    }

    private List<String> giteeBranch(String url) {
        ArrayList<String> branchList = new ArrayList<>();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class, JSONObject.class);
        HttpStatus statusCode = forEntity.getStatusCode();
        if (statusCode.isError()){
            return Collections.emptyList();
        }
        JSONArray branchS = JSON.parseArray(forEntity.getBody());
        if (branchS != null) {
            for (int i = 0; i < branchS.size(); i++) {
                JSONObject jsonArray = branchS.getJSONObject(i);
                branchList.add(jsonArray.getString("name"));
            }
        }
        return branchList;

    }

    private List<String> gitHubBranch(String accessToken,String url){
        List<String> list = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Authorization", "token"+" "+ accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String body = forEntity.getBody();
        if (body == null){
            return Collections.emptyList();
        }
        JSONArray allBranch = JSON.parseArray(body);
        for (int i = 0; i < allBranch.size(); i++) {
            JSONObject branch = allBranch.getJSONObject(i);
            list.add(branch.getString("name"));
        }
        return list;
    }

    //获取仓库的url
    @Override
    public String getHouseUrl(String authCodeId,String houseName,String type){
        AuthThird authCode = serverServer.findOneAuthServer(authCodeId);
        String accessToken = authCode.getAccessToken();

        if (houseName == null){
            return null;
        }
        String house = houseName.split("/")[1];
        String name = houseName.split("/")[0];

        //获取仓库URl
        String oneStorehouse = getOneHouse(name,house,accessToken,type);
        return switch (type) {
            case "2" , "gitee" -> giteeHouseUrl(oneStorehouse);
            case "3" , "github" -> gitHubHouseUrl(accessToken, oneStorehouse);
            default -> null;
        };
    }

    private String giteeHouseUrl( String oneStorehouse){
        ResponseEntity<String> forEntity1 = restTemplate.getForEntity(oneStorehouse, String.class, JSONObject.class);
        JSONObject jsonObject = JSON.parseObject(forEntity1.getBody());
        return jsonObject.getString("html_url");

    }

    private String gitHubHouseUrl(String accessToken, String oneStorehouse){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "token"+" "+ accessToken);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(paramMap, headers);
        //获取仓库URl
        ResponseEntity<String> response = restTemplate.exchange(oneStorehouse, HttpMethod.GET, entity, String.class);
        JSONObject jsonObject = JSON.parseObject(response.getBody());
        return jsonObject.getString("html_url");
    }

    //获取授权账户名
    public String findMessage(String accessToken,String authType){
        if (accessToken == null ){
            return null;
        }
        String userMessage = getUser(accessToken,authType);
        String username = null;
        if (authType.equals("2") || authType.equals("gitee")){
            username = giteeMessage(userMessage);
        }
        if (authType.equals("3") || authType.equals("github")){
            username =  gitHubUserMessage(accessToken,userMessage);
        }
        return username;
    }

    //获取登录信息
    private String giteeMessage(String messageUrl){
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(messageUrl, JSONObject.class, String.class);
        JSONObject body = jsonObject.getBody();
        if (body == null) {
            return null;
        }
        return body.getString("login");
    }

    private String gitHubUserMessage(String accessToken,String messageUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "token"+" "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(messageUrl, HttpMethod.GET, entity, JSONObject.class);
        JSONObject body = response.getBody();
        if (body == null){
            return null;
        }
        return body.getString("login");

    }

    /**
     * 访问url
     * @param url 访问地址
     * @return 访问结果
     * @throws IOException 转换异常
     */
    private String request(String url) throws IOException {
        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();

        StringBuilder result = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        return result.toString();
    }

    public String updateAuthorization(String authId) {
        AuthThirdQuery authThirdQuery = new AuthThirdQuery().setType(PipelineFinal.TASK_CODE_GITEE);
        List<AuthThird> allAuthCode = serverServer.findAuthServerList(authThirdQuery);
        if (allAuthCode == null) {
            return null;
        }
        AuthThird authCode = serverServer.findOneAuthServer(authId);
        boolean token = PipelineUtil.isNoNull(authCode.getAccessToken());
        boolean refresh = PipelineUtil.isNoNull(authCode.getRefreshToken());
        if (!token || !refresh) {
            return null;
        }
        logger.info("更新授权信息,授权人：" + authCode.getUsername());
        String refreshToken = findRefreshToken(authCode.getRefreshToken());
        ResponseEntity<JSONObject> forEntity;
        try {
            forEntity = restTemplate.postForEntity(refreshToken, String.class, JSONObject.class);
            JSONObject body = forEntity.getBody();
            if (body == null) {
                logger.error("授权人：" + authCode.getUsername() + "授权信息更新失败。");
                return null;
            }
            String access_token = body.getString("access_token");
            authCode.setAccessToken(body.getString("access_token"));
            authCode.setRefreshToken(body.getString("refresh_token"));
            serverServer.updateAuthServer(authCode);
            logger.info("授权人：" + authCode.getUsername() + "授权信息更新成功:"+access_token);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            long date = calendar.getTime().getTime();
            userAuthThirdTime.put(access_token , date);
            return access_token;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.info("授权人：" + authCode.getUsername() + "授权信息更新失败" + message);
            return null;
        }
    }

    /**
     * 获取转码后的回调地址
     * @param callbackUrl 回调地址
     * @return 转码后的回调地址
     */
    @Override
    public String callbackUrl(String callbackUrl){
        boolean b = PipelineUtil.validURL(callbackUrl);
        if (!b){
            return null;
        }
        String authority;
        String scheme;
        try {
            URL url = new URL(callbackUrl);
            authority = url.getAuthority();
            scheme = url.toURI().getScheme();
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
        callbackUrl =scheme+"://"+authority+"/#/index/authorize";
        return callbackUrl;
    }

    /**
     * 获取第三方code地址 （get）
     * @param authThird 第三方认证信息
     * @return code地址
     */
    private String getCode(AuthThird authThird) {
        String callbackUrl = authThird.getCallbackUrl().trim();
        String clientId = authThird.getClientId().trim();
        return switch (authThird.getType()) {
            case "2" , "gitee" ->
                    "https://gitee.com/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + callbackUrl + "&response_type=code";
            case "3" , "github" ->
                    "https://github.com/login/oauth/authorize?client_id=" + clientId + "&callback_url=" + callbackUrl + "&scope=repo repo admin:org_hook user ";
            default -> null;
        };
    }

    /**
     * 获取第三次accessToken地址
     * @param authThird 第三方认证信息
     * @return accessToken地址
     */
    private String getAccessToken(AuthThird authThird) {
        String code = authThird.getCode().trim();
        String clientId = authThird.getClientId().trim();
        String callbackUrl = authThird.getCallbackUrl().trim();
        String clientSecret = authThird.getClientSecret().trim();
        return switch (authThird.getType()) {
            case "2" , "gitee" -> "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + clientId + "&redirect_uri=" + callbackUrl + "&client_secret=" + clientSecret;
            case "3" , "github" -> "https://github.com/login/oauth/access_token";
            default -> null;
        };
    }

    /**
     * 请求第三方用户信息地址
     * @param accessToken accessToken
     * @param type 第三方类型
     * @return 请求地址
     */
    private String getUser (String accessToken ,String type) {
        return switch (type) {
            case "2" , "gitee" -> "https://gitee.com/api/v5/user?access_token="+accessToken;
            case "3" , "github" ->  "https://api.github.com/user";
            default -> null;
        };
    }

    /**
     * 获取单个仓库请求地址
     * @param username 用户名
     * @param houseName 仓库名
     * @param accessToken accessToken
     * @param type 第三方类型
     * @return 请求地址
     */
    private String getOneHouse (String username, String houseName, String accessToken,String type){
        return switch (type) {
            case "2" , "gitee" -> "https://gitee.com/api/v5/repos/"+username+"/"+houseName+"?access_token="+accessToken;
            case "3" , "github" -> "https://api.github.com/repos/" + username + "/" + houseName;
            default -> null;
        };
    }

    /**
     * 获取所有仓库请求地址
     * @param accessToken accessToken
     * @param type 第三方类型
     * @return 仓库请求地址
     */
    private String getAllStorehouse (String accessToken,String type) {
        return switch (type) {
            case "2" , "gitee" -> "https://gitee.com/api/v5/user/repos?access_token="+accessToken+"&sort=full_name&page=1&per_page=20";
            case "3" , "github" -> "https://api.github.com/user/repos";
            default -> null;
        };
    }

    /**
     * 获取仓库下所有分支
     * @param username 用户名
     * @param houseName 仓库名
     * @param accessToken accessToken
     * @param type 第三方类型
     * @return 请求地址
     */
    private String getBranch (String username, String houseName, String accessToken,String type){
        return switch (type) {
            case "2" , "gitee" -> "https://gitee.com/api/v5/repos/"+username+"/"+houseName+"/branches?access_token="+accessToken;
            case "3" , "github" ->  "https://api.github.com/repos/" + username + "/" + houseName + "/branches";
            default -> null;
        };
    }

    /**
     * 获取新的token凭证
     * @param refreshToken 回调token
     * @return 地址
     */
    private String findRefreshToken(String refreshToken) {
        //获取新的token
        return "https://gitee.com/oauth/token?grant_type=refresh_token&refresh_token="+ refreshToken;
    }
    
    
    
    

}





























