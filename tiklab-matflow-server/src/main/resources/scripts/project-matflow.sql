INSERT INTO `pipeline` VALUES ('679668d5ce24a7a9ff697feca5bb94ff', '示例项目1', '111111', '2022-07-28 15:47:29', 1, 0, 0);

INSERT INTO `orc_dm_user` VALUES ('q3ewqrfasf', '679668d5ce24a7a9ff697feca5bb94ff', '111111');

INSERT INTO `pipeline_configure` VALUES ('5ad268afc021ade3548e3d2aa53c5891', '679668d5ce24a7a9ff697feca5bb94ff', '76806330a7f7157828ddb12c44c17019', 1, 1, '2022-07-28 16:49:41', '源码管理', 0);
INSERT INTO `pipeline_configure` VALUES ('94ea78a31854d2b93df84a9508953d6f', '679668d5ce24a7a9ff697feca5bb94ff', '9b420a082fc51e79792edaea928dd1b7', 21, 2, '2022-07-28 16:17:54', '构建', 0);
INSERT INTO `pipeline_configure` VALUES ('ba67e442d1c1deefc2a74826101598aa', '679668d5ce24a7a9ff697feca5bb94ff', '41d705d8029d379a8a2330c315d9527f', 31, 3, '2022-07-28 16:17:54', '部署', 0);


INSERT INTO `pipeline_code` VALUES ('76806330a7f7157828ddb12c44c17019', 1, 'https://gitee.com/zcamy/doublekit.git', 'https://gitee.com/zcamy/doublekit.git', NULL, 'a8cc49221d618151939f2162f3cbeecb', 1, NULL);

INSERT INTO `pipeline_structure` VALUES ('9b420a082fc51e79792edaea928dd1b7', 21, '/', 'mvn clean package', 2, '构建');

INSERT INTO `pipeline_deploy` VALUES ('41d705d8029d379a8a2330c315d9527f', 31, 0, 'doublekit-0.0.1-SNAPSHOT.jar', 'root', 0, '52e6d477df54ba80b939dc27b911cd16', 0, 3, '部署', '172.12.1.18', 22, '/', '', 'java -jar demo-0.0.1-SNAPSHOT.jar');

INSERT INTO `pipeline_proof` VALUES ('52e6d477df54ba80b939dc27b911cd16', 'linux', 5, 'password', 'root', 'darth2020', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');
INSERT INTO `pipeline_proof` VALUES ('a8cc49221d618151939f2162f3cbeecb', '示例凭证1', 1, 'password', 'zcamy', 'zhq11191750', NULL, NULL, '2022-07-28 15:47:29', 1, '111111');
