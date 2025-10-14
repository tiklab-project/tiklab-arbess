create table IF NOT EXISTS pip_setting_cache  (
    id varchar(256) PRIMARY KEY ,
    log_cache int  ,
    artifact_cache int
);

INSERT INTO pip_setting_cache (id, log_cache, artifact_cache) VALUES ('default', 7, 7);


ALTER TABLE pip_setting_resources ADD COLUMN begin_time VARCHAR(64) ;

ALTER TABLE pip_setting_resources ADD COLUMN end_time VARCHAR(64) ;