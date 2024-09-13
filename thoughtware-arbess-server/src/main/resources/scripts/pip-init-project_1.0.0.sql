
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('03dd3aa23ce9', '系统信息', 'pipeline_system', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('0a783e372066', '权限', 'pipeline_permission', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('262b426e2a86', '插件', 'pipeline_plugin', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('47784cff8b3c', '主机配置', 'resources_host', NULL, 26, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('8700a662f1ae', '流水线设置', 'pipeline_seting', NULL, 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('96950f6c591d', '流水线成员', 'pipeline_user', NULL, 38, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('9a85c4041ea9', '服务配置', 'resources_server', NULL, 25, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('9c57b5343ffe', '认证配置', 'pipeline_auth', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c08882dca6ab', '修改流水线信息', 'pipeline_update', '8700a662f1ae', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c80b65d2cb97', '环境配置', 'pipeline_env', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c8774229c6b8', '操作日志', 'pipeline_log', NULL, 40, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('d5535a61c9ce', '删除流水线', 'pipeline_delete', '8700a662f1ae', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('d5e1ecaae2e0', '流水线权限', 'pipeline_auth', NULL, 37, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('f6f51f944133', '消息通知类型', 'message_type', NULL, 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('7d69fed448ee', '消息管理', 'message_setting', NULL, 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('f79c084575fa', '版本许可证', 'pipeline_version', NULL, 39, '1');
--INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('eqeqw84575fa', '应用授权', 'product_auth', NULL, 39, '1');

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('00e840ea5302', '1', '890e7d41decf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('05d66918b2dd', '1', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('064d1a5ddbc5', 'bf699ba68c87', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('0fd56de07eaf', '2', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('1134dbdbb6d0', '1', '9633d9475886');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('143e6010ba4b', 'f51b3e9cdf10', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('256bca68cd16', 'f51b3e9cdf10', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('28b4ec49b63c', '3f22e1a0ff00', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3572dd063f4f', '3f22e1a0ff00', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('39f06b018e83', '1', '03dd3aa23ce9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('401823a837d7', '1', '428be660dea3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('431db3316908', 'f51b3e9cdf10', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('4528a26ed101', 'f51b3e9cdf10', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('462d9282cc98', '1', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('4a5fc591234a', '1', '7d69fed448ee');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('50b9a6692ace', '1', '47784cff8b3c');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('51a182feaad3', '696e8b4575bb', '03dd3aa23ce9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('58a058ff9711', 'f51b3e9cdf10', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('5e6f1b2ae44e', 'ae28429ef243', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('5feb15faff5b', '1', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('6724ee306e70', '1efcc15ab020', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('732aa5077352', '1', 'f79c084575fa');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('74217ab2e9eb', 'ae28429ef243', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7d931f493b50', '1', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7d9f77e5e4da', '3f22e1a0ff00', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('81de829dec84', '1', '325c2503007f');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('887c296a9558', 'bf699ba68c87', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8cfe868f7e3d', '1', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8feb1f0512fe', '1', '0a783e372066');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('953b0d1b33a2', '1', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9c0d4f578ee7', '3f22e1a0ff00', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9c7732b2ce82', '1', '925371be8ec6');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9ea782824dd8', '696e8b4575bb', 'f6f51f944133');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a948facb3178', '2', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('b2e02db6d6d2', '1', 'c8774229c6b8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bbf66f07ea08', '3f22e1a0ff00', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bece5428495b', '1', '9c57b5343ffe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c04b4a8a7124', '1', '043e412151db');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c4004134de45', '1', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c5779edb1c87', '1', '447d9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c9d209973792', '696e8b4575bb', '7d69fed448ee');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d47dc6474805', '1', '262b426e2a86');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d58ab33914bd', '1efcc15ab020', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('dc13e349314c', '1', 'c80b65d2cb97');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e1a214388c4d', '1', 'f6f51f944133');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e1b6898cee7a', '696e8b4575bb', 'f79c084575fa');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e69a45532289', '1', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e6cb2507ce87', '2', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('ed97225cf0b9', '2', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f3df88d2de5b', '1', '9a85c4041ea9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f83883f4bbaa', '2', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f9011798e814', '696e8b4575bb', '9a85c4041ea9');


INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('1', '管理员', '管理员', 'system', '1', 1, 1, 0);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('2', '管理员', '管理员', 'system', '2', 1, 1, 0);

INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('1efcc15ab020', '普通用户', '普通用户', 'system', '2', 1, 0, 1);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('696e8b4575bb', '普通用户', '普通用户', 'system', '1', 1, 0, 1);


INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('3f22e1a0ff00', '管理员', '管理员', 'system', '2', 2, 1, 0);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('f51b3e9cdf10', '管理员', '管理员', 'system', '2', 2, 1, 0);

INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('ae28429ef243', '普通用户', '普通用户', 'system', '2', 2, 0, 1);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('bf699ba68c87', '普通用户', '普通用户', 'system', '2', 2, 0, 1);


INSERT INTO pcs_prc_role_user (id, role_id, user_id) VALUES ('d0c4e835c7a2', 'f51b3e9cdf10', '111111');
INSERT INTO pcs_prc_role_user (id, role_id, user_id) VALUES ('2adeb79ba186', '3f22e1a0ff00', '111111');

INSERT INTO pcs_prc_role_user (id, role_id,user_id) VALUES ('2e4b7342485b', '2', '111111');

-- 用户组信息
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('23wa081e7588', '1', '4235d2624bdf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw474ad31d1', '1', 'hf43e412151e');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw0011966d1', '1', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw8d88e5205', '1', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqwbbaa68277', '1', '43e7d41decf7');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqwl1fc798ae', '1', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('asdas1fc798a', '1', 'eqeqw84575fa');


INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('ONFIG_CREATE', '配置创建', '<span style=\"color: #5d70ea\">${user}</span>\n<span style=\"padding: 0 5px\">添加</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/tasks/${pipelineId}/config', 'arbess','配置创建');
INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('ONFIG_DELETE', '配置删除', '<span style=\"color: #5d70ea\">${user}</span>\n<span>删除</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>的${message}', '/index/tasks/${pipelineId}/config', 'arbess','配置删除');
INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('G_TEM_CREATE', '流水线创建', '<span style=\"color: #5d70ea\">${userName}</span>\n<span style=\"padding: 0 5px\">创建了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'arbess','流水线创建');
INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('G_TEM_DELETE', '流水线删除', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">删除了流水线</span>\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/tasks/${pipelineId}/survey', 'arbess','流水线删除');
INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('LOG_TEM_RUN', '流水线运行', '流水线<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/tasks/${pipelineId}/structure', 'arbess','流水线运行');
INSERT INTO pcs_op_log_template (id, title, content, link, bgroup,abstract_content) VALUES ('G_TEM_UPDATE', '流水线更新', '<span  style=\"color: #5d70ea\">${userName}</span>\n<span  style=\"padding: 0 5px\">更新了流水线</span>${message}\n', '/index/tasks/${pipelineId}/survey', 'arbess','流水线更新');

INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('LOG_CONFIG', '配置', 'arbess');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('LOG_PIPELINE', '流水线', 'arbess');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('LOG_RUN', '运行', 'arbess');


update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>添加了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where id = 'ONFIG_CREATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>删除了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where id = 'ONFIG_DELETE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${userName}</span><span>创建了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where id = 'G_TEM_CREATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>删除了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where id = 'G_TEM_DELETE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>更新了流水线 ${message}</span></div></div></div>' where id = 'G_TEM_UPDATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex-grow:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div>流水线<span style="color: #5d70ea">${pipelineName}</span>${message}</div></div></div>' where id = 'LOG_TEM_RUN';


update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>添加了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where id = 'ONFIG_CREATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${user}</span><span>删除了</span><span style="color: #5d70ea">${pipelineName}</span>的${message}</div></div></div>' where id = 'ONFIG_DELETE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span style="color: #5d70ea">${userName}</span><span>创建了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where id = 'G_TEM_CREATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>删除了流水线</span><span style="color: #5d70ea">${pipelineName}</span></div></div></div>' where id = 'G_TEM_DELETE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div><span  style="color: #5d70ea">${userName}</span><span>更新了流水线 ${message}</span></div></div></div>' where id = 'G_TEM_UPDATE';
update pcs_op_log_template set content = '<div style="display: flex;justify-content: flex-start; align-items:center;"><div style="width: 20px; height: 20px;"><img src="${img}" alt="" style="width: 100%; height:100%"/></div><div style="flex:1;margin: 0 15px"><div style="padding-bottom:5px;font-size:13px;font-weight:bold">${title}</div><div>流水线<span style="color: #5d70ea">${pipelineName}</span>${message}</div></div></div>' where id = 'LOG_TEM_RUN';


update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/config'  where id = 'ONFIG_CREATE';
update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/config'  where id = 'ONFIG_DELETE';
update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/survey' where id = 'G_TEM_CREATE';
update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/survey' where id = 'G_TEM_DELETE';
update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/survey' where id = 'G_TEM_UPDATE';
update pcs_op_log_template set link = '/index/pipeline/${pipelineId}/structure' where id = 'LOG_TEM_RUN';


-- 消息发送类型
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_CREATE', '流水线创建消息', '流水线创建消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_DELETE', '流水线删除消息', '流水线删除消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('ELINE_UPDATE', '流水线更新消息', '流水线更新消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIPELINE_RUN', '流水线运行消息', '流水线运行消息', 'arbess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('INE_TASK_RUN', '流水线任务运行', '流水线任务运行', 'arbess');

-- 消息发送类型模板
INSERT INTO pcs_mec_message_template VALUES ('0a5e5589435e', 'INE_TASK_RUN', 'dingding', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('0acf89d30595', 'INE_TASK_RUN', 'email', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('0eb63efc2afb', 'ELINE_DELETE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>删除了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('255688648ccb', 'PIPELINE_RUN', 'site', '运行', '<span style=\"color: #5d70ea\">${pipelineName}</span>${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('31ef95545115', 'INE_TASK_RUN', 'qywechat', NULL, '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('5c32aa18973d', 'PIPELINE_RUN', 'dingding', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('7fa67bf3d03d', 'INE_TASK_RUN', 'site', '运行', '${taskName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('91ed9da0bcc7', 'PIPELINE_RUN', 'qywechat', NULL, '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('c87c723ac6c7', 'ELINE_CREATE', 'site', '流水线', '<span style=\"color: #5d70ea\">${userName}</span>创建了流水线\n<span style=\"color: #5d70ea\">${pipelineName}</span>', '/index/pipeline/${pipelineId}/survey', 'arbess', NULL);
INSERT INTO pcs_mec_message_template VALUES ('d5264eac1729', 'PIPELINE_RUN', 'email', '运行', '${pipelineName}${message}', '/index/pipeline/${pipelineId}/structure', 'arbess', NULL);

-- 消息发送接收人类型
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('46d69ceeac26', 'MES_UPDATE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('7f600874216f', 'MES_DELETE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('c6fb3cb6a4d5', 'MES_CREATE', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('aa06b354036c', 'MES_RUN', '1');
INSERT INTO pcs_mec_message_notice_connect_role (id, message_notice_id, role_id) VALUES ('ece0c1aa8cad', 'MES_TASK_RUN', '1');

-- 消息发送通知方案
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_CREATE', 'ELINE_CREATE', 1, 'arbess', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_DELETE', 'ELINE_DELETE', 1, 'arbess', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_UPDATE', 'ELINE_UPDATE', 1, 'arbess', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_RUN', 'PIPELINE_RUN', 1, 'arbess', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('MES_TASK_RUN', 'INE_TASK_RUN', 1, 'arbess', 'dingding,email,qywechat,site');

-- 更新任务日志打印

update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '0a5e5589435e';
update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '0acf89d30595';
update pcs_mec_message_template set content = '任务${taskName}${message}' where id = '31ef95545115';
update pcs_mec_message_template set content = '任务<span style="color: #5d70ea">${taskName}</span>${message}' where id = '7fa67bf3d03d';


-- 初始化项目
INSERT INTO pip_pipeline (id, name, user_id, create_time, type, state, power, color) VALUES ('16065924e37d', '示例项目--多阶段', '111111', '2022-12-26 11:37:11', 2, 1, 1, 2);
-- INSERT INTO pip_pipeline (id, name, user_id, create_time, type, state, power, color) VALUES ('3847d6abea9c', '示例项目--多任务', '111111', '2022-12-26 11:35:00', 1, 1, 1, 5);

-- 初始化阶段
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('181631570f24', '2022-12-26 11:37:11', '阶段-1', '16065924e37d', 1, null,  'true');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id,  code) VALUES ('59380f9cd5dd', '2022-12-26 11:37:11', '源码', null, 1, '181631570f24',  'false');

-- 初始化任务
-- INSERT INTO pip_task (task_id, create_time, task_name, pipeline_id, stage_id, task_type, task_sort,postprocess_id) VALUES ('406d153ed223', '2022-12-26 11:35:00', '通用Git', '3847d6abea9c', null, 'git',1,null);
INSERT INTO pip_task (task_id, create_time, task_name, pipeline_id, stage_id, task_type, task_sort,postprocess_id) VALUES ('056d153edeqw', '2022-12-26 11:35:00', '通用Git', null, '59380f9cd5dd', 'git',1,null);

-- 初始化任务详情
-- INSERT INTO pip_task_code (task_id, code_name, code_address, code_branch, svn_file, auth_id) VALUES ('406d153ed223', 'git@gitee.com:tiklab/tiklab-example.git', 'git@gitee.com:tiklab/tiklab-example.git', 'master', NULL, 'f812ab93d8ff');
INSERT INTO pip_task_code (task_id, code_name, code_address, code_branch, svn_file, auth_id) VALUES ('056d153edeqw', 'git@gitee.com:tiklab/tiklab-example.git', 'git@gitee.com:tiklab/tiklab-example.git', 'master', NULL, 'f812ab93d8ff');

INSERT INTO pip_auth (id, auth_type, name, create_time, username, password, private_key, user_id, auth_public) VALUES ('f812ab93d8ff', 2, '示例项目私钥', '2022-12-19 14:59:14', NULL, NULL, '-----BEGIN OPENSSH PRIVATE KEY-----\n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\n-----END OPENSSH PRIVATE KEY-----\n', '111111', 1);

-- INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('0a0758b4f53f', '3847d6abea9c', 'bf699ba68c87', 0);
INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('2eb55dc97656', '16065924e37d', 'f51b3e9cdf10', 1);
-- INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('c33bb5694058', '3847d6abea9c', '3f22e1a0ff00', 1);
INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('e719de154148', '16065924e37d', 'ae28429ef243', 0);

--
-- INSERT INTO pcs_ucc_dm_user (id, domain_id, user_id, type) VALUES ('3eedd5b221aa', '3847d6abea9c', '111111', 1);
INSERT INTO pcs_ucc_dm_user (id, domain_id, user_id, type) VALUES ('e5f0d6a0b331', '16065924e37d', '111111', 1);

INSERT INTO pcs_prc_dm_role_user (id, dmRole_id, domain_id, user_id) VALUES ('asd5dc976563','2eb55dc97656','16065924e37d','111111');
-- INSERT INTO pcs_prc_dm_role_user (id, dmRole_id, domain_id, user_id) VALUES ('34435s694058','c33bb5694058','3847d6abea9c','111111');




