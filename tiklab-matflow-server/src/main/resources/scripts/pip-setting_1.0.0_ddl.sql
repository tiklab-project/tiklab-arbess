-- ----------------------------
-- 环境配置
-- @dsm.cmd.id="1001"
-- ----------------------------
create table pip_setting_scm  (
    scm_id varchar(256) PRIMARY KEY ,
    scm_type int  ,
    create_time varchar(256) ,
    scm_name varchar(255) ,
    scm_address varchar(255)
);

-- ----------------------------
-- 基本认证
-- @dsm.cmd.id="1002"
-- ----------------------------
create table pip_auth (
    id varchar(64) PRIMARY KEY,
    auth_type int ,
    name varchar(32) ,
    create_time varchar(64) ,
    username varchar(32),
    password varchar(32) ,
    private_key text ,
    user_id varchar(64)  ,
    auth_public int
);

-- ----------------------------
-- 服务认证
-- @dsm.cmd.id="1003"
-- ----------------------------
create table pip_auth_server (
    id varchar(64) PRIMARY KEY,
    type int,
    name varchar(32) ,
    create_time varchar(64) ,
    auth_type int ,
    username varchar(255) ,
    password varchar(255) ,
    private_key text ,
    server_address varchar(255) ,
    access_token varchar(255) ,
    refresh_token varchar(255) ,
    auth_public int ,
    user_id varchar(255) ,
    client_id varchar(255) ,
    client_secret varchar(255) ,
    callback_url varchar(255) ,
    message varchar(255)
);

-- ----------------------------
-- 主机认证
-- @dsm.cmd.id="1004"
-- ----------------------------
create table pip_auth_host  (
    id varchar(64) PRIMARY KEY,
    type int ,
    name varchar(32) ,
    create_time varchar(64) ,
    ip varchar(255) ,
    port varchar(255),
    auth_type int ,
    username varchar(255) ,
    password varchar(255) ,
    private_key text ,
    auth_id varchar(255) ,
    auth_public int ,
    user_id varchar(255)
);























