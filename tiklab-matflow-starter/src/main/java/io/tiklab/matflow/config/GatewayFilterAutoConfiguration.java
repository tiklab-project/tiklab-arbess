package io.tiklab.matflow.config;


import io.tiklab.eam.author.Authenticator;
import io.tiklab.eam.client.author.AuthorHandler;
import io.tiklab.eam.client.author.config.IgnoreConfig;
import io.tiklab.eam.client.author.config.IgnoreConfigBuilder;
import io.tiklab.gateway.GatewayFilter;
import io.tiklab.gateway.router.RouterHandler;
import io.tiklab.gateway.router.config.RouterConfig;
import io.tiklab.gateway.router.config.RouterConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterAutoConfiguration {

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

    @Value("${eas.embbed.enable:false}")
    Boolean enableEam;

    //gateway路由配置
    @Bean
    RouterConfig routerConfig(){
         String[] s = {
                 "/user",
                 "/eam",
                 "/appLink",
                 "/todo/deletetodo",
                 "/todo/updatetodo",
                 "/todo/detailtodo",
                 "/todo/findtodopage",
                 "/message/message",
                 "/message/messageItem",
                 "/message/messageReceiver",
                 "/oplog/deletelog",
                 "/oplog/updatelog",
                 "/oplog/detaillog",
                 "/oplog/findlogpage",
         };

        if (enableEam){
            s = new String[]{};
        }

        return RouterConfigBuilder.instance()
                .preRoute(s, authAddress)
                .get();
    }

}
