-- 添加新字段（如果不存在）
ALTER TABLE pip_agent
    ADD COLUMN IF NOT EXISTS display_type varchar(255) default 'yes';
