package net.tiklab.matflow.execute.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.execute.model.CodeAuthorizeApi;
import net.tiklab.matflow.orther.model.PipelineAuthorize;
import net.tiklab.matflow.orther.service.PipelineAuthorizeService;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.matflow.setting.service.ProofService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Exporter
@EnableScheduling
public class CodeAuthorizeServiceImpl implements CodeAuthorizeService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CodeAuthorizeApi codeAuthorizeApi;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineAuthorizeService authorizeService;

    Map<String,Integer> mapState = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(CodeAuthorizeServiceImpl.class);

    //获取code
    public String findCode(int type){
        PipelineAuthorize oneAuthorize = authorizeService.findOneAuthorize(type);
        return  codeAuthorizeApi.getCode(oneAuthorize,type);
    }

    //获取accessToken
    @Override
    public String findAccessToken(String code,int type) throws IOException {
        String loginId = LoginContext.getLoginId();
        PipelineAuthorize oneAuthorize = authorizeService.findOneAuthorize(type);
        if (code.equals("false")){
            mapState.put(loginId, 2);
            return null;
        }

        String accessTokenUrl = codeAuthorizeApi.getAccessToken(oneAuthorize,code,type);
        Map<String, String> map;
        String username ;
        switch (type) {
            case 2 -> {
                map = giteeAccessToken(accessTokenUrl);
                String accessToken = map.get("accessToken");
                username = giteeMessage(accessToken);
            }
            case 3 -> {
                map = gitHubAccessToken(code, accessTokenUrl);
                String accessToken = map.get("accessToken");
                username = gitHubUserMessage(accessToken);
            }
            default -> {
                return null;
            }
        }
        Proof proof = new Proof(1,type,"password",username,map.get("accessToken"),map.get("refreshToken"),"授权登录",loginId);
        mapState.put(loginId, 1);

        return  proofService.createProof(proof);
    }

    private Map<String, String> giteeAccessToken(String accessTokenUrl) throws IOException {
        String post = request(accessTokenUrl, "POST");
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(post);
        map.put("accessToken", jsonObject.getString("access_token"));
        map.put("refreshToken", jsonObject.getString("refresh_token"));
        return map;
    }

    private Map<String, String> gitHubAccessToken(String code,String accessTokenUrl){
        Map<String, String> map = new HashMap<>();
        PipelineAuthorize oneAuthorize = authorizeService.findOneAuthorize(2);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", oneAuthorize.getClientId());
        paramMap.add("client_secret", oneAuthorize.getClientSecret());
        paramMap.add("callback_url", oneAuthorize.getCallbackUrl());
        paramMap.add("code",code);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, JSONObject.class);
        JSONObject body = response.getBody();
        if (body != null){
            map.put("accessToken",body.getString("access_token")) ;
        }
        return map;
    }

    //列出授权用户的所有仓库
    @Override
    public List<String> findAllStorehouse(String proofId,int type) {
        Proof oneProof = proofService.findOneProof(proofId);
        String accessToken = oneProof.getProofPassword();

        String allStorehouseAddress = codeAuthorizeApi.getAllStorehouse(accessToken,type);
        return switch (type) {
            case 2 -> giteeAllStorehouse( allStorehouseAddress);
            case 3 -> gitHubAllStorehouse(accessToken, allStorehouseAddress);
            default -> null;
        };
    }

    private List<String> giteeAllStorehouse( String allStorehouseAddress) {
        List<String> storehouseList = new ArrayList<>();

        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器StringHttpMessageConverter，并设置utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> returnBody = restTemplate.getForEntity(allStorehouseAddress, String.class, JSONObject.class);

        HttpStatus statusCode = returnBody.getStatusCode();
        if (statusCode.isError()){
            return Collections.emptyList();
        }

        String body = returnBody.getBody();
        if (body == null){
            return Collections.emptyList();
        }

        JSONArray allStorehouseJson = JSON.parseArray(body);
        for (int i = 0; i < allStorehouseJson.size(); i++) {
            JSONObject storehouse=allStorehouseJson.getJSONObject(i);
            storehouseList.add(storehouse.getString("full_name"));
        }
        return storehouseList;
    }

    private  List<String> gitHubAllStorehouse( String accessToken, String allStorehouseAddress){

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
    public List<String> findBranch(String proofId,String houseName,int type) {
        Proof oneProof = proofService.findOneProof(proofId);
        String accessToken = oneProof.getProofPassword();
        String house = houseName.split("/")[1];
        String name = houseName.split("/")[0];
        String url = codeAuthorizeApi.getBranch(name,house,accessToken,type);
        return switch (type) {
            case 2 -> giteeBranch(url);
            case 3 -> gitHubBranch(accessToken, url);
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
    public String getHouseUrl(String proofId,String houseName,int type){
        Proof oneProof = proofService.findOneProof(proofId);
        String accessToken = oneProof.getProofPassword();
        if (houseName == null){
            return null;
        }
        String house = houseName.split("/")[1];
        String name = houseName.split("/")[0];

        //获取仓库URl
        String oneStorehouse = codeAuthorizeApi.getOneHouse(name,house,accessToken,type);
        return switch (type) {
            case 2 -> giteeHouseUrl(oneStorehouse);
            case 3 -> gitHubHouseUrl(accessToken, oneStorehouse);
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

    @Override
    public int findState() {
        String loginId = LoginContext.getLoginId();
        if (mapState.get(loginId) == null){
            return 0;
        }
        Integer integer = mapState.get(loginId);
        mapState.remove(loginId);
        return integer;
    }

    //获取登录信息
    private String giteeMessage(String accessToken){
        if (accessToken == null){
            return null;
        }
        String userMessage = codeAuthorizeApi.getUser(accessToken,2);
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(userMessage, JSONObject.class, String.class);
        JSONObject body = jsonObject.getBody();
        if (body == null) {
            return null;
        }
        return body.getString("login");

    }

    private String gitHubUserMessage(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "token"+" "+accessToken);
        String userUrl = codeAuthorizeApi.getUser(null,3);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, JSONObject.class);
        JSONObject body = response.getBody();
        if (body == null){
            return null;
        }
        return body.getString("login");

    }

    @Override
    public void updateProof(String proofId ,String name){
        Proof oneProof = proofService.findOneProof(proofId);
        oneProof.setProofName(name);
        proofService.updateProof(oneProof);
    }

    /**
     * 访问url
     * @param url 访问地址
     * @param type 请求方式
     * @return 访问结果
     * @throws IOException 转换异常
     */
    public String request(String url,String type) throws IOException {
        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod(type);
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


    /**
     * 更新授权
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void updateAuthorization() {
        logger.info("定时任务");
        List<Proof> authorizationProof = proofService.findAuthorizationProof(2);
        if (authorizationProof == null){
            return;
        }
        for (Proof proof : authorizationProof) {
            logger.info("gitee定时任务，时间:" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"更新授权信息");
            String refreshToken = codeAuthorizeApi.findRefreshToken(proof.getCallbackUrl());
            ResponseEntity<JSONObject> forEntity = restTemplate.postForEntity(refreshToken,String.class, JSONObject.class);
            JSONObject body = forEntity.getBody();
            if (body== null){
                return;
            }
            proof.setProofPassword(body.getString("access_token"));
            proof.setCallbackUrl( body.getString("refresh_token"));
            proofService.updateProof(proof);
        }
    }






}





























