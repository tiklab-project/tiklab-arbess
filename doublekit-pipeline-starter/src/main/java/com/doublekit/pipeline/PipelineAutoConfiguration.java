package com.doublekit.pipeline;

import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.dsm.starter.annotation.EnableDsm;
import com.doublekit.eam.starter.EnableEam;
import com.doublekit.gateway.starter.EnableGateway;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.message.starter.EnableMessage;
import com.doublekit.privilege.EnablePrivilegeServer;
import com.doublekit.rpc.starter.annotation.EnableRpc;
import com.doublekit.user.starter.EnableUser;
import com.doublekit.web.starter.annotation.EnableWeb;
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
//用户中心
@EnableEam
@EnableUser
//消息中心
@EnableMessage
//权限中心
@EnablePrivilegeServer

@EnableGateway
@EnablePipelineServer

public class PipelineAutoConfiguration {
}
