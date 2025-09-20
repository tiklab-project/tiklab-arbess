

-- 通知类型
TRUNCATE TABLE pcs_mec_message_type;
TRUNCATE TABLE pcs_mec_message_notice;
TRUNCATE TABLE pcs_mec_message_template;
TRUNCATE TABLE pcs_mec_message_notice_connect_user;

-- 平台数据
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('USER_CREATE', '创建用户', '创建用户', 'soular');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('USER_DELETE', '删除用户', '删除用户', 'soular');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('USER_LOGIN', '登录应用', '登录应用', 'soular');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('USER_UPDATE', '更新用户信息', '更新用户信息', 'soular');

INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('USER_DELETE', 'USER_DELETE', 1, 'soular', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('USER_LOGIN', 'USER_LOGIN', 1, 'soular', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('USER_UPDATE', 'USER_UPDATE', 1, 'soular', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('USER_CREATE', 'USER_CREATE', 1, 'soular', 'site,email,qywechat', 1, 'true');

INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('04b435522eb4', 'USER_UPDATE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('925303fefdda', 'USER_LOGIN', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('af552290c829', 'USER_DELETE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('7b02151cd559', 'USER_CREATE', '111111');


INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('8c56f747497a', 'USER_UPDATE', 'email', '更新用户信息', '${userName}更新用户${name} ${message} ', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('e9fd730456d4', 'USER_CREATE', 'email', '创建用户', '${userName}创建${name}', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('998613ab7e71', 'USER_LOGIN', 'email', '登录应用', '${userName}登录应用', '/work', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('fe6108ae3666', 'USER_DELETE', 'email', '删除用户', '${userName}删除用户${name}', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('41dd07a55856', 'USER_UPDATE', 'qywechat', NULL, '${userName}更新用户${name} ${message} ', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('34d48bf413a7', 'USER_CREATE', 'qywechat', NULL, '${userName}创建用户${name}', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('c9fe98a3344a', 'USER_DELETE', 'qywechat', NULL, '${userName}删除用户${name}', '/setting/user', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('d8d2e673fbb6', 'USER_LOGIN', 'qywechat', NULL, '${userName}登录应用', '/work', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('USER_UPDATE', 'USER_UPDATE', 'site', '修改用户信息', '修改用户信息', NULL, 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('USER_DELETE', 'USER_DELETE', 'site', '删除用户', '删除用户', NULL, 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('USER_LOGIN', 'USER_LOGIN', 'site', '登录应用', '${name}登录应用', '/work', 'soular', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('USER_CREATE', 'USER_CREATE', 'site', '创建用户', '创建用户', NULL, 'soular', NULL);


-- 流水线数据

INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_CREATE', '创建流水线', '流水线创建消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_DELETE', '删除流水线', '流水线删除消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_UPDATE', '更新流水线', '流水线更新消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_RUN', '运行流水线', '流水线运行消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_CLEAN', '清理流水线', '清理流水线', 'arbess');


INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_REVIEW_CREATE', '新建发布单', '发布单创建消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_REVIEW_DELETE', '删除发布单', '发布单删除消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_ENV_UPDATE', '更新变量', '更新变量消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_AGENT_TYPE_UPDATE', '变更Agent执行策略', '变更Agent执行策略消息', 'arbess');

INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_USER_ADD', '添加流水线成员', '添加流水线成员消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_PIPELINE_USER_DELETE', '删除流水线成员', '删除流水线成员', 'arbess');

INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('AR_TASK_RUN', '任务运行', '任务运行', 'arbess');

-- 通知方案
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_CREATE', 'AR_PIPELINE_CREATE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_DELETE', 'AR_PIPELINE_DELETE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_UPDATE', 'AR_PIPELINE_UPDATE', 2, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_RUN', 'AR_PIPELINE_RUN', 2, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_CLEAN', 'AR_PIPELINE_CLEAN', 2, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_REVIEW_CREATE', 'AR_REVIEW_CREATE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_REVIEW_DELETE', 'AR_REVIEW_DELETE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_ENV_UPDATE', 'AR_ENV_UPDATE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_AGENT_TYPE_UPDATE', 'AR_AGENT_TYPE_UPDATE', 1, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_USER_ADD', 'AR_PIPELINE_USER_ADD', 2, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open ) VALUES ('AR_PIPELINE_USER_DELETE', 'AR_PIPELINE_USER_DELETE', 2, 'arbess', 'site,email,qywechat', 1, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('AR_TASK_RUN', 'AR_TASK_RUN', 2, 'arbess', 'site,email,qywechat', 1, 'true');

-- 虚拟角色
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_REVIEW_CREATE', '流水线评审人员', '流水线评审人员', 1, 'AR_REVIEW_CREATE');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_REVIEW_DELETE', '流水线评审人员', '流水线评审人员', 1, 'AR_REVIEW_DELETE');

INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_ENV_UPDATE', '流水线管理员', '流水线管理员', 1, 'AR_ENV_UPDATE');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_AGENT_TYPE_UPDATE', '流水线管理员', '流水线管理员', 1, 'AR_AGENT_TYPE_UPDATE');

INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_CREATE', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_CREATE');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_DELETE', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_DELETE');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_UPDATE', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_UPDATE');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_RUN', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_RUN');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_CLEAN', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_CLEAN');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_USER_ADD', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_USER_ADD');
INSERT INTO pcs_prc_vrole (id, name, des, type, role_group) VALUES ('AR_PIPELINE_USER_DELETE', '流水线成员', '流水线成员', 2, 'AR_PIPELINE_USER_DELETE');



INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f976ca473d57', 'AR_PIPELINE_DELETE', 'email', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('6d4f248eea10', 'AR_PIPELINE_DELETE', 'site', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('7ff3948a5c9b', 'AR_PIPELINE_RUN', 'qywechat', NULL, '## 流水线执行信息 > 执行人：<font color=comment>${userName}</font> > 流水线：<font color=comment>[${pipelineName}](${qywxurl})</font> > 执行时间：<font color=comment>${execTime}</font> > 执行状态：<font color=${colour}>**${execStatus}**</font>', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('8fb7ce53b2a2', 'AR_PIPELINE_CREATE', 'site', '创建流水线', '${userName}创建流水线${pipelineName}', '/#/pipeline/${pipelineId}/config', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('016ff38e76d2', 'AR_PIPELINE_CREATE', 'email', '创建流水线', '${userName}创建流水线${pipelineName}', '/#/pipeline/${pipelineId}/config', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('ea2656a4c4bf', 'AR_PIPELINE_CREATE', 'qywechat', NULL, '## 创建流水线 > 创建人：<font color=comment>${userName}</font> > 流水线：<font color=info>[${pipelineName}](${qywxurl})</font>', '/#/pipeline/${pipelineId}/config', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('4b86f3b70139', 'AR_TASK_RUN', 'site', '任务运行信息', '流水线${pipelineName}任务${taskName}运行完成，运行时长：${runTime},状态：${runStatus}', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('a77de8870590', 'AR_TASK_RUN', 'email', '任务运行消息', '流水线${pipelineName}任务${taskName}运行完成，运行时长：${runTime},状态：${runStatus}', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('790b20237e06', 'AR_AGENT_TYPE_UPDATE', 'site', '变更Agent执行策略', '用户${userName}变更了流水线执行策略。 ${message}', '/#/setting/agent', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('aa45c23f3684', 'AR_TASK_RUN', 'qywechat', NULL, '## 任务运行消息 > 流水线：<font color=info>[${pipelineName}](${qywxurl})</font>  > 执行时长：<font color=comment>${runTime}</font> > 运行状态：<font color=${colour}>**${runStatus}**</font>', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('99dac273156a', 'AR_REVIEW_DELETE', 'site', '删除发布单', '${userName}删除发布单${reviewId}', NULL, 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('b7f4bc9b933f', 'AR_REVIEW_DELETE', 'email', '删除发布单', '${userName}删除发布单${reviewId}', NULL, 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('1f0420d5fd5c', 'AR_AGENT_TYPE_UPDATE', 'email', '变更流水线Agent执行策略', '用户${userName}变更了流水线Agent执行策略。 ${message}', '/#/setting/agent', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('a48b296dce37', 'AR_PIPELINE_DELETE', 'qywechat', NULL, '## 删除流水线 > 执行人：<font color=comment>${userName}</font> > 流水线名称：<font color=warning>[${pipelineName}](${qywxurl})</font>', '/#/', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('bd87ba90c6a0', 'AR_REVIEW_DELETE', 'qywechat', NULL, '## 删除发布单 > 操作人：<font color=comment>${userName}</font> > 发布单：<font color=warning>[${reviewId}](${qywxurl})</font>', '/#/', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('07d7f7ed0419', 'AR_PIPELINE_USER_DELETE', 'qywechat', NULL, '## 删除流水线成员 > 执行人：<font color=comment>${userName}</font> > 成员：<font color=warning>[${delUserName}](${qywxurl})</font>', NULL, 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('3f60591658e6', 'AR_PIPELINE_USER_DELETE', 'email', '删除流水线成员', '${userName}删除流水线${pipelineName}成员${delUserName}', '/#/pipeline/${pipelineId}/setting/user', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('5d652f41d2f1', 'AR_PIPELINE_USER_DELETE', 'site', '删除流水线成员', '${userName}删除流水线${pipelineName}成员${delUserName}', '/#/pipeline/${pipelineId}/setting/user', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('a583f1afe8cf', 'AR_AGENT_TYPE_UPDATE', 'qywechat', NULL, '## 变更流水线执行策略 > 操作人：<font color=comment>${userName}</font> > 变更信息：<font color=warning>[${message}](${qywxurl})</font>', '/#/setting/agent', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('e14665af04a1', 'AR_REVIEW_CREATE', 'site', '新建发布单', '用户新建发布单${reviewName},需要审批。', '/#/release/${reviewId}', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('07004a68b480', 'AR_REVIEW_CREATE', 'email', '新建发布单', '用户新建发布单${reviewName},需要审批。', '/#/release/${reviewId}', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('e0dcf82e7746', 'AR_REVIEW_CREATE', 'qywechat', NULL, '## 新建发布单 > 操作人：<font color=comment>${userName}</font> > 发布单：<font color=warning>[${reviewName}](${qywxurl})</font>', '/#/release/${reviewId}', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('01380d4047d7', 'AR_ENV_UPDATE', 'qywechat', NULL, '## 更新变量 > 操作人：<font color=comment>${userName}</font> > 变量名称：<font color=warning>[${envName}](${qywxurl})</font> > 变动：<font color=comment>${message}</font>', '/#/setting/variable', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('14fbadd5c160', 'AR_ENV_UPDATE', 'site', '更新变量', '用户${userName}更新了变量${envName}的值。 ${message}', '/#/setting/variable', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('8dc97848b0b5', 'AR_ENV_UPDATE', 'email', '更新变量', '用户${userName}更新了变量${envName}的值。 ${message}', '/#/setting/variable', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('ea37e83bacd5', 'AR_PIPELINE_UPDATE', 'site', '更新流水线', '${userName}更新流水线${pipelineName}信息。 ${message}', '/#/pipeline/${pipelineId}/setting/info', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('83f3e496abe9', 'AR_PIPELINE_UPDATE', 'email', '更新流水线', '${userName}更新流水线${pipelineName}信息。 ${message}', '/#/pipeline/${pipelineId}/setting/info', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('3509277e2e87', 'AR_PIPELINE_USER_ADD', 'qywechat', NULL, '## 添加流水线成员 > 操作人：<font color=comment>${userName}</font> > 流水线：<font color=warning>[${pipelineName}](${qywxurl})</font> > 成员：<font color=comment>${addUserName}</font>', '/#/pipeline/${pipelineId}/setting/user', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('bb09db8fa4d1', 'AR_PIPELINE_UPDATE', 'qywechat', NULL, '## 更新流水线 > 操作人：<font color=comment>${userName}</font> > 流水线：<font color=warning>[${pipelineName}](${qywxurl})</font> > 变动：<font color=comment>${message}</font>', '/#/pipeline/${pipelineId}/setting/info', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('5adbe973b120', 'AR_PIPELINE_USER_ADD', 'email', '添加流水线成员', '用户${userName}添加成员${addUserName}到流水线${pipelinName}', '/#/pipeline/${pipelineId}/setting/user', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('827a198a497c', 'AR_PIPELINE_CLEAN', 'site', '清理流水线', '用户${userName}清理了的流水线缓存。', '/#/pipeline/${pieplienId}/config', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('87484022654c', 'AR_PIPELINE_USER_ADD', 'site', '添加流水线成员', '用户${userName}添加成员${addUserName}到流水线${pipelinName}', '/#/pipeline/${pipelineId}/setting/user', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f9b8e28e710e', 'AR_PIPELINE_RUN', 'email', '运行流水线', '流水线${pipelineName}运行完成，运行时长：${runTime},状态：${runStatus}', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f3a4f5ef0b15', 'AR_PIPELINE_RUN', 'site', '运行流水线', '流水线${pipelineName}任务${taskName}运行完成，运行时长：${runTime},状态：${runStatus}', '/#/pipeline/${pipelineId}/history', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('afc5e0bb7cbe', 'AR_PIPELINE_CLEAN', 'email', '清理流水线', '用户${userName}清理了的流水线缓存。', '/#/pipeline/${pieplienId}/config', 'arbess', NULL);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('a526f82aa7fa', 'AR_PIPELINE_CLEAN', 'qywechat', NULL, '## 清理流水线 > 操作人：<font color=comment>${userName}</font> > 流水线：<font color=warning>[${pipelineName}](${qywxurl})</font> ', '/#/pipeline/${pieplienId}/config', 'arbess', NULL);


-- 通知方法成员
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('4373f1b84bac', 'AR_PIPELINE_CREATE', 'AR_PIPELINE_CREATE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('cf291e0ba57b', 'AR_PIPELINE_DELETE', 'AR_PIPELINE_DELETE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('49b6c53d975d', 'AR_REVIEW_CREATE', 'AR_REVIEW_CREATE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('f93bd7dbb709', 'AR_REVIEW_DELETE', 'AR_REVIEW_DELETE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('fa55dd4a0828', 'AR_ENV_UPDATE', 'AR_ENV_UPDATE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('fc664dc2d708', 'AR_AGENT_TYPE_UPDATE', 'AR_AGENT_TYPE_UPDATE');

INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('zc664dcsd718', 'AR_PIPELINE_UPDATE', 'AR_PIPELINE_UPDATE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('c664dc2d728', 'AR_PIPELINE_RUN', 'AR_PIPELINE_RUN');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('yc46edced738', 'AR_PIPELINE_CLEAN', 'AR_PIPELINE_CLEAN');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('rce64rc4dd48', 'AR_PIPELINE_USER_ADD', 'AR_PIPELINE_USER_ADD');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('wcg62dc2d758', 'AR_PIPELINE_USER_DELETE', 'AR_PIPELINE_USER_DELETE');
INSERT INTO pcs_mec_message_notice_connect_vrole (id, message_notice_id, vrole_id) VALUES ('jc6yjdc2dh68', 'AR_TASK_RUN', 'AR_TASK_RUN');





