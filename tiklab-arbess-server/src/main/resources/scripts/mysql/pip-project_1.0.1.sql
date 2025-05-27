-- 1.0.0 ~ 1.0.14 版本合并完整脚本

-- 1.0.0
ALTER TABLE pip_task_build ADD COLUMN product_rule varchar(255);

CREATE TABLE pip_task_build_product (
  id varchar(64) PRIMARY KEY,
  product_address varchar(255),
  instance_id varchar(255)
);

-- 1.0.2
ALTER TABLE pip_task_deploy MODIFY COLUMN start_order TEXT;

ALTER TABLE pip_task_build_product ADD COLUMN product_name varchar(255);

-- 1.0.3
ALTER TABLE pip_task_build_product RENAME COLUMN product_address TO `key`;
ALTER TABLE pip_task_build_product RENAME COLUMN product_name TO `value`;

ALTER TABLE pip_task_build_product ADD COLUMN type varchar(255);

-- 1.0.4
ALTER TABLE pip_task_deploy MODIFY COLUMN deploy_order text;
ALTER TABLE pip_task_deploy MODIFY COLUMN start_order text;

ALTER TABLE pip_pipeline_instance MODIFY COLUMN find_number varchar(50);

-- 1.0.5
UPDATE pcs_prc_role_function
SET role_id = '1', function_id = 'ma14739a13fe'
WHERE id = 'mabbd80b955f';

UPDATE pcs_prc_role_function
SET role_id = '1', function_id = 'ma73e628fd54'
WHERE id = 'ma2dd86200e1';
-- 初始化功能权限
UPDATE pcs_prc_function
SET name = '备份', code = 'backups', parent_function_id = NULL, sort = 38, type = '1'
WHERE id = 'ma73e628fd54';

UPDATE pcs_prc_function
SET name = '恢复', code = 'restore', parent_function_id = NULL, sort = 39, type = '1'
WHERE id = 'ma14739a13fe';


-- 1.0.7
-- 日志类型
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_DELETE', '删除流水线', 'arbess')   ;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_UPDATE', '更新流水线', 'arbess')   ;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_RUN', '运行流水线', 'arbess')   ;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_CREATE', '创建流水线', 'arbess')   ;

-- 消息类型
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_DELETE', '删除流水线', '流水线删除消息', 'arbess')   ;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_CREATE', '创建流水线', '流水线创建消息', 'arbess')   ;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_UPDATE', '更新流水线', '流水线更新消息', 'arbess')   ;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_RUN', '运行流水线', '流水线运行消息', 'arbess')   ;

-- 模板
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('bb09db8fa4d1', 'MF_MES_TYPE_UPDATE', 'qywechat', NULL, '${userName}更新流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('83f3e496abe9', 'MF_MES_TYPE_UPDATE', 'email', '更新流水线', '${userName}更新流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('ea37e83bacd5', 'MF_MES_TYPE_UPDATE', 'site', '更新流水线', '${userName}更新流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('7ff3948a5c9b', 'MF_MES_TYPE_RUN', 'qywechat', NULL, '${userName}运行流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('f9b8e28e710e', 'MF_MES_TYPE_RUN', 'email', '运行流水线', '${userName}运行流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('f3a4f5ef0b15', 'MF_MES_TYPE_RUN', 'site', '运行流水线', '${userName}运行流水线${pipelineName} ${message}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('a48b296dce37', 'MF_MES_TYPE_DELETE', 'qywechat', NULL, '${userName}删除流水线${pipelineName}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('6d4f248eea10', 'MF_MES_TYPE_DELETE', 'site', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('f976ca473d57', 'MF_MES_TYPE_DELETE', 'email', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('016ff38e76d2', 'MF_MES_TYPE_CREATE', 'email', '创建流水线', '${userName}创建流水线${pipelineName}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('ea2656a4c4bf', 'MF_MES_TYPE_CREATE', 'qywechat', NULL, '${userName}创建流水线${pipelineName}', NULL, 'arbess', NULL)   ;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES
  ('8fb7ce53b2a2', 'MF_MES_TYPE_CREATE', 'site', '创建流水线', '${userName}创建流水线${pipelineName}', NULL, 'arbess', NULL)   ;

-- 通知方案
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_CREATE', 'MF_MES_TYPE_CREATE', 1, 'arbess', 'site,email,qywechat')   ;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_DELETE', 'MF_MES_TYPE_DELETE', 1, 'arbess', 'site,email,qywechat')   ;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_UPDATE', 'MF_MES_TYPE_UPDATE', 1, 'arbess', 'site,email,qywechat')  ;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_RUN', 'MF_MES_TYPE_RUN', 1, 'arbess', 'site,email,qywechat')  ;



-- 通知人员
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('28d7402e4882', 'MF_MES_TYPE_CREATE', '111111')   ;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('003a7384b83f', 'MF_MES_TYPE_DELETE', '111111')   ;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('39a1cb668773', 'MF_MES_TYPE_UPDATE', '111111')   ;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('9100ec35886e', 'MF_MES_TYPE_RUN', '111111')   ;


-- 1.0.8
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_CREATE';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_DELETE';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_RUN';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_UPDATE';


DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_CREATE';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_DELETE';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_RUN';
DELETE FROM pcs_mec_message_notice WHERE id = 'PIP_UPDATE';

DELETE FROM pcs_mec_message_type WHERE id = 'PIP_CREATE';
DELETE FROM pcs_mec_message_type WHERE id = 'PIP_DELETE';
DELETE FROM pcs_mec_message_type WHERE id = 'PIP_RUN';
DELETE FROM pcs_mec_message_type WHERE id = 'PIP_UPDATE';

DELETE FROM pcs_op_log_type WHERE id = 'PIP_CREATE';
DELETE FROM pcs_op_log_type WHERE id = 'PIP_DELETE';
DELETE FROM pcs_op_log_type WHERE id = 'PIP_RUN';
DELETE FROM pcs_op_log_type WHERE id = 'PIP_UPDATE';


-- 1.0.9
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
VALUES ('c6c0042fd942', '消息通知方案', 'pip_message_notice', NULL, 41, '2');

INSERT INTO pcs_prc_role_function (id, role_id, function_id)
VALUES ('6aad8f7a21d4', '2', 'c6c0042fd942');



-- 1.0.12

--  pip_task
UPDATE pip_task
SET task_type = 'gittok'
WHERE task_type = 'gittork';

UPDATE pip_task
SET task_type = 'gittok'
WHERE task_type = 'xcode';


UPDATE pip_task
SET task_type = 'hadess'
WHERE task_type = 'xpack';

-- 1.0.13

DELETE FROM pip_setting_cache;
INSERT INTO pip_setting_cache (id, log_cache, artifact_cache) VALUES ('default', 7, 7);
ALTER TABLE pip_task_code ADD COLUMN house_id VARCHAR(255);

-- 1.0.14

-- 项目超级管理员权限
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8c409518b6f3', 'pro_111111', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7e3e631fe3c0', 'pro_111111', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a35f4c331053', 'pro_111111', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7b322bb9bfb9', 'pro_111111', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3bd7ce47e336', 'pro_111111', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a8772d7ed2b3', 'pro_111111', 'c6c0042fd942');


-- 系统超级管理员权限
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('cdd35c7028e3', '111111', 'f79c084575fa');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('ecd5e6df254d', '111111', 'f6f51f944133');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('af740487cb09', '111111', '7d69fed448ee');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f4d0715c6f72', '111111', '0a783e372066');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('fe10e6edc655', '111111', '9a85c4041ea9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('11888ad5d918', '111111', 'c8774229c6b8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('61839a8ac0af', '111111', '262b426e2a86');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('225f4b68fed4', '111111', 'ma14739a13fe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('cb1595eb0d6a', '111111', 'ma73e628fd54');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a5b2a5e95228', '111111', '47784cff8b3c');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9e55a8ebba75', '111111', '4235d2624bdf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8e328a939595', '111111', 'hf43e412151e');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9aeeed64034c', '111111', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a3a64ef3b565', '111111', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('177f310c3c17', '111111', '43e7d41decf7');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('89e225d8ace1', '111111', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('17ee1e849d86', '111111', '64bdf62686a4');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('4898e2bb0913', '111111', 'cb6c8c3f4048');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d60514918792', '111111', '447d9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3d4900af1701', '111111', 'e5b34be19fab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('1213c6b8191c', '111111', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('940c4fa6db68', '111111', '890e7d41decf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a0c7e1044bc8', '111111', '9633d9475886');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bf85a42f9fc3', '111111', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f2745b90f612', '111111', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('26100d580467', '111111', '428be660dea3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bc1a0a5957fd', '111111', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('b35accfbda9c', '111111', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('457e008a2430', '111111', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f89e6124cdbf', '111111', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('03b78519d220', '111111', '325c2503007f');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('32268245ab1f', '111111', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('b77104634e86', '111111', '043e412151db');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d7f427d4dc99', '111111', '925371be8ec6');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f5715ead1cbc', '111111', '4cc4e67319a0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c5e6b386de2a', '111111', 'resources845');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7939e4c93d27', '111111', '9c57b5343ffe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c3c1e78a27b1', '111111', '03dd3aa23ce9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('fcd77bbd8153', '111111', 'c80b65d2cb97');

-- 1.0.15
-- 消息模板更新
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_CREATE', msg_send_type_id = 'qywechat', title = NULL, content = '## 创建流水线\n
> 创建人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=info>${pipelineName}</font>', link = NULL, bgroup = 'arbess', link_params = NULL WHERE id = 'ea2656a4c4bf';

UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_DELETE', msg_send_type_id = 'qywechat', title = NULL, content = '## 删除流水线\n
> 操作人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=warning>${pipelineName}</font>', link = NULL, bgroup = 'arbess', link_params = NULL WHERE id = 'a48b296dce37';

UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_UPDATE', msg_send_type_id = 'qywechat', title = NULL, content = '## 更新流水线名称\n
> 操作人：<font color=comment>${userName}</font>\n
> 更新前名称：<font color=comment>${lastName}</font>\n
> 更新前后名称：<font color=warning>${pipelineName}</font>', link = NULL, bgroup = 'arbess', link_params = NULL WHERE id = 'bb09db8fa4d1';

UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_RUN', msg_send_type_id = 'qywechat', title = NULL, content = '## 流水线执行信息\n
> 执行人：<font color=comment>${userName}</font>\n
> 执行流水线：<font color=comment>${pipelineName}</font>\n
> 执行时间：<font color=comment>${execTime}</font>\n
> 执行状态：<font color=${colour}>**${execStatus}**</font>', link = NULL, bgroup = 'arbess', link_params = NULL WHERE id = '7ff3948a5c9b';


-- 1.0.16
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_RUN', msg_send_type_id = 'qywechat', title = NULL, content =   '## 流水线执行信息\n
> 执行人：<font color=comment>${userName}</font>\n
> 流水线：<font color=comment>[${pipelineName}](${qywxurl})</font>\n
> 执行时间：<font color=comment>${execTime}</font>\n
> 执行状态：<font color=${colour}>**${execStatus}**</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = '7ff3948a5c9b';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_CREATE', msg_send_type_id = 'qywechat', title = NULL, content =   '## 创建流水线\n
> 创建人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=info>[${pipelineName}](${qywxurl})</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = 'ea2656a4c4bf';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_DELETE', msg_send_type_id = 'qywechat', title = NULL, content =   '## 删除流水线\n
> 执行人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=warning>[${pipelineName}](${qywxurl})</font>', link = '/#/home', bgroup = 'arbess', link_params = NULL WHERE id = 'a48b296dce37';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_UPDATE', msg_send_type_id = 'qywechat', title = NULL, content =   '## 更新流水线名称\n
> 执行人：<font color=comment>${userName}</font>\n
> 更新前名称：<font color=comment>${lastName}</font>\n
> 更新前后名称：<font color=warning>[${pipelineName}](${qywxurl})</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = 'bb09db8fa4d1';

-- 1.0.17
create table IF NOT EXISTS pip_agent  (
  id varchar(256) PRIMARY KEY ,
  name varchar(256)  ,
  ip varchar(256) ,
  tenant_id varchar(256) ,
  address varchar(256)  ,
  create_time varchar(256),
  business_type varchar(256)
  );

-- 1.0.18

ALTER TABLE pip_task_deploy ADD COLUMN k8s_namespace VARCHAR(255) default 'default';
ALTER TABLE pip_task_deploy ADD COLUMN k8s_json text;

ALTER TABLE pip_task_build MODIFY COLUMN docker_order text;


-- 1.0.19

ALTER TABLE pip_task_deploy ADD COLUMN strategy_type VARCHAR(255) default 'default';
ALTER TABLE pip_task_deploy ADD COLUMN strategy_number int default 1;

create table pip_task_deploy_instance  (
   id varchar(64) PRIMARY KEY,
   task_instance_id varchar(255) ,
   name varchar(64),
   run_time varchar(64),
   run_status varchar(255),
   sort int,
   run_log text
);

-- 1.0.20

--  pip_task
UPDATE pip_task SET task_type = 'gitpuk' WHERE task_type = 'gittok';

UPDATE pip_task SET task_type = 'testrubo' WHERE task_type = 'teston';

--
UPDATE pcs_tool_app_link SET app_type = 'arbess'  WHERE app_type = 'matflow';

UPDATE pcs_op_log SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_op_log_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_op_log_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_notice SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_dispatch_item SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';


UPDATE pip_auth_server SET type = 'testrubo' WHERE type = 'teston';
UPDATE pip_auth_server SET type = 'gitpuk' WHERE type = 'gittok';


-- 1.0.21
create table pip_auth_host_k8s  (
  host_id varchar(64) PRIMARY KEY,
  type varchar(32) ,
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


UPDATE pip_task SET task_type = 'testhubo' WHERE task_type = 'testrubo';


UPDATE pip_auth_server SET type = 'testhubo' WHERE type = 'testrubo';
