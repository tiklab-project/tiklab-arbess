
TRUNCATE TABLE pip_trigger_webhook;
TRUNCATE TABLE pip_task_build_product;

ALTER TABLE pip_task_build_product DROP COLUMN key;
ALTER TABLE pip_task_build_product DROP COLUMN type;
-- ALTER TABLE pip_task_build_product DROP COLUMN product_name;

ALTER TABLE pip_task_build_product ADD COLUMN create_time timestamp;
ALTER TABLE pip_task_build_product ADD COLUMN agent_id varchar(255) default 'local-default';
ALTER TABLE pip_task_build_product ADD COLUMN pipeline_id varchar(255);
ALTER TABLE pip_task_build_product ADD COLUMN file_size varchar(255);