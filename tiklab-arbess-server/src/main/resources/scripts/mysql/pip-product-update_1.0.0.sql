
TRUNCATE TABLE pip_trigger_webhook;
TRUNCATE TABLE pip_task_build_product;

ALTER TABLE pip_task_build_product
DROP COLUMN `key`,
    DROP COLUMN `type`,
    ADD COLUMN create_time TIMESTAMP NULL,
    ADD COLUMN agent_id VARCHAR(255) DEFAULT 'local-default',
    ADD COLUMN pipeline_id VARCHAR(255),
    ADD COLUMN file_size VARCHAR(255);