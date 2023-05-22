ALTER TABLE pip_task ALTER COLUMN task_type TYPE varchar(255);
ALTER TABLE pip_task_instance ALTER COLUMN task_type TYPE varchar(255);
ALTER TABLE pip_auth_host ALTER COLUMN type TYPE varchar(255);
ALTER TABLE pip_auth_server ALTER COLUMN type TYPE varchar(255);
ALTER TABLE pip_postprocess ALTER COLUMN task_type TYPE varchar(255);


ALTER TABLE pip_task_test ADD COLUMN test_space varchar(255);
ALTER TABLE pip_task_test ADD COLUMN test_plan varchar(255);
ALTER TABLE pip_task_test ADD COLUMN api_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN app_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN web_env varchar(255);
ALTER TABLE pip_task_test ADD COLUMN auth_id varchar(255);