create table IF NOT EXISTS pip_test_maven_test  (
    id varchar(256) PRIMARY KEY ,
    pipeline_id varchar(256)  ,
    user_id varchar(256) ,
    all_number varchar(256) ,
    fail_number varchar(256) ,
    error_number varchar(256),
    skip_number varchar(256),
    test_state varchar(256),
    package_path varchar(256),
    name varchar(256),
    test_id varchar(256),
    message varchar(5000),
    create_time varchar(256)
);
