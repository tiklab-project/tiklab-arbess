package io.thoughtware.matflow.starter.config;

import io.thoughtware.openapi.router.Router;
import io.thoughtware.openapi.router.RouterBuilder;
import io.thoughtware.openapi.router.config.RouterConfig;
import io.thoughtware.openapi.router.config.RouterConfigBuilder;
import io.thoughtware.user.util.util.CodeUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatFlowOpenApiAutoConfiguration {

    @Autowired
    CodeUtilService codeUtilService;

    //路由
    @Bean("routerForOpenApi")
    Router router(@Qualifier("routerConfigForOpenApi") RouterConfig routerConfig){
        return RouterBuilder.newRouter(routerConfig);
    }

    //路由配置
    @Bean("routerConfigForOpenApi")
    RouterConfig routerConfig(){
        String[] s =  new String[]{};

         if (codeUtilService.findEmbedEnable()){
             s = new String[]{};
         }
        return RouterConfigBuilder.instance()
                .preRoute(s, codeUtilService.findEmbedAddress())
                .get();
    }
}
