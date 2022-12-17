package net.tiklab.matflow;

import net.tiklab.dal.starter.annotation.EnableDal;
import net.tiklab.dsm.starter.annotation.EnableDsm;
import net.tiklab.eam.starter.EnableEam;
import net.tiklab.gateway.starter.EnableGateway;
import net.tiklab.integration.starter.EnableIntegration;
import net.tiklab.licence.starter.EnableLicenceServer;
import net.tiklab.logging.stater.EnableLog;
import net.tiklab.message.starter.EnableMessage;
import net.tiklab.mysql.starter.EnableMysql;
import net.tiklab.pluginx.starter.EnablePluginServer;
import net.tiklab.privilege.stater.EnablePrivilegeServer;
import net.tiklab.rpc.starter.annotation.EnableRpc;
import net.tiklab.tks.annotation.EnableTks;
import net.tiklab.todotask.stater.EnableTodoTask;
import net.tiklab.user.starter.EnableUser;
import net.tiklab.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableTks
@EnableWeb
@EnableMysql
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
// @EnableMessageSms
//权限中心
@EnablePrivilegeServer
@EnableLicenceServer
@EnableIntegration
@EnableGateway
@EnableLog
@EnableEam
@EnableTodoTask
@EnableMatFlowServer
@ComponentScan(value = "net.tiklab.matflow")
public class MatFlowAutoConfiguration {
}
