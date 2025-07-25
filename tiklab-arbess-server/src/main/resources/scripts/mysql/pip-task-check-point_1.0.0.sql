create table IF NOT EXISTS pip_task_check_point  (
    task_id varchar(256) PRIMARY KEY ,
    wail_time int  ,
    inspect_ids varchar(566)
);


create table IF NOT EXISTS pip_variable  (
    id varchar(256) PRIMARY KEY ,
    create_time Timestamp  ,
    type int  ,
    description varchar(566),
    var_key varchar(566),
    var_value varchar(566)
);

INSERT INTO pip_variable (id, create_time, type, description, var_key, var_value)
VALUES ('DEFAULT', '2025-07-23 11:31:37', 1, '流水线默认运行路径', 'DEFAULT_CODE_ADDRESS', 'user.home/tiklab/tiklab-arbess/${pipelineId}');


ALTER TABLE pip_task_instance ADD COLUMN task_id VARCHAR(64);
ALTER TABLE pip_task_instance ADD COLUMN create_time Timestamp;



INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_server', '服务集成', 'pip_set_server', NULL, 10, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_server_create', '创建', 'pip_set_server_create', 'pip_set_server', 101, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_server_update', '编辑', 'pip_set_server_update', 'pip_set_server', 102, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_server_delete', '删除', 'pip_set_server_delete', 'pip_set_server', 103, '1');


INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_server', '111111', 'pip_set_server');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_server_create', '111111', 'pip_set_server_create');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_server_update', '111111', 'pip_set_server_update');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_server_delete', '111111', 'pip_set_server_delete');


INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_tool', '工具集成', 'pip_set_tool', NULL, 11, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_tool_create', '创建', 'pip_set_tool_create', 'pip_set_tool', 111, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_tool_update', '编辑', 'pip_set_tool_update', 'pip_set_tool', 112, '1');

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type)
    VALUES ('pip_set_tool_delete', '删除', 'pip_set_tool_delete', 'pip_set_tool', 113, '1');


INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_tool', '111111', 'pip_set_tool');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_tool_create', '111111', 'pip_set_tool_create');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_tool_update', '111111', 'pip_set_tool_update');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('pip_set_tool_delete', '111111', 'pip_set_tool_delete');

