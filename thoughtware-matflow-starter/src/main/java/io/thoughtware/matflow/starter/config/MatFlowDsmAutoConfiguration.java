package io.thoughtware.matflow.starter.config;

import io.thoughtware.dsm.config.model.DsmConfig;
import io.thoughtware.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库脚本配置
 * @author zcamy
 */
@Configuration
public class MatFlowDsmAutoConfiguration {

    /**
     * 初始化dsm
     */
    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        //1.0.0
        dsmConfig.newVersion("1.0.0", new String[]{
                //PrivilegeDsm
                "privilege_1.0.0",
                //UserDsm
                "user_1.0.0",
                "userCe_1.0.0",
                //IntegrationDsm
                "tool_1.0.0",
                //LicenceDsm
                "app-authorization_1.0.0",
                //MessageDsm
                "message_1.0.0",
                //SecurityDsm
                "oplog_1.0.0",
                //TodoTaskDsm
                "todotask_1.0.0",
                //MatfloeDsm
                "pip-pipeline_1.0.0",
                "pip-init-project_1.0.0",
                "pip-var_1.0.0",
                "backups_1.0.0",
                "pip-test_1.0.0",
                "pip-scan-spotbugs_1.0.0",
                "pip-host-group_1.0.0",
                "pip-maven-test_1.0.0",
                "pip-setting-envorgroup_1.0.0",
                "pip-trigger_1.0.0",
                "pip-setting-cache_1.0.0"
        });
        dsmConfig.newVersion("1.0.1", new String[]{
                "pip-project_1.0.1",
                "privilege_1.0.1",
                "pip-init-project_1.0.1",
                "oplog_1.0.1",
                "message_1.0.1",
                "todotask_1.0.1",
                "user-update_1.0.1"
        });
        dsmConfig.newVersion("1.0.2", new String[]{
                "pip-project_1.0.2",
                "message_1.0.2",
                "oplog_1.0.2"
        });
        dsmConfig.newVersion("1.0.3", new String[]{
                "pip-project_1.0.3",
                "message_1.0.3",
                "oplog_1.0.3"
        });
        dsmConfig.newVersion("1.0.4", new String[]{
                "pip-project_1.0.4",
                "message_1.0.4",
                "oplog_1.0.4"
        });
        dsmConfig.newVersion("1.0.5", new String[]{
                "pip-project_1.0.5",
                "message_1.0.5"
        });
        dsmConfig.newVersion("1.0.6", new String[]{
                "message_1.0.6",
        });
        dsmConfig.newVersion("1.0.7", new String[]{
                "pip-project_1.0.7"
        });
        dsmConfig.newVersion("1.0.8", new String[]{
                "pip-project_1.0.8"
        });
        dsmConfig.newVersion("1.0.9", new String[]{
                "pip-project_1.0.9"
        });
        dsmConfig.newVersion("1.0.10", new String[]{
                "pip-project_1.0.10"
        });
        return dsmConfig;
    }


}
























