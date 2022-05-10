package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.execute.model.CodeGit.CodeGitHubApi;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

@Service
@Exporter
public class CodeCitHubApiServiceImpl implements CodeCitLabApiService{

    @Autowired
    RestTemplate restTemplate;

    CodeGitHubApi codeGitHubApi = new CodeGitHubApi();

    private static final Logger logger = LoggerFactory.getLogger(CodeGiteeApiServiceImpl.class);

    // https://github.com/login/oauth/authorize?client_id=cf93e472f1ffe9521474&scope=repo admin:org_hook admin:repo_hook user
    public String code(){
        return codeGitHubApi.getCode();
    }

    public String accessToken(String code){
        if (code == null){
            return null;
        }
        String accessTokenUrl = codeGitHubApi.getAccessToken(code);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id",codeGitHubApi.getClient_ID());
        paramMap.add("client_secret",codeGitHubApi.getClient_Secret());
        paramMap.add("code",code);

        ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUrl, paramMap, String.class);
        String[] split = Objects.requireNonNull(response.getBody()).split("=");
        logger.info("\naccessToken : "+split[0]);
        String[] split1 = split[1].split("&");
        logger.info("\naccessToken : "+split1[0]);
        //return response;
        return split1[0];
    }

    public String codeSpace(String accessToken){
        String codeSpace = codeGitHubApi.getCodeSpace();
        HttpEntity<String> stringHttpEntity = httpEntity(accessToken);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(codeSpace, String.class,stringHttpEntity);
        logger.info("Body : "+forEntity);
        return forEntity.getBody();
    }

    public HttpEntity<String> httpEntity(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        //access_token:
        headers.setAccept(Collections.singletonList(MediaType.parseMediaType("application/vnd.github.v3"+accessToken)));
        return new HttpEntity<>("body", headers);
    }

}
