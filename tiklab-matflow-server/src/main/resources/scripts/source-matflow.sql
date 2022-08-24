--权限默认
--INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('0301f3df9112e6a49cb8092e0c63b270', '系统功能', 'E1', '0aee720166d8a13beb81f23bcf0f8d2d', NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('0aee720166d8a13beb81f23bcf0f8d2d', '系统权限', 'E', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('0b2120afa185b713a99e330e153d5c09', '项目角色', 'I2', 'f741b2f59129f7b025884b4000de72b0', NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('1ac7a5144aab83db56d0bdaab9da97dc', '工作空间', 'AA', NULL, NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('1de34bdfaad1e2e8d4f5b6a04670f787', '系统角色', 'E2', '0aee720166d8a13beb81f23bcf0f8d2d', NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('36f831865dd4bd490e1fdb4b1d38b763', '用户管理', 'A', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('3af907a0b9d62159c50ae13f17cb2c36', '系统信息', 'H', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('3ff92a1c0d35ff83f37d92aab543bb21', '配置', 'BB', NULL, NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('66ffbf32b20033c9546faae7336943f9', '组织管理', 'D', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('6c325e97fc58bd362cd00f74bf3f3609', '凭证管理', 'DD3', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('715a723396096aef0dca4bafa8f4cd24', '其他设置', 'DD4', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
--INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('744c253cce9c06d4aa27081707279457', '项目功能', 'I1', 'f741b2f59129f7b025884b4000de72b0', NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('8700a662f1aebb83f790783c3abe15b5', '设置', 'DD', NULL, NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('8d26791c4634aee4121115b29e944bc1', '历史', 'CC', NULL, NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('c08882dca6ab325b10fb2b7c0cfc8156', '角色管理', 'DD2', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('c103338b85c47ee6f5f9bc65e03cfa87', '用户列表', 'B', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('c3a07289216a53628a4f9c558e58d05c', '凭证管理', 'F', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('d258d8c1f73d4afc733600ab6504bf0c', '用户目录', 'C', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('d5535a61c9cefaccd71b44d298474775', '成员列表', 'DD1', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('dfa50d1a6f3466a75c37325c71b9a8fc', '插件管理', 'G', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('f741b2f59129f7b025884b4000de72b0', '项目权限', 'I', NULL, NULL, '1');
INSERT INTO prc_function(id, name, code, parent_function_id, sort, type) VALUES ('e49f5de6ceda7ca40c5dfe91bc25df3d', 'licence功能管理', 'J', NULL, NULL, '1');

--INSERT INTO `prc_role` VALUES ('7cfee91edf221ae1deed232ca14c03af', '项目角色', '项目角色', 'custom', '1');
--INSERT INTO `prc_role` VALUES ('97a7db718273636659fc5e146a00edd2', '管理员角色', NULL, 'system', '1');

--系统角色
--INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('002e6860f371172d9ecadcf975d9ab0e', '97a7db718273636659fc5e146a00edd2', '744c253cce9c06d4aa27081707279457');

INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('035e08a1613caf6cb13fdbc894df7aa7', '97a7db718273636659fc5e146a00edd2', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('08c6c732491745692f1463a6da12aff1', '97a7db718273636659fc5e146a00edd2', '0b2120afa185b713a99e330e153d5c09');
--项目权限
--INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('11f8eedd75001731074e8f3a151bcf64', '97a7db718273636659fc5e146a00edd2', '0301f3df9112e6a49cb8092e0c63b270');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('1d38fbb022cf06d89a39c7ca2d7b7e25', '7cfee91edf221ae1deed232ca14c03af', '1ac7a5144aab83db56d0bdaab9da97dc');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('26fe8a8b28a617e95894535c2f7f3ce4', '97a7db718273636659fc5e146a00edd2', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('298de307e3ddfac85416f014923d8d20', '97a7db718273636659fc5e146a00edd2', '1de34bdfaad1e2e8d4f5b6a04670f787');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('2c999a039c0749fddfb8417e78531e93', '97a7db718273636659fc5e146a00edd2', 'c103338b85c47ee6f5f9bc65e03cfa87');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('35b868b8b34d057caa028455c763caba', '97a7db718273636659fc5e146a00edd2', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('3706eda84cbd13bbb1192a576b4204fb', '97a7db718273636659fc5e146a00edd2', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('3aa55fb02a7b9bad039e014a353c6f5c', '7cfee91edf221ae1deed232ca14c03af', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('498d681f42d5af60f16cdb680500931b', '97a7db718273636659fc5e146a00edd2', 'd258d8c1f73d4afc733600ab6504bf0c');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('4b62eefdd9c8d558a950970c6376a166', '97a7db718273636659fc5e146a00edd2', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('4fb44e966c66c0ef89ebf00bd0d07c35', '97a7db718273636659fc5e146a00edd2', 'f741b2f59129f7b025884b4000de72b0');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('69d195ba31757957529182abcf66b578', '7cfee91edf221ae1deed232ca14c03af', 'd258d8c1f73d4afc733600ab6504bf0c');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('71c6dd5b32f4f1f7079e49323f87c304', '97a7db718273636659fc5e146a00edd2', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('87f158ec8b353dc0ba4692d4468febeb', '97a7db718273636659fc5e146a00edd2', '6c325e97fc58bd362cd00f74bf3f3609');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('8b74451cc1df6a117fc34b46a08e0a24', '97a7db718273636659fc5e146a00edd2', 'dfa50d1a6f3466a75c37325c71b9a8fc');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('8eea0b6e79da80cb2dfebd85dcc0f3d9', '97a7db718273636659fc5e146a00edd2', '1ac7a5144aab83db56d0bdaab9da97dc');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('96c766ac2cb8c033a8c214f95f9a2a9b', '7cfee91edf221ae1deed232ca14c03af', 'c103338b85c47ee6f5f9bc65e03cfa87');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('987189d9243af8b1135659bbbb190e08', '97a7db718273636659fc5e146a00edd2', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('9a21b50249d40a7894888e5e8b73db0a', '7cfee91edf221ae1deed232ca14c03af', '3af907a0b9d62159c50ae13f17cb2c36');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('9e70b160b0d1429428e7e097ef4f4d0c', '97a7db718273636659fc5e146a00edd2', '715a723396096aef0dca4bafa8f4cd24');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('9eca8d031a103cb42d4361729ffbcd67', '97a7db718273636659fc5e146a00edd2', '0aee720166d8a13beb81f23bcf0f8d2d');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('b7a8be493069202e3404a2ed66139d5e', '7cfee91edf221ae1deed232ca14c03af', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('c12af496894579339ce94e70c684d9e3', '97a7db718273636659fc5e146a00edd2', '3af907a0b9d62159c50ae13f17cb2c36');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('d4357701e5040553c341854b28017584', '7cfee91edf221ae1deed232ca14c03af', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('da0c3aa4a572241233c0ca419a99e82b', '97a7db718273636659fc5e146a00edd2', '66ffbf32b20033c9546faae7336943f9');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('f0c64325fc0875f1fe3544c4c5c2a2b6', '7cfee91edf221ae1deed232ca14c03af', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO prc_role_function (id,role_id,function_id)  VALUES ('c688ff0e519dc0b917d180090e7c50ac', '97a7db718273636659fc5e146a00edd2', 'e49f5de6ceda7ca40c5dfe91bc25df3d');

