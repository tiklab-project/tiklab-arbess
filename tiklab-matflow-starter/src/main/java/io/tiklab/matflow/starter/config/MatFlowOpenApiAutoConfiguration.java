package io.tiklab.matflow.starter.config;

import io.tiklab.openapi.router.Router;
import io.tiklab.openapi.router.RouterBuilder;
import io.tiklab.openapi.router.config.RouterConfig;
import io.tiklab.openapi.router.config.RouterConfigBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatFlowOpenApiAutoConfiguration {

    @Value("${eas.address:null}")
    String authAddress;

    @Value("${eas.embbed.enable:false}")
    Boolean enableEam;

    //路由
    @Bean("routerForOpenApi")
    Router router(@Qualifier("routerConfigForOpenApi") RouterConfig routerConfig){
        return RouterBuilder.newRouter(routerConfig);
    }

    //路由配置
    @Bean("routerConfigForOpenApi")
    RouterConfig routerConfig(){
        String[] s =  new String[]{};

         if (enableEam){
             s = new String[]{};
         }
        return RouterConfigBuilder.instance()
                .preRoute(s, authAddress)
                .get();
    }
}
