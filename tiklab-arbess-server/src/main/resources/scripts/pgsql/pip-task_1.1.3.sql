
-- 源码
ALTER TABLE pip_task_code ADD COLUMN tool_git VARCHAR(255);
ALTER TABLE pip_task_code ADD COLUMN tool_svn VARCHAR(255);

-- 测试
ALTER TABLE pip_task_test ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_test ADD COLUMN tool_maven VARCHAR(255);

-- 代码扫描
ALTER TABLE pip_task_code_scan ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_code_scan ADD COLUMN tool_maven VARCHAR(255);

-- 构建
ALTER TABLE pip_task_build ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN tool_nodejs VARCHAR(255);

-- 推送制品
ALTER TABLE pip_task_artifact ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_artifact ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_artifact ADD COLUMN tool_nodejs VARCHAR(255);

-- 拉取制品
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_nodejs VARCHAR(255);

alter table pip_setting_scm alter column scm_type  type VARCHAR(32);


UPDATE pip_setting_scm
SET scm_type = CASE
                   WHEN scm_type = '1' THEN 'git'
                   WHEN scm_type = '5' THEN 'svn'
                   WHEN scm_type = '21' THEN 'maven'
                   WHEN scm_type = '22' THEN 'nodejs'
                   ELSE scm_type
    END;



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


-- 添加新字段（如果不存在）
ALTER TABLE pip_setting_scm
    ADD COLUMN IF NOT EXISTS add_type varchar(255) default 'local';

ALTER TABLE pip_setting_scm
    ADD COLUMN IF NOT EXISTS pkg_address varchar(255) ;

ALTER TABLE pip_task_build
    ADD COLUMN IF NOT EXISTS tool_go varchar(255) ;




-- 添加新字段（如果不存在）
ALTER TABLE pip_agent
    ADD COLUMN IF NOT EXISTS display_type varchar(255) default 'yes';
