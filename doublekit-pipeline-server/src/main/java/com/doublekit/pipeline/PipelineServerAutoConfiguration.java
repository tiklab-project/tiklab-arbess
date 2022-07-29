package com.doublekit.pipeline;


import com.doublekit.dsm.annotation.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SQL(modules = {"pipeline","source-pipeline","pipeline-project"})
@ComponentScan({"com.doublekit.pipeline"})
public class PipelineServerAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PipelineServerAutoConfiguration.class);
}
