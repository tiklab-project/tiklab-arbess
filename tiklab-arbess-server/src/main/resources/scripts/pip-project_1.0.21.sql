create table pip_auth_host_k8s  (
    host_id varchar(64) PRIMARY KEY,
    type varchar(32) ,
    name varchar(32) ,
    create_time varchar(64) ,
    ip varchar(255) ,
    port varchar(255),
    auth_type int ,
    username varchar(255) ,
    password varchar(255) ,
    private_key text ,
    auth_id varchar(255) ,
    auth_public int ,
    user_id varchar(255)
);


UPDATE pip_task SET task_type = 'testhubo' WHERE task_type = 'testrubo';


UPDATE pip_auth_server SET type = 'testhubo' WHERE type = 'testrubo';
