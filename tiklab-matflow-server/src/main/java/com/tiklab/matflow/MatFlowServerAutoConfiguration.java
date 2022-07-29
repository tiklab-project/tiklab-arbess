package com.tiklab.matflow;


import com.doublekit.dsm.annotation.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SQL(modules = {"DDL-matFlow.sql","project-matflow.sql","source-matflow.sql"})
@ComponentScan({"com.tiklab.matfiow"})
public class MatFlowServerAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(MatFlowServerAutoConfiguration.class);
}
