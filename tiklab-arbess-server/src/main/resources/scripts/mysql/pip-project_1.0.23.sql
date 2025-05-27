-- 添加新字段（必须手动确保不存在）
ALTER TABLE pip_task_pull_artifact ADD COLUMN artifact_name VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN artifact_type VARCHAR(255);

-- 删除旧字段（每个字段单独写一条 DROP COLUMN）
-- 表：pip_task_artifact
-- ALTER TABLE pip_task_artifact DROP COLUMN tool_jdk;
-- ALTER TABLE pip_task_artifact DROP COLUMN tool_maven;
-- ALTER TABLE pip_task_artifact DROP COLUMN tool_nodejs;
-- ALTER TABLE pip_task_artifact DROP COLUMN xpack_id;
-- ALTER TABLE pip_task_artifact DROP COLUMN artifact_id;
-- ALTER TABLE pip_task_artifact DROP COLUMN group_id;

-- 表：pip_task_pull_artifact
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_jdk;
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_maven;
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN tool_nodejs;
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN xpack_id;
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN artifact_id;
-- ALTER TABLE pip_task_pull_artifact DROP COLUMN group_id;