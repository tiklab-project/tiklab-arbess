
CREATE TABLE pip_trigger_webhook (
    id VARCHAR(32) PRIMARY KEY,
    name VARCHAR(256),
    url VARCHAR(256),
    `key` VARCHAR(256),
    branch VARCHAR(256),
    `type` VARCHAR(256),
    parameters VARCHAR(256),
    expires_time TIMESTAMP,
    pipeline_id VARCHAR(32)
);

ALTER TABLE pip_trigger_time
    ADD COLUMN exec_status varchar(12) default '1';

ALTER TABLE pip_trigger_webhook
    ADD COLUMN status int default 1;

create table pip_message (
    id varchar(32) PRIMARY KEY,
    name varchar(256) ,
    task_id varchar(32) ,
    `type` int default 1,
    notice_type int default 1,
    create_time timestamp ,
    pipeline_id varchar(32)
);


-- 重命名表
RENAME TABLE pip_task_message_type TO pip_message_type;

RENAME TABLE pip_task_message_user TO pip_message_user;

-- 删除字段（如果存在）
ALTER TABLE pip_message_user
DROP COLUMN receive_type;

-- 重命名字段
ALTER TABLE pip_message_user
    CHANGE COLUMN message_id id VARCHAR(255);

ALTER TABLE pip_message_user
    CHANGE COLUMN task_id message_id VARCHAR(255);


ALTER TABLE pip_message_type
    CHANGE COLUMN task_id message_id VARCHAR(255);

ALTER TABLE pip_message_type
    CHANGE COLUMN task_type send_type VARCHAR(255);


-- 删除表（如果存在）
DROP TABLE IF EXISTS pip_postprocess_message_type;

DROP TABLE IF EXISTS pip_postprocess_message_user;

DROP TABLE IF EXISTS pip_postprocess_script;


DROP TABLE IF EXISTS pip_trigger;

create table pip_trigger (
    id varchar(32) PRIMARY KEY,
    `status` int ,
    exec_type int ,
    week_time int ,
    data varchar(256) ,
    cron varchar(256) ,
    create_time timestamp ,
    pipeline_id varchar(32)
);

ALTER TABLE pip_pipeline_instance
    ADD COLUMN run_log text ;

