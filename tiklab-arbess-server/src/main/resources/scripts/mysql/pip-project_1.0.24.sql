-- 删除旧字段（每个字段必须单独删除，MySQL 无 IF NOT EXISTS 支持，执行前需手动确认字段存在）
ALTER TABLE pip_auth_host_k8s DROP COLUMN ip;
ALTER TABLE pip_auth_host_k8s DROP COLUMN type;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_type;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_id;
ALTER TABLE pip_auth_host_k8s DROP COLUMN port;
ALTER TABLE pip_auth_host_k8s DROP COLUMN username;
ALTER TABLE pip_auth_host_k8s DROP COLUMN password;
ALTER TABLE pip_auth_host_k8s DROP COLUMN auth_public;
ALTER TABLE pip_auth_host_k8s DROP COLUMN private_key;

-- 添加新字段（MySQL 不支持 ADD COLUMN IF NOT EXISTS，需提前判断字段是否存在）
ALTER TABLE pip_auth_host_k8s ADD COLUMN kube_config TEXT;
-- ALTER TABLE pip_auth_host_k8s ADD COLUMN user_id VARCHAR(255);
ALTER TABLE pip_auth_host_k8s ADD COLUMN tool_id VARCHAR(255);
ALTER TABLE pip_auth_host_k8s ADD COLUMN kube_address VARCHAR(255);

-- 修改字段名
ALTER TABLE pip_auth_host_k8s CHANGE COLUMN host_id id BIGINT;

-- 修改表名
RENAME TABLE pip_auth_host_k8s TO pip_k8s;

-- 添加新字段到 pip_task_deploy 表
ALTER TABLE pip_task_deploy ADD COLUMN k8s_address VARCHAR(255);
ALTER TABLE pip_task_deploy ADD COLUMN kube_conf_type VARCHAR(255) DEFAULT 'file';