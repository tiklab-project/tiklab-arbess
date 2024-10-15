package io.tiklab.arbess.starter.config;

import io.tiklab.openapi.router.Router;
import io.tiklab.openapi.router.RouterBuilder;
import io.tiklab.openapi.router.config.RouterConfig;
import io.tiklab.openapi.router.config.RouterConfigBuilder;
import io.tiklab.user.util.util.CodeUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArbessOpenApiAutoConfiguration {

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
