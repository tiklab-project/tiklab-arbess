package com.doublekit.pipeline.config;


import com.doublekit.datafly.annotation.DataFly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DataFly(modules = "wiki")
@ComponentScan({"com.doublekit.wiki"})
public class PipelineServerAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PipelineServerAutoConfiguration.class);
}
