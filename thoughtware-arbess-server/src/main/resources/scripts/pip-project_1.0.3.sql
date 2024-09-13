alter table pip_task_build_product rename product_address to  "key";
alter table pip_task_build_product rename product_name to  "value";
ALTER TABLE pip_task_build_product ADD COLUMN type varchar(255);