ALTER TABLE pip_task_code_scan ADD COLUMN open_assert VARCHAR(64);
ALTER TABLE pip_task_code_scan ADD COLUMN open_debug VARCHAR(64);

create table IF NOT EXISTS pip_task_code_scan_spotbugs  (
    id varchar(256) PRIMARY KEY ,
    pipeline_id varchar(256)  ,
    scan_time varchar(256) ,
    total_classes varchar(256) ,
    referenced_classes varchar(256) ,
    total_bugs varchar(256) ,
    num_packages varchar(256) ,
    priority_one varchar(256) ,
    priority_two varchar(256) ,
    priority_three varchar(256)
);