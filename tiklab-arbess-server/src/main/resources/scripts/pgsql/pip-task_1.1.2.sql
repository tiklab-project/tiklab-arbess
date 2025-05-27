-- 添加新字段（如果不存在）
ALTER TABLE pip_setting_scm
    ADD COLUMN IF NOT EXISTS add_type varchar(255) default 'local';

ALTER TABLE pip_setting_scm
    ADD COLUMN IF NOT EXISTS pkg_address varchar(255) ;

ALTER TABLE pip_task_build
    ADD COLUMN IF NOT EXISTS tool_go varchar(255) ;