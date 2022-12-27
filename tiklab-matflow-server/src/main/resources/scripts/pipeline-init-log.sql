
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_CONFIG_CREATE', '配置创建', '<span style=\"color: #5d70ea\">${user}</span>\n<span style=\"padding: 0 5px\">添加</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/task/${pipelineId}/config', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_CONFIG_DELETE', '配置删除', '<span style=\"color: #5d70ea\">${user}</span>\n<span>删除</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/task/${pipelineId}/config', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_CREATE', '流水线创建', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">创建了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_DELETE', '流水线删除', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">删除了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_RUN', '流水线运行', '流水线<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_UPDATE', '流水线更新', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">更新了流水线</span>${message}\n', '/index/task/${pipelineId}/survey', 'matflow');

INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_CONFIG', '配置', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE', '流水线', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_RUN', '运行', 'matflow');


















































