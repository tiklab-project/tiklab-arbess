package io.tiklab.matflow.starter.config;

import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.matflow.EnableMatFlowServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageClient;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.plugin.starter.EnablePluginServer;
import io.tiklab.postgresql.EnablePostgresql;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityClient;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.todotask.boot.stater.annotation.EnableTodoTaskClient;
import io.tiklab.todotask.boot.stater.annotation.EnableTodoTaskServer;
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
@EnableDal
@EnableRpc
@EnableGateway
@EnableDcsClient
@EnableDcsServer
@EnablePluginServer

//用户中心
@EnableUserServer
@EnableUserClient

//消息,日志,待办
@EnableMessageServer
@EnableMessageClient
@EnableSecurityServer
@EnableSecurityClient
@EnableTodoTaskServer
@EnableTodoTaskClient

//登录,认证
@EnableEamClient
@EnableEamServer

//权限中心
@EnablePrivilegeServer
@EnableLicenceServer

@EnableMatFlowServer
@ComponentScan(value = "io.tiklab.matflow")
public class MatFlowAutoConfiguration {
}
