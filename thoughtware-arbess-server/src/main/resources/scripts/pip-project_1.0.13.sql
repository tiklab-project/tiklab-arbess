
DELETE FROM pip_setting_cache;


INSERT INTO pip_setting_cache (id, log_cache, artifact_cache) VALUES ('default', 7, 7);


ALTER TABLE pip_task_code ADD COLUMN house_id VARCHAR(255);