-- 变量类型
alter table pip_pipeline_variable rename column task_type to var_type;
alter table pip_pipeline_variable alter column var_type type VARCHAR(32);

-- 后置处理
alter table pip_postprocess rename column postprocess_id to post_id;
alter table pip_postprocess rename column name to post_name;

-- 执行脚本
alter table pip_task_script alter column type type VARCHAR(32);

-- 流水线
ALTER TABLE pip_pipeline DROP COLUMN yaml;