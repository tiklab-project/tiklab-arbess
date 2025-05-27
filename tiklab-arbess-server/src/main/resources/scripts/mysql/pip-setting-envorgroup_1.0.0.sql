-- 创建 pip_setting_env 表（如果不存在）
CREATE TABLE IF NOT EXISTS pip_setting_env (
   id VARCHAR(256) PRIMARY KEY,
  env_name VARCHAR(256),
  user_id VARCHAR(256),
  detail VARCHAR(256),
  create_time VARCHAR(256)
  );

-- 创建 pip_setting_group 表（如果不存在）
CREATE TABLE IF NOT EXISTS pip_setting_group (
   id VARCHAR(256) PRIMARY KEY,
  group_name VARCHAR(256),
  user_id VARCHAR(256),
  detail VARCHAR(256),
  create_time VARCHAR(256)
  );

-- 向 pip_pipeline 表添加字段（MySQL 不支持 IF NOT EXISTS，添加前建议先检查）
ALTER TABLE pip_pipeline ADD COLUMN env_id VARCHAR(64) DEFAULT 'default';
ALTER TABLE pip_pipeline ADD COLUMN group_id VARCHAR(64) DEFAULT 'default';

-- 插入默认环境记录（避免重复插入可加 EXISTS 判断）
INSERT INTO pip_setting_env (id, env_name, user_id, detail, create_time)
SELECT * FROM (
                  SELECT 'default' AS id,
                         '默认环境' AS env_name,
                         '111111' AS user_id,
                         '默认环境' AS detail,
                         NULL AS create_time
              ) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM pip_setting_env WHERE id = 'default'
);

INSERT INTO pip_setting_group (id, group_name, user_id, detail, create_time)
SELECT * FROM (
                  SELECT 'default' AS id,
                         '默认分组' AS group_name,
                         '111111' AS user_id,
                         '默认分组' AS detail,
                         NULL AS create_time
              ) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM pip_setting_group WHERE id = 'default'
);