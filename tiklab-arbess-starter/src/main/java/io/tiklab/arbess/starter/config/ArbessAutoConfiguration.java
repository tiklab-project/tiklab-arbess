package io.tiklab.arbess.starter.config;

import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.install.spring.boot.starter.EnableInstallServer;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.arbess.EnableArbessServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.postgresql.spring.boot.starter.EnablePostgresql;
import io.tiklab.postin.client.EnablePostInClient;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


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
}
