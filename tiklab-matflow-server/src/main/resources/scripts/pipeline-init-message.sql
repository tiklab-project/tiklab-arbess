

INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_CREATE', '流水线创建消息', '流水线创建消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_DELETE', '流水线删除消息', '流水线删除消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_UPDATE', '流水线更新消息', '流水线更新消息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_RUN', '流水线运行消息', '流水线运行消息', 'matflow');


INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0eb63efc2afb2c42922a5f4eb40322ee', 'MES_PIPELINE_DELETE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>删除了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('c87c723ac6c75958290f37405804cd7b', 'MES_PIPELINE_CREATE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>创建了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('ef7f238648019f0ea0088b0a1ad17259', 'add81ef9c02fb9d2d2b96a2ec0a97373', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>更改了流水线${message}', '/index/task/${pipelineId}/config', 'matflow');


INSERT INTO `pcs_mec_message_notice_connect` (`id`, `message_notice_id`, `type`, `type_value`) VALUES ('46d69ceeac263a19c1026ed704f44e27', 'MES_UPDATE', 7, '1');
INSERT INTO `pcs_mec_message_notice_connect` (`id`, `message_notice_id`, `type`, `type_value`) VALUES ('7f600874216fa4dd55c0a028b2f00cc7', 'MES_DELETE', 7, '1');
INSERT INTO `pcs_mec_message_notice_connect` (`id`, `message_notice_id`, `type`, `type_value`) VALUES ('c6fb3cb6a4d5b55d834a6942155be296', 'MES_CREATE', 7, '1');


INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_CREATE', 'MES_PIPELINE_CREATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_DELETE', 'MES_PIPELINE_DELETE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO `pcs_mec_message_notice` (`id`, `message_type_id`, `type`, `bgroup`, `message_send_type_id`) VALUES ('MES_UPDATE', 'MES_PIPELINE_UPDATE', 1, 'matflow', 'dingding,email,qywechat,site');















