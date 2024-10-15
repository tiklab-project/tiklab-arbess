create table IF NOT EXISTS pip_agent  (
    id varchar(256) PRIMARY KEY ,
    name varchar(256)  ,
    ip varchar(256) ,
    tenant_id varchar(256) ,
    address varchar(256)  ,
    create_time varchar(256),
    business_type varchar(256)
);