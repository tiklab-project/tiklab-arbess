ALTER TABLE pip_task_code ADD COLUMN auth_type varchar(32);
ALTER TABLE pip_task_code ADD COLUMN username varchar(255);
ALTER TABLE pip_task_code ADD COLUMN password varchar(255);
ALTER TABLE pip_task_code ADD COLUMN pri_key text;