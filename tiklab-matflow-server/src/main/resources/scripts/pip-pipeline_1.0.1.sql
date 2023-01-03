
create table pip_pipeline_condition  (
   cond_id varchar(256)  COMMENT '条件id',
   cond_name varchar(256)  COMMENT '名称',
   task_id varchar(256)  COMMENT '任务id',
   create_time datetime  COMMENT '创建时间',
   cond_type int  COMMENT '流水线类型',
   cond_key varchar(256)  COMMENT '条件key',
   cond_value varchar(256)  COMMENT '条件value',
   PRIMARY KEY (cond_id) USING BTREE
);

ALTER TABLE pip_pipeline_post CHANGE pipeline_id task_id VARCHAR(256);