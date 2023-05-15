-- 消息发送类型
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_CREATE', '流水线创建消息', '流水线创建消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_DELETE', '流水线删除消息', '流水线删除消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_UPDATE', '流水线更新消息', '流水线更新消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIPELINE_RUN', '流水线运行消息', '流水线运行消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('INE_TASK_RUN', '流水线任务运行', '流水线任务运行', 'matflow');

-- 消息发送类型模板
INSERT INTO pcs_mec_message_template VALUES ('0a5e5589435e', 'INE_TASK_RUN', 'dingding', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('0acf89d30595', 'INE_TASK_RUN', 'email', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('0eb63efc2afb', 'ELINE_DELETE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>删除了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('255688648ccb', 'PIPELINE_RUN', 'site', '运行', '<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('31ef95545115', 'INE_TASK_RUN', 'qywechat', NULL, '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('5c32aa18973d', 'PIPELINE_RUN', 'dingding', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('7fa67bf3d03d', 'INE_TASK_RUN', 'site', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('91ed9da0bcc7', 'PIPELINE_RUN', 'qywechat', NULL, '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('c87c723ac6c7', 'ELINE_CREATE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>创建了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'matflow', NULL);
INSERT INTO pcs_mec_message_template VALUES ('d5264eac1729', 'PIPELINE_RUN', 'email', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);

-- 消息发送接收人类型
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('46d69ceeac26', 'MES_UPDATE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('7f600874216f', 'MES_DELETE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('c6fb3cb6a4d5', 'MES_CREATE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('aa06b354036c', 'MES_RUN', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('ece0c1aa8cad', 'MES_TASK_RUN', '1');

-- 消息发送通知方案
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_CREATE', 'ELINE_CREATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_DELETE', 'ELINE_DELETE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_UPDATE', 'ELINE_UPDATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_RUN', 'PIPELINE_RUN', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_TASK_RUN', 'INE_TASK_RUN', 1, 'matflow', 'dingding,email,qywechat,site');

-- 更新任务日志打印

update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '0a5e5589435e';
update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '0acf89d30595';
update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '31ef95545115';
update pcs_mec_message_template set content = '任务<span style="color: #5d70ea">${taskName}</span>${message}' where id = '7fa67bf3d03d';
















