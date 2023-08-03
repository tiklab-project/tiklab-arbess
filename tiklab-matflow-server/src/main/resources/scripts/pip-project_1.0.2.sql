ALTER TABLE pip_task_deploy ALTER COLUMN start_order TYPE varchar(10000);

ALTER TABLE pip_task_build_product ADD COLUMN product_name varchar(255);