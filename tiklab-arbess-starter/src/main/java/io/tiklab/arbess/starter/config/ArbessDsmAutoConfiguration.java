package io.tiklab.arbess.starter.config;

import io.tiklab.dsm.config.model.DsmConfig;
import io.tiklab.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库脚本配置
 * @author zcamy
 */
@Configuration
public class ArbessDsmAutoConfiguration {

    /**
     * 初始化dsm
     */
    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        //1.0.1
        dsmConfig.newVersion("1.0.1", new String[]{
                //PrivilegeDsm
                "privilege_1.0.1",
                //UserDsm
                "user_1.0.0",
                "userCe_1.0.0",
                //IntegrationDsm
                "tool_1.0.0",
                //LicenceDsm
                "app-authorization_1.0.0",
                //MessageDsm
                "message_1.0.1",
                //SecurityDsm
                "oplog_1.0.1",
                //TodoTaskDsm
                "todotask_1.0.1",
                //MatfloeDsm
                "pip-pipeline_1.0.0",
                "pip-init-project_1.0.1",
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
                "user-update_1.0.1",
                "apply-auth_1.0.1"
        });
        dsmConfig.newVersion("1.0.2", new String[]{
                "pip-project_1.0.2",
                "message_1.0.2",
                "oplog_1.0.2",
                "apply-auth_1.0.2",
                "privilege_1.0.2",
        });
        dsmConfig.newVersion("1.0.3", new String[]{
                "pip-project_1.0.3",
                "message_1.0.3",
                "oplog_1.0.3",
                "apply-auth_1.0.3",
                "privilege_1.0.3"
        });
        dsmConfig.newVersion("1.0.4", new String[]{
                "pip-project_1.0.4",
                "message_1.0.4",
                "oplog_1.0.4",
                "privilege_1.0.4",
        });
        dsmConfig.newVersion("1.0.5", new String[]{
                "pip-project_1.0.5",
                "message_1.0.5"
        });
        dsmConfig.newVersion("1.0.6", new String[]{
                "message_1.0.6",
        });
        dsmConfig.newVersion("1.0.7", new String[]{
                "pip-project_1.0.7",
                "message_1.0.7"
        });
        dsmConfig.newVersion("1.0.8", new String[]{
                "pip-project_1.0.8",
                "message_1.0.8"
        });
        dsmConfig.newVersion("1.0.9", new String[]{
                "pip-project_1.0.9"
        });
        dsmConfig.newVersion("1.0.10", new String[]{
                "pip-project_1.0.10"
        });
        dsmConfig.newVersion("1.0.11", new String[]{
                "pip-project_1.0.11"
        });
        dsmConfig.newVersion("1.0.12", new String[]{
                "pip-project_1.0.12"
        });
        dsmConfig.newVersion("1.0.13", new String[]{
                "pip-project_1.0.13"
        });
        dsmConfig.newVersion("1.0.14", new String[]{
                "pip-project_1.0.14"
        });
        dsmConfig.newVersion("1.0.15", new String[]{
                "pip-project_1.0.15"
        });
        dsmConfig.newVersion("1.0.16", new String[]{
                "pip-project_1.0.16"
        });
        dsmConfig.newVersion("1.0.17", new String[]{
                "pip-project_1.0.17"
        });
        dsmConfig.newVersion("1.0.18", new String[]{
                "pip-project_1.0.18"
        });
        dsmConfig.newVersion("1.0.19", new String[]{
                "pip-project_1.0.19"
        });
        dsmConfig.newVersion("1.0.20", new String[]{
                "pip-project_1.0.20"
        });
        dsmConfig.newVersion("1.0.21", new String[]{
                "pip-project_1.0.21"
        });
        return dsmConfig;
    }


}
























