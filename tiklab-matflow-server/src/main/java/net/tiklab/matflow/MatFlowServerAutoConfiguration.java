package net.tiklab.matflow;

import net.tiklab.dsm.annotation.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SQL(modules = {
        "pip-pipeline",
        "pip-pipeline-config",
        "pip-pipeline-other",
        "pip-pipeline-task",
        "pipeline-init-authority",
        "pipeline-init-message",
        "pipeline-init-project",
        "pipeline-init-log",
},order = 101)
@ComponentScan({"net.tiklab.matflow"})
public class MatFlowServerAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowServerAutoConfiguration.class);
}
