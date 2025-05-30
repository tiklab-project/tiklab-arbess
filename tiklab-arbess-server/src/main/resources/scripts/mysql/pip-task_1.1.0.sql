-- 源码工具字段添加（MySQL 不支持 IF NOT EXISTS，添加前需检查或捕获异常）
ALTER TABLE pip_task_code ADD COLUMN tool_git VARCHAR(255);
ALTER TABLE pip_task_code ADD COLUMN tool_svn VARCHAR(255);

-- 测试任务工具字段添加
ALTER TABLE pip_task_test ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_test ADD COLUMN tool_maven VARCHAR(255);

-- 代码扫描任务工具字段添加
ALTER TABLE pip_task_code_scan ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_code_scan ADD COLUMN tool_maven VARCHAR(255);

-- 构建任务工具字段添加
ALTER TABLE pip_task_build ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_build ADD COLUMN tool_nodejs VARCHAR(255);

-- 推送制品任务工具字段添加
ALTER TABLE pip_task_artifact ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_artifact ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_artifact ADD COLUMN tool_nodejs VARCHAR(255);

-- 拉取制品任务工具字段添加
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_jdk VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_maven VARCHAR(255);
ALTER TABLE pip_task_pull_artifact ADD COLUMN tool_nodejs VARCHAR(255);

-- 修改 pip_setting_scm 表字段类型（MySQL 中用 MODIFY 而不是 alter column type）
ALTER TABLE pip_setting_scm MODIFY COLUMN scm_type VARCHAR(32);

-- 更新 scm_type 字段值
UPDATE pip_setting_scm
SET scm_type = CASE
   WHEN scm_type = '1' THEN 'git'
   WHEN scm_type = '5' THEN 'svn'
   WHEN scm_type = '21' THEN 'maven'
   WHEN scm_type = '22' THEN 'nodejs'
   ELSE scm_type
  END;