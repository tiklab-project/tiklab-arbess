
ALTER TABLE pip_task ADD COLUMN field_status int default 1;

TRUNCATE TABLE pip_other_open;
ALTER TABLE pip_other_open ADD COLUMN update_time timestamp;