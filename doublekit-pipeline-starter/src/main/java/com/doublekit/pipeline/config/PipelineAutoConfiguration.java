package com.doublekit.pipeline.config;

import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.dss.starter.EnableDss;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.pipeline.annotation.EnablePipelineServer;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

@Configuration
//platform
@EnableBeans
@EnableWeb
@EnableDal
@EnableJoin
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
// @EnableApiboxClient
public class PipelineAutoConfiguration {
}
