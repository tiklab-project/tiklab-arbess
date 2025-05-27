
ALTER TABLE pip_task_test
    ADD COLUMN test_env varchar(255);


ALTER TABLE pip_task_relevance_teston
    ADD COLUMN test_plan_id varchar(255);

ALTER TABLE pip_task_test
DROP COLUMN app_env,
DROP COLUMN api_env,
DROP COLUMN web_env;