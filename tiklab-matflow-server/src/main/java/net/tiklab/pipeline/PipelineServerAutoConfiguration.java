package net.tiklab.pipeline;

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
@ComponentScan({"net.tiklab.pipeline"})
public class PipelineServerAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PipelineServerAutoConfiguration.class);
}
