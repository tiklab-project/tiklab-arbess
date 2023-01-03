
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('255688648ccba57b0902fa502c677dfe', 'MES_PIPELINE_RUN', 'site', '运行', '<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('5c32aa18973df4c45a42f60752d7710e', 'MES_PIPELINE_RUN', 'dingding', '运行', 'http://192.168.10.23:3000/#/index/task/${id}/structure', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('91ed9da0bcc7c1d3a76407bfa1c80297', 'MES_PIPELINE_RUN', 'qywechat', NULL, '${pipelineName}${message}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('d5264eac1729809cba38f383c69dc580', 'MES_PIPELINE_RUN', 'email', '运行', '${pipelineName}${message}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');












