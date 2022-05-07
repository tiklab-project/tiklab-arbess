
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pipeline
-- ----------------------------
DROP TABLE IF EXISTS `pipeline`;
CREATE TABLE `pipeline`  (
     `pipeline_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流水线id',
     `pipeline_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流水线名称',
     `pipeline_create_user` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流水线创建人',
     `pipeline_create_time` datetime(0) NOT NULL COMMENT '流水线创建时间',
     `pipeline_create_type` int(0) NOT NULL COMMENT '流水线类型',
     PRIMARY KEY (`pipeline_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '流水线表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pipeline_configure
-- ----------------------------
DROP TABLE IF EXISTS `pipeline_configure`;
CREATE TABLE `pipeline_configure`(
       `configure_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置信息id',
       `configure_code_source` int(0) NULL DEFAULT NULL COMMENT '代码源',
       `configure_code_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库名',
       `configure_code_source_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '代码源地址',
       `configure_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分支',
       `configure_test_type` int(0) NULL DEFAULT NULL COMMENT '测试类型',
       `configure_test_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '测试内容',
       `configure_code_structure` int(0) NULL DEFAULT NULL COMMENT '构建源',
       `configure_structure_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '构建文件地址',
       `configure_structure_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '构建命令',
       `configure_deploy_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部署地址',
       `configure_target_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'target文件地址',
       `configure_create_time` datetime(0) NULL DEFAULT NULL COMMENT '配置创建时间',
       `configure_shell` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'shell脚本',
       `pipeline_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id',
       `proof_id_git` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证gitId',
       `proof_id_deploy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证部署id',
       PRIMARY KEY (`configure_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '流水线配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pipeline_history
-- ----------------------------
DROP TABLE IF EXISTS `pipeline_history`;
CREATE TABLE `pipeline_history`  (
     `history_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
     `history_number` int(0) NULL DEFAULT NULL COMMENT '构建次数',
     `history_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建时间',
     `history_way` int(0) NULL DEFAULT NULL COMMENT '构建方式',
     `history_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分支',
     `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id\r\n            ',
     `proof_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证id',
     `log_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志id',
     `configure_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置id',
     PRIMARY KEY (`history_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '构建历史表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pipeline_log
-- ----------------------------
DROP TABLE IF EXISTS `pipeline_log`;
CREATE TABLE `pipeline_log`  (
     `log_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行信息id',
     `log_run_log` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '运行日志',
     `log_code_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拉取时间',
     `log_code_state` int(0) NULL DEFAULT NULL COMMENT '拉取状态',
     `log_test_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '测试时间',
     `log_test_state` int(0) NULL DEFAULT NULL COMMENT '测试状态',
     `log_pack_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '打包时间',
     `log_pack_state` int(0) NULL DEFAULT NULL COMMENT '打包状态',
     `log_deploy_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部署时间',
     `log_deploy_state` int(0) NULL DEFAULT NULL COMMENT '部署状态',
     `log_run_status` int(0) NULL DEFAULT NULL COMMENT '运行状态',
     `pipeline_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水线id',
     PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pipeline_proof
-- ----------------------------
DROP TABLE IF EXISTS `pipeline_proof`;
CREATE TABLE `pipeline_proof`  (
   `proof_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '凭证id',
   `proof_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证名',
   `proof_scope` int(0) NULL DEFAULT NULL COMMENT '作用域',
   `proof_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证类型',
   `proof_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip地址',
   `proof_port` int(0) NULL DEFAULT NULL COMMENT '端口号',
   `proof_username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户',
   `proof_password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
   `proof_describe` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证描述',
   PRIMARY KEY (`proof_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '凭证表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
