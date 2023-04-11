-- 消息发送类型
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_CREATE', '流水线创建消息', '流水线创建消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_DELETE', '流水线删除消息', '流水线删除消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_UPDATE', '流水线更新消息', '流水线更新消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_RUN', '流水线运行消息', '流水线运行消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_TASK_RUN', '流水线任务运行', '流水线任务运行', 'matflow');

-- 消息发送类型模板
INSERT INTO `pcs_mec_message_template` VALUES ('0a5e5589435eecf85caafae4fd7d6b23', 'MES_PIPELINE_TASK_RUN', 'dingding', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('0acf89d305952cf42d446d2973905d96', 'MES_PIPELINE_TASK_RUN', 'email', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('0eb63efc2afb2c42922a5f4eb40322ee', 'MES_PIPELINE_DELETE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>删除了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('255688648ccba57b0902fa502c677dfe', 'MES_PIPELINE_RUN', 'site', '运行', '<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('31ef95545115a6ba9d4572264d9bddc8', 'MES_PIPELINE_TASK_RUN', 'qywechat', NULL, '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('5c32aa18973df4c45a42f60752d7710e', 'MES_PIPELINE_RUN', 'dingding', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('7fa67bf3d03d3fa4debd8062054c751d', 'MES_PIPELINE_TASK_RUN', 'site', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('91ed9da0bcc7c1d3a76407bfa1c80297', 'MES_PIPELINE_RUN', 'qywechat', NULL, '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('c87c723ac6c75958290f37405804cd7b', 'MES_PIPELINE_CREATE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>创建了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'matflow', NULL);
INSERT INTO `pcs_mec_message_template` VALUES ('d5264eac1729809cba38f383c69dc580', 'MES_PIPELINE_RUN', 'email', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'matflow', NULL);

-- 消息发送接收人类型
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('46d69ceeac263a19c1026ed704f44e27', 'MES_UPDATE', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('7f600874216fa4dd55c0a028b2f00cc7', 'MES_DELETE', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('c6fb3cb6a4d5b55d834a6942155be296', 'MES_CREATE', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('aa06b354036c04e3829eece0c1aa8cad', 'MES_RUN', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('tsa0dasasd29eece0c1aa8cad', 'MES_TASK_RUN', '1');

-- 消息发送通知方案
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_CREATE', 'MES_PIPELINE_CREATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_DELETE', 'MES_PIPELINE_DELETE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_UPDATE', 'MES_PIPELINE_UPDATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_RUN', 'MES_PIPELINE_RUN', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_TASK_RUN', 'MES_PIPELINE_TASK_RUN', 1, 'matflow', 'dingding,email,qywechat,site');

-- 更新任务日志打印

update pcs_mec_message_template set pcs_mec_message_template.content = '任务${taskName}${message}' where pcs_mec_message_template.id = '0a5e5589435eecf85caafae4fd7d6b23';
update pcs_mec_message_template set pcs_mec_message_template.content = '任务${taskName}${message}' where pcs_mec_message_template.id = '0acf89d305952cf42d446d2973905d96';
update pcs_mec_message_template set pcs_mec_message_template.content = '任务${taskName}${message}' where pcs_mec_message_template.id = '31ef95545115a6ba9d4572264d9bddc8';
update pcs_mec_message_template set pcs_mec_message_template.content = '任务<span style="color: #5d70ea">${taskName}</span>${message}' where pcs_mec_message_template.id = '7fa67bf3d03d3fa4debd8062054c751d';
















