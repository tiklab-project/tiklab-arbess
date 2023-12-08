ALTER TABLE pip_task_deploy ALTER COLUMN deploy_order TYPE text;
ALTER TABLE pip_task_deploy ALTER COLUMN start_order TYPE text;
ALTER TABLE pip_pipeline_instance ALTER COLUMN find_number TYPE varchar(50);