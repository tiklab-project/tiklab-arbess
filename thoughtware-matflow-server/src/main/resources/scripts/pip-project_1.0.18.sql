
ALTER TABLE pip_task_deploy ADD COLUMN k8s_namespace VARCHAR(255) default 'default';
ALTER TABLE pip_task_deploy ADD COLUMN k8s_json text;


ALTER TABLE pip_task_build ALTER COLUMN docker_order TYPE text;