
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('mabbd80b955f', '1', 'ma14739a13fe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('ma2dd86200e1', '1', 'ma73e628fd54');

-- 初始化功能权限
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('ma73e628fd54', '备份', 'backups', NULL, 38, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('ma14739a13fe', '恢复', 'restore', NULL, 39, '1');



