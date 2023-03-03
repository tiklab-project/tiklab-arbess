
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_CREATE', '流水线创建消息', '流水线创建消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_DELETE', '流水线删除消息', '流水线删除消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_UPDATE', '流水线更新消息', '流水线更新消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_RUN', '流水线运行消息', '流水线运行消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_TASK_RUN', '流水线任务运行', '流水线任务运行', 'matflow');

INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0eb63efc2afb2c42922a5f4eb40322ee', 'MES_PIPELINE_DELETE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>删除了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('c87c723ac6c75958290f37405804cd7b', 'MES_PIPELINE_CREATE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>创建了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('255688648ccba57b0902fa502c677dfe', 'MES_PIPELINE_RUN', 'site', '运行', '<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('5c32aa18973df4c45a42f60752d7710e', 'MES_PIPELINE_RUN', 'dingding', '运行', '${pipelineName}${message}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('91ed9da0bcc7c1d3a76407bfa1c80297', 'MES_PIPELINE_RUN', 'qywechat', NULL, '${pipelineName}${message}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('d5264eac1729809cba38f383c69dc580', 'MES_PIPELINE_RUN', 'email', '运行', '${pipelineName}${message}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0a5e5589435eecf85caafae4fd7d6b23', 'MES_PIPELINE_TASK_RUN', 'dingding', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0acf89d305952cf42d446d2973905d96', 'MES_PIPELINE_TASK_RUN', 'email', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('31ef95545115a6ba9d4572264d9bddc8', 'MES_PIPELINE_TASK_RUN', 'qywechat', NULL, '${taskMessage}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('7fa67bf3d03d3fa4debd8062054c751d', 'MES_PIPELINE_TASK_RUN', 'site', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/tasks/${pipelineId}/structure', 'matflow');


INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('46d69ceeac263a19c1026ed704f44e27', 'MES_UPDATE', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('7f600874216fa4dd55c0a028b2f00cc7', 'MES_DELETE', '1');
INSERT INTO `pcs_mec_message_notice_connect_role` (`id`, `message_notice_id`, `role_id`) VALUES ('c6fb3cb6a4d5b55d834a6942155be296', 'MES_CREATE', '1');

INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_CREATE', 'MES_PIPELINE_CREATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_DELETE', 'MES_PIPELINE_DELETE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_UPDATE', 'MES_PIPELINE_UPDATE', 1, 'matflow', 'dingding,email,qywechat,site');
















