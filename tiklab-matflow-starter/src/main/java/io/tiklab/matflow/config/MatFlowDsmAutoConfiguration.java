package io.tiklab.matflow.config;

import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatFlowDsmAutoConfiguration {

    /**
     * 初始化dsm
     * @return
     */
    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        //1.0.0
        dsmConfig.newVersion("1.0.0", new String[]{
                //pipeline
                "pip-pipeline_1.0.0_ddl",
                "pip-init-project_1.0.0_dml"
        });

        //1.0.1
        /*
        dsmConfig.newVersion("1.0.1", new String[]{
                //pipeline
                "pip-pipeline_1.0.1_ddl",
                "pip-init-project_1.0.1_dml"
        });
         */

        return dsmConfig;
    }


}
























