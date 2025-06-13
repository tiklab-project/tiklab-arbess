package io.tiklab.arbess.starter.config;

import io.tiklab.postin.client.openapi.ParamConfig;
import io.tiklab.postin.client.openapi.ParamConfigBuilder;
import io.tiklab.postin.client.openapi.PostInClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class ArbessPostInClientConfiguration {

    @Bean
    PostInClientConfig postInClientConfig(ParamConfig paramConfig){
        PostInClientConfig config = new PostInClientConfig();
        config.setParamConfig(paramConfig);

        return config;
    }

    @Bean
    ParamConfig paramConfig(){
        //设置请求头，属性名称：属性描述
        HashMap<String,String> headers = new HashMap<>();
        headers.put("accessToken","openApi中添加accessToken");

        return ParamConfigBuilder.instance()
                .setScanPackage("io.tiklab.arbess") //设置扫描的包路径
                .prePath("/api")             //设置额外的前缀
                .setHeaders(headers)               //设置请求头
                .get();
    }

}