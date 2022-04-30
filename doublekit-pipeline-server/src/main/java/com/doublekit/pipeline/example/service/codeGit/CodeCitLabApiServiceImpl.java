package com.doublekit.pipeline.example.service.codeGit;

import com.doublekit.pipeline.example.model.CodeGit.CodeGitLabApi;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Exporter
public class CodeCitLabApiServiceImpl implements CodeCitLabApiService{

    @Autowired
    RestTemplate restTemplate;

    CodeGitLabApi codeGitLabApi = new CodeGitLabApi();

    private static final Logger logger = LoggerFactory.getLogger(CodeGiteeApiServiceImpl.class);


    public void code(){
        String code = codeGitLabApi.getCode();
        logger.info(code);
        ResponseEntity<String> entity = restTemplate.getForEntity(code, String.class, String.class);
        logger.info("返回值为 ： "+entity.getBody());
    }
}
