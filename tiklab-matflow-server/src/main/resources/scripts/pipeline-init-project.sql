INSERT INTO pipeline(pipeline_id,pipeline_name,user_id,pipeline_create_time,pipeline_create_type,pipeline_collect,pipeline_state,pipeline_power) VALUES ('679668d5ce24a7a9ff697feca5bb94ff', '示例项目1', '111111', '2022-07-28 15:47:29', 1, 0, 0, 1);

INSERT INTO pipeline_code(code_id,type,code_name,code_address,code_branch,proof_id,sort,code_alias,pipeline_id) VALUES ('76806330a7f7157828ddb12c44c17019', 1, 'https://gitee.com/zcamy/doublekit.git', 'https://gitee.com/zcamy/doublekit.git', NULL, 'a8cc49221d618151939f2162f3cbeecb', 1, NULL,'679668d5ce24a7a9ff697feca5bb94ff');

INSERT INTO pipeline_build(build_id,type,build_address,build_order,sort,build_alias,pipeline_id) VALUES ('9b420a082fc51e79792edaea928dd1b7', 21, '/', 'mvn clean package', 2, '构建','679668d5ce24a7a9ff697feca5bb94ff');

INSERT INTO pipeline_deploy(deploy_id,type,deploy_type,source_address,deploy_address,start_port,proof_id,mapping_port,sort,deploy_alias,ssh_ip,ssh_port,start_address,deploy_order,start_shell,pipeline_id) VALUES ('41d705d8029d379a8a2330c315d9527f', 31, 0, 'doublekit-0.0.1-SNAPSHOT.jar', 'root', 0, '52e6d477df54ba80b939dc27b911cd16', 0, 3, '部署', '172.12.1.18', 22, '/', '', 'java -jar demo-0.0.1-SNAPSHOT.jar','679668d5ce24a7a9ff697feca5bb94ff');

INSERT INTO pipeline_proof VALUES ('52e6d477df54ba80b939dc27b911cd16', 'linux', 5, 'password', 'root', 'darth2020', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');
INSERT INTO pipeline_proof VALUES ('a8cc49221d618151939f2162f3cbeecb', '示例凭证1', 1, 'password', 'zcamy', 'zhq11191750', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');



pipeline_id varchar(256) COMMENT '流水线id',