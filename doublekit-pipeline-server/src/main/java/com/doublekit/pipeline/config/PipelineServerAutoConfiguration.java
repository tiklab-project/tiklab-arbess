package com.doublekit.pipeline.config;


import com.doublekit.dsm.annotation.Dsm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Dsm(modules = "pipeline")
@ComponentScan({"com.doublekit.pipeline"})
public class PipelineServerAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PipelineServerAutoConfiguration.class);
}
