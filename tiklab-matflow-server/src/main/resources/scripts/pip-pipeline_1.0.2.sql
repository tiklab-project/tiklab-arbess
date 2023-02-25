alter table pip_pipeline_task_product rename as pip_task_artifact;
alter table pip_pipeline_task_code rename as pip_task_code;
alter table pip_pipeline_task_code_scan rename as pip_task_code_scan;
alter table pip_pipeline_task_test rename as pip_task_test;
alter table pip_pipeline_task_build rename as pip_task_build;
alter table pip_pipeline_task_deploy rename as pip_task_deploy;
alter table pip_pipeline_task_script rename as pip_task_script;
alter table pip_pipeline_task_message_type rename as pip_task_message_type;
alter table pip_pipeline_task_message_user rename as pip_task_message_user;

alter table pip_pipeline_auth rename as pip_auth;
alter table pip_pipeline_auth_host rename as pip_auth_host;
alter table pip_pipeline_auth_server rename as pip_auth_server;

alter table pip_pipeline_setting_path rename as pip_setting_path;
alter table pip_pipeline_setting_scm rename as pip_setting_scm;

alter table pip_pipeline_post rename as pip_pipeline_postprocess;
alter table pip_pipeline_task_time rename as pip_pipeline_trigger_time;
