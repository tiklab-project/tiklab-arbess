-- 向 pip_task_code_scan 表添加字段（MySQL 不支持 IF NOT EXISTS，执行前请确认字段未存在）
ALTER TABLE pip_task_code_scan ADD COLUMN open_assert VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN open_debug VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN scan_path VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN err_grade VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN scan_grade VARCHAR(64);

-- 创建 pip_task_code_scan_spotbugs 表（MySQL 支持 IF NOT EXISTS）
CREATE TABLE IF NOT EXISTS pip_task_code_scan_spotbugs (
   id VARCHAR(256) PRIMARY KEY,
  pipeline_id VARCHAR(256),
  scan_time VARCHAR(256),
  total_classes VARCHAR(256),
  referenced_classes VARCHAR(256),
  total_bugs VARCHAR(256),
  num_packages VARCHAR(256),
  priority_one VARCHAR(256),
  priority_two VARCHAR(256),
  xml_path VARCHAR(256),
  priority_three VARCHAR(256)
  );