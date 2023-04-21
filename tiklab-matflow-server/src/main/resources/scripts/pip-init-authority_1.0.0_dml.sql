
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('03dd3aa23ce98c638471eec6ce3aaf19', '系统信息', 'pipeline_system', NULL, 1, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('0a783e37206685608ccf9e40865e74a0', '权限', 'pipeline_permission', NULL, 1, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('262b426e2a86fe736480eb4404214f09', '插件', 'pipeline_plugin', NULL, 1, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('47784cff8b3c54f009705ea783461221', '主机配置', 'resources_host', NULL, 26, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('8700a662f1aebb83f790783c3abe15b5', '流水线设置', 'pipeline_seting', NULL, 1, '2');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('96950f6c591db90081a065e619d1645b', '流水线成员', 'pipeline_user', NULL, 38, '2');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('9a85c4041ea9a0e98d86ba7db9ba7539', '服务配置', 'resources_server', NULL, 25, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('9c57b5343ffed09c5d806a96433b0aca', '认证配置', 'pipeline_auth', NULL, 1, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('c08882dca6ab325b10fb2b7c0cfc8156', '修改流水线信息', 'pipeline_update', '8700a662f1aebb83f790783c3abe15b5', 1, '2');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('c80b65d2cb975639de41f22b02fba92d', '环境配置', 'pipeline_env', NULL, 1, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('c8774229c6b8f6b045fa37e02be522eb', '操作日志', 'pipeline_log', NULL, 40, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('d5535a61c9cefaccd71b44d298474775', '删除流水线', 'pipeline_delete', '8700a662f1aebb83f790783c3abe15b5', 1, '2');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('d5e1ecaae2e07789ae934f3c73eab502', '流水线权限', 'pipeline_auth', NULL, 37, '2');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('f6f51f94413343fs0013f4f20c378dc6', '消息通知类型', 'message_type', NULL, 6, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('f6f51f9441339cd20013f4f20c378dc6', '消息管理', 'message_setting', NULL, 6, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('f79c084575fa3ee8614b3a778edb7e4e', '版本许可证', 'pipeline_version', NULL, 39, '1');
INSERT INTO `pcs_prc_function` (`id`, `name`, `code`, `parent_function_id`, `sort`, `type`) VALUES ('eqeqw84575fa3ee8614eqwew8edb7e4e', '应用授权', 'product_auth', NULL, 39, '1');

INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('00e840ea5302ec71f77b7f2d4cbf8e88', '1', '890e7d41decf71cfe3b0e80b0c4179cf');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('05d66918b2ddc9786b57e0779084f286', '1', '57a3bcd1e5fedd77824359d06b06fe49');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('064d1a5ddbc586f814deb4feeabe40d5', 'bf699ba68c8700a9760b79bdf859a092', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('0fd56de07eaf4f9f2809e1b1f774eeb5', '2', '96950f6c591db90081a065e619d1645b');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('1134dbdbb6d09c269542963a38ad90d1', '1', '9633d947588684a5881ccff9eaa3aba0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('143e6010ba4b7e86f7e3c7f49d231797', 'f51b3e9cdf10968d2f1080c43647eac5', '96950f6c591db90081a065e619d1645b');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('256bca68cd161a90fdf03fdaa300d82b', 'f51b3e9cdf10968d2f1080c43647eac5', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('28b4ec49b63c040dd5b9ddb848fc94c7', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', 'd5e1ecaae2e07789ae934f3c73eab502');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('3572dd063f4fbad849a54d96e43b0398', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('39f06b018e83edbc6dffa44526f9b57a', '1', '03dd3aa23ce98c638471eec6ce3aaf19');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('401823a837d7c0c209df8870f6c706e0', '1', '428be660dea3db2a2c5a613420b3ead7');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('431db3316908fa248f6bc295a0d74b02', 'f51b3e9cdf10968d2f1080c43647eac5', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('4528a26ed10167d967840587b2c97868', 'f51b3e9cdf10968d2f1080c43647eac5', 'd5e1ecaae2e07789ae934f3c73eab502');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('462d9282cc98214ab13c8940228b3f6c', '1', 'e8bf9843bc9da8d3c4c33e31174496b3');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('4a5fc591234a28ed1aaa69dcdc5f1d6c', '1', 'f6f51f9441339cd20013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('50b9a6692aced99d72d7ad0c802dd3c0', '1', '47784cff8b3c54f009705ea783461221');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('51a182feaad3ca16658cb011e7b4d9e4', '696e8b4575bbfe842d5a5d534508fa7c', '03dd3aa23ce98c638471eec6ce3aaf19');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('58a058ff9711d0e7f1705c78e2f46621', 'f51b3e9cdf10968d2f1080c43647eac5', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('5e6f1b2ae44e85f54ffb692cffef791e', 'ae28429ef243647b0c07cd113ef3393c', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('5feb15faff5b5b140848b734db6d1900', '1', '585d26bcbdf3047e502e4aa51c816090');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('6724ee306e70145306f3e6d647b9879d', '1efcc15ab020858f4907f7ac94128af5', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('732aa5077352c609f23bc3e626ca6438', '1', 'f79c084575fa3ee8614b3a778edb7e4e');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('74217ab2e9ebb624b1608e1f6419f623', 'ae28429ef243647b0c07cd113ef3393c', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('7d931f493b50e0fbb7093769bfe242c6', '1', 'cb954a7c0be3b37fcc96ec023924262c');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('7d9f77e5e4daa7cb9dc3403aeea8d802', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', '96950f6c591db90081a065e619d1645b');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('81de829dec84d983a3f71f79359bcadf', '1', '325c2503007fd5127baca9d7618e8291');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('887c296a955840a4204876fc52fa5a6e', 'bf699ba68c8700a9760b79bdf859a092', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('8cfe868f7e3d1e24ec14ab87ead9df02', '1', 'dd81bdbb52bca933d1e7336f9c877f8e');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('8feb1f0512fe7a249e9d3e7e04f8e9b5', '1', '0a783e37206685608ccf9e40865e74a0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('953b0d1b33a2c44a6cf7402e8d4d4b6b', '1', '5fb7863b09a8d0c99cef173e18106fb3');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('9c0d4f578ee73d9fdc741226e0ff4579', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('9c7732b2ce82eb281a3350ada75687f0', '1', '925371be8ec674a06613bf8e37ec356c');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('9ea782824dd82f733b76f5443917959b', '696e8b4575bbfe842d5a5d534508fa7c', 'f6f51f94413343fs0013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('a948facb31783280b7b218a5ed7e464e', '2', 'd5e1ecaae2e07789ae934f3c73eab502');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('b2e02db6d6d2aed309c64844630acecc', '1', 'c8774229c6b8f6b045fa37e02be522eb');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('bbf66f07ea0827ec183a92ce40758786', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('bece5428495b04cf6334d17e5910c87c', '1', '9c57b5343ffed09c5d806a96433b0aca');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c04b4a8a712403dfa58b342f701bae4a', '1', '043e412151db118d27f2ab60d8ff73a0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c4004134de450b89335bb747727de7ec', '1', '6b61fbe5091a8e04d2b016f15d598f1f');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c5779edb1c873aa7eb5e26615407bfd2', '1', '447d9998fc00fe64c96c6f09f0d41c32');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c9d2099737928fab96f819e9e0fc95ce', '696e8b4575bbfe842d5a5d534508fa7c', 'f6f51f9441339cd20013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('d47dc6474805e50bc6cba831de679f5f', '1', '262b426e2a86fe736480eb4404214f09');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('d58ab33914bdacbb4aea8681fcc8ee35', '1efcc15ab020858f4907f7ac94128af5', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('dc13e349314c1eba157cb852c791ba03', '1', 'c80b65d2cb975639de41f22b02fba92d');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('e1a214388c4d3eda7cbd5248bb0a1d04', '1', 'f6f51f94413343fs0013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('e1b6898cee7a6e96c1d8125f5f2b1775', '696e8b4575bbfe842d5a5d534508fa7c', 'f79c084575fa3ee8614b3a778edb7e4e');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('e69a4553228951700c287aa1bd00ded0', '1', '9c99b8a096c8788bc27be5122d0700e8');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('e6cb2507ce87d38871f32b2672f595ef', '2', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('ed97225cf0b9bfee5e10e0578a8e3f71', '2', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('f3df88d2de5b920f85183a68c2c44121', '1', '9a85c4041ea9a0e98d86ba7db9ba7539');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('f83883f4bbaa27ff3c594bf4b2f97c0c', '2', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('f9011798e8148f225f74e59fe5fc3302', '696e8b4575bbfe842d5a5d534508fa7c', '9a85c4041ea9a0e98d86ba7db9ba7539');


INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('1', '管理员', '管理员', 'system', '1', 1, 1, 0);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('2', '管理员', '管理员', 'system', '2', 1, 1, 0);

INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('1efcc15ab020858f4907f7ac94128af5', '普通用户', '普通用户', 'system', '2', 1, 0, 1);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('696e8b4575bbfe842d5a5d534508fa7c', '普通用户', '普通用户', 'system', '1', 1, 0, 1);


INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('3f22e1a0ff00ae98132fe5d2fe5d0d5d', '管理员', '管理员', 'system', '2', 2, 1, 0);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('f51b3e9cdf10968d2f1080c43647eac5', '管理员', '管理员', 'system', '2', 2, 1, 0);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('ae28429ef243647b0c07cd113ef3393c', '普通用户', '普通用户', 'system', '2', 2, 0, 1);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`, `business_type`, `default_role`) VALUES ('bf699ba68c8700a9760b79bdf859a092', '普通用户', '普通用户', 'system', '2', 2, 0, 1);


INSERT INTO `pcs_prc_role_user` (`id`, `role_id`, `user_id`) VALUES ('d0c4e835c7a2d89d56cccbeaccda3fc4', 'f51b3e9cdf10968d2f1080c43647eac5', '111111');
INSERT INTO `pcs_prc_role_user` (`id`, `role_id`, `user_id`) VALUES ('2adeb79ba186538330a7337ec56a0222', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', '111111');

INSERT INTO `pcs_prc_role_user` (`id`, `role_id`,`user_id`) VALUES ('2e4b7342485b2878ce55b7e57401f76c', '2', '111111');
-- INSERT INTO `pcs_prc_role_user` (`id`, `role_id`, `user_id`) VALUES ('38d74b4a0a5a7ee2b0b6f56171e4e590', '1', '111111');

-- 用户组信息
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('23wa081e7588865a6ab75c14caf8317b', '1', '4235d2624bdf30ye502e4aa51c816090');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('eqw474ad31d1948f35f39313f6496f7b', '1', 'hf43e412151eqwqd27f2ab60d8ff73a0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('eqw0011966d136925c0895bf5d459bcb', '1', 'hfg5371be8ec674a06613bf8e37ec343');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('eqw8d88e520509822ac64455fff8b59c', '1', 'oug5371be8ec674a06613bf8e37ec343');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('eqwbbaa68277053c89933b73a3b4bbad', '1', '43e7d41decf71cfe3b0e80b0c417976g');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('eqwl1fc798ae06acfc4e2439b2f9236e', '1', 'wqre9998fc00fe64c96c6f09f0d45343');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('asdas1fc798ae06acfdasdasb2f9236e', '1', 'eqeqw84575fa3ee8614eqwew8edb7e4e');





