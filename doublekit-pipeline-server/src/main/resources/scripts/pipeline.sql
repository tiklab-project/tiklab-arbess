
CREATE TABLE `pipeline`  (
  `pipeline_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流水线id',
  `pipeline_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线名称',
  `user_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `pipeline_create_time` datetime NULL DEFAULT NULL COMMENT '流水线创建时间',
  `pipeline_create_type` int NULL DEFAULT NULL COMMENT '流水线类型',
  `pipeline_collect` int NULL DEFAULT NULL COMMENT '收藏状态',
  `pipeline_state` int NULL DEFAULT NULL COMMENT '运行状态',
  PRIMARY KEY (`pipeline_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '流水线表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_action`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `massage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `news` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_code`  (
  `code_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'gitId',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `code_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址名',
  `code_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'git地址',
  `code_branch` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分支',
  `proof_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证id',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `code_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行名称',
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_configure`  (
  `configure_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置id',
  `pipeline_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id',
  `task_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'taskId',
  `task_type` int NULL DEFAULT NULL COMMENT '执行类型',
  `task_sort` int NULL DEFAULT NULL COMMENT '执行顺序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `task_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '别名',
  `view` int NULL DEFAULT NULL,
  PRIMARY KEY (`configure_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_deploy`  (
  `deploy_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部署id',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `deploy_target_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '打包文件地址',
  `deploy_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部署文件地址',
  `deploy_shell` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'shell命令',
  `deploy_docker_port` int NULL DEFAULT NULL COMMENT 'docker启动端口',
  `proof_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证id',
  `deploy_mapping_port` int NULL DEFAULT NULL COMMENT '映射端口',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `deploy_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行名称',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `port` int NULL DEFAULT NULL COMMENT '端口号',
  PRIMARY KEY (`deploy_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_follow`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_history`  (
  `history_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `run_way` int NULL DEFAULT NULL COMMENT '构建方式',
  `run_status` int NULL DEFAULT NULL COMMENT '执行状态',
  `run_time` int NULL DEFAULT NULL COMMENT '执行时间',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行人',
  `run_log` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '日志',
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id',
  `find_number` int NULL DEFAULT NULL COMMENT '构建次数',
  `find_state` int NULL DEFAULT NULL COMMENT '判断是否正在执行',
  PRIMARY KEY (`history_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '构建历史表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_log`  (
  `log_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志id',
  `history_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '历史id',
  `task_sort` int NULL DEFAULT NULL COMMENT '运行顺序',
  `task_type` int NULL DEFAULT NULL COMMENT '执行类型',
  `run_state` int NULL DEFAULT NULL COMMENT '运行状态',
  `run_time` int NULL DEFAULT NULL COMMENT '运行时间',
  `run_log` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '运行日志',
  `task_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_open`  (
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `number` int NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_proof`  (
  `proof_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '凭证id',
  `proof_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证名',
  `proof_scope` int NULL DEFAULT NULL COMMENT '作用域',
  `proof_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证类型',
  `proof_username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户',
  `proof_password` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '密码',
  `proof_describe` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证描述',
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id',
  `proof_create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`proof_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '凭证表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_structure`  (
  `structure_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '构建id',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `structure_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件地址',
  `structure_order` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '构建命令',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `structure_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行名称',
  PRIMARY KEY (`structure_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `pipeline_test`  (
  `test_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试id',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `test_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '测试命令',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `test_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`test_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;



--权限默认
INSERT INTO `prc_function` VALUES ('0a41330da8abce203072dda6f1b17340', '凭证管理', 'F', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1478e6b893fd9859c3d5423cac6f0cca', '用户目录', 'C', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1de39b831122c491f5cc23571e35c8d7', '插件管理', 'G', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1ec604c4a89c34b58a3831ac236d78ab', '系统管理', 'H', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('21912558cb2163ebc878be110461bd7a', '用户列表', 'B', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('28f6b00c82b67a1205f8e4509a5666d3', '用户管理', 'A', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('5d43842bb973a89e8da8d5a74c7b3a08', '权限管理', 'E', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('972a60cda050cb457d2593651062cc0d', '组织管理', 'D', NULL, NULL, '1');


INSERT INTO `prc_role_function` VALUES ('00a51cf35fbc3e5843eeb7c31170bc05', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'fe0cb56aa84da1a0e15c40c85518b497');
INSERT INTO `prc_role_function` VALUES ('013520f86aa3a0ae523fa5f98fd5fdeb', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'd33d203f073af128be9865c5d33ebaf9');
INSERT INTO `prc_role_function` VALUES ('02c0c44b3164117b1ab7f18bbd227328', '58a8e84ca6759113a28cbb52ad79ce81', 'b54053757cc5d7f708fb8e4c6ad9ca58');
INSERT INTO `prc_role_function` VALUES ('0545bdf768c85de390273b740184525d', '58a8e84ca6759113a28cbb52ad79ce81', 'fe0cb56aa84da1a0e15c40c85518b497');
INSERT INTO `prc_role_function` VALUES ('08a9a84948d8d7fd187fef03c2a4c186', '58a8e84ca6759113a28cbb52ad79ce81', '9eb6bafd52f2f420daeabec0f13d95b0');
INSERT INTO `prc_role_function` VALUES ('175515838ced4480aaf75f11f779b8dc', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'b54053757cc5d7f708fb8e4c6ad9ca58');
INSERT INTO `prc_role_function` VALUES ('242e1ec8f600fccb2b2f3b60a8255b0e', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '5d43842bb973a89e8da8d5a74c7b3a08');
INSERT INTO `prc_role_function` VALUES ('29bae94302fc54dd22786c8bdfe8488f', '58a8e84ca6759113a28cbb52ad79ce81', 'd33d203f073af128be9865c5d33ebaf9');
INSERT INTO `prc_role_function` VALUES ('2b0bc4d697eca5c511e322285e8940e5', '58a8e84ca6759113a28cbb52ad79ce81', '972a60cda050cb457d2593651062cc0d');
INSERT INTO `prc_role_function` VALUES ('2b73798880fa8cd41fd89dcb7fb49cb2', '58a8e84ca6759113a28cbb52ad79ce81', '431b86238f21d8f4802eddbfbcdcf921');
INSERT INTO `prc_role_function` VALUES ('346354af333392bfe0df26ff2a972850', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '3a0b21c6fa07feaf3131e8e81d36b5c0');
INSERT INTO `prc_role_function` VALUES ('35367f80e8f020965ea62c70df86a632', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '0469560b683b68dc95d68681141a3296');
INSERT INTO `prc_role_function` VALUES ('3a4e67753b194c21648fba554aa3a9c2', '58a8e84ca6759113a28cbb52ad79ce81', 'c2ef175fed67a08cd11ed0b1abacd39b');
INSERT INTO `prc_role_function` VALUES ('3c69918ea54e5c6942cbbdd0a458f39c', '58a8e84ca6759113a28cbb52ad79ce81', '3a0b21c6fa07feaf3131e8e81d36b5c0');
INSERT INTO `prc_role_function` VALUES ('3fae3f069d571deb89f08d9fc886af62', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'df0c8ec7c305e4e079e709c5edc9ca5b');
INSERT INTO `prc_role_function` VALUES ('4d05d19579c1a3d26718358616c5f2d0', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '21912558cb2163ebc878be110461bd7a');
INSERT INTO `prc_role_function` VALUES ('51be8dd84c26e6fb2ab8441c494dd835', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'bfb2324849b70ad6758ff087a85edaab');
INSERT INTO `prc_role_function` VALUES ('522e5b59ca9d58af8f27f9175707f0ba', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '1de39b831122c491f5cc23571e35c8d7');
INSERT INTO `prc_role_function` VALUES ('5a70d51926bbb2a1f50311a1d017a666', '58a8e84ca6759113a28cbb52ad79ce81', '28f6b00c82b67a1205f8e4509a5666d3');
INSERT INTO `prc_role_function` VALUES ('5d5389430c95e088032b9c75c60eea70', '58a8e84ca6759113a28cbb52ad79ce81', 'da131327027b71b29c20f40125e82b77');
INSERT INTO `prc_role_function` VALUES ('64e40e69d0b093a188e32fbd42685a58', '58a8e84ca6759113a28cbb52ad79ce81', '5d43842bb973a89e8da8d5a74c7b3a08');
INSERT INTO `prc_role_function` VALUES ('65a917287008b315fa5eacabb22ffde8', '58a8e84ca6759113a28cbb52ad79ce81', '7b4971a1fc0d23e79dfedcc56d53ce62');
INSERT INTO `prc_role_function` VALUES ('6ad751b4c987f8c3ebe11561c60695ca', '58a8e84ca6759113a28cbb52ad79ce81', '2b66d97a006c4fc23d630d22cbfebc41');
INSERT INTO `prc_role_function` VALUES ('6b612638162e62408ccf0542f63b6455', '58a8e84ca6759113a28cbb52ad79ce81', 'e0e0e08dc820fd40095fe2e768c063cb');
INSERT INTO `prc_role_function` VALUES ('764e71a154100b8d774d80415cf96da0', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'bb5c30716831996748901e7940c01dbc');
INSERT INTO `prc_role_function` VALUES ('7e88fe8991a98a35389feef53cc0e19b', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '972a60cda050cb457d2593651062cc0d');
INSERT INTO `prc_role_function` VALUES ('7ebf94f50b28a870518ca2a44c86528e', '58a8e84ca6759113a28cbb52ad79ce81', 'aa6d631b987670cf68d3113c336a6417');
INSERT INTO `prc_role_function` VALUES ('84f809af48ab40f38f5add0f4fb32bd0', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '6fc9af2d5eed5e0d7864a68a29aa2f80');
INSERT INTO `prc_role_function` VALUES ('8f69e900479e4bf2be088e4fe239cfdb', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '1478e6b893fd9859c3d5423cac6f0cca');
INSERT INTO `prc_role_function` VALUES ('90f8ad3b1d42933b6f37429bccaa9a7f', '58a8e84ca6759113a28cbb52ad79ce81', 'bb5c30716831996748901e7940c01dbc');
INSERT INTO `prc_role_function` VALUES ('9120db7b13f54fab01f0acb7392992a1', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '1ec604c4a89c34b58a3831ac236d78ab');
INSERT INTO `prc_role_function` VALUES ('937cb596a37f351ab5272e941f975191', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '0a41330da8abce203072dda6f1b17340');
INSERT INTO `prc_role_function` VALUES ('94658977145a8a88fc17925aa44c3aef', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'a14ef995b8cff4ef4473b27709d18140');
INSERT INTO `prc_role_function` VALUES ('977ba1c6da7427bd311045383ae30be9', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'a3667f1ab9ddc63a2da47c373bebb471');
INSERT INTO `prc_role_function` VALUES ('a2fe7cd420c9106c2c54e442a8d46502', '58a8e84ca6759113a28cbb52ad79ce81', '1ec604c4a89c34b58a3831ac236d78ab');
INSERT INTO `prc_role_function` VALUES ('b6f4128257db7245067df206211ed0b2', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '9eb6bafd52f2f420daeabec0f13d95b0');
INSERT INTO `prc_role_function` VALUES ('b8ee40bc910ef43f4d66eed03d1e2788', '58a8e84ca6759113a28cbb52ad79ce81', '3b42efc2684189ce09b9d9102731714c');
INSERT INTO `prc_role_function` VALUES ('b98670b7a46adb89d0f0d2cb0bdcd308', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'ba36a695b760e496083380270844a43a');
INSERT INTO `prc_role_function` VALUES ('baf6185612ce8ba35bea3154d6c5d5d0', '58a8e84ca6759113a28cbb52ad79ce81', '6fc9af2d5eed5e0d7864a68a29aa2f80');
INSERT INTO `prc_role_function` VALUES ('bb3eb64670f02809411486d556c055a0', '58a8e84ca6759113a28cbb52ad79ce81', '086c3ec51e555475275784a49cd7913d');
INSERT INTO `prc_role_function` VALUES ('bec6dcb8c579c2b8d9cfa009c9af7247', '58a8e84ca6759113a28cbb52ad79ce81', 'a3667f1ab9ddc63a2da47c373bebb471');
INSERT INTO `prc_role_function` VALUES ('bf6d26f8a370d881160ca94dc2b1df7c', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', 'd6509a9cb2ba2f4c97216a41aab4cc1b');
INSERT INTO `prc_role_function` VALUES ('bf7674eadbc895af1ddfaf61b508fe81', '58a8e84ca6759113a28cbb52ad79ce81', '4fb2151d2b08356abc43cb177f354c60');
INSERT INTO `prc_role_function` VALUES ('c5a2765e2e9d9156586c710f91d22b9b', '58a8e84ca6759113a28cbb52ad79ce81', '0a41330da8abce203072dda6f1b17340');
INSERT INTO `prc_role_function` VALUES ('c5f4c58e71fa0871c94044716164fb96', '58a8e84ca6759113a28cbb52ad79ce81', '1de39b831122c491f5cc23571e35c8d7');
INSERT INTO `prc_role_function` VALUES ('ccacbebd6a8b1b43a414885049393b9d', '58a8e84ca6759113a28cbb52ad79ce81', '21912558cb2163ebc878be110461bd7a');
INSERT INTO `prc_role_function` VALUES ('d4026bde34cb92295d784e486b15b31e', '58a8e84ca6759113a28cbb52ad79ce81', '54b8176ad2c33773a4b235a6911a6b46');
INSERT INTO `prc_role_function` VALUES ('d6fd973fa464b39deb3d1e1305e0b493', '58a8e84ca6759113a28cbb52ad79ce81', 'ba36a695b760e496083380270844a43a');
INSERT INTO `prc_role_function` VALUES ('d8cd3d2e02fe70580968b1df362e241b', '58a8e84ca6759113a28cbb52ad79ce81', 'a14ef995b8cff4ef4473b27709d18140');
INSERT INTO `prc_role_function` VALUES ('d913a13780d9a70b640ec2be301d2810', '58a8e84ca6759113a28cbb52ad79ce81', '45193e97209e31c1bb461f22ff0e66d5');
INSERT INTO `prc_role_function` VALUES ('e276bf195318c18686938ad24f144941', '58a8e84ca6759113a28cbb52ad79ce81', 'df0c8ec7c305e4e079e709c5edc9ca5b');
INSERT INTO `prc_role_function` VALUES ('e318738e2eb20474bd790546ba3ed90a', '58a8e84ca6759113a28cbb52ad79ce81', 'fc0e45ebc130db71342da2b33f3135da');
INSERT INTO `prc_role_function` VALUES ('e3e4b806e517f645db1c950d09d03508', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '54b8176ad2c33773a4b235a6911a6b46');
INSERT INTO `prc_role_function` VALUES ('e579c4fed08f3451dd7996ca4876ef2e', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '2cd725b7166fd2d7ec9a3651b4f40d7b');
INSERT INTO `prc_role_function` VALUES ('e7123c9fb547eda3dd19c6da2baa1e62', '58a8e84ca6759113a28cbb52ad79ce81', '0469560b683b68dc95d68681141a3296');
INSERT INTO `prc_role_function` VALUES ('f13b5ab195749e2c004a2907e80a5712', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '28f6b00c82b67a1205f8e4509a5666d3');
INSERT INTO `prc_role_function` VALUES ('f218e6250318f32f354534a0a301bfa8', '58a8e84ca6759113a28cbb52ad79ce81', '2cd725b7166fd2d7ec9a3651b4f40d7b');
INSERT INTO `prc_role_function` VALUES ('f88dbcce30bfebb97ad08342a14fc5ac', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '2b66d97a006c4fc23d630d22cbfebc41');
INSERT INTO `prc_role_function` VALUES ('fa526f59d87aff24136ee4ecc290ae45', 'dcb0dfc3a9e144ac0fd96ef5c38eef98', '94da83ba3016cf19c7bc05add26e06f7');
INSERT INTO `prc_role_function` VALUES ('faddef4b02a5f75a966513bc2dff6f82', '58a8e84ca6759113a28cbb52ad79ce81', '1478e6b893fd9859c3d5423cac6f0cca');


INSERT INTO `prc_role` VALUES ('4b0a7d5f837fefb46f78036e6ffc11f1', 'root', '管理员', 'system', '2');
INSERT INTO `prc_role` VALUES ('d42c4f94db5073d2c9891d8fa8962e9b', 'user', '用户', 'custom', '2');
