package com.doublekit.pipeline;

import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

@Configuration
//platform
@EnableBeans
@EnableJoin
@EnableWeb
@EnableDal
//@EnableDss
//@EnableDsm
//@EnableRpc
//@EnableMessage
//pcs
//@EnablePortalServer
//@EnablePortalClient
//@EnablePrivilegeServer
//@EnableToolkitServer
//other
@EnablePipelineServer
//@EnableApiboxClient
public class PipelineAutoConfiguration {
}
