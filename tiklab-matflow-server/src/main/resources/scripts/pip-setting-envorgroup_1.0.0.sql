create table IF NOT EXISTS pip_setting_env  (
    id varchar(256) PRIMARY KEY ,
    env_name varchar(256)  ,
    user_id varchar(256) ,
    detail varchar(256) ,
    create_time varchar(256)
);


create table IF NOT EXISTS pip_setting_group  (
    id varchar(256) PRIMARY KEY ,
    group_name varchar(256)  ,
    user_id varchar(256) ,
    detail varchar(256) ,
    create_time varchar(256)
);

ALTER TABLE pip_pipeline ADD COLUMN env_id VARCHAR(64) default 'default';
ALTER TABLE pip_pipeline ADD COLUMN group_id VARCHAR(64) default 'default' ;

INSERT INTO pip_setting_env (id, env_name, user_id, detail, create_time) VALUES ('default', '默认环境', '111111', '默认环境', NULL);
INSERT INTO pip_setting_group (id, group_name, user_id, detail, create_time) VALUES ('default', '默认分组', '111111', '默认分组', NULL);