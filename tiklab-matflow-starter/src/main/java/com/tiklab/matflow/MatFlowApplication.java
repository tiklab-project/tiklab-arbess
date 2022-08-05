package com.tiklab.matflow;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * matflowApplication
 */
@SpringBootApplication
@PropertySource(value = "classpath:application-${env:test}.properties")
@EnableScheduling
@EnableMatFlow
public class MatFlowApplication {

    public static final Logger logger = LoggerFactory.getLogger(MatFlowApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MatFlowApplication.class, args);
    }

}
