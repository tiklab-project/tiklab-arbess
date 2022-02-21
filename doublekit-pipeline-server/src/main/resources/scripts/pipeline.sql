create table pipeline
(
    pipeline_id          varchar(256) not null comment '流水线id',
    pipeline_name        varchar(256) not null comment '流水线名称',
    pipeline_create_user varchar(256) not null comment '流水线创建人',
    pipeline_create_time varchar(256) not null comment '流水线创建时间',
    pipeline_create_type int not null comment '流水线类型',
    primary key (pipeline_id)
);
alter table pipeline comment '流水线表';

create table pipeline_configure
(
    configure_id         varchar(256) not null comment '配置信息id',
    configure_code_source varchar(256) not null comment '代码源',
    configure_code_source_address varchar(256) not null comment '代码源地址',
    configure_structure_address varchar(256) not null comment '构建源地址',
    configure_structure_order varchar(256) not null comment '构建命令',
    configure_deploy_address varchar(256) not null comment '部署地址',
    configure_create_time varchar(256) comment '配置创建时间',
    pipeline_id          varchar(256) not null comment '流水线id',
    proof_id             varchar(256) not null comment '凭证id',
    primary key (configure_id)
);
alter table pipeline_configure comment '流水线配置表';

create table pipeline_history
(
    history_id varchar(256) not null comment '历史id',
    history_time datetime not null comment '创建构建时间',
    history_way          int comment '构建方式',
    history_branch       varchar(256) not null comment '分支',
    proof_id             varchar(256) comment '凭证id',
    pipeline_id          varchar(256) comment '流水线id',
    log_id               varchar(256) comment '日志id',
    configure_id         varchar(256) comment '配置id',
    primary key (history_id)
);
alter table pipeline_history comment '构建历史表';

create table pipeline_log
(
    log_id               varchar(256) not null comment '执行信息id',
    log_address          varchar(256) not null comment '日志地址',
    log_code_time        varchar(256) not null comment '拉取时间',
    log_code_state       int comment '拉取状态',
    log_pack_time        varchar(256) comment '打包时间',
    log_pack_state       int comment '打包状态',
    log_deploy_time      varchar(256) comment '部署时间',
    log_deploy_state     int comment '部署状态',
    log_run_status       int comment '运行状态',
    primary key (log_id)
);
alter table pipeline_log comment '日志表';

create table pipeline_proof
(
    proof_id             varchar(256) not null comment '凭证id',
    proof_type           varchar(256) not null comment '凭证类型',
    proof_username       varchar(256) not null comment '凭证名',
    proof_password       varchar(256) not null comment '凭证密码',
    proof_describe       varchar(256) not null comment '凭证描述',
    primary key (proof_id)
);
alter table pipeline_proof comment '凭证表';
