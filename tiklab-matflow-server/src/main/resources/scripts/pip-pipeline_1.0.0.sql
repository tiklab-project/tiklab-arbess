-- ---------------------------
-- 流水线表
-- ----------------------------
create table IF NOT EXISTS pip_pipeline  (
   id varchar(256) PRIMARY KEY ,
   name varchar(256)  ,
   user_id varchar(256) ,
   create_time timestamp ,
   type int  ,
   state int  ,
   power int  ,
   color int
);

-- ---------------------------
-- 流水线实例表
-- ----------------------------
create table IF NOT EXISTS pip_pipeline_instance  (
    instance_id varchar(255)  PRIMARY KEY,
    create_time timestamp  ,
    run_way int  ,
    run_status varchar(255) ,
    run_time int  ,
    user_id varchar(255)  ,
    pipeline_id varchar(255)  ,
    find_number int
);

-- ---------------------------
-- 任务实例表
-- ----------------------------
create table IF NOT EXISTS pip_task_instance  (
    id varchar(256)  PRIMARY KEY,
    instance_id varchar(256) ,
    task_sort int  ,
    task_type int  ,
    task_name varchar(255)  ,
    run_state varchar(255) ,
    run_time int ,
    log_address TEXT,
    stages_id varchar(256),
    postprocess_id varchar(256)
);

-- ---------------------------
-- 后置
-- ----------------------------
create table IF NOT EXISTS pip_postprocess  (
    postprocess_id varchar(255) PRIMARY KEY ,
    task_id varchar(255) ,
    name varchar(32) ,
    task_type int ,
    create_time varchar(255) ,
    task_sort varchar(255) ,
    pipeline_id varchar(255)
);


create table IF NOT EXISTS pip_condition  (
    cond_id varchar(256) PRIMARY KEY ,
    cond_name varchar(256) ,
    task_id varchar(256) ,
    create_time timestamp   ,
    cond_type int  ,
    cond_key varchar(256)  ,
    cond_value varchar(256)
);

-- ---------------------------
-- 流程设计
-- ----------------------------
create table IF NOT EXISTS pip_task  (
    task_id varchar(255) PRIMARY KEY ,
    create_time timestamp  ,
    stage_id varchar(255) ,
    task_name varchar(255) ,
    pipeline_id varchar(255) ,
    task_type int ,
    task_sort int ,
    postprocess_id varchar(255)
);

-- ---------------------------
-- 阶段
-- ----------------------------
create table IF NOT EXISTS pip_stage  (
    stage_id varchar(255) PRIMARY KEY,
    create_time timestamp  ,
    stage_name varchar(255) ,
    pipeline_id varchar(255) ,
    stage_sort int ,
    parent_id varchar(255) ,
    code varchar(255)
);

-- ---------------------------
-- 阶段实例
-- ----------------------------
create table IF NOT EXISTS pip_stage_instance  (
    id varchar(255) PRIMARY KEY ,
    stage_name varchar(255),
    stage_sort int ,
    stage_time int ,
    stage_address varchar(255) ,
    stage_state varchar(255) ,
    parent_id varchar(255),
    instance_id varchar(255)
);

-- ----------------------------
-- 触发器
-- ----------------------------
create table IF NOT EXISTS pip_trigger  (
    trigger_id varchar(255) PRIMARY KEY ,
    name varchar(32) ,
    task_type int  ,
    create_time varchar(255) ,
    task_sort varchar(255) ,
    pipeline_id varchar(255)
);


-- ----------------------------
-- 触发器时间
-- ----------------------------
create table IF NOT EXISTS pip_trigger_time  (
    time_id varchar(255)  PRIMARY KEY ,
    task_type int ,
    date varchar(255),
    cron varchar(255) ,
    trigger_id varchar(255)
);

-- ----------------------------
-- 变量
-- ----------------------------
create table IF NOT EXISTS pip_pipeline_variable  (
    var_id varchar(255)  PRIMARY KEY,
    task_type int ,
    create_time varchar(255) ,
    var_key varchar(255),
    var_value varchar(255) ,
    task_id varchar(255) ,
    type int ,
    var_values varchar(255) ,
    pipeline_id varchar(255)
);

-- ----------------------------
-- 收藏
-- ----------------------------
create table IF NOT EXISTS pip_other_follow  (
    id varchar(255) PRIMARY KEY,
    user_id varchar(255) ,
    pipeline_id varchar(255)
);

-- ----------------------------
-- 最近打开
-- ----------------------------
create table IF NOT EXISTS pip_other_open  (
    open_id varchar(255) PRIMARY KEY,
    pipeline_id varchar(255) ,
    number int ,
    user_id varchar(255) ,
    create_time varchar(255)
);

-- ---------------------------
-- 流水线后置任务实例表
-- ----------------------------
create table pip_postprocess_instance  (
    id varchar(256) PRIMARY KEY ,
    instance_id varchar(256) ,
    taskInstance_id varchar(256)  ,
    post_address varchar(256) ,
    post_time int  ,
    post_state varchar(256)
);


-- ----------------------------
-- teston关联表
-- ----------------------------
create table IF NOT EXISTS pip_task_relevance_teston  (
    relevance_id varchar(255)  PRIMARY KEY,
    teston_id varchar(255) ,
    pipeline_id varchar(255),
    create_time varchar(255)
);


-- ---------------------------
-- 流水线实例表
-- ----------------------------
create table IF NOT EXISTS pip_setting_resources  (
    id varchar(255)  PRIMARY KEY,
    concurrency_number varchar(255) ,
    structure_number int  ,
    cache_number varchar(255),
    month varchar(255)
);

-- ----------------------------
-- 源码
-- ----------------------------
create table pip_task_code  (
    task_id varchar(256)  PRIMARY KEY ,
    code_name varchar(255) ,
    code_address varchar(256)  ,
    code_branch varchar(256)  ,
    svn_file varchar(256)  ,
    auth_id varchar(256)
);
-- ----------------------------
-- 测试
-- ----------------------------
create table pip_task_test  (
    task_id varchar(256) PRIMARY KEY ,
    test_order varchar(255)  ,
    address varchar(255)
);
-- ----------------------------
-- 构建
-- ----------------------------
create table pip_task_build  (
    task_id varchar(256) PRIMARY KEY ,
    build_address varchar(256)  ,
    build_order varchar(256)
);
-- ----------------------------
-- 部署
-- ----------------------------
create table pip_task_deploy  (
    task_id varchar(256) PRIMARY KEY ,
    auth_type int  ,
    local_address varchar(255)  ,
    auth_id varchar(256) ,
    deploy_address varchar(256) ,
    deploy_order varchar(255) ,
    start_address varchar(255) ,
    start_order varchar(255)
);
-- ----------------------------
-- 代码扫描
-- ----------------------------
create table pip_task_code_scan  (
    task_id varchar(256) PRIMARY KEY ,
    type int  ,
    auth_id varchar(256) ,
    project_name varchar(256)
);
-- ----------------------------
-- 推送制品
-- ----------------------------
create table pip_task_artifact  (
    task_id varchar(256) PRIMARY KEY,
    group_id varchar(255) ,
    artifact_id varchar(256) ,
    version varchar(256) ,
    file_type varchar(256) ,
    file_address varchar(256) ,
    auth_id varchar(256) ,
    put_address varchar(256)
);

-- ----------------------------
-- 脚本
-- ----------------------------
create table pip_task_script  (
    task_id varchar(255) PRIMARY KEY,
    name varchar(32),
    type int ,
    script_order text
);

-- ----------------------------
-- 消息发送类型
-- ----------------------------
create table pip_task_message_type  (
    id varchar(255) PRIMARY KEY,
    task_id varchar(255) ,
    task_type varchar(255)
);

-- ----------------------------
-- 消息发送人
-- ----------------------------
create table pip_task_message_user  (
    message_id varchar(255) PRIMARY KEY ,
    task_id varchar(255) ,
    receive_user varchar(255) ,
    receive_type int
);


-- ----------------------------
-- 环境配置
-- ----------------------------
create table pip_setting_scm  (
    scm_id varchar(256) PRIMARY KEY ,
    scm_type int  ,
    create_time varchar(256) ,
    scm_name varchar(255) ,
    scm_address varchar(255)
);

-- ----------------------------
-- 基本认证
-- ----------------------------
create table pip_auth (
    id varchar(64) PRIMARY KEY,
    auth_type int ,
    name varchar(32) ,
    create_time varchar(64) ,
    username varchar(32),
    password varchar(32) ,
    private_key text ,
    user_id varchar(64)  ,
    auth_public int
);

-- ----------------------------
-- 服务认证
-- ----------------------------
create table pip_auth_server (
    id varchar(64) PRIMARY KEY,
    type int,
    name varchar(32) ,
    create_time varchar(64) ,
    auth_type int ,
    username varchar(255) ,
    password varchar(255) ,
    private_key text ,
    server_address varchar(255) ,
    access_token varchar(255) ,
    refresh_token varchar(255) ,
    auth_public int ,
    user_id varchar(255) ,
    client_id varchar(255) ,
    client_secret varchar(255) ,
    callback_url varchar(255) ,
    message varchar(255)
);

-- ----------------------------
-- 主机认证
-- ----------------------------
create table pip_auth_host  (
    id varchar(64) PRIMARY KEY,
    type int ,
    name varchar(32) ,
    create_time varchar(64) ,
    ip varchar(255) ,
    port varchar(255),
    auth_type int ,
    username varchar(255) ,
    password varchar(255) ,
    private_key text ,
    auth_id varchar(255) ,
    auth_public int ,
    user_id varchar(255)
);


ALTER TABLE pip_task ALTER COLUMN task_type TYPE varchar(255);
ALTER TABLE pip_task_instance ALTER COLUMN task_type TYPE varchar(255);
ALTER TABLE pip_auth_host ALTER COLUMN type TYPE varchar(255);
ALTER TABLE pip_auth_server ALTER COLUMN type TYPE varchar(255);
ALTER TABLE pip_postprocess ALTER COLUMN task_type TYPE varchar(255);


ALTER TABLE pip_task_test ADD COLUMN test_space varchar(255);
ALTER TABLE pip_task_test ADD COLUMN test_plan varchar(255);
ALTER TABLE pip_task_test ADD COLUMN api_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN app_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN web_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN auth_id varchar(255);


ALTER TABLE pip_task_artifact ADD COLUMN xpack_id varchar(255);
ALTER TABLE pip_task_code ADD COLUMN xcode_id varchar(255);
ALTER TABLE pip_task_code ADD COLUMN branch_id varchar(255);

INSERT INTO "pip_auth_server" VALUES ('xpack', 'xpack', 'xpack', '2023-05-19 17:34:47', 1, null, null, NULL, 'http://xpack.tiklab.net', NULL, NULL, 1, '111111', NULL, NULL, NULL, NULL);
INSERT INTO "pip_auth_server" VALUES ('xcode', 'xcode', 'xcode', '2023-05-19 14:22:22', 1, null, null, NULL, 'http://xcode.tiklab.net', NULL, NULL, 1, '111111', NULL, NULL, NULL, NULL);
INSERT INTO "pip_auth_server" VALUES ('teston', 'teston', 'teston', '2023-05-22 14:23:21', 1, null, null, NULL, 'http://teston.tiklab.net', NULL, NULL, 1, '111111', NULL, NULL, NULL, NULL);



INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('resources845', '资源占用', 'pipeline_resources', NULL, 39, '1');


INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('resources845', '1', 'resources845');































