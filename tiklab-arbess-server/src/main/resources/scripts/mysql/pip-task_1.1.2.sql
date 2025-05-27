-- 向 pip_setting_scm 表添加新字段（如果不存在）
ALTER TABLE pip_setting_scm
    ADD COLUMN add_type VARCHAR(255) DEFAULT 'local';

ALTER TABLE pip_setting_scm
    ADD COLUMN pkg_address VARCHAR(255);

-- 向 pip_task_build 表添加新字段（如果不存在）
ALTER TABLE pip_task_build
    ADD COLUMN tool_go VARCHAR(255);