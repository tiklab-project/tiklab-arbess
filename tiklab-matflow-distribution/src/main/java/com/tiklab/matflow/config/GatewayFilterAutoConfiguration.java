package com.tiklab.matflow.config;

import com.tiklab.eam.author.Authenticator;
import com.tiklab.eam.client.author.AuthorHandler;
import com.tiklab.eam.client.author.config.IgnoreConfig;
import com.tiklab.eam.client.author.config.IgnoreConfigBuilder;
import com.tiklab.gateway.GatewayFilter;
import com.tiklab.gateway.router.RouterHandler;
import com.tiklab.gateway.router.config.RouterConfig;
import com.tiklab.gateway.router.config.RouterConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GatewayFilterAutoConfiguration.class);

    //网关filter
    @Bean
    GatewayFilter gatewayFilter(RouterHandler routerHandler, AuthorHandler authorHandler){
        return new GatewayFilter()
                .setRouterHandler(routerHandler)
                .addHandler(authorHandler);
    }

    //认证handler
    @Bean
    AuthorHandler authorHandler(Authenticator authenticator, IgnoreConfig ignoreConfig){
        return new AuthorHandler()
                .setAuthenticator(authenticator)
                .setIgnoreConfig(ignoreConfig);
    }

    @Bean
    public IgnoreConfig ignoreConfig(){
        return IgnoreConfigBuilder.instance()
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
                        ".gz"
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
                        "/user/findAllUser",
                        "/orga/findAllOrga",
                        "/userOrga/findAllUserOrga",
                        "/dingdingcfg/findId",
                        "/dingding/passport/login",
                        "/dingding/passport/logout",
                        "/dingding/passport/valid",
                        "/wechatcfg/findWechatById",
                        "/wechat/passport/login",
                        "/wechat/passport/logout",
                        "/wechat/passport/internallogin",
                        "/wechat/passport/internalacclogin",
                        "/ldap/passport/login",
                        "/ldap/passport/logout",
                        "/version/getVersion",
                        "/licence/import",
                        "/wechatCallback/instruct",
                        "/gui"
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
                        "/eas"
                })
                .get();
    }

    //路由handler
    @Bean
    RouterHandler routerHandler(RouterConfig routerConfig){
        return new RouterHandler()
                .setRouterConfig(routerConfig);
    }

    //路由转发配置
    @Value("${eas.address:null}")
    String authAddress;


    //gateway路由配置
    @Bean
    RouterConfig routerConfig(){
        return RouterConfigBuilder.instance()
                .preRoute(new String[]{
                        "/user",
                        "/eam",
                        "/message",
                }, authAddress)
                .get();
    }

}
