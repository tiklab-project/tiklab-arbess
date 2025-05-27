-- 添加新字段（如果不存在）
-- ALTER TABLE pip_task_pull_artifact
--     ADD COLUMN artifact_name VARCHAR(255);
--
-- ALTER TABLE pip_task_pull_artifact
--     ADD COLUMN artifact_type VARCHAR(255);

-- pip_task_artifact 表
ALTER TABLE pip_task_artifact DROP COLUMN tool_jdk;
ALTER TABLE pip_task_artifact DROP COLUMN tool_maven;
ALTER TABLE pip_task_artifact DROP COLUMN tool_nodejs;
ALTER TABLE pip_task_artifact DROP COLUMN xpack_id;
ALTER TABLE pip_task_artifact DROP COLUMN artifact_id;
ALTER TABLE pip_task_artifact DROP COLUMN group_id;

-- pip_task_pull_artifact 表
ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_jdk;
ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_maven;
ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_nodejs;
ALTER TABLE pip_task_pull_artifact DROP COLUMN xpack_id;
ALTER TABLE pip_task_pull_artifact DROP COLUMN artifact_id;
ALTER TABLE pip_task_pull_artifact DROP COLUMN group_id;