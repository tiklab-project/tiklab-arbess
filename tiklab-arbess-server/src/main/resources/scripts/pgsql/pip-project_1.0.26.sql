
ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS tool_sonar varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS code_type varchar(255) default 'java';


create table pip_task_code_scan_sonar (
    id varchar(32) PRIMARY KEY,
    status varchar(256) ,
    ncloc int ,
    create_time varchar(64) ,
    name varchar(256) ,
    files int ,
    bugs int ,
    code_smells int ,
    loophole int ,
    duplicated_lines int ,
    repetition varchar(256) ,
    coverage varchar(256) ,
    url varchar(256) ,
    pipeline_id varchar(32)
);