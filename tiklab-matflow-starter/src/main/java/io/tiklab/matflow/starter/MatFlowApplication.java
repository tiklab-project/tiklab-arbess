package io.tiklab.matflow.starter;

import io.tiklab.core.property.PropertyAndYamlSourceFactory;
import io.tiklab.matflow.starter.annotation.MatFlowPipeline;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * pipelineApplication
 */

@SpringBootApplication
@PropertySource(value = {"classpath:application.yaml"}, factory = PropertyAndYamlSourceFactory.class)
@EnableScheduling
@MatFlowPipeline
public class MatFlowApplication {

    public static final Logger logger = LoggerFactory.getLogger(MatFlowApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MatFlowApplication.class, args);
    }
}


























