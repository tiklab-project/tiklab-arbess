
-- ----------------------------
-- Table structure for pipeline_after_config
-- ----------------------------
create table pipeline_after_config  (
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
-- Table structure for pipeline_script
-- ----------------------------
create table pipeline_script  (
    script_id varchar(255) COMMENT '脚本',
    name varchar(32) COMMENT '名称',
    type int COMMENT '脚本类型',
    script_order longtext COMMENT '脚本命令',
    PRIMARY KEY (script_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_message_type
-- ----------------------------
create table pipeline_message_type  (
    message_task_id varchar(255) COMMENT '消息',
    type varchar(255) COMMENT '认证信息',
    PRIMARY KEY (message_task_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_message_user
-- ----------------------------
create table pipeline_message_user  (
    message_id varchar(255) COMMENT '认证信息',
    message_task_id varchar(255) COMMENT '类型信息',
    receive_user varchar(255) COMMENT '接收人信息',
    receive_type int COMMENT '接收类型 1.全部 2.仅成功 3.仅失败',
    PRIMARY KEY (message_id) USING BTREE
);




























