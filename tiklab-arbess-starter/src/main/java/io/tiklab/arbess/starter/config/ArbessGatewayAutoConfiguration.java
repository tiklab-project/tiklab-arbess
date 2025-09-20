package io.tiklab.arbess.starter.config;

import io.tiklab.gateway.config.GatewayConfig;
import io.tiklab.gateway.config.IgnoreConfig;
import io.tiklab.gateway.config.IgnoreConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArbessGatewayAutoConfiguration {

    @Bean
    GatewayConfig gatewayConfig(IgnoreConfig ignoreConfig){
        GatewayConfig gatewayConfig = new GatewayConfig();
        gatewayConfig.setIgnoreConfig(ignoreConfig);

        return gatewayConfig;
    }

    @Bean
    public IgnoreConfig authorConfig(){
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
                        "svg",
                        "docx",
                        "xlsx"
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
                        "/message/messageItem/syncUpdateMessage",
                        "/message/messageItem/syncDeleteMessage",
                        "/arbess/handler",
                        "/other/handler",
                        "/eam/auth/valid",
                        "/init/install/findStatus",
                        "/openapi/doc",
                        "/scm/file/upload",
                        "/instance/artifact/download"
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
                        "/update/pipeline",
                        "/state/apply/findApply",
                        "/pipeline/webhook/",
                        "/instance/artifact/download/"
                })
                .get();
    }
}
