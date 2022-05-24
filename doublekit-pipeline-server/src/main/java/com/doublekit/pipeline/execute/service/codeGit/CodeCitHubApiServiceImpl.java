package com.doublekit.pipeline.execute.service.codeGit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doublekit.pipeline.execute.model.CodeGit.CodeGitHubApi;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Exporter
public class CodeCitHubApiServiceImpl implements CodeCitLabApiService{

    @Autowired
    RestTemplate restTemplate;

    CodeGitHubApi codeGitHubApi = new CodeGitHubApi();

    private static final Logger logger = LoggerFactory.getLogger(CodeGiteeApiServiceImpl.class);

    // https://github.com/login/oauth/authorize?client_id=cf93e472f1ffe9521474&scope=repo admin:org_hook admin:repo_hook user
    @Override
    public String getCode(){
        return codeGitHubApi.getCode();
    }

    @Override
    public String getAccessToken(String code){
        if (code == null){
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String accessTokenUrl = codeGitHubApi.getAccessToken(code);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id",codeGitHubApi.getClient_ID());
        paramMap.add("client_secret",codeGitHubApi.getClient_Secret());
        paramMap.add("code",code);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, JSONObject.class);
        JSONObject body = response.getBody();
        if (body != null){
            return body.getString("access_token");
        }
        return null;

    }

    @Override
    public String getUserMessage(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "token"+" "+accessToken);
        String userUrl = codeGitHubApi.getUser();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, JSONObject.class);
        logger.info("数据为 ： "+response.getBody());
        return response.toString();

    }

    @Override
    public  Map<String, String> getAllStorehouse(String accessToken){
        Map<String, String> map = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        //headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.set("Authorization", "token"+" "+accessToken);
        String userUrl = codeGitHubApi.getAllStorehouse();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> forEntity = restTemplate.exchange(userUrl, HttpMethod.GET, entity, String.class);
        String body = forEntity.getBody();
        logger.info("仓库信息 ："+body);
        if (body != null){
            JSONArray allStorehouseJson = JSONArray.parseArray(body);
            for (int i = 0; i < allStorehouseJson.size(); i++) {
                JSONObject storehouse=allStorehouseJson.getJSONObject(i);
                map.put(storehouse.getString("full_name"),storehouse.getString("url"));
            }
        }
        return map;
    }

    @Override
    public List<String> getBranch(String accessToken,String name){
        List<String> list = new ArrayList<>();
        if (name!= null){
            String[] split = name.split("/");
            String branchUrl = codeGitHubApi.getBranch(split[0], split[1]);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/vnd.github.v3+json");
            headers.set("Authorization", "token"+" "+accessToken);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(branchUrl, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            logger.info("凭证信息 ："+body);
            if (body != null){
                JSONArray allBranch = JSONArray.parseArray(body);
                for (int i = 0; i < allBranch.size(); i++) {
                    JSONObject branch = allBranch.getJSONObject(i);
                    list.add(branch.getString("name"));
                }
                return list;
            }
        }
        return  null;
    }

}
