package io.tiklab.arbess.starter.config;

import io.tiklab.gateway.config.RouterConfig;
import io.tiklab.gateway.config.RouterConfigBuilder;
import io.tiklab.openapi.config.AllowConfig;
import io.tiklab.openapi.config.AllowConfigBuilder;
import io.tiklab.openapi.config.OpenApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArbessOpenApiAutoConfiguration {

    @Bean
    OpenApiConfig openApiConfig(AllowConfig allowConfig){
        OpenApiConfig openApiConfig = new OpenApiConfig();
        openApiConfig.setAllowConfig(allowConfig);

        return openApiConfig;
    }

    //开放许可配置
    @Bean
    AllowConfig allowConfig(){
        String[] s =  new String[]{
                "/pipeline/findAllPipeline",
                "/pipeline/createPipeline",
                "/pipeline/deletePipeline",
                "/pipeline/findOnePipeline",
                "/pipeline/findPipelineNoQuery",
                "/pipeline/findUserPipelinePage",
                "/pipeline/findPipelineUser",
                "/pipeline/findUserPipeline",
        };
        return AllowConfigBuilder.instance()
                .allowUrls(s)
                .get();
    }


}
