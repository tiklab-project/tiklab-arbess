
-- pip_task 表：添加列，如果不存在
ALTER TABLE pip_task
    ADD COLUMN IF NOT EXISTS field_status INT DEFAULT 1;

-- 清空 pip_other_open 表
TRUNCATE TABLE pip_other_open;

-- pip_other_open 表：添加列，如果不存在
ALTER TABLE pip_other_open
    ADD COLUMN IF NOT EXISTS update_time TIMESTAMP;