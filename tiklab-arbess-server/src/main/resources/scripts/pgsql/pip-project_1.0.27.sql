
create table pip_trigger_webhook (
    id varchar(32) PRIMARY KEY,
    name varchar(256) ,
    url varchar(256) ,
    key varchar(256) ,
    branch varchar(256) ,
    type varchar(256) ,
    parameters varchar(256) ,
    expires_time timestamp ,
    pipeline_id varchar(32)
);

ALTER TABLE pip_trigger_time
    ADD COLUMN IF NOT EXISTS exec_status varchar(12) default '1';

ALTER TABLE pip_trigger_webhook
    ADD COLUMN IF NOT EXISTS status int default 1;

create table pip_message (
    id varchar(32) PRIMARY KEY,
    name varchar(256) ,
    task_id varchar(32) ,
    type int default 1,
    notice_type int default 1,
    create_time timestamp ,
    pipeline_id varchar(32)
);


ALTER TABLE pip_task_message_type RENAME TO pip_message_type;

ALTER TABLE pip_task_message_user RENAME TO pip_message_user;

ALTER TABLE pip_message_type
    RENAME COLUMN  task_id TO message_id;

ALTER TABLE pip_message_type
    RENAME COLUMN task_type TO send_type;

ALTER TABLE pip_message_user
DROP COLUMN IF EXISTS receive_type;

ALTER TABLE pip_message_user
    RENAME COLUMN message_id TO id;

ALTER TABLE pip_message_user
    RENAME COLUMN task_id TO message_id;

DROP TABLE IF EXISTS pip_postprocess_message_type;
DROP TABLE IF EXISTS pip_postprocess_message_user;
DROP TABLE IF EXISTS pip_postprocess_script;


DROP TABLE IF EXISTS pip_trigger;

create table pip_trigger (
    id varchar(32) PRIMARY KEY,
    status int ,
    exec_type int ,
    week_time int ,
    data varchar(256) ,
    cron varchar(256) ,
    create_time timestamp ,
    pipeline_id varchar(32)
);


ALTER TABLE pip_pipeline_instance
    ADD COLUMN IF NOT EXISTS run_log text ;
