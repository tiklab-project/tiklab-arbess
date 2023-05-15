
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('03dd3aa23ce9', '系统信息', 'pipeline_system', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('0a783e372066', '权限', 'pipeline_permission', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('262b426e2a86', '插件', 'pipeline_plugin', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('47784cff8b3c', '主机配置', 'resources_host', NULL, 26, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('8700a662f1ae', '流水线设置', 'pipeline_seting', NULL, 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('96950f6c591d', '流水线成员', 'pipeline_user', NULL, 38, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('9a85c4041ea9', '服务配置', 'resources_server', NULL, 25, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('9c57b5343ffe', '认证配置', 'pipeline_auth', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c08882dca6ab', '修改流水线信息', 'pipeline_update', '8700a662f1ae', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c80b65d2cb97', '环境配置', 'pipeline_env', NULL, 1, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('c8774229c6b8', '操作日志', 'pipeline_log', NULL, 40, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('d5535a61c9ce', '删除流水线', 'pipeline_delete', '8700a662f1ae', 1, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('d5e1ecaae2e0', '流水线权限', 'pipeline_auth', NULL, 37, '2');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('f6f51f944133', '消息通知类型', 'message_type', NULL, 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('7d69fed448ee', '消息管理', 'message_setting', NULL, 6, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('f79c084575fa', '版本许可证', 'pipeline_version', NULL, 39, '1');
INSERT INTO pcs_prc_function (id, name, code, parent_function_id, sort, type) VALUES ('eqeqw84575fa', '应用授权', 'product_auth', NULL, 39, '1');

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('00e840ea5302', '1', '890e7d41decf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('05d66918b2dd', '1', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('064d1a5ddbc5', 'bf699ba68c87', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('0fd56de07eaf', '2', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('1134dbdbb6d0', '1', '9633d9475886');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('143e6010ba4b', 'f51b3e9cdf10', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('256bca68cd16', 'f51b3e9cdf10', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('28b4ec49b63c', '3f22e1a0ff00', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3572dd063f4f', '3f22e1a0ff00', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('39f06b018e83', '1', '03dd3aa23ce9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('401823a837d7', '1', '428be660dea3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('431db3316908', 'f51b3e9cdf10', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('4528a26ed101', 'f51b3e9cdf10', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('462d9282cc98', '1', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('4a5fc591234a', '1', '7d69fed448ee');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('50b9a6692ace', '1', '47784cff8b3c');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('51a182feaad3', '696e8b4575bb', '03dd3aa23ce9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('58a058ff9711', 'f51b3e9cdf10', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('5e6f1b2ae44e', 'ae28429ef243', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('5feb15faff5b', '1', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('6724ee306e70', '1efcc15ab020', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('732aa5077352', '1', 'f79c084575fa');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('74217ab2e9eb', 'ae28429ef243', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7d931f493b50', '1', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('7d9f77e5e4da', '3f22e1a0ff00', '96950f6c591d');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('81de829dec84', '1', '325c2503007f');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('887c296a9558', 'bf699ba68c87', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8cfe868f7e3d', '1', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('8feb1f0512fe', '1', '0a783e372066');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('953b0d1b33a2', '1', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9c0d4f578ee7', '3f22e1a0ff00', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9c7732b2ce82', '1', '925371be8ec6');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('9ea782824dd8', '696e8b4575bb', 'f6f51f944133');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('a948facb3178', '2', 'd5e1ecaae2e0');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('b2e02db6d6d2', '1', 'c8774229c6b8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bbf66f07ea08', '3f22e1a0ff00', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('bece5428495b', '1', '9c57b5343ffe');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c04b4a8a7124', '1', '043e412151db');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c4004134de45', '1', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c5779edb1c87', '1', '447d9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('c9d209973792', '696e8b4575bb', '7d69fed448ee');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d47dc6474805', '1', '262b426e2a86');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('d58ab33914bd', '1efcc15ab020', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('dc13e349314c', '1', 'c80b65d2cb97');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e1a214388c4d', '1', 'f6f51f944133');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e1b6898cee7a', '696e8b4575bb', 'f79c084575fa');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e69a45532289', '1', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('e6cb2507ce87', '2', 'c08882dca6ab');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('ed97225cf0b9', '2', 'd5535a61c9ce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f3df88d2de5b', '1', '9a85c4041ea9');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f83883f4bbaa', '2', '8700a662f1ae');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('f9011798e814', '696e8b4575bb', '9a85c4041ea9');


INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('1', '管理员', '管理员', 'system', '1', 1, 1, 0);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('2', '管理员', '管理员', 'system', '2', 1, 1, 0);

INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('1efcc15ab020', '普通用户', '普通用户', 'system', '2', 1, 0, 1);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('696e8b4575bb', '普通用户', '普通用户', 'system', '1', 1, 0, 1);


INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('3f22e1a0ff00', '管理员', '管理员', 'system', '2', 2, 1, 0);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('f51b3e9cdf10', '管理员', '管理员', 'system', '2', 2, 1, 0);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('ae28429ef243', '普通用户', '普通用户', 'system', '2', 2, 0, 1);
INSERT INTO pcs_prc_role (id, name, description, grouper, type, scope, business_type, default_role) VALUES ('bf699ba68c87', '普通用户', '普通用户', 'system', '2', 2, 0, 1);


INSERT INTO pcs_prc_role_user (id, role_id, user_id) VALUES ('d0c4e835c7a2', 'f51b3e9cdf10', '111111');
INSERT INTO pcs_prc_role_user (id, role_id, user_id) VALUES ('2adeb79ba186', '3f22e1a0ff00', '111111');

INSERT INTO pcs_prc_role_user (id, role_id,user_id) VALUES ('2e4b7342485b', '2', '111111');

-- 用户组信息
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('23wa081e7588', '1', '4235d2624bdf');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw474ad31d1', '1', 'hf43e412151e');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw0011966d1', '1', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqw8d88e5205', '1', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqwbbaa68277', '1', '43e7d41decf7');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('eqwl1fc798ae', '1', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('asdas1fc798a', '1', 'eqeqw84575fa');





