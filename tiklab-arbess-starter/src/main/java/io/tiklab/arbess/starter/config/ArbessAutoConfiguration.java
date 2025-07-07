package io.tiklab.arbess.starter.config;

import io.tiklab.arbess.EnableArbessServer;
import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.install.spring.boot.starter.EnableInstallServer;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.postgresql.spring.boot.starter.EnablePostgresql;
import io.tiklab.postin.client.EnablePostInClient;
import io.tiklab.postin.client.openapi.ParamConfig;
import io.tiklab.postin.client.openapi.ParamConfigBuilder;
import io.tiklab.postin.client.openapi.PostInClientConfig;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.util.HashMap;


/**
 * @author admin
 */

@Configuration
@EnableToolkit
//内嵌数据库
@EnablePostgresql
@EnableInstallServer
@EnableDal
// @EnableRpc
@EnableGateway
@EnableOpenApi
@EnableDcsClient
@EnableDcsServer
@EnableDsm
@EnableRpc
//用户中心
@EnableUserServer
@EnableUserClient

//登录,认证
@EnableEamClient
@EnableEamServer

//消息,日志,待办
@EnableMessageServer
@EnableSecurityServer
//权限中心
@EnablePrivilegeServer
@EnableLicenceServer

// postin
@EnablePostInClient

@EnableArbessServer
@ComponentScan(value = "io.tiklab.arbess")
public class ArbessAutoConfiguration {

    // @Autowired
    // MessageTest messageTest;
    //
    // @Value("${app.name}")
    // private String appName;
    //
    // @Bean
    // public MessageSubscribeConfig messageSubscribeConfig() {
    //     String[] msyTypes = { "USER_CREATE",
    //             "USER_UPDATE",
    //             "USER_DELETE",
    //             "USER_LOGIN"
    //     };
    //     return MessageSubscribeConfig.instance()
    //             .subscribe(msyTypes, "http://192.168.10.15:8080")
    //             .service(messageTest)
    //             .consumer(CodeFinal.findAppId(appName))
    //             .get();
    // }

    @Bean
    public MultipartConfigElement multipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小5GB
        factory.setMaxFileSize(DataSize.ofGigabytes(5L));
        //设置总上传数据大小5GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(5L));

        return factory.createMultipartConfig();
    }

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




