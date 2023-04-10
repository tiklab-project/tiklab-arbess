package io.tiklab.matflow;

import io.tiklab.dsm.model.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"io.tiklab.matflow"})
public class MatFlowServerAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowServerAutoConfiguration.class);

    @Bean
    SQL matflowInitSql() {
        logger.info("init matflow project SQL");
        return new SQL(new String[]{
                "pip-pipeline",
                "pip-setting",
                "pip-task",
                "pip-init-authority",
                "pip-init-message",
                "pip-init-project",
                "pip-init-log",
        }, 101);
    }
}
























