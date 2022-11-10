INSERT INTO `pipeline` (`pipeline_id`, `pipeline_name`, `user_id`, `pipeline_create_time`, `pipeline_type`, `pipeline_state`, `pipeline_power`, `color`) VALUES ('679668d5ce24a7a9ff697feca5bb94ff', '示例项目1', '111111', '2022-07-28 15:47:29', 1, 0, 1, 3);
INSERT INTO `pipeline` (`pipeline_id`, `pipeline_name`, `user_id`, `pipeline_create_time`, `pipeline_type`, `pipeline_state`, `pipeline_power`, `color`) VALUES ('78d1f3132bec370c8feac6400c801d2b', '测试node', '111111', '2022-11-08 09:46:34', 1, 0, 1, 3);
INSERT INTO `pipeline` (`pipeline_id`, `pipeline_name`, `user_id`, `pipeline_create_time`, `pipeline_type`, `pipeline_state`, `pipeline_power`, `color`) VALUES ('9e37ec363589292b0fcbb1d48536ae11', '测试SSH', '111111', '2022-11-09 17:21:07', 1, 0, 1, 4);
INSERT INTO `pipeline` (`pipeline_id`, `pipeline_name`, `user_id`, `pipeline_create_time`, `pipeline_type`, `pipeline_state`, `pipeline_power`, `color`) VALUES ('df1b2f1b384468065c6b920e62b1972c', '测试', '111111', '2022-10-29 19:48:07', 1, 0, 1, 4);

INSERT INTO `pipeline_product` (`id`, `group_id`, `artifact_id`, `version`, `file_type`, `file_address`, `auth_id`, `put_address`) VALUES ('315f4e0c15b25f32d8babbe81799081c', NULL, NULL, NULL, NULL, 'pipeline.sql', 'eaea86561b322b088662ee180c6e0234', '/root');
INSERT INTO `pipeline_product` (`id`, `group_id`, `artifact_id`, `version`, `file_type`, `file_address`, `auth_id`, `put_address`) VALUES ('bb68d836c2134642c15f5ec1809a56c2', 'zhang.tiklab', 'matflow', '1.0.0', 'md', 'README.md', 'd01c57f5656a23193c315a799cca4bbc', NULL);


INSERT INTO `pipeline_deploy` (`id`, `auth_type`, `local_address`, `auth_id`, `deploy_address`, `deploy_order`, `start_address`, `start_order`) VALUES ('41d705d8029d379a8a2330c315d9527f', 1, 'doublekit-0.0.1-SNAPSHOT.jar', '52e6d477df54ba80b939dc27b911cd16', 'root', '', 'root', 'java -jar demo-0.0.1-SNAPSHOT.jar');

INSERT INTO `pipeline_code_scan` (`id`, `auth_id`, `project_name`) VALUES ('a0b356d1d37781917d40c27451b3f0cf', 'bf038c74441e35e2495c2ec2d4693cc7', 'matflow');

INSERT INTO `pipeline_code` (`id`, `code_name`, `code_address`, `code_branch`, `auth_id`) VALUES ('3f412d09d56cd38733395f52f78eb646', 'https://gitee.com/zcamy/doublekit.git', 'https://gitee.com/zcamy/doublekit.git', 'master', 'a8cc49221d618151939f2162f3cbeecb');
INSERT INTO `pipeline_code` (`id`, `code_name`, `code_address`, `code_branch`, `auth_id`) VALUES ('4019912f4830002c65ff3c6816a78c7c', 'gaomengyuana/drivers-school', 'https://gitee.com/gaomengyuana/drivers-school.git', 'master', '6e65db6c50306f7671171c3305c00f72');
INSERT INTO `pipeline_code` (`id`, `code_name`, `code_address`, `code_branch`, `auth_id`) VALUES ('74d82243940a9e72e56cb29a5c1e1e2b', 'http://172.12.1.10/devops-itdd/tiklab-matflow.git', 'http://172.12.1.10/devops-itdd/tiklab-matflow.git', NULL, '41ea82cec1136cceabed144baf44e3e1');
INSERT INTO `pipeline_code` (`id`, `code_name`, `code_address`, `code_branch`, `auth_id`) VALUES ('d68945b29b86e560de6940f07735616c', 'http://172.12.1.10/devops-itdd/tiklab-matflow.git', 'http://172.12.1.10/devops-itdd/tiklab-matflow.git', NULL, '41ea82cec1136cceabed144baf44e3e1');


INSERT INTO `pipeline_build` (`build_id`, `build_address`, `build_order`) VALUES ('fae3e8707010794cdfa2b4352d84ffce', '', 'mvn clean package');


INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('2c8013697d3e6dd67b49351998bb9ca1', '2022-11-09 17:22:16', '9e37ec363589292b0fcbb1d48536ae11', 'd68945b29b86e560de6940f07735616c', 1, 1);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('42fb358a54b40b192280dbe181bd8f9c', '2022-11-04 16:20:11', 'df1b2f1b384468065c6b920e62b1972c', '74d82243940a9e72e56cb29a5c1e1e2b', 1, 1);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('55e10663f38660604b4d52040e51ae10', '2022-10-17 16:13:09', '679668d5ce24a7a9ff697feca5bb94ff', '3f412d09d56cd38733395f52f78eb646', 1, 1);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('a59009ac256eb76412500cc2280e7c38', '2022-11-07 16:56:33', 'df1b2f1b384468065c6b920e62b1972c', 'a0b356d1d37781917d40c27451b3f0cf', 41, 2);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('b0d4f645f0cac753c8e29535f63a150d', '2022-11-08 17:49:45', '78d1f3132bec370c8feac6400c801d2b', 'bb68d836c2134642c15f5ec1809a56c2', 51, 2);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('c65370935368c23ea5ffdd9ea6f4edd5', '2022-10-17 16:13:13', '679668d5ce24a7a9ff697feca5bb94ff', '41d705d8029d379a8a2330c315d9527f', 31, 3);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('dbfd69f7d96f67200644c38817c07926', '2022-11-08 15:28:21', '78d1f3132bec370c8feac6400c801d2b', '4019912f4830002c65ff3c6816a78c7c', 2, 1);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('e284c27eb4c1d504f09344b2a58c61fb', '2022-11-09 17:22:24', '9e37ec363589292b0fcbb1d48536ae11', '315f4e0c15b25f32d8babbe81799081c', 52, 2);
INSERT INTO `pipeline_config_order` (`config_id`, `create_time`, `pipeline_id`, `task_id`, `task_type`, `task_sort`) VALUES ('fe1e32c4574398fc7826b9059430b2f2', '2022-10-17 16:13:11', '679668d5ce24a7a9ff697feca5bb94ff', 'fae3e8707010794cdfa2b4352d84ffce', 21, 2);
