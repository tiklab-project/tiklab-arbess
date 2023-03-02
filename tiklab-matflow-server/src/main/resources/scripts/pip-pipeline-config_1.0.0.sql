
-- ---------------------------
-- 流程设计
-- ----------------------------
create table pip_pipeline_tasks  (
    task_id varchar(255) COMMENT '配置id',
    create_time datetime COMMENT '创建时间',
    stages_id varchar(255) COMMENT '阶段id',
    pipeline_id varchar(255) COMMENT '流水线id',
    task_type int COMMENT '任务类型',
    task_sort int COMMENT '任务顺序',
    PRIMARY KEY (task_id) USING BTREE
);

-- ---------------------------
-- 阶段
-- ----------------------------
create table pip_pipeline_stages  (
    stages_id varchar(255) COMMENT 'id',
    create_time datetime COMMENT '创建时间',
    stages_name varchar(255) COMMENT '阶段名称',
    pipeline_id varchar(255) COMMENT '流水线id',
    stages_sort int COMMENT '阶段顺序',
    main_stage varchar(255) COMMENT '主阶段',
    code varchar(255) COMMENT '是否为源码',
    PRIMARY KEY (stages_id) USING BTREE
);

-- ----------------------------
-- 触发器
-- ----------------------------
create table pip_pipeline_trigger  (
    config_id varchar(255) COMMENT '配置',
    name varchar(32) COMMENT '名称',
    task_type int COMMENT '任务类型' ,
    create_time varchar(255) COMMENT '创建时间',
    task_sort varchar(255) COMMENT '任务顺序',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (config_id) USING BTREE
);

-- ----------------------------
-- 变量
-- ----------------------------
create table pip_pipeline_variable  (
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

















