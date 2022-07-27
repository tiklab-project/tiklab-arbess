
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
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
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
  `type` int NULL DEFAULT NULL COMMENT '容器类型',
  `deploy_type` int NULL DEFAULT NULL COMMENT '部署类型',
  `source_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '打包文件地址',
  `deploy_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部署文件地址',
  `start_port` int NULL DEFAULT NULL COMMENT 'docker启动端口',
  `proof_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证id',
  `mapping_port` int NULL DEFAULT NULL COMMENT '映射端口',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `deploy_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行名称',
  `ssh_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `ssh_port` int NULL DEFAULT NULL COMMENT '端口号',
  `start_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '启动文件地址',
  `deploy_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件操作',
  `start_shell` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '启动命令',
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


CREATE TABLE `pipeline_proof_task`  (
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `proof_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



--权限默认
INSERT INTO `prc_function` VALUES ('0aee720166d8a13beb81f23bcf0f8d2d', '系统权限中心', 'E', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1ac7a5144aab83db56d0bdaab9da97dc', '工作空间', 'AA', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('36f831865dd4bd490e1fdb4b1d38b763', '用户管理', 'A', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('3af907a0b9d62159c50ae13f17cb2c36', '系统信息', 'H', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('3ff92a1c0d35ff83f37d92aab543bb21', '配置', 'BB', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('66ffbf32b20033c9546faae7336943f9', '组织管理', 'D', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('6c325e97fc58bd362cd00f74bf3f3609', '凭证管理', 'DD3', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('715a723396096aef0dca4bafa8f4cd24', '其他设置', 'DD4', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('8700a662f1aebb83f790783c3abe15b5', '设置', 'DD', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('8d26791c4634aee4121115b29e944bc1', '历史', 'CC', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('c08882dca6ab325b10fb2b7c0cfc8156', '角色管理', 'DD2', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('c103338b85c47ee6f5f9bc65e03cfa87', '用户列表', 'B', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('c3a07289216a53628a4f9c558e58d05c', '凭证管理', 'F', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('d258d8c1f73d4afc733600ab6504bf0c', '用户目录', 'C', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('d5535a61c9cefaccd71b44d298474775', '成员列表', 'DD1', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('dfa50d1a6f3466a75c37325c71b9a8fc', '插件管理', 'G', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('f741b2f59129f7b025884b4000de72b0', '项目权限中心', 'I', NULL, NULL, '1');

INSERT INTO `prc_role` VALUES ('83dc0140bc3a491383b8de5099b1d87b', 'user', '用户', 'custom', '1');
INSERT INTO `prc_role` VALUES ('f1b425c39359e053eed89a069c9601cf', 'root', '管理员', 'custom', '1');


INSERT INTO `prc_role_function` VALUES ('05a3ea2d80ddefeb2b264bcbad294f93', 'f1b425c39359e053eed89a069c9601cf', 'c103338b85c47ee6f5f9bc65e03cfa87');
INSERT INTO `prc_role_function` VALUES ('06545816b38ab94c9060d80e1cff485b', 'f1b425c39359e053eed89a069c9601cf', 'f741b2f59129f7b025884b4000de72b0');
INSERT INTO `prc_role_function` VALUES ('141e9e0785a5dfbbe9c1890a020da09f', 'f1b425c39359e053eed89a069c9601cf', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `prc_role_function` VALUES ('1931f07540ebd9fc393fe95a12b07f64', 'f1b425c39359e053eed89a069c9601cf', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO `prc_role_function` VALUES ('1c5c8a8f8aabaa7f7b39dffc4aac0d96', 'f1b425c39359e053eed89a069c9601cf', '6c325e97fc58bd362cd00f74bf3f3609');
INSERT INTO `prc_role_function` VALUES ('1d401ccf8e830863a1f9f64f8cc408c8', '83dc0140bc3a491383b8de5099b1d87b', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO `prc_role_function` VALUES ('471696af197e899a887e0d526a59f5e8', 'f1b425c39359e053eed89a069c9601cf', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO `prc_role_function` VALUES ('52e87e9e0353247618bace9d6dacc688', 'f1b425c39359e053eed89a069c9601cf', '0aee720166d8a13beb81f23bcf0f8d2d');
INSERT INTO `prc_role_function` VALUES ('5eab324e375ca979136268e53c8677d5', '83dc0140bc3a491383b8de5099b1d87b', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO `prc_role_function` VALUES ('67ab7dda75d1b5abe06d23238900bce7', '83dc0140bc3a491383b8de5099b1d87b', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO `prc_role_function` VALUES ('70bb3d37ebdd3acfbc3b048ecd28944c', 'f1b425c39359e053eed89a069c9601cf', '66ffbf32b20033c9546faae7336943f9');
INSERT INTO `prc_role_function` VALUES ('76a6e37ea5cface70e952edd68ac5293', 'f1b425c39359e053eed89a069c9601cf', '3af907a0b9d62159c50ae13f17cb2c36');
INSERT INTO `prc_role_function` VALUES ('7fb53048f672717c8b350f6d582f1afd', 'f1b425c39359e053eed89a069c9601cf', '715a723396096aef0dca4bafa8f4cd24');
INSERT INTO `prc_role_function` VALUES ('8924712bc42ce166a7f1d6fa01e39dbc', 'f1b425c39359e053eed89a069c9601cf', 'dfa50d1a6f3466a75c37325c71b9a8fc');
INSERT INTO `prc_role_function` VALUES ('89d2137b1a8c8355c4e63f2363d538b9', 'f1b425c39359e053eed89a069c9601cf', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO `prc_role_function` VALUES ('bfaa8c53da5ddc317e7a1567686d4ce4', '83dc0140bc3a491383b8de5099b1d87b', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO `prc_role_function` VALUES ('cc112e16dcf37be3fe9e5a71d375ae07', 'f1b425c39359e053eed89a069c9601cf', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO `prc_role_function` VALUES ('e584521ba07b2ae6b8dbf521e8920171', 'f1b425c39359e053eed89a069c9601cf', 'd258d8c1f73d4afc733600ab6504bf0c');
INSERT INTO `prc_role_function` VALUES ('eae3c67c44c6e040a2469f5c794ffa49', 'f1b425c39359e053eed89a069c9601cf', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `prc_role_function` VALUES ('f613e919f659d2abe26fb89fd20bef46', 'f1b425c39359e053eed89a069c9601cf', '1ac7a5144aab83db56d0bdaab9da97dc');
INSERT INTO `prc_role_function` VALUES ('f8ef3ab245f87dd9012907efc60e9c20', 'f1b425c39359e053eed89a069c9601cf', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `prc_role_function` VALUES ('ffcfba5120c8a26b9d3b02a6178df68f', '83dc0140bc3a491383b8de5099b1d87b', '1ac7a5144aab83db56d0bdaab9da97dc');

INSERT INTO `prc_role_user` VALUES ('3822dcb805855ae85c51a3522c90e623', 'f1b425c39359e053eed89a069c9601cf', '111111');
INSERT INTO `prc_role_user` VALUES ('95cbb9ee728100b79cf9a18290298ace', '83dc0140bc3a491383b8de5099b1d87b', '222222');
