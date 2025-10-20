UPDATE pcs_prc_function_group SET function_ids = 'pipeline,pipeline_release,pipeline_statistics', sort = 4, type = 1 WHERE id = 'pipeline';
UPDATE pcs_prc_function_group SET function_ids = 'pip_setting_msg,domain_user,domain_role,domain_message', sort = 5, type = 2 WHERE id = 'pip_setting';

DELETE FROM pcs_prc_function
WHERE id IN (
  'pipeline_run',
  'pipeline_update',
  'pipeline_delete',
  'pipeline_setting',
  'pipeline_clone',
  'pipeline_import',
  'pipeline_history',
  'pipeline_history_delete',
  'pipeline_history_rollback',
  'pipeline_history_run'
);

INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_export', '克隆流水线', 'pip_setting_clean', 'pip_setting_msg', 4, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('pip_setting_clone', '导出流水线', 'pip_setting_clean', 'pip_setting_msg', 5, '2');
