INSERT INTO `pcs_mec_message_template` (`id`, `name`, `msg_type_id`, `msg_send_type_id`, `title`, `content_config_type`, `content`, `content_url`, `link`, `bgroup`) VALUES ('pipelineCreate', '流水线创建信息', 'das95e3d943c66cdae31c68ab130182c', '站内发送', '流水线创建信息', 1, '<span><font color=\"blue\">${userName}</font></span>创建了流水线<span><font color=\"blue\">${pipelineName}</font></span>', NULL, '${pipelineId}', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `name`, `msg_type_id`, `msg_send_type_id`, `title`, `content_config_type`, `content`, `content_url`, `link`, `bgroup`) VALUES ('pipelineDelete', '流水线删除信息', '06e95e3d943c66cdae31c68ab130182c', '站内发送', '流水线删除信息', 1, '<span><font color=\"blue\">${userName}</font></span>删除了流水线<span><font color=\"blue\">${pipelineName}</font></span>', NULL, '${pipelineId}', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `name`, `msg_type_id`, `msg_send_type_id`, `title`, `content_config_type`, `content`, `content_url`, `link`, `bgroup`) VALUES ('pipelineExec', '流水线执行信息', '4ae0772966592ad3b969f2787db48539', '站内发送', '流水线执行信息', 1, '<span><font color=\"blue\">${userName}</font></span>执行了流水线<span><font color=\"blue\">${pipelineName}</font></span>', NULL, '${pipelineId}', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `name`, `msg_type_id`, `msg_send_type_id`, `title`, `content_config_type`, `content`, `content_url`, `link`, `bgroup`) VALUES ('pipelineRun', '流水线运行信息', '07d0194bd6e2a8eb730f1240d050c964', '站内发送', '流水线运行信息', 1, '</span>流水线<span><font color=\"blue\">${pipelineName}</font></span>${message}', NULL, '${pipelineId}', 'matflow');


INSERT INTO `pcs_mec_message_send_type` (`id`, `name`, `code`, `description`, `bgroup`) VALUES ('站内发送', '站内发送', 'site', '站内发送', 'matflow');


INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('06e95e3d943c66cdae31c68ab130182c', '流水线删除信息', '流水线删除信息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('07d0194bd6e2a8eb730f1240d050c964', '流水线运行信息', '流水线运行信息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('4ae0772966592ad3b969f2787db48539', '流水线执行信息', '流水线执行信息', 'matflow');
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('das95e3d943c66cdae31c68ab130182c', '流水线信息', '流水线信息', 'matflow');
