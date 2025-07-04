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



-- 1.0.10
-- 对于 MF_MES_TYPE_RUN，MySQL 不支持 ON CONFLICT，改用 INSERT ... ON DUPLICATE KEY UPDATE
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_RUN', 'MF_MES_TYPE_RUN', 2, 'arbess', 'site,email,qywechat', 1)
    ON DUPLICATE KEY UPDATE
                         message_type_id = VALUES(message_type_id),
                         type = VALUES(type),
                         bgroup = VALUES(bgroup),
                         message_send_type_id = VALUES(message_send_type_id),
                         scope = VALUES(scope);

-- 对于 MF_MES_TYPE_UPDATE
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_UPDATE', 'MF_MES_TYPE_UPDATE', 2, 'arbess', 'site,email,qywechat', 1)
    ON DUPLICATE KEY UPDATE
                         message_type_id = VALUES(message_type_id),
                         type = VALUES(type),
                         bgroup = VALUES(bgroup),
                         message_send_type_id = VALUES(message_send_type_id),
                         scope = VALUES(scope);

-- 1.0.11
DELETE FROM pcs_mec_message_notice WHERE id IN ('0fae4e670d99', 'MES_CREATE', 'MES_DELETE', 'MES_UPDATE', 'MES_RUN', 'MES_TASK_RUN');

-- 初始化项目
INSERT INTO pip_pipeline (id, name, user_id, create_time, type, state, power, color, env_id, group_id)
VALUES ('fda56c97a076', '示例项目', '111111', '2024-11-27 10:00:01', 2, 1, 1, 5, 'default', 'default');

-- 初始化阶段
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES
                                                                                                        ('4e8b673f2d47', '2024-11-27 10:00:01', '源码', NULL, 1, '9a2d5cb7ae76', 'false'),
                                                                                                        ('10b7125fd8ca', '2024-11-27 10:00:35', '并行阶段-2-1', NULL, 1, '5d8ec00d6009', 'false'),
                                                                                                        ('038e993f8aef', '2024-11-27 10:00:47', '并行阶段-3-1', NULL, 1, 'aeb270dff1d6', 'false'),
                                                                                                        ('2aadef0b6b85', '2024-11-27 10:00:56', '并行阶段-4-1', NULL, 1, '54dbea9c00bf', 'false'),
                                                                                                        ('e30118e27731', '2024-11-27 10:01:01', '并行阶段-5-1', NULL, 1, '3c9918cc1741', 'false'),
                                                                                                        ('9a2d5cb7ae76', '2024-11-27 10:00:01', '源码', 'fda56c97a076', 1, NULL, 'true'),
                                                                                                        ('5d8ec00d6009', '2024-11-27 10:00:29', '代码扫描', 'fda56c97a076', 2, NULL, 'false'),
                                                                                                        ('aeb270dff1d6', '2024-11-27 10:00:40', '测试', 'fda56c97a076', 3, NULL, 'false'),
                                                                                                        ('54dbea9c00bf', '2024-11-27 10:00:56', '构建', 'fda56c97a076', 4, NULL, 'false'),
                                                                                                        ('3c9918cc1741', '2024-11-27 10:01:01', '部署', 'fda56c97a076', 5, NULL, 'false');

-- 初始化任务
INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES
                                                                                                                        ('6c622862e172', '2024-11-27 10:00:01', '4e8b673f2d47', '通用Git', NULL, 'git', 1, NULL),
                                                                                                                        ('d725c5e8214f', '2024-11-27 10:00:35', '10b7125fd8ca', 'Java代码扫描', NULL, 'spotbugs', 1, NULL),
                                                                                                                        ('d22436d820f4', '2024-11-27 10:00:48', '038e993f8aef', 'Maven单元测试', NULL, 'maventest', 1, NULL),
                                                                                                                        ('ceb7b80f990b', '2024-11-27 10:00:56', '2aadef0b6b85', 'Maven构建', NULL, 'maven', 1, NULL),
                                                                                                                        ('1aef0331461d', '2024-11-27 10:01:01', 'e30118e27731', '主机部署', NULL, 'liunx', 1, NULL);

-- 初始化任务详情
INSERT INTO pip_task_code (task_id, code_name, code_address, code_branch, svn_file, auth_id, xcode_id, branch_id, house_id) VALUES
    ('6c622862e172', 'https://gitee.com/tiklab-project/tiklab-arbess.git', 'https://gitee.com/tiklab-project/tiklab-arbess.git', 'master', NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_code_scan (task_id, type, auth_id, project_name, open_assert, open_debug, scan_path, err_grade, scan_grade) VALUES
    ('d725c5e8214f', NULL, NULL, NULL, 'false', 'false', '${DEFAULT_CODE_ADDRESS}', 'default', 'default');

INSERT INTO pip_task_test (task_id, test_order, address, test_space, test_plan, api_env, app_env, web_env, auth_id) VALUES
    ('d22436d820f4', 'mvn test', '${DEFAULT_CODE_ADDRESS}', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_build (task_id, build_address, build_order, docker_name, docker_version, docker_file, docker_order, product_rule) VALUES
    ('ceb7b80f990b', '${DEFAULT_CODE_ADDRESS}', 'mvn clean package', NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_deploy (task_id, auth_type, local_address, auth_id, deploy_address, deploy_order, start_address, start_order, rule, docker_image, k8s_namespace, k8s_json, strategy_type, strategy_number) VALUES
    ('1aef0331461d', 1, '${DEFAULT_CODE_ADDRESS}', 'ceedf0d9242c', '/usr/local/apps', NULL, NULL, NULL, 'tiklab.*\\.tar\\.gz', NULL, 'default', NULL, 'default', 1);

-- 权限
INSERT INTO pcs_prc_role (id, name, description, grouper, type, business_type, default_role, scope, parent_id) VALUES
                                                                                                                   ('30c03c1bbafd', '普通用户', '普通用户', 'system', 2, 0, 1, 2, '1efcc15ab020'),
                                                                                                                   ('44563b8d8d1a', '项目超级管理员', '项目超级管理员角色,只能存在一个。', 'system', 2, 2, 2, 2, 'pro_111111'),
                                                                                                                   ('921c8468f084', '管理员', '管理员', 'system', 2, 1, 0, 2, '2');

INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES
                                                                        ('6a2bc0b218d6', 'fda56c97a076', '921c8468f084', 1),
                                                                        ('32ced8240a7f', 'fda56c97a076', '30c03c1bbafd', 0),
                                                                        ('81c1869f6a02', 'fda56c97a076', '44563b8d8d1a', 2);

INSERT INTO pcs_prc_dm_role_user (id, dmrole_id, domain_id, user_id) VALUES
    ('cb62af0d8da2', '81c1869f6a02', 'fda56c97a076', '111111');

INSERT INTO pcs_ucc_dm_user (id, domain_id, user_id, type, status) VALUES
    ('704af514eca8', 'fda56c97a076', '111111', 0, 1);

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES
                                                                 ('de0f51b120aa', '30c03c1bbafd', '8700a662f1ae'),
                                                                 ('3d480d6bdc70', '30c03c1bbafd', 'c08882dca6ab'),

                                                                 ('dbbfb92ac452', '44563b8d8d1a', '8700a662f1ae'),
                                                                 ('c0a44dc96af4', '44563b8d8d1a', 'c6c0042fd942'),
                                                                 ('0a0950c96e0e', '44563b8d8d1a', 'd5e1ecaae2e0'),
                                                                 ('b888302df319', '44563b8d8d1a', '96950f6c591d'),
                                                                 ('89ecf651b7cb', '44563b8d8d1a', 'c08882dca6ab'),
                                                                 ('a6e856c8c73c', '44563b8d8d1a', 'd5535a61c9ce'),

                                                                 ('f7ec77048158', '921c8468f084', 'c6c0042fd942'),
                                                                 ('34ed85731481', '921c8468f084', '8700a662f1ae'),
                                                                 ('c667ef7169ba', '921c8468f084', '96950f6c591d'),
                                                                 ('835ff21804d9', '921c8468f084', 'd5e1ecaae2e0'),
                                                                 ('5c0d3a3a8e63', '921c8468f084', 'c08882dca6ab'),
                                                                 ('f7fc96626f6c', '921c8468f084', 'd5535a61c9ce');

-- 消息
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES
                                                                                                                 ('e39e5c061341', 'MF_MES_TYPE_RUN', 2, 'arbess', 'site,email,qywechat', 2, 'true'),
                                                                                                                 ('bdf9d092f442', 'MF_MES_TYPE_UPDATE', 2, 'arbess', 'site,email,qywechat', 2, 'true');

INSERT INTO pcs_mec_message_dm_notice (id, message_notice_id, domain_id, source_notice_id, is_open) VALUES
                                                                                                        ('cd3483e44201', 'e39e5c061341', 'fda56c97a076', 'MF_MES_TYPE_RUN', 'true'),
                                                                                                        ('4a91fd82490e', 'bdf9d092f442', 'fda56c97a076', 'MF_MES_TYPE_UPDATE', 'true');

-- 消息

INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('d803fc01f4ef', 'e39e5c061341', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('66d242fe7029', 'bdf9d092f442', '111111');


INSERT INTO pip_auth_host (host_id, type, name, create_time, ip, port, auth_type, username, password, private_key, auth_id, auth_public, user_id) VALUES ('ceedf0d9242c', 'common', 'dev', '2024-11-25 13:57:05', '172.10.1.12', '22', 1, 'admin', '123456', NULL, NULL, 0, '111111');


ALTER TABLE pip_task_code
    ADD COLUMN auth_type varchar(32),
  ADD COLUMN username varchar(255),
  ADD COLUMN password varchar(255),
  ADD COLUMN pri_key text;


-- 添加新字段（必须手动确保不存在）
ALTER TABLE pip_task_pull_artifact ADD COLUMN artifact_name VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN artifact_type VARCHAR(255);

-- 删除旧字段（每个字段必须单独删除，MySQL 无 IF NOT EXISTS 支持，执行前需手动确认字段存在）
ALTER TABLE pip_auth_host_k8s DROP COLUMN ip;
ALTER TABLE pip_auth_host_k8s DROP COLUMN type;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_type;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_id;
ALTER TABLE pip_auth_host_k8s DROP COLUMN port;
ALTER TABLE pip_auth_host_k8s DROP COLUMN username;
ALTER TABLE pip_auth_host_k8s DROP COLUMN password;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_public;
ALTER TABLE pip_auth_host_k8s DROP COLUMN private_key;

-- 添加新字段（MySQL 不支持 ADD COLUMN IF NOT EXISTS，需提前判断字段是否存在）
ALTER TABLE pip_auth_host_k8s ADD COLUMN kube_config TEXT;
-- ALTER TABLE pip_auth_host_k8s ADD COLUMN user_id VARCHAR(255);
ALTER TABLE pip_auth_host_k8s ADD COLUMN tool_id VARCHAR(255);
ALTER TABLE pip_auth_host_k8s ADD COLUMN kube_address VARCHAR(255);

-- 修改字段名
ALTER TABLE pip_auth_host_k8s CHANGE COLUMN host_id id BIGINT;

-- 修改表名
RENAME TABLE pip_auth_host_k8s TO pip_k8s;

-- 添加新字段到 pip_task_deploy 表
ALTER TABLE pip_task_deploy ADD COLUMN k8s_address VARCHAR(255);
ALTER TABLE pip_task_deploy ADD COLUMN kube_conf_type VARCHAR(255) DEFAULT 'file';


ALTER TABLE pip_task_test
    ADD COLUMN test_env varchar(255);


ALTER TABLE pip_task_relevance_teston
    ADD COLUMN test_plan_id varchar(255);

ALTER TABLE pip_task_test
DROP COLUMN app_env,
DROP COLUMN api_env,
DROP COLUMN web_env;



ALTER TABLE pip_task_code_scan
    ADD COLUMN tool_sonar varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN code_type varchar(255) DEFAULT 'java';


ALTER TABLE pip_task_code_scan
    ADD COLUMN tool_source_fare varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN tool_go varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN tool_node varchar(255);



create table pip_task_code_scan_sonar (
                                          id varchar(32) PRIMARY KEY,
                                          status varchar(256) ,
                                          ncloc int ,
                                          create_time varchar(256) ,
                                          name varchar(256) ,
                                          files int ,
                                          bugs int ,
                                          code_smells int ,
                                          loophole int ,
                                          duplicated_lines int ,
                                          repetition varchar(256) ,
                                          coverage varchar(256) ,
                                          url varchar(256) ,
                                          pipeline_id varchar(32)
);


create table pip_task_code_scan_sourcefare (
                                               id varchar(32) PRIMARY KEY,
                                               status varchar(256) ,
                                               url varchar(256) ,
                                               create_time varchar(32),
                                               plan_id varchar(32),
                                               project_id varchar(32),
                                               pipeline_id varchar(32),
                                               all_trouble int,
                                               severity_trouble int,
                                               notice_trouble int,
                                               suggest_trouble int
);


ALTER TABLE pip_task_pull_artifact
    MODIFY COLUMN remote_address VARCHAR(255);

CREATE TABLE pip_trigger_webhook (
    id VARCHAR(32) PRIMARY KEY,
    name VARCHAR(256),
    url VARCHAR(256),
    `key` VARCHAR(256),
    branch VARCHAR(256),
    `type` VARCHAR(256),
    parameters VARCHAR(256),
    expires_time TIMESTAMP,
    pipeline_id VARCHAR(32)
);

ALTER TABLE pip_trigger_time
    ADD COLUMN exec_status varchar(12) default '1';

ALTER TABLE pip_trigger_webhook
    ADD COLUMN status int default 1;

create table pip_message (
    id varchar(32) PRIMARY KEY,
    name varchar(256) ,
    task_id varchar(32) ,
    `type` int default 1,
    notice_type int default 1,
    create_time timestamp ,
    pipeline_id varchar(32)
);


-- 重命名表
RENAME TABLE pip_task_message_type TO pip_message_type;

RENAME TABLE pip_task_message_user TO pip_message_user;

-- 删除字段（如果存在）
ALTER TABLE pip_message_user
DROP COLUMN receive_type;

-- 重命名字段
ALTER TABLE pip_message_user
    CHANGE COLUMN message_id id VARCHAR(255);

ALTER TABLE pip_message_user
    CHANGE COLUMN task_id message_id VARCHAR(255);


ALTER TABLE pip_message_type
    CHANGE COLUMN task_id message_id VARCHAR(255);

ALTER TABLE pip_message_type
    CHANGE COLUMN task_type send_type VARCHAR(255);


-- 删除表（如果存在）
DROP TABLE IF EXISTS pip_postprocess_message_type;

DROP TABLE IF EXISTS pip_postprocess_message_user;

DROP TABLE IF EXISTS pip_postprocess_script;


DROP TABLE IF EXISTS pip_trigger;

create table pip_trigger (
    id varchar(32) PRIMARY KEY,
    `status` int ,
    exec_type int ,
    week_time int ,
    data varchar(256) ,
    cron varchar(256) ,
    create_time timestamp ,
    pipeline_id varchar(32)
);

ALTER TABLE pip_pipeline_instance
    ADD COLUMN run_log text ;

