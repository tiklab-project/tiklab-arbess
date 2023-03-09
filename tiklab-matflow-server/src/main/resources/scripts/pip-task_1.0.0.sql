-- ----------------------------
-- 源码
-- ----------------------------
create table pip_task_code  (
    task_id varchar(256)  COMMENT 'gitId',
    code_name varchar(255)  COMMENT '地址名',
    code_address varchar(256)  COMMENT 'git地址',
    code_branch varchar(256)  COMMENT '分支',
    svn_file varchar(256)  COMMENT '签出文件信息',
    auth_id varchar(256)  COMMENT '凭证id',
    PRIMARY KEY (task_id) USING BTREE
);
-- ----------------------------
-- 测试
-- ----------------------------
create table pip_task_test  (
    task_id varchar(256)  COMMENT '测试id',
    test_order varchar(255)  COMMENT '测试命令',
    address varchar(255)  COMMENT '测试地址',
    PRIMARY KEY (task_id) USING BTREE
);
-- ----------------------------
-- 构建
-- ----------------------------
create table pip_task_build  (
    task_id varchar(256)  COMMENT '构建id',
    build_address varchar(256)  COMMENT '文件地址',
    build_order varchar(256)  COMMENT '构建命令',
    PRIMARY KEY (task_id) USING BTREE
);
-- ----------------------------
-- 部署
-- ----------------------------
create table pip_task_deploy  (
    task_id varchar(256)  COMMENT '部署id',
    auth_type int  COMMENT '认证类型',
    local_address varchar(255)  COMMENT '打包文件地址',
    auth_id varchar(256)  COMMENT '认证id',
    deploy_address varchar(256)  COMMENT '部署文件地址',
    deploy_order varchar(255)  COMMENT '部署命令',
    start_address varchar(255)   COMMENT '启动文件地址',
    start_order varchar(255)   COMMENT '启动命令',
    PRIMARY KEY (task_id) USING BTREE
);
-- ----------------------------
-- 代码扫描
-- ----------------------------
create table pip_task_code_scan  (
    task_id varchar(256)  COMMENT 'id',
    type int  COMMENT '类型',
    auth_id varchar(256)  COMMENT '授权id',
    project_name varchar(256)  COMMENT '授权id',
    PRIMARY KEY (task_id) USING BTREE
);
-- ----------------------------
-- 推送制品
-- ----------------------------
create table pip_task_artifact  (
    task_id varchar(256)  COMMENT 'id',
    group_id varchar(255)  COMMENT 'groupId',
    artifact_id varchar(256)  COMMENT 'artifactId',
    version varchar(256)  COMMENT 'version',
    file_type varchar(256)  COMMENT '文件类型',
    file_address varchar(256)  COMMENT '文件地址',
    auth_id varchar(256)  COMMENT '凭证id',
    put_address varchar(256)  COMMENT '凭证id',
    PRIMARY KEY (task_id) USING BTREE
);

-- ----------------------------
-- 脚本
-- ----------------------------
create table pip_task_script  (
    task_id varchar(255) COMMENT '脚本',
    name varchar(32) COMMENT '名称',
    type int COMMENT '脚本类型',
    script_order longtext COMMENT '脚本命令',
    PRIMARY KEY (task_id) USING BTREE
);

-- ----------------------------
-- 消息发送类型
-- ----------------------------
create table pip_task_message_type  (
    id varchar(255) COMMENT 'id',
    task_id varchar(255) COMMENT '任务id',
    task_type varchar(255) COMMENT '认证类型',
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- 消息发送人
-- ----------------------------
create table pip_task_message_user  (
    message_id varchar(255) COMMENT '认证信息',
    task_id varchar(255) COMMENT '类型信息',
    receive_user varchar(255) COMMENT '接收人信息',
    receive_type int COMMENT '接收类型 1.全部 2.仅成功 3.仅失败',
    PRIMARY KEY (message_id) USING BTREE
);













