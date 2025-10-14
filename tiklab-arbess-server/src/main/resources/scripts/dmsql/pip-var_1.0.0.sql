-- 变量类型
alter table pip_pipeline_variable rename column task_type to var_type;

ALTER TABLE pip_pipeline_variable MODIFY var_type varchar(32);

-- 后置处理


alter table pip_postprocess rename column postprocess_id to post_id;
alter table pip_postprocess rename column name to post_name;

-- 执行脚本
ALTER TABLE pip_task_script MODIFY type varchar(32);
--alter table pip_task_script alter column type type VARCHAR(32);

-- 流水线
ALTER TABLE pip_pipeline DROP COLUMN IF EXISTS yaml;

-- 构建
ALTER TABLE pip_task_build ADD COLUMN docker_name VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN docker_version VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN docker_file VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN docker_order VARCHAR(600);


-- 测试
ALTER TABLE pip_task_test MODIFY test_order text;

-- 推送制品
ALTER TABLE pip_task_artifact ADD COLUMN artifact_type VARCHAR(32);
ALTER TABLE pip_task_artifact ADD COLUMN rule VARCHAR(64);
ALTER TABLE pip_task_artifact ADD COLUMN docker_image VARCHAR(64);

-- 部署
ALTER TABLE pip_task_deploy ADD COLUMN rule VARCHAR(64);
ALTER TABLE pip_task_deploy ADD COLUMN docker_image VARCHAR(64);



create table pip_task_pull_artifact  (
    task_id varchar(64) PRIMARY KEY,
    pull_type varchar(32) ,
    docker_image varchar(32) ,
    remote_address varchar(64) ,
    local_address varchar(255) ,
    auth_id varchar(255),
    group_id varchar(255),
    version varchar(255),
    artifact_id varchar(255),
    xpack_id varchar(255),
    transitive varchar(255)
);