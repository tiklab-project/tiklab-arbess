

TRUNCATE TABLE pcs_prc_function;
TRUNCATE TABLE pcs_prc_role_function;
TRUNCATE TABLE pcs_prc_function_group;


INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
    VALUES ('user', '用户', 'user', 'user,orga,user_group,user_dir', 1, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('permission', '权限', 'permission', 'permission', 2, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('message', '消息', 'message', 'message', 3, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pipeline', '流水线', 'pipeline', 'pipeline,pipeline_history,pipeline_release,pipeline_statistics', 4, 1);


INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pipeline_conf', '流水线配置', 'pipeline_conf', 'pipeline_application,pipeline_environment,pipeline_variable,pipeline_agent', 5, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pipeline_other', '资源配置', 'pipeline_other', 'pipeline_host,pipeline_host_group,pipeline_kubernetes_cluster', 6, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pipeline_tool', '集成开放', 'pipeline_tool', 'pipeline_tool_integration,pipeline_service_integration,openapi', 7, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('security', '安全', 'security', 'log,backups_and_recover,ip_whitelist', 8, 1);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('system', '系统', 'system', 'licence,apply_limits,custom_logo,pipeline_resource_monitor', 9, 1);

-- 用户
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user', '用户管理', 'user', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_add_user', '添加用户', 'user_add_user', 'user', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_update_user', '编辑用户', 'user_update_user', 'user', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_delete_user', '删除用户', 'user_delete_user', 'user', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_update_user_password', '修改用户密码', 'user_update_user_password', 'user', 4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_update_user_recover', '找回用户密码', 'user_update_user_recover', 'user', 4, '1');

--部门
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga', '部门管理', 'orga', NULL, 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga_add_orga', '添加组织', 'orga_add_orga', 'orga', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga_update_orga', '编辑组织信息', 'orga_update_orga', 'orga', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga_delete_orga', '删除组织', 'orga_delete_orga', 'orga', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga_add_user', '添加组织成员', 'orga_add_user', 'orga', 4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('orga_delete_user', '删除组织成员', 'orga_delete_user', 'orga', 5, '1');

--用户组
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_group', '用户组管理', 'user_group', NULL, 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_add_group', '添加用户组', 'user_add_group', 'user_group', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_update_group', '更新用户组信息', 'user_update_group', 'user_group', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_delete_group', '删除用户组', 'user_delete_group', 'user_group', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_add_group_user', '添加用户组成员', 'user_add_group_user', 'user_group', 4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_delete_group_user', '删除用户组成员', 'user_delete_group_user', 'user_group', 5, '1');

-- 用户目录
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_dir', '用户目录管理', 'user_dir', NULL, 4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_dir_sync', '同步数据', 'user_dir_sync', 'user_dir', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_dir_open', '开启', 'user_dir_open', 'user_dir', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_dir_config', '配置', 'user_dir_config', 'user_dir', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('user_dir_forbid', '禁止', 'user_dir_forbid', 'user_dir',4, '1');

--权限

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission', '权限管理', 'permission', NULL, 5, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_add', '添加角色', 'permission_role_add', 'permission',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_delete', '删除角色', 'permission_role_delete', 'permission',2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_update', '修改角色信息', 'permission_role_update', 'permission',3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_user_add', '添加角色成员', 'permission_role_user_add', 'permission',3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_user_delete', '删除角色成员', 'permission_role_user_delete', 'permission',3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_permission_update', '编辑角色权限', 'permission_role_permission_update', 'permission',3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('permission_role_update_default', '修改默认角色', 'permission_role_update_default', 'permission',4, '1');

-- 消息

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message', '消息管理', 'message', NULL, 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message_update_send_way', '配置消息发送方式', 'message_update_send_way', 'message',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message_update_plan_status', '修改通知方案状态', 'message_update_plan_status', 'message',2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message_update_plan_send_way', '修改通知方案发送方式', 'message_update_plan_send_way', 'message',3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message_plan_user_add', '添加通知方案通知成员', 'message_plan_user_add', 'message',4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('message_plan_delete', '删除通知方案通知成员', 'message_plan_delete', 'message',4, '1');



-- openapi
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('openapi', 'openapi管理', 'openapi', NULL, 100, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('openapi_add', '添加授权秘钥', 'openapi_add', 'openapi',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('openapi_delete', '删除授权秘钥', 'openapi_delete', 'openapi',2, '1');


-- 备份与恢复
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('backups_and_recover', '备份与恢复管理', 'backups_and_recover', NULL, 101, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('backups_update_status', '修改定时备份状态', 'backups_update_status', 'backups_and_recover',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('backups_create', '手动备份', 'backups_create', 'backups_and_recover',2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('recover_create', '恢复', 'recover_create', 'backups_and_recover',3, '1');

-- 日志
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('log', '操作日志管理', 'log', NULL, 102, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('log_find', '操作日志', 'log', 'log', 1, '1');

-- ip黑白名单
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('ip_whitelist', 'IP黑白名单管理', 'ip_whitelist', NULL,103, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('ip_whitelist_white', '配置白名单', 'ip_whitelist_white', 'ip_whitelist',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('ip_whitelist_black', '配置黑名单', 'ip_whitelist_black', 'ip_whitelist',2, '1');

-- 版本与许可证
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('licence', '版本与许可证管理', 'licence', NULL,104, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('licence_import', '导入Licence', 'licence_import', 'licence',1, '1');

--系统访问权限
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('apply_limits', '系统访问权限管理', 'apply_limits', NULL, 105, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('apply_limits_add_user', '添加授权用户', 'apply_limits_add_user', 'apply_limits', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('apply_limits_delete_user', '删除授权用户', 'apply_limits_delete_user', 'apply_limits', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('apply_limits_open_user', '激活授权', 'apply_limits_open_user', 'apply_limits', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('apply_limits_close_user', '取消授权', 'apply_limits_close_user', 'apply_limits', 4, '1');


-- 自定义Logo
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('custom_logo', '自定义Logo管理', 'custom_logo', NULL, 106, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('custom_logo_update_title', '修改标题', 'custom_logo_update_title', 'custom_logo',1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('custom_logo_update_status', '修改开启状态', 'custom_logo_update_status', 'custom_logo',2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('custom_logo_update_pic', '修改Logo图片', 'custom_logo_update_pic', 'custom_logo',3, '1');


-- 以下是流水线菜单

-- 流水线
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline', '流水线管理', 'pipeline', NULL, 30, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_create', '创建流水线', 'pipeline_create', 'pipeline', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_run', '运行流水线', 'pipeline_run', 'pipeline', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_update', '编辑流水线', 'pipeline_update', 'pipeline', 3, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_delete', '删除流水线', 'pipeline_delete', 'pipeline', 4, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_setting', '流水线设置', 'pipeline_setting', 'pipeline', 5, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_clone', '克隆流水线', 'pipeline_clone', 'pipeline', 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_import', '导出流水线', 'pipeline_import', 'pipeline', 7, '1');

-- 流水线历史
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_history', '流水线历史管理', 'pipeline_history', NULL, 31, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_history_delete', '删除历史', 'pipeline_history_delete', 'pipeline_history', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_history_rollback', '回滚流水线', 'pipeline_history_rollback', 'pipeline_history', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_history_run', '运行流水线', 'pipeline_history_run', 'pipeline_history', 3, '1');

-- 流水线发布管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_release', '流水线发布单管理', 'pipeline_release', NULL, 32, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_release_add', '添加流水线发布单', 'pipeline_release_add', 'pipeline_release', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_release_update', '修改流水线发布单', 'pipeline_release_update', 'pipeline_release', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_release_delete', '删除流水线发布单', 'pipeline_release_delete', 'pipeline_release', 3, '1');

--流水线统计管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_statistics', '流水线统计管理', 'pipeline_statistics', NULL, 33, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_overview_statistics', '流水线统计概况', 'pipeline_overview_statistics', 'pipeline_statistics', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_run_statistics', '运行统计', 'pipeline_run_statistics', 'pipeline_statistics', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_result_statistics', '结果统计', 'pipeline_result_statistics', 'pipeline_statistics', 3, '1');

-- 流水线应用管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_application', '流水线应用管理', 'pipeline_application', NULL, 34, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_create_application', '创建应用', 'pipeline_create_application', 'pipeline_application', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_update_application', '修改应用', 'pipeline_update_application', 'pipeline_application', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_delete_application', '删除应用', 'pipeline_delete_application', 'pipeline_application', 3, '1');

-- 流水线环境管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_environment', '流水线环境管理', 'pipeline_environment', NULL, 35, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_environment_add', '添加流水线环境', 'pipeline_environment_add', 'pipeline_environment', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_environment_update', '修改流水线环境', 'pipeline_environment_update', 'pipeline_environment', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_environment_delete', '删除流水线环境', 'pipeline_environment_delete', 'pipeline_environment', 3, '1');

-- 流水线变量管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_variable', '流水线变量管理', 'pipeline_variable', NULL, 36, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_variable_add', '添加流水线变量', 'pipeline_variable_add', 'pipeline_variable', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_variable_update', '修改流水线变量', 'pipeline_variable_update', 'pipeline_variable', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_variable_delete', '删除流水线变量', 'pipeline_variable_delete', 'pipeline_variable', 3, '1');

-- 流水线Agent管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_agent', '流水线Agent管理', 'pipeline_agent', NULL, 37, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_agent_add', '添加流水线Agent', 'pipeline_agent_add', 'pipeline_agent', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_agent_update', '修改流水线Agent执行策略', 'pipeline_agent_update', 'pipeline_agent', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_agent_delete', '删除流水线Agent', 'pipeline_agent_delete', 'pipeline_agent', 3, '1');

-- 流水线主机管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host', '流水线主机管理', 'pipeline_host', NULL, 38, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_add', '添加流水线主机', 'pipeline_host_add', 'pipeline_host', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_update', '修改流水线主机', 'pipeline_host_update', 'pipeline_host', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_delete', '删除流水线主机', 'pipeline_host_delete', 'pipeline_host', 3, '1');

-- 流水线主机组管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_group', '流水线主机组管理', 'pipeline_host_group', NULL, 39, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_group_add', '添加流水线主机组', 'pipeline_host_group_add', 'pipeline_host_group', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_group_update', '修改流水线主机组', 'pipeline_host_group_update', 'pipeline_host_group', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_host_group_delete', '删除流水线主机组', 'pipeline_host_group_delete', 'pipeline_host_group', 3, '1');

--流水线Kubernetes集群管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_kubernetes_cluster', '流水线Kubernetes集群管理', 'pipeline_kubernetes_cluster', NULL, 40, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_kubernetes_cluster_add', '添加流水线Kubernetes集群', 'pipeline_kubernetes_cluster_add', 'pipeline_kubernetes_cluster', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_kubernetes_cluster_update', '修改流水线Kubernetes集群', 'pipeline_kubernetes_cluster_update', 'pipeline_kubernetes_cluster', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_kubernetes_cluster_delete', '删除流水线Kubernetes集群', 'pipeline_kubernetes_cluster_delete', 'pipeline_kubernetes_cluster', 2, '1');

-- 流水线工具集成管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_tool_integration', '流水线工具集成管理', 'pipeline_tool_integration', NULL, 41, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_tool_integration_add', '添加流水线工具集成', 'pipeline_tool_integration_add', 'pipeline_tool_integration', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_tool_integration_update', '修改流水线工具集成', 'pipeline_tool_integration_update', 'pipeline_tool_integration', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_tool_integration_delete', '删除流水线工具集成', 'pipeline_tool_integration_delete', 'pipeline_tool_integration', 3, '1');

-- 流水线服务集成管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_service_integration', '流水线服务集成管理', 'pipeline_service_integration', NULL, 42, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_service_integration_add', '添加流水线服务集成', 'pipeline_service_integration_add', 'pipeline_service_integration', 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_service_integration_update', '修改流水线服务集成', 'pipeline_service_integration_update', 'pipeline_service_integration', 2, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_service_integration_delete', '删除流水线服务集成', 'pipeline_service_integration_delete', 'pipeline_service_integration', 3, '1');

-- 流水线资源监控管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_resource_monitor', '流水线资源监控管理', 'pipeline_resource_monitor', NULL, 999, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pipeline_resource_update', '修改资源配置', 'pipeline_resource_update', 'pipeline_resource_monitor', 1, '1');

-- 以下是流水线项目域权限

-- 流水线设计管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design', '流水线设计管理', 'pip_design', NULL, 43, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_run', '运行流水线', 'pip_design_run', 'pip_design', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_update', '编辑任务', 'pip_design_update', 'pip_design', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_webhook', '配置webhook触发', 'pip_design_webhook', 'pip_design', 4, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_timeout', '配置定时触发', 'pip_design_timeout', 'pip_design', 5, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_var_add', '添加变量', 'pip_design_var_add', 'pip_design', 6, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_var_update', '修改变量', 'pip_design_var_update', 'pip_design', 7, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_design_var_delete', '删除变量', 'pip_design_var_delete', 'pip_design', 8, '2');


-- 流水线历史管理
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_history', '流水线历史管理', 'pip_history', NULL, 44, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_history_delete', '删除历史', 'pip_history_delete', 'pip_history', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_history_rollback', '回滚流水线', 'pip_history_rollback', 'pip_history', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_history_run', '运行流水线', 'pip_history_run', 'pip_history', 3, '2');


-- 流水线测试报告
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report', '流水线测试报告', 'pip_test_report', NULL, 45, '2');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_overview', '概况', 'pip_test_report_overview', 'pip_test_report', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_overview_find', '概况', 'pip_test_report_overview', 'pip_test_report_overview', 1, '2');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_sonarqube_scan', '代码扫描', 'pip_test_report_sonarqube', 'pip_test_report', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_sonarqube_find', 'SonarQube', 'pip_test_report_sonarqube', 'pip_test_report_sonarqube_scan', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_sonarqube_delete', '删除SonarQube代码扫描', 'pip_test_report_sonarqube_delete', 'pip_test_report_sonarqube_scan', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_sourcefare_find', 'SourceFare', 'pip_test_report_sourcefare', 'pip_test_report_sonarqube_scan', 3, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_sourcefare_delete', '删除SourceFare代码扫描扫描结果', 'pip_test_report_sourcefare_delete', 'pip_test_report_sonarqube_scan', 4, '2');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_testhubo', '自动化测试', 'pip_test_report_testhubo', 'pip_test_report', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_testhubo_find', 'TestHubo', 'pip_test_report_testhubo_find', 'pip_test_report_testhubo', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_test_report_testhubo_delete', '删除TestHubo自动化测试结果', 'pip_test_report_testhubo_delete', 'pip_test_report_testhubo', 2, '2');

-- 流水线统计
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_statistics', '流水线统计', 'pip_statistics', NULL, 46, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_statistics_overview', '概况', 'pip_statistics_overview', 'pip_statistics', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_statistics_run', '运行统计', 'pip_statistics_run', 'pip_statistics', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_statistics_result', '结果统计', 'pip_statistics_result', 'pip_statistics', 3, '2');

-- 流水线设置

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_msg', '流水线信息', 'pip_setting_msg', NULL, 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_user', '成员', 'domain_user', NULL, 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role', '权限', 'domain_role', NULL, 3, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_message', '消息', 'domain_message', NULL, 4, '2');


INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_update', '更新流水线', 'pip_setting_update', 'pip_setting_msg', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_delete', '删除流水线', 'pip_setting_delete', 'pip_setting_msg', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_clean', '清理流水线', 'pip_setting_clean', 'pip_setting_msg', 3, '2');

-- 此为公共的项目权限

-- 成员
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_user_add', '添加成员', 'domain_user_add', 'domain_user', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_user_delete', '删除流水线成员', 'domain_user_delete', 'domain_user', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_user_update', '修改流水线成员角色', 'domain_user_update', 'domain_user', 3, '2');

--角色
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_add', '添加角色', 'domain_role_add', 'domain_role', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_delete', '删除角色', 'domain_role_delete', 'domain_role', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_update', '修改角色信息', 'domain_role_update', 'domain_role',3, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_user_add', '添加角色成员', 'domain_role_user_add', 'domain_role', 4, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_user_delete', '删除角色成员', 'domain_role_user_delete', 'domain_role', 5, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_permission_update', '编辑角色权限', 'domain_role_permission_update', 'domain_role',6, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_role_update_default', '修改默认角色', 'domain_role_update_default', 'domain_role',7, '2');


--消息
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_message_status', '配置消息通知方案开启状态', 'domain_message_status', 'domain_message', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_message_way', '配置消息通知方案通知方式', 'domain_message_way', 'domain_message', 2, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_message_user_add', '配置消息通知方案通知对象', 'domain_message_user_add', 'domain_message', 3, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('domain_message_user_delete', '删除消息通知方案通知对象', 'domain_message_user_delete', 'domain_message', 4, '2');


INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pip_design', '设计', 'pip_design', 'pip_design', 1, 2);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pip_history', '历史', 'pip_history', 'pip_history', 2, 2);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pip_test_report', '测试报告', 'pip_test_report', 'pip_test_report_overview,pip_test_report_sonarqube_scan,pip_test_report_testhubo', 3, 2);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pip_statistics', '流水线统计', 'pip_statistics', 'pip_statistics', 4, 2);

INSERT INTO pcs_prc_function_group (id, name, code, function_ids, sort, type)
VALUES ('pip_setting', '流水线设置', 'pip_setting', 'pip_setting_msg,domain_user,domain_role,domain_message', 5, 2);
