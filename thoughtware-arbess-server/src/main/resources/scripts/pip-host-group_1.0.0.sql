create table IF NOT EXISTS pip_auth_host_group_details  (
    id varchar(256) PRIMARY KEY ,
    group_id varchar(256)  ,
    host_id varchar(256)
);

create table IF NOT EXISTS pip_auth_host_group  (
    id varchar(256) PRIMARY KEY ,
    group_name varchar(256)  ,
    auth_public varchar(256) ,
    details varchar(256) ,
    user_id varchar(256) ,
    create_time varchar(256)
);

-- 更改ID
alter table pip_auth_host rename column id to host_id;