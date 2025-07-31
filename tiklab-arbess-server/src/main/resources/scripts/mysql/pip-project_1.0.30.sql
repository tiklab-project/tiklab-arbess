create table pip_task_host_order (
    task_id varchar(64) PRIMARY KEY,
    auth_id varchar(64),
    run_order text
);



create table pip_task_strategy (
    task_id varchar(64) PRIMARY KEY,
    strategy_type int,
    wail_time int,
    order_type int,
    inspect_ids varchar(64),
    run_order text
);