-- ----------------------------
-- 源码
-- @dsm.cmd.id="1001"
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
-- @dsm.cmd.id="1002"
-- ----------------------------
create table pip_task_test  (
    task_id varchar(256) PRIMARY KEY ,
    test_order varchar(255)  ,
    address varchar(255)
);
-- ----------------------------
-- 构建
-- @dsm.cmd.id="1003"
-- ----------------------------
create table pip_task_build  (
    task_id varchar(256) PRIMARY KEY ,
    build_address varchar(256)  ,
    build_order varchar(256)
);
-- ----------------------------
-- 部署
-- @dsm.cmd.id="1004"
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
-- @dsm.cmd.id="1005"
-- ----------------------------
create table pip_task_code_scan  (
    task_id varchar(256) PRIMARY KEY ,
    type int  ,
    auth_id varchar(256) ,
    project_name varchar(256)
);
-- ----------------------------
-- 推送制品
-- @dsm.cmd.id="1006"
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
-- @dsm.cmd.id="1007"
-- ----------------------------
create table pip_task_script  (
    task_id varchar(255) PRIMARY KEY,
    name varchar(32),
    type int ,
    script_order text
);

-- ----------------------------
-- 消息发送类型
-- @dsm.cmd.id="1008"
-- ----------------------------
create table pip_task_message_type  (
    id varchar(255) PRIMARY KEY,
    task_id varchar(255) ,
    task_type varchar(255)
);

-- ----------------------------
-- 消息发送人
-- @dsm.cmd.id="1009"
-- ----------------------------
create table pip_task_message_user  (
    message_id varchar(255) PRIMARY KEY ,
    task_id varchar(255) ,
    receive_user varchar(255) ,
    receive_type int
);













