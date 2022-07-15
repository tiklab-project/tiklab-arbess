
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



--权限默认
INSERT INTO `prc_function` VALUES ('0a41330da8abce203072dda6f1b17340', '凭证管理', 'F', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1478e6b893fd9859c3d5423cac6f0cca', '用户目录', 'C', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1de39b831122c491f5cc23571e35c8d7', '插件管理', 'G', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('1ec604c4a89c34b58a3831ac236d78ab', '系统管理', 'H', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('21912558cb2163ebc878be110461bd7a', '用户列表', 'B', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('28f6b00c82b67a1205f8e4509a5666d3', '用户管理', 'A', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('5d43842bb973a89e8da8d5a74c7b3a08', '权限管理', 'E', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('972a60cda050cb457d2593651062cc0d', '组织管理', 'D', NULL, NULL, '1');


INSERT INTO `prc_role_function` VALUES ('242e1ec8f600fccb2b2f3b60a8255b0e', '4b0a7d5f837fefb46f78036e6ffc11f1', '5d43842bb973a89e8da8d5a74c7b3a08');
INSERT INTO `prc_role_function` VALUES ('4d05d19579c1a3d26718358616c5f2d0', '4b0a7d5f837fefb46f78036e6ffc11f1', '21912558cb2163ebc878be110461bd7a');
INSERT INTO `prc_role_function` VALUES ('522e5b59ca9d58af8f27f9175707f0ba', '4b0a7d5f837fefb46f78036e6ffc11f1', '1de39b831122c491f5cc23571e35c8d7');
INSERT INTO `prc_role_function` VALUES ('7e88fe8991a98a35389feef53cc0e19b', '4b0a7d5f837fefb46f78036e6ffc11f1', '972a60cda050cb457d2593651062cc0d');
INSERT INTO `prc_role_function` VALUES ('8f69e900479e4bf2be088e4fe239cfdb', '4b0a7d5f837fefb46f78036e6ffc11f1', '1478e6b893fd9859c3d5423cac6f0cca');
INSERT INTO `prc_role_function` VALUES ('9120db7b13f54fab01f0acb7392992a1', '4b0a7d5f837fefb46f78036e6ffc11f1', '1ec604c4a89c34b58a3831ac236d78ab');
INSERT INTO `prc_role_function` VALUES ('937cb596a37f351ab5272e941f975191', '4b0a7d5f837fefb46f78036e6ffc11f1', '0a41330da8abce203072dda6f1b17340');
INSERT INTO `prc_role_function` VALUES ('f13b5ab195749e2c004a2907e80a5712', '4b0a7d5f837fefb46f78036e6ffc11f1', '28f6b00c82b67a1205f8e4509a5666d3');


INSERT INTO `prc_role` VALUES ('4b0a7d5f837fefb46f78036e6ffc11f1', 'root', '管理员', 'custom', '1');


INSERT INTO `prc_role_user` VALUES ('ed75499b782751b2bdf410183872b65d', '4b0a7d5f837fefb46f78036e6ffc11f1', '111111');