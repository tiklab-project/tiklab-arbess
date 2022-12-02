-- ----------------------------
-- Table structure for pipeline_before_config
-- ----------------------------
create table pipeline_before_config  (
    config_id varchar(255) COMMENT '配置',
    name varchar(32) COMMENT '名称',
    task_type int COMMENT '任务类型' ,
    create_time varchar(255) COMMENT '创建时间',
    task_id varchar(255) COMMENT '任务id',
    task_sort varchar(255) COMMENT '任务顺序',
    pipeline_id varchar(255) COMMENT '流水线id',
    PRIMARY KEY (config_id) USING BTREE
);


-- ----------------------------
-- Table structure for pipeline_after_config
-- ----------------------------
create table pipeline_time  (
     time_id varchar(255) COMMENT '配置',
     task_type int COMMENT 'cycle:周期 one:单次' ,
     date varchar(255) COMMENT '时间',
     time varchar(255) COMMENT '详细时间',
     pipeline_id varchar(255) COMMENT '配置id',
     PRIMARY KEY (time_id) USING BTREE
);
