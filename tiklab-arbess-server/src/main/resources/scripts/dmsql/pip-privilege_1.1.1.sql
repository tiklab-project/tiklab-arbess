
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('66660f6c591d', '流水线设计', 'pipeline_task', NULL, 39, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('77770f6c591d', '运行流水线', 'pipeline_task_run', '66660f6c591d', 39, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('88880f6c591d', '编辑流水线', 'pipeline_task_update', '66660f6c591d', 39, '2');



INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('88409518b612', 'pro_111111', '66660f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('88409518b613', 'pro_111111', '77770f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('88409518b614', 'pro_111111', '88880f6c591d');



INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('99409518b612', '2', '66660f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('99409518b613', '2', '77770f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('99409518b614', '2', '88880f6c591d');


UPDATE pcs_prc_role SET name = '流水线管理员', description = '流水线管理员' WHERE id = 'pro_111111';



UPDATE pcs_prc_role SET name = '流水线成员', description = '流水线成员' WHERE id = '1efcc15ab020';


UPDATE pcs_prc_role SET name = '流水线管理员', description = '流水线管理员' WHERE parent_id = 'pro_111111';

UPDATE pcs_prc_role SET name = '流水线成员', description = '流水线成员' , default_role = 1 WHERE parent_id = '1efcc15ab020';

