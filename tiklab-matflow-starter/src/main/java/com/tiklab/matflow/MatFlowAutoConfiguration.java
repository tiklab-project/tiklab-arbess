package com.tiklab.matflow;

import   com.tiklab.beans.starter.annotation.EnableBeans;
import com.tiklab.dal.starter.annotation.EnableDal;
import com.tiklab.dsm.starter.annotation.EnableDsm;
import com.tiklab.eam.starter.EnableEam;
import com.tiklab.gateway.starter.EnableGateway;
import com.tiklab.join.starter.annotation.EnableJoin;
import com.tiklab.licence.starter.EnableLicenceServer;
import com.tiklab.message.starter.EnableMessage;
import com.tiklab.pluginx.starter.EnablePluginServer;
import com.tiklab.privilege.stater.EnablePrivilegeServer;
import com.tiklab.rpc.starter.annotation.EnableRpc;
import com.tiklab.user.starter.EnableUser;
import com.tiklab.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBeans
@EnableJoin
@EnableWeb
@EnableDal
//rpc
@EnableRpc
//数据库脚本
@EnableDsm

@EnablePluginServer
//用户中心
@EnableEam
@EnableUser
//消息中心
@EnableMessage
//权限中心
@EnablePrivilegeServer
@EnableLicenceServer

@EnableGateway
@EnableMatFlowServer
@ComponentScan("com.tiklab.matflow")
public class MatFlowAutoConfiguration {
}
