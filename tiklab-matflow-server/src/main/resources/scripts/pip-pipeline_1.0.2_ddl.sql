-- ---------------------------
-- 流水线实例表
-- @dsm.cmd.id="1001"
-- ----------------------------
create table IF NOT EXISTS pip_setting_resources  (
    id varchar(255)  PRIMARY KEY,
    concurrency_number varchar(255) ,
    structure_number int  ,
    cache_number varchar(255),
    month varchar(255)
);