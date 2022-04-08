package com.doublekit.pipeline.config;


import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;

import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.rpc.starter.annotation.EnableRpc;
import com.doublekit.web.starter.annotation.EnableWeb;
import com.doublekit.pipeline.annotation.EnablePipelineServer;
import org.springframework.context.annotation.Configuration;

@Configuration
//platform
@EnableBeans
@EnableWeb
@EnableDal
@EnableJoin
@EnableDfs
@EnableDcs
@EnableDss
// @DataFly(modules = {
//         "user"
// })
// @EnableMessage
//@EnableDataFly
//@EnableRpc
//pcs
// @EnableUserServer
// @EnableEamServer
// @EnableEamClient
// @EnablePrivilegeServer
// @EnableToolkitServer
//other
@EnablePipelineServer
@EnableApiboxClient
public class PipelineAutoConfiguration {
}
