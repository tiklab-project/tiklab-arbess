-- 字段重命名和类型修改
ALTER TABLE pip_pipeline_variable CHANGE COLUMN task_type var_type VARCHAR(32);

ALTER TABLE pip_postprocess
  CHANGE COLUMN postprocess_id post_id VARCHAR(255),
  CHANGE COLUMN name post_name VARCHAR(255);

ALTER TABLE pip_task_script MODIFY COLUMN type VARCHAR(32);

ALTER TABLE pip_task_test MODIFY COLUMN test_order TEXT;

-- 删除字段（需确认字段存在）
-- ALTER TABLE pip_pipeline DROP COLUMN yaml;

-- 新增字段
ALTER TABLE pip_task_build
  ADD COLUMN docker_name VARCHAR(255),
  ADD COLUMN docker_version VARCHAR(255),
  ADD COLUMN docker_file VARCHAR(255),
  ADD COLUMN docker_order VARCHAR(600);

ALTER TABLE pip_task_artifact
  ADD COLUMN artifact_type VARCHAR(32),
  ADD COLUMN rule VARCHAR(64),
  ADD COLUMN docker_image VARCHAR(64);

ALTER TABLE pip_task_deploy
  ADD COLUMN rule VARCHAR(64),
  ADD COLUMN docker_image VARCHAR(64);

-- 新表创建
CREATE TABLE IF NOT EXISTS pip_task_pull_artifact (
  task_id VARCHAR(64) PRIMARY KEY,
  pull_type VARCHAR(32),
  docker_image VARCHAR(32),
  remote_address VARCHAR(64),
  local_address VARCHAR(255),
  auth_id VARCHAR(255),
  group_id VARCHAR(255),
  version VARCHAR(255),
  artifact_id VARCHAR(255),
  xpack_id VARCHAR(255),
  transitive VARCHAR(255)
  );