
ALTER TABLE pip_task_deploy ADD COLUMN strategy_type VARCHAR(255) default 'default';
ALTER TABLE pip_task_deploy ADD COLUMN strategy_number int default 1;


create table pip_task_deploy_instance  (
    id varchar(64) PRIMARY KEY,
    task_instance_id varchar(255) ,
    name varchar(64),
    run_time varchar(64),
    run_status varchar(255),
    sort int,
    run_log text
);

