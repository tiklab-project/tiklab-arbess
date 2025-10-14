
ALTER TABLE pip_task_build ADD COLUMN tool_other VARCHAR(64);

ALTER TABLE pip_task_code_scan ADD COLUMN tool_other VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN scan_coverage int default 0;

ALTER TABLE pip_task_code_scan_sourcefare ADD COLUMN error_trouble int default 0;
