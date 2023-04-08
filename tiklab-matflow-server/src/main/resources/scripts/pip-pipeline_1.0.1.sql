-- ---------------------------
-- 流水线后置任务实例表
-- ----------------------------
create table pip_postprocess_instance  (
    id varchar(256)  COMMENT '流水线id',
    instance_id varchar(256)  COMMENT '流水线名称',
    taskInstance_id varchar(256)  COMMENT '用户id',
    post_address varchar(256)  COMMENT '创建时间',
    post_time int  COMMENT '流水线类型',
    post_state varchar(256)  COMMENT '运行状态（1.正常 2.运行中）',
   PRIMARY KEY (id) USING BTREE
);



alter table pip_task_instance add postprocess_id varchar(256) COMMENT '后置处理id';







































