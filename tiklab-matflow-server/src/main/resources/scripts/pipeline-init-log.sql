INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_CONFIG_CREATE', '配置创建', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">添加</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/task/${pipelineId}/config', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_CONFIG_DELETE', '配置删除', '<span style=\"color: #5d70ea\">${userName}</span>\n<span>删除</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/task/${pipelineId}/config', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_CONFIG_UPDATE', '配置更新', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">更新</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/task/${pipelineId}/config', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_CREATE', '流水线创建', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">创建流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_DELETE', '流水线删除', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">删除流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_EXEC', '流水线执行', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">执行流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/task/${pipelineId}/survey', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_RUN', '流水线运行', '<span style=\"padding-right:5px\"流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>\n执行${message}', '/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`) VALUES ('LOG_TEM_PIPELINE_UPDATE', '流水线更新', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">更新流水线</span>\n<span style=\"color: #5d70ea”>${pipelineName}</span>的${message}', '/index/task/${pipelineId}/survey', 'matflow');

INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE', '流水线', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE_AUTH', '权限变更', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE_CONFIG', '配置', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE_RUN', '运行', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE_USER', '成员邀请', 'matflow');






