package com.doublekit.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * WikiApplication
 */
@SpringBootApplication
@PropertySource(value = "classpath:application-${env:prd}.properties")
@EnableScheduling
@EnablePipeline
public class PipelineApplication {

    public static final Logger logger = LoggerFactory.getLogger(PipelineApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PipelineApplication.class, args);
    }

}
