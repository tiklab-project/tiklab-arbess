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






INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('0553377a08ac3a23386e1af271c47911', 'matflowUser', '64bdf62686a4939c1061422394ded7cd');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('0734a8162ef1dc2acdc4b20063a1a682', '1', '585d26bcbdf3047e502e4aa51c816090');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('1391f3639f5a2befd27103b1bfc23e8b', '2', 'd5e1ecaae2e07789ae934f3c73eab502');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('14ad0f8e4d882764b3b68d4aeb9e9775', '1', '64bdf62686a4939c1061422394ded7cd');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('14b3007137810f6e731933672dc364b2', '1', '9633d947588684a5881ccff9eaa3aba0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('19493f46a8f3d60359811af6a374f6b3', '1', '798f63da3ae51964796e275485925a47');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('1b15d55aee60ecc2c681b602b100b29b', '2', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('1eea201f026b23dbf28823a11d716ec2', '1', '043e412151db118d27f2ab60d8ff73a0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('213128751bacaae0e2d80903dee379de', '1', 'f6f51f94413343fs0013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('37cb7983c1d3c01cea3b7ffb7e9acc28', '1', '447d9998fc00fe64c96c6f09f0d41c32');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('3b45b56907fce0a7f510e5383ff5ab60', '1', '262b426e2a86fe736480eb4404214f09');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('434ab0c6e34f2aa07cef89347aab55f8', 'matflowUser', 'f6f51f9441339cd20013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('4409344946530147e7d12c8a8e0e31bc', '1', '428be660dea3db2a2c5a613420b3ead7');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('45019b81445f9e36605cd43beafa088d', '8fa985860fc1205852395b974717a678', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('4917206e0cc9151dfdc4449cd7827e95', 'matflowUser', '03dd3aa23ce98c638471eec6ce3aaf19');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('4f29ad131acc6bc4557d9654d82aa4fd', '1', '0c73e628fd5410c382f28a956304a9d7');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('58bfb5fd1370380214b0d477880a56e7', 'matflowUser', 'c80b65d2cb975639de41f22b02fba92d');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('5be6521137c41e41f1a2a398d6f45713', '2', '96950f6c591db90081a065e619d1645b');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('61324f90eb21ea82af4248d2db2ed5ba', '1', '925371be8ec674a06613bf8e37ec356c');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('61a3c316ed967a70a80d42122699741f', '8fa985860fc1205852395b974717a678', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('6441bacaf64da2c17c62e43cf6af7168', '1', '5fb7863b09a8d0c99cef173e18106fb3');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('654a61ae38655e674307170e66ae1984', '1', 'c8774229c6b8f6b045fa37e02be522eb');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('6599eee24ca8b8a34332d44cfe791040', '1', 'af9697436cfd156cad21fcce5ca0c424');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('72a7e7aa6d5d132277f7e8063172a11a', '1', '57a3bcd1e5fedd77824359d06b06fe49');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('7efbf67317202d1bcbe73171e4fafd3c', '1', '49e12c2b8fca01894886211df2b797d0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('8623c658e16cbcc4979dd83d419d52c2', 'matflowUser', '47784cff8b3c54f009705ea783461221');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('86ed9af33a3e220e2dc66dcc0793aea7', '1', '0a783e37206685608ccf9e40865e74a0');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('86f5b535b1034e84590c1489ea66902f', 'matflowUser', 'af9697436cfd156cad21fcce5ca0c424');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('88ce66434d2714620f9b5192a39809f6', '1', 'edb60bf65e4279e46ce21210b3213bd8');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('8af599eac83d73fdbe05f572f32fd0c6', '1', '9c57b5343ffed09c5d806a96433b0aca');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('90083f4f46dff3acbdb62266cb6a0cda', 'matflowUser', '9c57b5343ffed09c5d806a96433b0aca');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('95554114b3a74e6691ad5512772f4e69', '2', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('99d5610c4a2ed19c37a4554a1b980a5d', '1', '47784cff8b3c54f009705ea783461221');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('9e16ceca78262208a211b41d6dddd6de', '1', '9a85c4041ea9a0e98d86ba7db9ba7539');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('a0e5a6522177e6969ced7cca76b2487c', '1', 'cb954a7c0be3b37fcc96ec023924262c');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('a254f993b4ca1d9e1d3383d81a78c768', '1', '9314739a13fedc65c948180d7702f518');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('b29a157735eb796b5decab5f3f631b2a', '1', 'e8bf9843bc9da8d3c4c33e31174496b3');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('b9829cac1955f60348b030ed5f0adec3', 'matflowUser', '9a85c4041ea9a0e98d86ba7db9ba7539');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('bd5571c0c995ebc5283f1a50acd39794', '1', '325c2503007fd5127baca9d7618e8291');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c34ef0cfa5ecb4819759559da853e436', '1', 'f79c084575fa3ee8614b3a778edb7e4e');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c434234e4cb32af4d2bede546a74f01f', '1', '890e7d41decf71cfe3b0e80b0c4179cf');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c99a2e0f23e50eb277fb7701ecf6de58', '1', 'c80b65d2cb975639de41f22b02fba92d');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('c9bb3053db6a4bfa91af97f80c0fc4d8', '1', '9c99b8a096c8788bc27be5122d0700e8');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('d91ff47cbf401542b5ad7c58edad349f', '1', '6b61fbe5091a8e04d2b016f15d598f1f');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('ddbc640ab0cb438766aea0b4c9ba8b63', 'matflowUser', '9314739a13fedc65c948180d7702f518');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('e54d9206ba5e5473aa490f2d79375f32', '1', 'f6f51f9441339cd20013f4f20c378dc6');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('f37e2f8d895ce539c98aff6deeb91f7d', '2', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('f9ad961dc18f902a64d697a32175b79c', 'matflowUser', 'edb60bf65e4279e46ce21210b3213bd8');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('fa041fe4f7b6222ce8ed242438be3883', '1', 'dd81bdbb52bca933d1e7336f9c877f8e');
INSERT INTO `pcs_prc_role_function` (`id`, `role_id`, `function_id`) VALUES ('ffd2b278b7d2fe985c36e10877e48eff', '1', '03dd3aa23ce98c638471eec6ce3aaf19');


INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`,`tag`) VALUES ('8fa985860fc1205852395b974717a678', '项目角色', '项目角色', 'system', '2', 1,2);
INSERT INTO `pcs_prc_role` (`id`, `name`, `description`, `grouper`, `type`, `scope`,`tag`) VALUES ('2', '管理员', '管理员', 'system', '2', 1,1);


INSERT INTO pcs_prc_role (`id`,`name`,`description`,`grouper`,`type`,`scope`,`tag`) VALUES ('1', '管理员','管理员','system','1',1,1);
INSERT INTO pcs_prc_role (`id`,`name`,`description`,`grouper`,`type`,`scope`,`tag`) VALUES ('matflowUser', '项目角色','项目角色','system','1',1,2);


INSERT INTO pcs_prc_role_user (id,role_id,user_id) VALUES ('matflow', '1','111111');
INSERT INTO pcs_prc_role_user (id,role_id,user_id)  VALUES ('matflowUser', 'matflowUser','f7d301865bb64ef21ef88f8cd58a69a5');











