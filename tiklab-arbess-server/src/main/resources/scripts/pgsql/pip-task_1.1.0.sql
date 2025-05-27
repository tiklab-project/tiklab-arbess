
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






