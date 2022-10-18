INSERT INTO pipeline (pipeline_id,pipeline_name,user_id,pipeline_create_time,pipeline_create_type,pipeline_collect,pipeline_state,pipeline_power) VALUES ('679668d5ce24a7a9ff697feca5bb94ff', '示例项目1', '111111', '2022-07-28 15:47:29', 1, 0, 0, 1);

INSERT INTO pipeline_proof VALUES ('52e6d477df54ba80b939dc27b911cd16', 'linux', 5, 'password', 'root', 'darth2020', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');

INSERT INTO pipeline_proof VALUES ('a8cc49221d618151939f2162f3cbeecb', '示例凭证1', 1, 'password', 'zcamy', 'zhq11191750', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');

INSERT INTO pipeline_code (code_id,code_name,code_address,code_branch,proof_id) VALUES ('3f412d09d56cd38733395f52f78eb646', 'https://gitee.com/zcamy/doublekit.git', 'https://gitee.com/zcamy/doublekit.git', "master", 'a8cc49221d618151939f2162f3cbeecb');

INSERT INTO pipeline_build (build_id,build_address,build_order) VALUES ('fae3e8707010794cdfa2b4352d84ffce', '/', 'mvn clean package');

INSERT INTO pipeline_deploy (deploy_id,deploy_type,source_address,deploy_address,start_port,proof_id,mapping_port,ssh_ip,ssh_port,start_address,deploy_order,start_shell) VALUES ('41d705d8029d379a8a2330c315d9527f', 0, 'doublekit-0.0.1-SNAPSHOT.jar', 'root', 0, '52e6d477df54ba80b939dc27b911cd16', 0, '106.14.114.8', 22, 'root', '', 'java -jar demo-0.0.1-SNAPSHOT.jar');

INSERT INTO pipeline_config_order (config_id,create_time,pipeline_id,task_id,task_type,task_sort ) VALUES ("fe1e32c4574398fc7826b9059430b2f2","2022-10-17 16:13:11","679668d5ce24a7a9ff697feca5bb94ff","fae3e8707010794cdfa2b4352d84ffce",21,2);
INSERT INTO pipeline_config_order (config_id,create_time,pipeline_id,task_id,task_type,task_sort ) VALUES ("55e10663f38660604b4d52040e51ae10","2022-10-17 16:13:09","679668d5ce24a7a9ff697feca5bb94ff","3f412d09d56cd38733395f52f78eb646",1,1);
INSERT INTO pipeline_config_order (config_id,create_time,pipeline_id,task_id,task_type,task_sort ) VALUES ("c65370935368c23ea5ffdd9ea6f4edd5","2022-10-17 16:13:13","679668d5ce24a7a9ff697feca5bb94ff","41d705d8029d379a8a2330c315d9527f",31,3);

















