package net.tiklab.matflow;

import net.tiklab.dsm.annotation.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SQL(modules = {
        "pipeline",
        "pipeline-authority",
        "pipeline-authority-1.0.1",
        "pipeline-init-project",
        "pipeline-log-template"
},order = 101)
@ComponentScan({"net.tiklab.matflow"})
public class MatFlowServerAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowServerAutoConfiguration.class);
}
