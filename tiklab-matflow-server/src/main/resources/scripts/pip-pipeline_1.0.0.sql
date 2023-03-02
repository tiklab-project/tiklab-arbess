
create table pip_pipeline  (
   id varchar(256)  COMMENT '流水线id',
   name varchar(256)  COMMENT '流水线名称',
   user_id varchar(256)  COMMENT '用户id',
   create_time datetime  COMMENT '创建时间',
   type int  COMMENT '流水线类型',
   state int  COMMENT '运行状态（1.正常 2.运行中）',
   power int  COMMENT '项目权限（1.公共的 2.私有）',
   color int  COMMENT '颜色',
   PRIMARY KEY (id) USING BTREE
);

create table pip_pipeline_instance  (
    instance_id varchar(255) COMMENT 'id',
    create_time datetime COMMENT '创建时间',
    run_way int  COMMENT '构建方式',
    run_status int  COMMENT '执行状态 1:失败 10:成功 20: 停止',
    run_time int  COMMENT '执行时间',
    user_id varchar(255)  COMMENT '执行人',
    pipeline_id varchar(255)  COMMENT '流水线id',
    find_number int  COMMENT '构建次数',
    find_state int  COMMENT '判断是否正在执行',
    PRIMARY KEY (instance_id) USING BTREE
);

create table pip_pipeline_instance_log  (
    log_id varchar(256)  COMMENT '日志id',
    instance_id varchar(256)  COMMENT '历史id',
    task_sort int  COMMENT '运行顺序',
    task_type int  COMMENT '执行类型',
    task_name varchar(255)  COMMENT '执行类型',
    run_state int  COMMENT '运行状态',
    run_time int  COMMENT '运行时间',
    log_address longtext COMMENT '运行日志',
    stages_id varchar(256)  COMMENT '阶段id',
    PRIMARY KEY (log_id) USING BTREE
);

-- ---------------------------
-- 后置
-- ----------------------------
create table pip_pipeline_postprocess  (
    config_id varchar(255) COMMENT '配置',
    name varchar(32) COMMENT '名称',
    task_type int COMMENT '任务类型' ,
    create_time varchar(255) COMMENT '创建时间',
    task_sort varchar(255) COMMENT '任务顺序',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (config_id) USING BTREE
);



create table pip_pipeline_condition  (
    cond_id varchar(256)  COMMENT '条件id',
    cond_name varchar(256)  COMMENT '名称',
    task_id varchar(256)  COMMENT '任务id',
    create_time datetime  COMMENT '创建时间',
    cond_type int  COMMENT '流水线类型',
    cond_key varchar(256)  COMMENT '条件key',
    cond_value varchar(256)  COMMENT '条件value',
    PRIMARY KEY (cond_id) USING BTREE
);
