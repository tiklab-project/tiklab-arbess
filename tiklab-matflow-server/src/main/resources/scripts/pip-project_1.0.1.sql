ALTER TABLE pip_task_build ADD COLUMN product_rule varchar(255);

create table pip_task_build_product  (
    id varchar(64) PRIMARY KEY,
    product_address varchar(255) ,
    instance_id varchar(255)
);