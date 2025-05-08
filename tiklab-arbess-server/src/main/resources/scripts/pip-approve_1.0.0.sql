
create table pip_approve (
    id varchar(32) PRIMARY KEY,
    name varchar(256) ,
    user_id varchar(32) ,
    create_time varchar(32) ,
    exec_time varchar(64) ,
    corn varchar(64) ,
    status varchar(32)
);

create table pip_approve_pipeline (
    id varchar(32) PRIMARY KEY,
    pipeline_id varchar(32) ,
    create_time varchar(32) ,
    approve_id varchar(32) ,
    instance_id varchar(32) ,
    status varchar(32)
);


create table pip_approve_user (
    id varchar(32) PRIMARY KEY,
    user_id varchar(32) ,
    create_time varchar(32) ,
    approve_id varchar(32) ,
    message varchar(32) ,
    status varchar(32)
);