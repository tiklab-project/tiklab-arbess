package io.thoughtware.arbess.starter.config;

import io.thoughtware.dal.boot.starter.annotation.EnableDal;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsClient;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsServer;
import io.thoughtware.dsm.boot.starter.annotation.EnableDsm;
import io.thoughtware.eam.boot.starter.annotation.EnableEamClient;
import io.thoughtware.eam.boot.starter.annotation.EnableEamServer;
import io.thoughtware.gateway.boot.starter.annotation.EnableGateway;
import io.thoughtware.licence.boot.starter.annotation.EnableLicenceServer;
import io.thoughtware.arbess.EnableArbessServer;
import io.thoughtware.messsage.boot.starter.annotation.EnableMessageServer;
import io.thoughtware.openapi.boot.starter.annotation.EnableOpenApi;
import io.thoughtware.postgresql.EnablePostgresql;
import io.thoughtware.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.thoughtware.security.boot.stater.annotation.EnableSecurityServer;
import io.thoughtware.toolkit.boot.starter.annotation.EnableToolkit;
import io.thoughtware.user.boot.starter.annotation.EnableUserClient;
import io.thoughtware.user.boot.starter.annotation.EnableUserServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author admin
 */

@Configuration
@EnableToolkit
//内嵌数据库
@EnablePostgresql
@EnableDal
// @EnableRpc
@EnableGateway
@EnableOpenApi
@EnableDcsClient
@EnableDcsServer
@EnableDsm
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

@EnableArbessServer
@ComponentScan(value = "io.thoughtware.matflow")
public class ArbessAutoConfiguration {
}
