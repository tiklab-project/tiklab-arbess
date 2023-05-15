-- ---------------------------
-- 流水线表
-- @dsm.cmd.id="1001"
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
-- @dsm.cmd.id="1002"
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
-- @dsm.cmd.id="1003"
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
-- @dsm.cmd.id="1004"
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

-- @dsm.cmd.id="1005"
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
-- @dsm.cmd.id="1006"
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
-- @dsm.cmd.id="1007"
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
-- @dsm.cmd.id="1008"
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
-- @dsm.cmd.id="1009"
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
-- @dsm.cmd.id="1010"
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
-- @dsm.cmd.id="1011"
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
-- @dsm.cmd.id="1012"
-- ----------------------------
create table IF NOT EXISTS pip_other_follow  (
    id varchar(255) PRIMARY KEY,
    user_id varchar(255) ,
    pipeline_id varchar(255)
);

-- ----------------------------
-- 最近打开
-- @dsm.cmd.id="1013"
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
-- @dsm.cmd.id="1014"
-- ----------------------------
create table pip_postprocess_instance  (
    id varchar(256) PRIMARY KEY ,
    instance_id varchar(256) ,
    taskInstance_id varchar(256)  ,
    post_address varchar(256) ,
    post_time int  ,
    post_state varchar(256)
);










































