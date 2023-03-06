
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_CONFIG_CREATE', '配置创建', '<span style=\"color: #5d70ea\">${user}</span>\n<span style=\"padding: 0 5px\">添加</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/tasks/${pipelineId}/config', 'matflow','配置创建');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_CONFIG_DELETE', '配置删除', '<span style=\"color: #5d70ea\">${user}</span>\n<span>删除</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/tasks/${pipelineId}/config', 'matflow','配置删除');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_CREATE', '流水线创建', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">创建了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'matflow','流水线创建');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_DELETE', '流水线删除', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">删除了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'matflow','流水线删除');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_RUN', '流水线运行', '流水线<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/tasks/${pipelineId}/structure', 'matflow','流水线运行');
INSERT INTO `pcs_op_log_template` (`id`, `title`, `content`, `link`, `bgroup`,abstract_content) VALUES ('LOG_TEM_UPDATE', '流水线更新', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">更新了流水线</span>${message}\n', '/index/tasks/${pipelineId}/survey', 'matflow','流水线更新');

INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_CONFIG', '配置', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_PIPELINE', '流水线', 'matflow');
INSERT INTO `pcs_op_log_type` (`id`, `name`, `bgroup`) VALUES ('LOG_RUN', '运行', 'matflow');


update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>添加了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CONFIG_CREATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>删除了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CONFIG_DELETE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${userName}</span><span>创建了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CREATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>删除了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_DELETE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>更新了流水线 ${message}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_UPDATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div>流水线<span style="color: #5d70ea">${pipelineName}</span>${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_RUN';

















































