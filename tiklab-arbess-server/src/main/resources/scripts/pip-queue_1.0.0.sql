
create table pip_queue  (
    id varchar(64) PRIMARY KEY,
    create_time varchar(32) ,
    user_id varchar(32) ,
    pipeline_id varchar(64) ,
    status varchar(255) ,
    instance_id varchar(255),
    agent_id varchar(255)
);