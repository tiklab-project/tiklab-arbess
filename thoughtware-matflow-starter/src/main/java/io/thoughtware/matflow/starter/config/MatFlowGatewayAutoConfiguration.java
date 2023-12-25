package io.thoughtware.matflow.starter.config;

import io.thoughtware.eam.author.Authenticator;
import io.thoughtware.eam.client.author.config.AuthorConfig;
import io.thoughtware.eam.client.author.config.AuthorConfigBuilder;
import io.thoughtware.eam.client.author.handler.AuthorHandler;
import io.thoughtware.gateway.router.Router;
import io.thoughtware.gateway.router.RouterBuilder;
import io.thoughtware.gateway.router.config.RouterConfig;
import io.thoughtware.gateway.router.config.RouterConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatFlowGatewayAutoConfiguration {

    @Value("${darth.address:null}")
    String authAddress;

    @Value("${darth.embbed.enable:false}")
    Boolean enableEam;

    //路由
    @Bean
    Router router(RouterConfig routerConfig){
        return RouterBuilder.newRouter(routerConfig);
    }

    //路由配置
    @Bean
    RouterConfig routerConfig(){
        String[] s =  new String[]{};

         if (enableEam){
             s = new String[]{};
         }
        return RouterConfigBuilder.instance()
                .preRoute(s, authAddress)
                .get();
    }

    //认证filter
    @Bean
    AuthorHandler authorFilter(Authenticator authenticator, AuthorConfig ignoreConfig){
        return new AuthorHandler()
                .setAuthenticator(authenticator)
                .setAuthorConfig(ignoreConfig);
    }

    @Bean
    public AuthorConfig authorConfig(){
        return AuthorConfigBuilder.instance()
                .ignoreTypes(new String[]{
                        ".ico",
                        ".jpg",
                        ".jpeg",
                        ".png",
                        ".gif",
                        ".html",
                        ".js",
                        ".css",
                        ".json",
                        ".xml",
                        ".ftl",
                        ".map",
                        ".gz",
                        "svg"
                })
                .ignoreUrls(new String[]{
                        "/",
                        "/eam/auth/login",
                        "/eam/auth/logout",
                        "/eam/auth/valid",
                        "/auth/valid",
                        "/document/view",
                        "/comment/view",
                        "/share/verifyAuthCode",
                        "/share/judgeAuthCode",
                        "/user/user/findAllUser",
                        "/user/orga/findAllOrga",
                        "/userOrga/findAllUserOrga",
                        "/dingding/passport/login",
                        "/user/dingdingcfg/findId",
                        "/dingding/passport/logout",
                        "/dingding/passport/valid",
                        "/user/wechatcfg/findWechatById",
                        "/wechat/passport/login",
                        "/wechat/passport/logout",
                        "/wechat/passport/internallogin",
                        "/wechat/passport/internalacclogin",
                        "/ldap/passport/login",
                        "/ldap/passport/logout",
                        "/version/getVersion",
                        "/licence/import",
                        "/wechatCallback/instruct",
                        "/gui",
                        "/disk/findDiskList",
                        "/appAuthorization/validUserInProduct",
                        "/clean/data/cleanMessageData",
                })
                .ignorePreUrls(new String[]{
                        "/service",
                        "/apis/list",
                        "/apis/detail",
                        "/file",
                        "/plugin",
                        "/authConfig",
                        "/ws",
                        "/socket",
                        "/start",
                        "/eas",
                        "/sql",
                        "/maven/test",
                })
                .get();
    }
}
