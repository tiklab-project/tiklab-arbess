-- 1.0.10
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_RUN', 'MF_MES_TYPE_RUN', 2, 'arbess', 'site,email,qywechat', 1)
    ON CONFLICT (id)
DO UPDATE SET
    message_type_id = EXCLUDED.message_type_id,
           type = EXCLUDED.type,
           bgroup = EXCLUDED.bgroup,
           message_send_type_id = EXCLUDED.message_send_type_id,
           scope = EXCLUDED.scope;

-- 对于 MF_MES_TYPE_UPDATE
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_UPDATE', 'MF_MES_TYPE_UPDATE', 2, 'arbess', 'site,email,qywechat', 1)
    ON CONFLICT (id)
DO UPDATE SET
    message_type_id = EXCLUDED.message_type_id,
           type = EXCLUDED.type,
           bgroup = EXCLUDED.bgroup,
           message_send_type_id = EXCLUDED.message_send_type_id,
           scope = EXCLUDED.scope;

-- 1.0.11
DELETE FROM pcs_mec_message_notice WHERE id = '0fae4e670d99';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_CREATE';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_DELETE';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_UPDATE';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_RUN';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_RUN';
DELETE FROM pcs_mec_message_notice WHERE id = 'MES_TASK_RUN';

-- 初始化项目

INSERT INTO pip_pipeline (id, name, user_id, create_time, type, state, power, color, env_id, group_id) VALUES ('fda56c97a076', '示例项目', '111111', '2024-11-27 10:00:01', 2, 1, 1, 5, 'default', 'default');

-- 初始化阶段

INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('4e8b673f2d47', '2024-11-27 10:00:01', '源码', NULL, 1, '9a2d5cb7ae76', 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('10b7125fd8ca', '2024-11-27 10:00:35', '并行阶段-2-1', NULL, 1, '5d8ec00d6009', 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('038e993f8aef', '2024-11-27 10:00:47', '并行阶段-3-1', NULL, 1, 'aeb270dff1d6', 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('2aadef0b6b85', '2024-11-27 10:00:56', '并行阶段-4-1', NULL, 1, '54dbea9c00bf', 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('e30118e27731', '2024-11-27 10:01:01', '并行阶段-5-1', NULL, 1, '3c9918cc1741', 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('9a2d5cb7ae76', '2024-11-27 10:00:01', '源码', 'fda56c97a076', 1, NULL, 'true');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('5d8ec00d6009', '2024-11-27 10:00:29', '代码扫描', 'fda56c97a076', 2, NULL, 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('aeb270dff1d6', '2024-11-27 10:00:40', '测试', 'fda56c97a076', 3, NULL, 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('54dbea9c00bf', '2024-11-27 10:00:56', '构建', 'fda56c97a076', 4, NULL, 'false');
INSERT INTO pip_stage (stage_id, create_time, stage_name, pipeline_id, stage_sort, parent_id, code) VALUES ('3c9918cc1741', '2024-11-27 10:01:01', '部署', 'fda56c97a076', 5, NULL, 'false');

-- 初始化任务

INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES ('6c622862e172', '2024-11-27 10:00:01', '4e8b673f2d47', '通用Git', NULL, 'git', 1, NULL);
INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES ('d725c5e8214f', '2024-11-27 10:00:35', '10b7125fd8ca', 'Java代码扫描', NULL, 'spotbugs', 1, NULL);
INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES ('d22436d820f4', '2024-11-27 10:00:48', '038e993f8aef', 'Maven单元测试', NULL, 'maventest', 1, NULL);
INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES ('ceb7b80f990b', '2024-11-27 10:00:56', '2aadef0b6b85', 'Maven构建', NULL, 'maven', 1, NULL);
INSERT INTO pip_task (task_id, create_time, stage_id, task_name, pipeline_id, task_type, task_sort, postprocess_id) VALUES ('1aef0331461d', '2024-11-27 10:01:01', 'e30118e27731', '主机部署', NULL, 'liunx', 1, NULL);

-- 初始化任务详情

INSERT INTO pip_task_code (task_id, code_name, code_address, code_branch, svn_file, auth_id, xcode_id, branch_id, house_id) VALUES ('6c622862e172', 'https://gitee.com/tiklab-project/tiklab-arbess.git', 'https://gitee.com/tiklab-project/tiklab-arbess.git', 'master', NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_code_scan (task_id, type, auth_id, project_name, open_assert, open_debug, scan_path, err_grade, scan_grade) VALUES ('d725c5e8214f', NULL, NULL, NULL, 'false', 'false', '${DEFAULT_CODE_ADDRESS}', 'default', 'default');

INSERT INTO pip_task_test (task_id, test_order, address, test_space, test_plan, api_env, app_env, web_env, auth_id) VALUES ('d22436d820f4', 'mvn test', '${DEFAULT_CODE_ADDRESS}', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_build (task_id, build_address, build_order, docker_name, docker_version, docker_file, docker_order, product_rule) VALUES ('ceb7b80f990b', '${DEFAULT_CODE_ADDRESS}', 'mvn clean package', NULL, NULL, NULL, NULL, NULL);

INSERT INTO pip_task_deploy (task_id, auth_type, local_address, auth_id, deploy_address, deploy_order, start_address, start_order, rule, docker_image, k8s_namespace, k8s_json, strategy_type, strategy_number) VALUES ('1aef0331461d', 1, '${DEFAULT_CODE_ADDRESS}', 'ceedf0d9242c', '/usr/local/apps', NULL, NULL, NULL, 'tiklab.*\.tar\.gz', NULL, 'default', NULL, 'default', 1);

 -- 权限

INSERT INTO pcs_prc_role (id, name, description, grouper, type, business_type, default_role, scope, parent_id) VALUES ('30c03c1bbafd', '普通用户', '普通用户', 'system', '2', 0, 1, 2, '1efcc15ab020');
INSERT INTO pcs_prc_role (id, name, description, grouper, type, business_type, default_role, scope, parent_id) VALUES ('44563b8d8d1a', '项目超级管理员', '项目超级管理员角色,只能存在一个。', 'system', '2', 2, 2, 2, 'pro_111111');
INSERT INTO pcs_prc_role (id, name, description, grouper, type, business_type, default_role, scope, parent_id) VALUES ('921c8468f084', '管理员', '管理员', 'system', '2', 1, 0, 2, '2');


INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('6a2bc0b218d6', 'fda56c97a076', '921c8468f084', 1);
INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('32ced8240a7f', 'fda56c97a076', '30c03c1bbafd', 0);
INSERT INTO pcs_prc_dm_role (id, domain_id, role_id, business_type) VALUES ('81c1869f6a02', 'fda56c97a076', '44563b8d8d1a', 2);


INSERT INTO pcs_prc_dm_role_user (id, dmrole_id, domain_id, user_id) VALUES ('cb62af0d8da2', '81c1869f6a02', 'fda56c97a076', '111111');


INSERT INTO pcs_ucc_dm_user (id, domain_id, user_id, type, status) VALUES ('704af514eca8', 'fda56c97a076', '111111', 0, 1);


INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('de0f51b120aa', '30c03c1bbafd', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3d480d6bdc70', '30c03c1bbafd', 'c08882dca6ab');

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('dbbfb92ac452', '44563b8d8d1a', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c0a44dc96af4', '44563b8d8d1a', 'c6c0042fd942');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('0a0950c96e0e', '44563b8d8d1a', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('b888302df319', '44563b8d8d1a', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('89ecf651b7cb', '44563b8d8d1a', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a6e856c8c73c', '44563b8d8d1a', 'd5535a61c9ce');

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f7ec77048158', '921c8468f084', 'c6c0042fd942');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('34ed85731481', '921c8468f084', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c667ef7169ba', '921c8468f084', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('835ff21804d9', '921c8468f084', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('5c0d3a3a8e63', '921c8468f084', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f7fc96626f6c', '921c8468f084', 'd5535a61c9ce');


-- 消息

INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('e39e5c061341', 'MF_MES_TYPE_RUN', 2, 'arbess', 'site,email,qywechat', 2, 'true');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('bdf9d092f442', 'MF_MES_TYPE_UPDATE', 2, 'arbess', 'site,email,qywechat', 2, 'true');

INSERT INTO pcs_mec_message_dm_notice (id, message_notice_id, domain_id, source_notice_id, is_open) VALUES ('cd3483e44201', 'e39e5c061341', 'fda56c97a076', 'MF_MES_TYPE_RUN', 'true');
INSERT INTO pcs_mec_message_dm_notice (id, message_notice_id, domain_id, source_notice_id, is_open) VALUES ('4a91fd82490e', 'bdf9d092f442', 'fda56c97a076', 'MF_MES_TYPE_UPDATE', 'true');

INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('d803fc01f4ef', 'e39e5c061341', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('66d242fe7029', 'bdf9d092f442', '111111');


INSERT INTO pip_auth_host (host_id, type, name, create_time, ip, port, auth_type, username, password, private_key, auth_id, auth_public, user_id) VALUES ('ceedf0d9242c', 'common', 'dev', '2024-11-25 13:57:05', '172.10.1.12', '22', 1, 'admin', '123456', NULL, NULL, 0, '111111');
