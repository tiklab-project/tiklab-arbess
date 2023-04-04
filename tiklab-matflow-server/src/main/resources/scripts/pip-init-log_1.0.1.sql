
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>添加了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CONFIG_CREATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>删除了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CONFIG_DELETE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${userName}</span><span>创建了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_CREATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>删除了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_DELETE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>更新了流水线 ${message}</span></div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_UPDATE';
update pcs_op_log_template set pcs_op_log_template.content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div>流水线<span style="color: #5d70ea">${pipelineName}</span>${message}</div></div></div>' where pcs_op_log_template.id = 'LOG_TEM_RUN';


update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/config'  where pcs_op_log_template.id = 'LOG_TEM_CONFIG_CREATE';
update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/config'  where pcs_op_log_template.id = 'LOG_TEM_CONFIG_DELETE';
update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/survey' where pcs_op_log_template.id = 'LOG_TEM_CREATE';
update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/survey' where pcs_op_log_template.id = 'LOG_TEM_DELETE';
update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/survey' where pcs_op_log_template.id = 'LOG_TEM_UPDATE';
update pcs_op_log_template set pcs_op_log_template.link = '/index/pipeline/${pipelineId}/structure' where pcs_op_log_template.id = 'LOG_TEM_RUN';














































