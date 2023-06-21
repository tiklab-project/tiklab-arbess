package io.tiklab.matflow;

import io.tiklab.dal.starter.annotation.EnableDal;
import io.tiklab.dsm.starter.annotation.EnableDsm;
import io.tiklab.eam.starter.EnableEam;
import io.tiklab.gateway.starter.EnableGateway;
import io.tiklab.integration.starter.EnableIntegration;
import io.tiklab.join.starter.EnableToolkit;
import io.tiklab.licence.starter.EnableLicenceServer;
import io.tiklab.messsage.starter.EnableMessage;
import io.tiklab.pluginx.starter.EnablePluginServer;
import io.tiklab.postgresql.EnablePostgresql;
import io.tiklab.privilege.EnablePrivilegeServer;
import io.tiklab.rpc.starter.annotation.EnableRpc;
import io.tiklab.security.stater.EnableSecurity;
import io.tiklab.todotask.stater.EnableTodoTask;
import io.tiklab.user.starter.EnableUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableToolkit
@EnablePostgresql
@EnableDal
//rpc
@EnableRpc
//数据库脚本
@EnableDsm
@EnablePluginServer
//用户中心
@EnableUser
//消息中心
@EnableMessage
//权限中心
@EnableLicenceServer
@EnableIntegration
@EnableGateway
@EnableSecurity
@EnableEam
@EnableTodoTask
@EnablePrivilegeServer
@EnableMatFlowServer
@ComponentScan(value = "io.tiklab.matflow")
public class MatFlowAutoConfiguration {
}
