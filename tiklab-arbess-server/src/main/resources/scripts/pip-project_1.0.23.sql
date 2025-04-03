-- 添加新字段（如果不存在）
ALTER TABLE pip_task_pull_artifact
    ADD COLUMN IF NOT EXISTS artifact_name varchar(255);

ALTER TABLE pip_task_pull_artifact
    ADD COLUMN IF NOT EXISTS artifact_type varchar(255);

-- 删除旧字段（如果存在）
ALTER TABLE pip_task_artifact
DROP COLUMN IF EXISTS tool_jdk,
DROP COLUMN IF EXISTS tool_maven,
DROP COLUMN IF EXISTS tool_nodejs,
DROP COLUMN IF EXISTS xpack_id,
DROP COLUMN IF EXISTS artifact_id,
DROP COLUMN IF EXISTS group_id;

ALTER TABLE pip_task_pull_artifact
DROP COLUMN IF EXISTS tool_jdk,
DROP COLUMN IF EXISTS tool_maven,
DROP COLUMN IF EXISTS tool_nodejs,
DROP COLUMN IF EXISTS xpack_id,
DROP COLUMN IF EXISTS artifact_id,
DROP COLUMN IF EXISTS group_id;

