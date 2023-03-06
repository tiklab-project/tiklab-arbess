package net.tiklab.matflow;

import net.tiklab.dsm.annotation.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SQL(modules = {
        "pip-pipeline",
        "pip-pipeline-setting",
        "pip-pipeline-task",
        "pip-init-authority",
        "pip-init-message",
        "pip-init-project",
        "pip-init-log",
},order = 101)
@ComponentScan({"net.tiklab.matflow"})
public class MatFlowServerAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowServerAutoConfiguration.class);
}
