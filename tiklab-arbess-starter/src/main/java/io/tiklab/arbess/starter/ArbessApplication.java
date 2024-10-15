package io.tiklab.arbess.starter;

import io.tiklab.arbess.starter.annotation.EnableArbess;
import io.tiklab.core.property.PropertyAndYamlSourceFactory;
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
@EnableArbess
public class ArbessApplication {

    public static final Logger logger = LoggerFactory.getLogger(ArbessApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ArbessApplication.class, args);
    }
}


























