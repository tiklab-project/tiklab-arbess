package com.doublekit.pipeline.instance.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.definition.model.Gitee;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.PlatformLoggingMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Exporter
public class GiteeServiceImpl implements GiteeService{

    @Autowired
    RestTemplate restTemplate;

    Gitee gitee = new Gitee();

    private static final Logger logger = LoggerFactory.getLogger(GiteeServiceImpl.class);


    @Override
    public String getCode(){
        return  gitee.getCode();
    }

    @Override
    public String  getAccessToken(String code) throws IOException {

        logger.info("code凭证 ：" +code);
        logger.info("开始获取token。。。。。。。" );
        String url = gitee.getAccessToken(code);
        logger.info("url :  "+url);
        // Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("grant_type","authorization_code");
        // hashMap.put("code",code);
        // hashMap.put("client_id",gitee.getClient_id());
        // hashMap.put("redirect_uri",gitee.getCallback_uri());
        // hashMap.put("client_secret",gitee.getClient_secret());
        // ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url,String.class, String.class);
        // logger.info("================");
        // String body = stringResponseEntity.getBody();
        // // JSONArray objects = JSONArray.parseArray(body);
        // // for (int i = 0; i < objects.size(); i++) {
        // //     JSONObject jsonObject = objects.getJSONObject(i);
        // //     logger.info(jsonObject.toJSONString());
        // // }
        // logger.info(body);
        // return null;

        String post = request(url, "POST");
        JSONObject jsonObject = JSONObject.parseObject(post);
        String access_token = jsonObject.getString("access_token");
        gitee.setAccessToken(access_token);
        logger.info("token凭证 :  "+access_token);
        return access_token;
    }

    //列出授权用户的所有仓库
    @Override
    public List<String> getAllStorehouse() {

        ArrayList<String> strings = new ArrayList<>();
        String allStorehouseAddress = gitee.getAllStorehouse(gitee.getAccessToken());
        logger.info("token凭证 :"+gitee.getAccessToken());
        logger.info("仓库url :  "+allStorehouseAddress);
        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器StringHttpMessageConverter，并设置utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> returnBody = restTemplate.getForEntity(allStorehouseAddress, String.class, JSONObject.class);
        String body = returnBody.getBody();
        logger.info("仓库信息 ： "+body);
        JSONArray allStorehouseJson = JSONArray.parseArray(body);
        for (int i = 0; i < allStorehouseJson.size(); i++) {
            JSONObject storehouse=allStorehouseJson.getJSONObject(i);
            String human_name = storehouse.getString("human_name");
            strings.add(human_name);
            gitee.setName(storehouse.getString("project_creator"));
        }
        logger.info("登录名称 ："+gitee.getName());
        return strings;
    }

    //获取一个仓库的所有分支
    public List<String> getBranch(String projectName) {
        String[] split = projectName.split("/");
        ArrayList<String> branchList = new ArrayList<>();
        String branchAddress = gitee.getWarehouseBranch(split[1], gitee.getAccessToken());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(branchAddress, String.class, JSONObject.class);
        JSONArray branchS = JSONArray.parseArray(forEntity.getBody());
        for (int i = 0; i < branchS.size(); i++) {
            JSONObject jsonArray = branchS.getJSONObject(i);
            branchList.add(jsonArray.getString("name"));
        }
        return branchList;
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

    }
