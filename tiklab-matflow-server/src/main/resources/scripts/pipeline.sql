
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
  deploy_id varchar(256)  COMMENT '部署id',
  deploy_type int  COMMENT '部署类型',
  source_address varchar(255)  COMMENT '打包文件地址',
  deploy_address varchar(256)  COMMENT '部署文件地址',
  start_port int  COMMENT 'docker启动端口',
  proof_id varchar(256)  COMMENT '凭证id',
  mapping_port int  COMMENT '映射端口',
  ssh_ip varchar(255)  COMMENT 'IP地址',
  ssh_port int  COMMENT '端口号',
  start_address varchar(255)   COMMENT '启动文件地址',
  deploy_order varchar(255)   COMMENT '文件操作',
  start_shell longtext  COMMENT '启动命令',
  PRIMARY KEY (deploy_id) USING BTREE
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
-- Table structure for pipeline_proof
-- ----------------------------
create table pipeline_proof  (
  proof_id varchar(256)  COMMENT '凭证id',
  proof_name varchar(255)  COMMENT '凭证名',
  proof_scope int  COMMENT '作用域',
  proof_type varchar(256)  COMMENT '凭证类型',
  proof_username varchar(256)  COMMENT '账户',
  proof_password longtext  COMMENT '密码',
  proof_describe varchar(256)  COMMENT '凭证描述',
  pipeline_id varchar(255)  COMMENT '流水线id',
  proof_create_time datetime  COMMENT '创建时间',
  callback_url varchar(255)  COMMENT '回调地址',
  type int  COMMENT '类型',
  user_id varchar(255)  COMMENT '用户id',
  PRIMARY KEY (proof_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_proof_task
-- ----------------------------
create table pipeline_proof_task  (
  task_id varchar(255) ,
  proof_id varchar(255) ,
  pipeline_id varchar(255) ,
  PRIMARY KEY (task_id) USING BTREE
);

-- ----------------------------
-- Table structure for pipeline_scm
-- ----------------------------
create table pipeline_scm  (
  scm_id varchar(256) ,
  scm_type int ,
  scm_name varchar(255) ,
  scm_address varchar(255) ,
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
-- Table structure for pipeline_authorization
-- ----------------------------
create table pipeline_authorize  (
  id varchar(256) ,
  type int ,
  client_id varchar(255) ,
  client_secret varchar(255) ,
  callback_url varchar(255) ,
  PRIMARY KEY (id) USING BTREE
);



