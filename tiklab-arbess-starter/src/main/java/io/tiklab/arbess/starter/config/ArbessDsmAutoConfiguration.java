package io.tiklab.arbess.starter.config;

import io.tiklab.arbess.support.util.task.service.TaskUpdate;
import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.model.DsmVersion;
import io.tiklab.dsm.support.DsmVersionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库脚本配置
 * @author zcamy
 */
@Configuration
public class ArbessDsmAutoConfiguration {

    @Autowired
    TaskUpdate taskUpdate;

    @Bean
    DsmConfig dsmConfig(){
        DsmConfig dsmConfig = new DsmConfig();

        dsmConfig.setVersionList(initDsmConfig());
        return dsmConfig;
    }

    /**
     * 初始化dsm
     */
    @Bean
    List<DsmVersion> initDsmConfig() {

        List<DsmVersion> versionList = new ArrayList<>();

        //1.0.0
        DsmVersion dsmVersion = DsmVersionBuilder.instance()
                .version("1.0.0")
                .db(new String[]{
                        "user_1.0.0",
                        "privilege_1.0.0",
                        "app-authorization_1.0.0",
                        "message_1.0.0",
                        "oplog_1.0.0",
                        "todotask_1.0.0",
                        "openapi_1.0.0",

                        "pip-pipeline_1.0.0",
                        "pip-init-project_1.0.0",
                        "pip-var_1.0.0",
                        "pip-test_1.0.0",
                        "pip-scan-spotbugs_1.0.0",
                        "pip-host-group_1.0.0",
                        "pip-maven-test_1.0.0",
                        "pip-setting-envorgroup_1.0.0",
                        "pip-trigger_1.0.0",
                        "pip-setting-cache_1.0.0",
                        "pip-queue_1.0.0",
                        "pip-approve_1.0.0"

                        // "ipRoll_1.0.0",
                }).get();

        versionList.add(dsmVersion);


        DsmVersion dsmVersion27 = DsmVersionBuilder.instance()
                .version("1.0.27")
                .db(new String[]{
                        "pip-project_1.0.27",
                }).get();

        versionList.add(dsmVersion27);

        DsmVersion dsmVersion28 = DsmVersionBuilder.instance()
                .version("1.0.28")
                .db(new String[]{
                        "pip-project_1.0.28",
                }).get();

        versionList.add(dsmVersion28);

        DsmVersion dsmVersion29 = DsmVersionBuilder.instance()
                .version("1.0.29")
                .db(new String[]{
                        "pip-project_1.0.29",
                }).get();

        versionList.add(dsmVersion29);

        DsmVersion dsmVersion11 = DsmVersionBuilder.instance()
                .version("1.1.1")
                .db(new String[]{
                        "pip-privilege_1.1.1",
                }).get();

        versionList.add(dsmVersion11);

        DsmVersion dsmVersion13 = DsmVersionBuilder.instance()
                .version("1.1.3")
                .db(new String[]{
                        "pip-task_1.1.3"
                })
                .task(taskUpdate)
                .get();

        versionList.add(dsmVersion13);

        DsmVersion message_109 = DsmVersionBuilder.instance()
                .version("message_1.0.9")
                .db(new String[]{
                        "message_1.0.9",
                }).get();
        versionList.add(message_109);

        DsmVersion agentRole = DsmVersionBuilder.instance()
                .version("pip-agent-role_1.0.0")
                .db(new String[]{
                        "pip-agent-role_1.0.0",
                }).get();
        versionList.add(agentRole);

        DsmVersion checkPoint = DsmVersionBuilder.instance()
                .version("pip-task-check-point_1.0.0")
                .db(new String[]{
                        "pip-task-check-point_1.0.0",
                }).get();
        versionList.add(checkPoint);

        return versionList;
    }


}
























