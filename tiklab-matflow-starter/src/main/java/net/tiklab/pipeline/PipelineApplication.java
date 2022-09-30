package net.tiklab.pipeline;

import net.tiklab.pipeline.EnablePipeline;
import net.tiklab.utils.property.PropertyAndYamlSourceFactory;
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
@EnablePipeline
public class PipelineApplication {

    public static final Logger logger = LoggerFactory.getLogger(PipelineApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(PipelineApplication.class, args);

    }
}


























