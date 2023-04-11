-- ----------------------------
-- 环境配置
-- @dsm.cmd.id="1001"
-- ----------------------------
create table pip_setting_scm  (
    scm_id varchar(256)  COMMENT 'id' ,
    scm_type int COMMENT '类型' ,
    create_time varchar(256) COMMENT '创建时间' ,
    scm_name varchar(255) COMMENT '名称' ,
    scm_address varchar(255) COMMENT '地址' ,
    PRIMARY KEY (scm_id) USING BTREE
);

-- ----------------------------
-- 基本认证
-- @dsm.cmd.id="1002"
-- ----------------------------
create table pip_auth (
    id varchar(64) COMMENT 'id',
    auth_type int COMMENT '类型 1.username&password 2. 公钥私钥',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    username varchar(32) COMMENT '用户名',
    password varchar(32) COMMENT '密码',
    private_key longtext COMMENT '私钥',
    user_id varchar(64)  COMMENT '用户id',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- 服务认证
-- @dsm.cmd.id="1003"
-- ----------------------------
create table pip_auth_server (
    id varchar(64) COMMENT 'id',
    type int COMMENT '1.gitee 2.github 3.sonar 4.nexus',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    auth_type int COMMENT '认证方式 1. 自定义 ，2. 统一认证' ,
    username varchar(255) COMMENT '用户名',
    password varchar(255) COMMENT '密码',
    private_key longtext COMMENT '私钥',
    server_address varchar(255) COMMENT '服务地址',
    access_token varchar(255) COMMENT '认证信息',
    refresh_token varchar(255) COMMENT '刷新认证信息',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    user_id varchar(255) COMMENT '用户id' ,
    client_id varchar(255) ,
    client_secret varchar(255) ,
    callback_url varchar(255) ,
    message varchar(255) ,
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- 主机认证
-- @dsm.cmd.id="1004"
-- ----------------------------
create table pip_auth_host  (
    id varchar(64) COMMENT 'id',
    type int COMMENT '代码扫描类型 1：sonar ',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    ip varchar(255) COMMENT 'ip地址',
    port varchar(255) COMMENT '端口',
    auth_type int COMMENT '认证方式 1. 自定义 ，2. 统一认证' ,
    username varchar(255) COMMENT '用户名',
    password varchar(255) COMMENT '密码',
    private_key longtext COMMENT '私钥',
    auth_id varchar(255) COMMENT '凭证id',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    user_id varchar(255) COMMENT '用户id' ,
    PRIMARY KEY (id) USING BTREE
);























