
ALTER TABLE pip_task_test
    ADD COLUMN IF NOT EXISTS test_env varchar(255);


ALTER TABLE pip_task_relevance_teston
    ADD COLUMN IF NOT EXISTS test_plan_id varchar(255);

ALTER TABLE pip_task_test
DROP COLUMN IF EXISTS app_env,
DROP COLUMN IF EXISTS api_env,
DROP COLUMN IF EXISTS web_env;