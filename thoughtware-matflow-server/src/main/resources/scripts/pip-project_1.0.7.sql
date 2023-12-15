
-- 日志类型
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_DELETE', '删除流水线', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_UPDATE', '更新流水线', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_RUN', '运行流水线', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('MF_LOG_TYPE_CREATE', '创建流水线', 'matflow')ON CONFLICT (id) DO NOTHING;


-- 消息类型
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_DELETE', '删除流水线', '流水线删除消息', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_CREATE', '创建流水线', '流水线创建消息', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_UPDATE', '更新流水线', '流水线更新消息', 'matflow')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('MF_MES_TYPE_RUN', '运行流水线', '流水线运行消息', 'matflow')ON CONFLICT (id) DO NOTHING;

-- 模版
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('bb09db8fa4d1', 'MF_MES_TYPE_UPDATE', 'qywechat', NULL, '${userName}更新流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('83f3e496abe9', 'MF_MES_TYPE_UPDATE', 'email', '更新流水线', '${userName}更新流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('ea37e83bacd5', 'MF_MES_TYPE_UPDATE', 'site', '更新流水线', '${userName}更新流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('7ff3948a5c9b', 'MF_MES_TYPE_RUN', 'qywechat', NULL, '${userName}运行流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f9b8e28e710e', 'MF_MES_TYPE_RUN', 'email', '运行流水线', '${userName}运行流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f3a4f5ef0b15', 'MF_MES_TYPE_RUN', 'site', '运行流水线', '${userName}运行流水线${pipelineName} ${message}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('a48b296dce37', 'MF_MES_TYPE_DELETE', 'qywechat', NULL, '${userName}删除流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('6d4f248eea10', 'MF_MES_TYPE_DELETE', 'site', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('f976ca473d57', 'MF_MES_TYPE_DELETE', 'email', '删除流水线', '${userName}删除流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('016ff38e76d2', 'MF_MES_TYPE_CREATE', 'email', '创建流水线', '${userName}创建流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('ea2656a4c4bf', 'MF_MES_TYPE_CREATE', 'qywechat', NULL, '${userName}创建流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content, link, bgroup, link_params) VALUES ('8fb7ce53b2a2', 'MF_MES_TYPE_CREATE', 'site', '创建流水线', '${userName}创建流水线${pipelineName}', NULL, 'matflow', NULL)ON CONFLICT (id) DO NOTHING;

-- 通知方案
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_CREATE', 'MF_MES_TYPE_CREATE', 1, 'matflow', 'site,email,qywechat')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_DELETE', 'MF_MES_TYPE_DELETE', 1, 'matflow', 'site,email,qywechat')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_UPDATE', 'MF_MES_TYPE_UPDATE', 1, 'matflow', 'site,email,qywechat')ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MF_MES_TYPE_RUN', 'MF_MES_TYPE_RUN', 1, 'matflow', 'site,email,qywechat')ON CONFLICT (id) DO NOTHING;

-- 通知人员
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('28d7402e4882', 'MF_MES_TYPE_CREATE', '111111') ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('003a7384b83f', 'MF_MES_TYPE_DELETE', '111111') ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('39a1cb668773', 'MF_MES_TYPE_UPDATE', '111111') ON CONFLICT (id) DO NOTHING;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('9100ec35886e', 'MF_MES_TYPE_RUN', '111111') ON CONFLICT (id) DO NOTHING;











