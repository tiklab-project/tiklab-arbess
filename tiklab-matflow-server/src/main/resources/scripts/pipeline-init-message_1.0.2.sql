
INSERT INTO `pcs_mec_message_type` (`id`, `name`, `description`, `bgroup`) VALUES ('MES_PIPELINE_TASK_RUN', '流水线任务运行', '流水线任务运行', 'matflow');

INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0a5e5589435eecf85caafae4fd7d6b23', 'MES_PIPELINE_TASK_RUN', 'dingding', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('0acf89d305952cf42d446d2973905d96', 'MES_PIPELINE_TASK_RUN', 'email', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('31ef95545115a6ba9d4572264d9bddc8', 'MES_PIPELINE_TASK_RUN', 'qywechat', NULL, '${taskMessage}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');
INSERT INTO `pcs_mec_message_template` (`id`, `msg_type_id`, `msg_send_type_id`, `title`, `content`, `link`, `bgroup`) VALUES ('7fa67bf3d03d3fa4debd8062054c751d', 'MES_PIPELINE_TASK_RUN', 'site', '运行', '${taskMessage}', 'http://192.168.10.23:3000/#/index/task/${pipelineId}/structure', 'matflow');


update pcs_mec_message_template set pcs_mec_message_template.content = '${pipelineName}${message}' where pcs_mec_message_template.id='5c32aa18973df4c45a42f60752d7710e'






