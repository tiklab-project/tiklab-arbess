ALTER TABLE pip_task_relevance_teston ADD COLUMN exec_status varchar(255);


ALTER TABLE pip_task_relevance_teston ADD COLUMN error_rate varchar(255);
ALTER TABLE pip_task_relevance_teston ADD COLUMN pass_rate varchar(255);
ALTER TABLE pip_task_relevance_teston ADD COLUMN exec_num int;
ALTER TABLE pip_task_relevance_teston ADD COLUMN fail_num int;
ALTER TABLE pip_task_relevance_teston ADD COLUMN pass_num int;
ALTER TABLE pip_task_relevance_teston ADD COLUMN test_name varchar(255);


ALTER TABLE pip_approve_user ADD COLUMN pipeline_id varchar(255);
ALTER TABLE pip_approve_pipeline ADD COLUMN exec_status int default 0;

TRUNCATE TABLE pip_approve;
TRUNCATE TABLE pip_approve_user;
TRUNCATE TABLE pip_approve_pipeline;