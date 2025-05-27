
-- 删除旧字段（如果存在）
ALTER TABLE pip_auth_host_k8s
DROP COLUMN IF EXISTS ip,
DROP COLUMN IF EXISTS type,
DROP COLUMN IF EXISTS auth_type,
DROP COLUMN IF EXISTS auth_id,
DROP COLUMN IF EXISTS port,
DROP COLUMN IF EXISTS username,
DROP COLUMN IF EXISTS password,
DROP COLUMN IF EXISTS auth_public,
DROP COLUMN IF EXISTS private_key;

-- 添加新字段（如果不存在）
ALTER TABLE pip_auth_host_k8s
    ADD COLUMN IF NOT EXISTS kube_config text;

ALTER TABLE pip_auth_host_k8s
    ADD COLUMN IF NOT EXISTS user_id varchar(255);

ALTER TABLE pip_auth_host_k8s
    ADD COLUMN IF NOT EXISTS tool_id varchar(255);

ALTER TABLE pip_auth_host_k8s
    ADD COLUMN IF NOT EXISTS kube_address varchar(255);


-- 更新id名称
ALTER TABLE pip_auth_host_k8s RENAME COLUMN host_id TO id;

-- 更新表名称
ALTER TABLE pip_auth_host_k8s RENAME TO pip_k8s;


ALTER TABLE pip_task_deploy
    ADD COLUMN IF NOT EXISTS k8s_address varchar(255);

ALTER TABLE pip_task_deploy
    ADD COLUMN IF NOT EXISTS kube_conf_type varchar(255) default 'file';
