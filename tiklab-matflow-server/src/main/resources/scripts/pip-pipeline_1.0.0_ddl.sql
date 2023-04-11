-- ---------------------------
-- 流水线表
-- @dsm.cmd.id="1001"
-- ----------------------------
create table IF NOT EXISTS pip_pipeline  (
   id varchar(256)  COMMENT '流水线id',
   name varchar(256)  COMMENT '流水线名称',
   user_id varchar(256)  COMMENT '用户id',
   create_time datetime  COMMENT '创建时间',
   type int  COMMENT '流水线类型',
   state int  COMMENT '运行状态（1.正常 2.运行中）',
   power int  COMMENT '项目权限（1.公共的 2.私有）',
   color int  COMMENT '颜色（1-5随机数）',
   PRIMARY KEY (id) USING BTREE
);

-- ---------------------------
-- 流水线实例表
-- @dsm.cmd.id="1002"
-- ----------------------------
create table IF NOT EXISTS pip_pipeline_instance  (
    instance_id varchar(255) COMMENT 'id',
    create_time datetime COMMENT '创建时间',
    run_way int  COMMENT '构建方式 1。手动触发 2.触发器触发',
    run_status varchar(255)  COMMENT '执行状态 成功:success 失败:error 停止:halt 等待运行:wait',
    run_time int  COMMENT '执行时间',
    user_id varchar(255)  COMMENT '执行人',
    pipeline_id varchar(255)  COMMENT '流水线id',
    find_number int  COMMENT '构建次数',
    PRIMARY KEY (instance_id) USING BTREE
);

-- ---------------------------
-- 任务实例表
-- @dsm.cmd.id="1003"
-- ----------------------------
create table IF NOT EXISTS pip_task_instance  (
    id varchar(256)  COMMENT '日志id',
    instance_id varchar(256)  COMMENT '历史id',
    task_sort int  COMMENT '运行顺序',
    task_type int  COMMENT '执行类型',
    task_name varchar(255)  COMMENT '执行名称',
    run_state varchar(255)  COMMENT '运行状态,成功:success 失败:error 停止:halt 等待运行:wait',
    run_time int  COMMENT '运行时间',
    log_address longtext COMMENT '运行日志',
    stages_id varchar(256)  COMMENT '阶段id',
    postprocess_id varchar(256) COMMENT '后置处理id',
    PRIMARY KEY (id) USING BTREE
);

-- ---------------------------
-- 后置
-- @dsm.cmd.id="1004"
-- ----------------------------
create table IF NOT EXISTS pip_postprocess  (
    postprocess_id varchar(255) COMMENT 'id',
    task_id varchar(255) COMMENT '任务配置',
    name varchar(32) COMMENT '名称',
    task_type int COMMENT '任务类型' ,
    create_time varchar(255) COMMENT '创建时间',
    task_sort varchar(255) COMMENT '任务顺序',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (postprocess_id) USING BTREE
);

-- @dsm.cmd.id="1005"
create table IF NOT EXISTS pip_condition  (
    cond_id varchar(256)  COMMENT '条件id',
    cond_name varchar(256)  COMMENT '名称',
    task_id varchar(256)  COMMENT '任务id',
    create_time datetime  COMMENT '创建时间',
    cond_type int  COMMENT '流水线类型',
    cond_key varchar(256)  COMMENT '条件key',
    cond_value varchar(256)  COMMENT '条件value',
    PRIMARY KEY (cond_id) USING BTREE
);

-- ---------------------------
-- 流程设计
-- @dsm.cmd.id="1006"
-- ----------------------------
create table IF NOT EXISTS pip_task  (
    task_id varchar(255) COMMENT '配置id',
    create_time datetime COMMENT '创建时间',
    stage_id varchar(255) COMMENT '阶段id',
    task_name varchar(255) COMMENT '阶段名称',
    pipeline_id varchar(255) COMMENT '流水线id',
    task_type int COMMENT '任务类型',
    task_sort int COMMENT '任务顺序',
    postprocess_id varchar(255) COMMENT 'id',
    PRIMARY KEY (task_id) USING BTREE
);

-- ---------------------------
-- 阶段
-- @dsm.cmd.id="1007"
-- ----------------------------
create table IF NOT EXISTS pip_stage  (
    stage_id varchar(255) COMMENT 'id',
    create_time datetime COMMENT '创建时间',
    stage_name varchar(255) COMMENT '阶段名称',
    pipeline_id varchar(255) COMMENT '流水线id',
    stage_sort int COMMENT '阶段顺序',
    parent_id varchar(255) COMMENT '主阶段',
    code varchar(255) COMMENT '是否为源码',
    PRIMARY KEY (stage_id) USING BTREE
);

-- ---------------------------
-- 阶段实例
-- @dsm.cmd.id="1008"
-- ----------------------------
create table IF NOT EXISTS pip_stage_instance  (
    id varchar(255) COMMENT 'id',
    stage_name varchar(255) COMMENT '阶段实例名称',
    stage_sort int COMMENT '阶段实例顺序',
    stage_time int COMMENT '阶段实例顺序',
    stage_address varchar(255) COMMENT '阶段实例运行日志地址',
    stage_state varchar(255) COMMENT '阶段实例运行状态 成功:success 失败:error 停止:halt 等待运行:wait',
    parent_id varchar(255) COMMENT '主阶段',
    instance_id varchar(255) COMMENT '所属实例',
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- 触发器
-- @dsm.cmd.id="1009"
-- ----------------------------
create table IF NOT EXISTS pip_trigger  (
    trigger_id varchar(255) COMMENT '配置',
    name varchar(32) COMMENT '名称',
    task_type int COMMENT '任务类型' ,
    create_time varchar(255) COMMENT '创建时间',
    task_sort varchar(255) COMMENT '任务顺序',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (trigger_id) USING BTREE
);


-- ----------------------------
-- 触发器时间
-- @dsm.cmd.id="1010"
-- ----------------------------
create table IF NOT EXISTS pip_trigger_time  (
    time_id varchar(255) COMMENT '配置',
    task_type int COMMENT '1:单次,2:周期' ,
    date varchar(255) COMMENT '时间',
    cron varchar(255) COMMENT '详细时间',
    trigger_id varchar(255) COMMENT '配置id',
    PRIMARY KEY (time_id) USING BTREE
);

-- ----------------------------
-- 变量
-- @dsm.cmd.id="1011"
-- ----------------------------
create table IF NOT EXISTS pip_pipeline_variable  (
    var_id varchar(255) COMMENT '变量id',
    task_type int COMMENT '类型 1.字符串 2.单选' ,
    create_time varchar(255) COMMENT '创建时间',
    var_key varchar(255) COMMENT '名称',
    var_value varchar(255) COMMENT '值',
    task_id varchar(255) COMMENT '任务id',
    type int COMMENT '类型 1:全局,2:任务',
    var_values varchar(255) COMMENT '值集合',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (var_id) USING BTREE
);

-- ----------------------------
-- 收藏
-- @dsm.cmd.id="1012"
-- ----------------------------
create table IF NOT EXISTS pip_other_follow  (
    id varchar(255) ,
    user_id varchar(255) ,
    pipeline_id varchar(255) ,
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- 最近打开
-- @dsm.cmd.id="1013"
-- ----------------------------
create table IF NOT EXISTS pip_other_open  (
    open_id varchar(255) ,
    pipeline_id varchar(255) ,
    number int ,
    user_id varchar(255) ,
    create_time varchar(255) ,
    PRIMARY KEY (open_id) USING BTREE
);

-- ---------------------------
-- 流水线后置任务实例表
-- @dsm.cmd.id="1014"
-- ----------------------------
create table pip_postprocess_instance  (
    id varchar(256)  COMMENT '流水线id',
    instance_id varchar(256)  COMMENT '流水线名称',
    taskInstance_id varchar(256)  COMMENT '用户id',
    post_address varchar(256)  COMMENT '创建时间',
    post_time int  COMMENT '流水线类型',
    post_state varchar(256)  COMMENT '运行状态（1.正常 2.运行中）',
    PRIMARY KEY (id) USING BTREE
);










































