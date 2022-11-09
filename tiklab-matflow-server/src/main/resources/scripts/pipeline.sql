
-- ----------------------------
-- Table structure for pipeline
-- ----------------------------
create table pipeline  (
  pipeline_id varchar(256)  COMMENT '流水线id',
  pipeline_name varchar(256)  COMMENT '流水线名称',
  user_id varchar(256)  COMMENT '用户id',
  pipeline_create_time datetime  COMMENT '流水线创建时间',
  pipeline_type int  COMMENT '流水线类型',
  pipeline_state int  COMMENT '运行状态',
  pipeline_power int  COMMENT '项目权限',
  color int  COMMENT '项目权限',
  PRIMARY KEY (pipeline_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_build
-- ----------------------------
create table pipeline_build  (
  build_id varchar(256)  COMMENT '构建id',
  build_address varchar(256)  COMMENT '文件地址',
  build_order varchar(256)  COMMENT '构建命令',
  PRIMARY KEY (build_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_code
-- ----------------------------
create table pipeline_code  (
  code_id varchar(256)  COMMENT 'gitId',
  code_name varchar(255)  COMMENT '地址名',
  code_address varchar(256)  COMMENT 'git地址',
  code_branch varchar(256)  COMMENT '分支',
  proof_id varchar(256)  COMMENT '凭证id',
  PRIMARY KEY (code_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_config_order
-- ----------------------------
create table pipeline_config_order  (
  config_id varchar(255) ,
  create_time datetime ,
  pipeline_id varchar(255) ,
  task_id varchar(255) ,
  task_type int ,
  task_sort int ,
  PRIMARY KEY (config_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_deploy
-- ----------------------------
create table pipeline_deploy  (
  id varchar(256)  COMMENT '部署id',
  auth_type int  COMMENT '认证类型',
  local_address varchar(255)  COMMENT '打包文件地址',
  auth_id varchar(256)  COMMENT '认证id',
  deploy_address varchar(256)  COMMENT '部署文件地址',
  deploy_order varchar(255)  COMMENT '部署命令',
  start_address varchar(255)   COMMENT '启动文件地址',
  start_order varchar(255)   COMMENT '启动命令',
  PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_follow
-- ----------------------------
create table pipeline_follow  (
  id varchar(255) ,
  user_id varchar(255) ,
  pipeline_id varchar(255) ,
  PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_history
-- ----------------------------
create table pipeline_history  (
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

-- ----------------------------
-- Table structure for pipeline_log
-- ----------------------------
create table pipeline_log  (
  log_id varchar(256)  COMMENT '日志id',
  history_id varchar(256)  COMMENT '历史id',
  task_sort int  COMMENT '运行顺序',
  task_type int  COMMENT '执行类型',
  run_state int  COMMENT '运行状态',
  run_time int  COMMENT '运行时间',
  run_log longtext COMMENT '运行日志',
  PRIMARY KEY (log_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_open
-- ----------------------------
create table pipeline_open  (
  open_id varchar(255) ,
  pipeline_id varchar(255) ,
  number int ,
  user_id varchar(255) ,
  PRIMARY KEY (open_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_path
-- ----------------------------
create table pipeline_path  (
  path_id varchar(255) ,
  path_type int ,
  path_name varchar(255) ,
  path_address varchar(255) ,
  PRIMARY KEY (path_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_scm
-- ----------------------------
create table pipeline_scm  (
  scm_id varchar(256)  COMMENT 'id' ,
  scm_type int COMMENT '类型' ,
  create_time varchar(256) COMMENT '创建时间' ,
  scm_name varchar(255) COMMENT '名称' ,
  scm_address varchar(255) COMMENT '地址' ,
  PRIMARY KEY (scm_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_test
-- ----------------------------
create table pipeline_test  (
  test_id varchar(256)  COMMENT '测试id',
  test_order varchar(255)  COMMENT '测试命令',
  PRIMARY KEY (test_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_code_scan
-- ----------------------------
create table pipeline_code_scan  (
    id varchar(256)  COMMENT 'id',
    type int  COMMENT '类型',
    auth_id varchar(256)  COMMENT '授权id',
    project_name varchar(256)  COMMENT '授权id',
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_product
-- ----------------------------
create table pipeline_product  (
    id varchar(256)  COMMENT 'id',
    group_id varchar(255)  COMMENT 'groupId',
    artifact_id varchar(256)  COMMENT 'artifactId',
    version varchar(256)  COMMENT 'version',
    file_type varchar(256)  COMMENT '文件类型',
    file_address varchar(256)  COMMENT '文件地址',
    auth_id varchar(256)  COMMENT '凭证id',
    put_address varchar(256)  COMMENT '凭证id',
    PRIMARY KEY (id) USING BTREE
);


-- ----------------------------
-- Table structure for pipeline_third_address
-- ----------------------------
create table pipeline_third_address  (
    id varchar(256) ,
    type int ,
    client_id varchar(255) ,
    client_secret varchar(255) ,
    callback_url varchar(255) ,
    PRIMARY KEY (id) USING BTREE
);

# 认证信息

-- ----------------------------
-- Table structure for pipeline_auth
-- ----------------------------
create table pipeline_auth  (
    id varchar(64) COMMENT 'id',
    type int COMMENT '类型 1.username&password 2. 公钥私钥',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    username varchar(32) COMMENT '用户名',
    password varchar(32) COMMENT '密码',
    private_key varchar(255) COMMENT '私钥',
    user_id varchar(64)  COMMENT '用户id',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_auth_code
-- ----------------------------
create table pipeline_auth_server  (
    id varchar(64) COMMENT 'id',
    type int COMMENT '1.gitee 2.github 3.sonar 4.nexus',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    auth_type int COMMENT '认证方式 1. 自定义 ，2. 统一认证' ,
    username varchar(255) COMMENT '用户名',
    password varchar(255) COMMENT '密码',
    private_key varchar(255) COMMENT '私钥',
    server_address varchar(255) COMMENT '服务地址',
    access_token varchar(255) COMMENT '认证信息',
    refresh_token varchar(255) COMMENT '刷新认证信息',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    user_id varchar(255) COMMENT '用户id' ,
    PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_auth_host
-- ----------------------------
create table pipeline_auth_host  (
    id varchar(64) COMMENT 'id',
    type int COMMENT '代码扫描类型 1：sonar ',
    name varchar(32) COMMENT '名称',
    create_time varchar(64) COMMENT '创建时间',
    ip varchar(255) COMMENT 'ip地址',
    port varchar(255) COMMENT '端口',
    auth_type int COMMENT '认证方式 1. 自定义 ，2. 统一认证' ,
    username varchar(255) COMMENT '用户名',
    password varchar(255) COMMENT '密码',
    private_key varchar(255) COMMENT '私钥',
    auth_id varchar(255) COMMENT '凭证id',
    auth_public int COMMENT '是否公开 1：公开， 2：不公开',
    user_id varchar(255) COMMENT '用户id' ,
    PRIMARY KEY (id) USING BTREE
);























