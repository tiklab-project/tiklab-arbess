
ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS tool_sonar varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS code_type varchar(255) default 'java';


ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS tool_source_fare varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS tool_go varchar(255);

ALTER TABLE pip_task_code_scan
    ADD COLUMN IF NOT EXISTS tool_node varchar(255);



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


create table pip_task_code_scan_sourcefare (
    id varchar(32) PRIMARY KEY,
    status varchar(256) ,
    url varchar(256) ,
    create_time varchar(32),
    plan_id varchar(32),
    project_id varchar(32),
    pipeline_id varchar(32),
    all_trouble int,
    severity_trouble int,
    notice_trouble int,
    suggest_trouble int
);