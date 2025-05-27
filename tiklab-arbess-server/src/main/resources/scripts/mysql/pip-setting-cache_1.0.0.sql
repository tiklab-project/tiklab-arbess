-- 创建 pip_setting_cache 表（如果不存在）
CREATE TABLE IF NOT EXISTS pip_setting_cache (
    id VARCHAR(256) PRIMARY KEY,
    log_cache INT,
    artifact_cache INT
    );

-- 插入默认数据（建议先检查是否已存在以避免重复插入）
INSERT INTO pip_setting_cache (id, log_cache, artifact_cache)
SELECT * FROM (
                  SELECT 'default' AS id, 7 AS log_cache, 7 AS artifact_cache
              ) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM pip_setting_cache WHERE id = 'default'
);
-- 向 pip_setting_resources 表添加字段（MySQL 不支持 IF NOT EXISTS，建议先检查）
ALTER TABLE pip_setting_resources ADD COLUMN begin_time VARCHAR(64);
ALTER TABLE pip_setting_resources ADD COLUMN end_time VARCHAR(64);