package com.doublekit.pipeline.example.service.codeGit;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doublekit.pipeline.example.model.CodeGit.CodeGiteeApi;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
@Exporter
public class CodeGiteeApiServiceImpl implements CodeGiteeApiService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    ProofService proofService;

    CodeGiteeApi codeGiteeApi = new CodeGiteeApi();

    private static final Logger logger = LoggerFactory.getLogger(CodeGiteeApiServiceImpl.class);


    @Override
    public String getCode(){
        return  codeGiteeApi.getCode();
    }

    @Override
    public String  getAccessToken(String code) throws IOException {
        logger.info("获取code凭证 ：" +code);
        String url = codeGiteeApi.getAccessToken(code);
        logger.info("获取token地址 ：" + url );
        String post = request(url, "POST");
        if (post != null){
            JSONObject jsonObject = JSONObject.parseObject(post);
            String access_token = jsonObject.getString("access_token");
            String refresh_token = jsonObject.getString("refresh_token");
            codeGiteeApi.setAccessToken(access_token);
            codeGiteeApi.setRefreshToken(refresh_token);
            logger.info("Gitee的token值 : "+ access_token);
            return access_token;
        }
        return null;
    }

    //获取登录信息
    @Override
    public String getUserMessage(){
        if (codeGiteeApi.getAccessToken() != null){
            String userMessage = codeGiteeApi.getUserMessage();
            ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(userMessage, JSONObject.class, String.class);
            JSONObject body = jsonObject.getBody();
            if (body != null) {
                codeGiteeApi.setName(body.getString("login"));
                return body.getString("login");
            }
        }
        return null;
    }

    //凭证信息
    @Override
    public String getProof(String proofName) {
        Proof proof = new Proof();
        String accessToken = codeGiteeApi.getAccessToken();
         if (accessToken != null){
             proof.setProofName(proofName);
             proof.setProofPassword(accessToken);
             proof.setProofUsername(getUserMessage());
             proof.setProofType("gitee");
             proof.setProofScope(3);
             proof.setProofDescribe("gitee授权登录");
             return proofService.createProof(proof);
         }
        return  null;
    }

    //列出授权用户的所有仓库
    @Override
    public List<String> getAllStorehouse() {
        ArrayList<String> strings = new ArrayList<>();
        String allStorehouseAddress = codeGiteeApi.getAllStorehouse(codeGiteeApi.getAccessToken());
        logger.info("token凭证 :"+ codeGiteeApi.getAccessToken());
        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器StringHttpMessageConverter，并设置utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> returnBody = restTemplate.getForEntity(allStorehouseAddress, String.class, JSONObject.class);
        String body = returnBody.getBody();
        if (body != null){
            logger.info("仓库信息 ： "+body);
            JSONArray allStorehouseJson = JSONArray.parseArray(body);
            for (int i = 0; i < allStorehouseJson.size(); i++) {
                JSONObject storehouse=allStorehouseJson.getJSONObject(i);
                strings.add(storehouse.getString("human_name"));
            }
        }

        return strings;
    }

    //获取一个仓库的所有分支
    @Override
    public List<String> getBranch(String projectName) {
        logger.info("projectName:"+projectName);
        String[] split = projectName.split("/");
        String name = split[1];
        if (split.length != 2){
            return null;
        }
        ArrayList<String> branchList = new ArrayList<>();
        logger.info("name : "+name);
        String branchAddress = codeGiteeApi.getWarehouseBranch(name);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(branchAddress, String.class, JSONObject.class);
        JSONArray branchS = JSONArray.parseArray(forEntity.getBody());
        if (branchS != null) {
            for (int i = 0; i < branchS.size(); i++) {
                JSONObject jsonArray = branchS.getJSONObject(i);
                branchList.add(jsonArray.getString("name"));
            }
        }
        return branchList;
    }

    //获取仓库的url
    @Override
    public String getCloneUrl(String projectName){
        if (projectName != null){
            String[] split = projectName.split("/");
            if (split.length == 2){
                String name = split[1];
                //获取仓库URl
                String oneStorehouse = codeGiteeApi.getOneStorehouse(name);
                ResponseEntity<String> forEntity1 = restTemplate.getForEntity(oneStorehouse, String.class, JSONObject.class);
                JSONObject jsonObject = JSONObject.parseObject(forEntity1.getBody());
                return jsonObject.getString("html_url");
            }
        }

        return null;
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

    //定时任务
    @Scheduled(cron = "0 0 0/23 1/1 * ? ")
    public void time() {
        String token = codeGiteeApi.getToken();
        ResponseEntity<JSONObject> postForEntity = restTemplate.postForEntity(token, String.class, JSONObject.class);
        JSONObject body = postForEntity.getBody();
        if (body != null){
            String access_token = body.getString("access_token");
            String refresh_token = body.getString("refresh_token");
            codeGiteeApi.setAccessToken(access_token);
            codeGiteeApi.setRefreshToken(refresh_token);
        }
    }
}
