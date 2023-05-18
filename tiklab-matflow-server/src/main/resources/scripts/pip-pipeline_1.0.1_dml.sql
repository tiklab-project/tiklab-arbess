ALTER TABLE pip_pipeline ALTER COLUMN create_time TYPE varchar(255);
ALTER TABLE pip_pipeline_instance ALTER COLUMN create_time TYPE varchar(255);
ALTER TABLE pip_condition ALTER COLUMN create_time TYPE varchar(255);
ALTER TABLE pip_task ALTER COLUMN create_time TYPE varchar(255);
ALTER TABLE pip_stage ALTER COLUMN create_time TYPE varchar(255);