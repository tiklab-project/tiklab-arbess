
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

create table pip_pipeline_history  (
    history_id varchar(255) COMMENT 'id',
    create_time datetime COMMENT '创建时间',
    run_way int  COMMENT '构建方式',
    run_status int  COMMENT '执行状态',
    run_time int  COMMENT '执行时间',
    user_id varchar(255)  COMMENT '执行人',
    run_log longtext COMMENT '日志',
    pipeline_id varchar(255)  COMMENT '流水线id',
    find_number int  COMMENT '构建次数',
    find_state int  COMMENT '判断是否正在执行',
    PRIMARY KEY (history_id) USING BTREE
);

create table pip_pipeline_history_log  (
    log_id varchar(256)  COMMENT '日志id',
    history_id varchar(256)  COMMENT '历史id',
    task_sort int  COMMENT '运行顺序',
    task_type int  COMMENT '执行类型',
    task_name varchar(255)  COMMENT '执行类型',
    run_state int  COMMENT '运行状态',
    run_time int  COMMENT '运行时间',
    log_address longtext COMMENT '运行日志',
    stages_id varchar(256)  COMMENT '阶段id',
    PRIMARY KEY (log_id) USING BTREE
);