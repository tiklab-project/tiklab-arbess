
ALTER TABLE pip_task_code_scan ADD COLUMN scan_type VARCHAR(32);
ALTER TABLE pip_task_code_scan ADD COLUMN scan_project_id VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN scan_project_name VARCHAR(64);
