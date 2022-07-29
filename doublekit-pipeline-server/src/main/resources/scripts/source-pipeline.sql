--权限默认
INSERT INTO `prc_function` VALUES ('0301f3df9112e6a49cb8092e0c63b270', '系统功能', 'E1', '0aee720166d8a13beb81f23bcf0f8d2d', NULL, '1');
INSERT INTO `prc_function` VALUES ('0aee720166d8a13beb81f23bcf0f8d2d', '系统权限', 'E', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('0b2120afa185b713a99e330e153d5c09', '项目角色', 'I2', 'f741b2f59129f7b025884b4000de72b0', NULL, '1');
INSERT INTO `prc_function` VALUES ('1ac7a5144aab83db56d0bdaab9da97dc', '工作空间', 'AA', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('1de34bdfaad1e2e8d4f5b6a04670f787', '系统角色', 'E2', '0aee720166d8a13beb81f23bcf0f8d2d', NULL, '1');
INSERT INTO `prc_function` VALUES ('36f831865dd4bd490e1fdb4b1d38b763', '用户管理', 'A', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('3af907a0b9d62159c50ae13f17cb2c36', '系统信息', 'H', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('3ff92a1c0d35ff83f37d92aab543bb21', '配置', 'BB', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('66ffbf32b20033c9546faae7336943f9', '组织管理', 'D', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('6c325e97fc58bd362cd00f74bf3f3609', '凭证管理', 'DD3', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('715a723396096aef0dca4bafa8f4cd24', '其他设置', 'DD4', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('744c253cce9c06d4aa27081707279457', '项目功能', 'I1', 'f741b2f59129f7b025884b4000de72b0', NULL, '1');
INSERT INTO `prc_function` VALUES ('8700a662f1aebb83f790783c3abe15b5', '设置', 'DD', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('8d26791c4634aee4121115b29e944bc1', '历史', 'CC', NULL, NULL, '2');
INSERT INTO `prc_function` VALUES ('c08882dca6ab325b10fb2b7c0cfc8156', '角色管理', 'DD2', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('c103338b85c47ee6f5f9bc65e03cfa87', '用户列表', 'B', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('c3a07289216a53628a4f9c558e58d05c', '凭证管理', 'F', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('d258d8c1f73d4afc733600ab6504bf0c', '用户目录', 'C', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('d5535a61c9cefaccd71b44d298474775', '成员列表', 'DD1', '8700a662f1aebb83f790783c3abe15b5', NULL, '2');
INSERT INTO `prc_function` VALUES ('dfa50d1a6f3466a75c37325c71b9a8fc', '插件管理', 'G', NULL, NULL, '1');
INSERT INTO `prc_function` VALUES ('f741b2f59129f7b025884b4000de72b0', '项目权限', 'I', NULL, NULL, '1');

INSERT INTO `prc_role` VALUES ('83dc0140bc3a491383b8de5099b1d87b', 'user', '用户', 'custom', '1');
INSERT INTO `prc_role` VALUES ('f1b425c39359e053eed89a069c9601cf', 'root', '管理员', 'custom', '1');


INSERT INTO `prc_role_function` VALUES ('05a3ea2d80ddefeb2b264bcbad294f93', 'f1b425c39359e053eed89a069c9601cf', 'c103338b85c47ee6f5f9bc65e03cfa87');
INSERT INTO `prc_role_function` VALUES ('141e9e0785a5dfbbe9c1890a020da09f', 'f1b425c39359e053eed89a069c9601cf', 'c08882dca6ab325b10fb2b7c0cfc8156');
INSERT INTO `prc_role_function` VALUES ('1931f07540ebd9fc393fe95a12b07f64', 'f1b425c39359e053eed89a069c9601cf', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO `prc_role_function` VALUES ('1c5c8a8f8aabaa7f7b39dffc4aac0d96', 'f1b425c39359e053eed89a069c9601cf', '6c325e97fc58bd362cd00f74bf3f3609');
INSERT INTO `prc_role_function` VALUES ('1d401ccf8e830863a1f9f64f8cc408c8', '83dc0140bc3a491383b8de5099b1d87b', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO `prc_role_function` VALUES ('294fa3ed4ad01b938461a83caff4ab33', 'f1b425c39359e053eed89a069c9601cf', '0aee720166d8a13beb81f23bcf0f8d2d');
--系统功能
--INSERT INTO `prc_role_function` VALUES ('32227af1f706dee925560670c8467461', 'f1b425c39359e053eed89a069c9601cf', '0301f3df9112e6a49cb8092e0c63b270');

INSERT INTO `prc_role_function` VALUES ('471696af197e899a887e0d526a59f5e8', 'f1b425c39359e053eed89a069c9601cf', 'c3a07289216a53628a4f9c558e58d05c');
INSERT INTO `prc_role_function` VALUES ('5eab324e375ca979136268e53c8677d5', '83dc0140bc3a491383b8de5099b1d87b', '36f831865dd4bd490e1fdb4b1d38b763');
INSERT INTO `prc_role_function` VALUES ('621330ae7508b7b9706e7295cfe876b0', 'f1b425c39359e053eed89a069c9601cf', 'f741b2f59129f7b025884b4000de72b0');
INSERT INTO `prc_role_function` VALUES ('67ab7dda75d1b5abe06d23238900bce7', '83dc0140bc3a491383b8de5099b1d87b', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO `prc_role_function` VALUES ('70bb3d37ebdd3acfbc3b048ecd28944c', 'f1b425c39359e053eed89a069c9601cf', '66ffbf32b20033c9546faae7336943f9');
INSERT INTO `prc_role_function` VALUES ('76a6e37ea5cface70e952edd68ac5293', 'f1b425c39359e053eed89a069c9601cf', '3af907a0b9d62159c50ae13f17cb2c36');
INSERT INTO `prc_role_function` VALUES ('7fb53048f672717c8b350f6d582f1afd', 'f1b425c39359e053eed89a069c9601cf', '715a723396096aef0dca4bafa8f4cd24');
INSERT INTO `prc_role_function` VALUES ('8924712bc42ce166a7f1d6fa01e39dbc', 'f1b425c39359e053eed89a069c9601cf', 'dfa50d1a6f3466a75c37325c71b9a8fc');
INSERT INTO `prc_role_function` VALUES ('89d2137b1a8c8355c4e63f2363d538b9', 'f1b425c39359e053eed89a069c9601cf', '8d26791c4634aee4121115b29e944bc1');
INSERT INTO `prc_role_function` VALUES ('961020c4c73c63be77ef7d5299111825', 'f1b425c39359e053eed89a069c9601cf', '1de34bdfaad1e2e8d4f5b6a04670f787');

--项目功能
--INSERT INTO `prc_role_function` VALUES ('b1e50ab0ab7a56b3ea4e440b47c2d17c', 'f1b425c39359e053eed89a069c9601cf', '744c253cce9c06d4aa27081707279457');

INSERT INTO `prc_role_function` VALUES ('bfaa8c53da5ddc317e7a1567686d4ce4', '83dc0140bc3a491383b8de5099b1d87b', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO `prc_role_function` VALUES ('cc112e16dcf37be3fe9e5a71d375ae07', 'f1b425c39359e053eed89a069c9601cf', '3ff92a1c0d35ff83f37d92aab543bb21');
INSERT INTO `prc_role_function` VALUES ('d2ef83e0aa36cf57bd84ce54d2b9a9c0', 'f1b425c39359e053eed89a069c9601cf', '0b2120afa185b713a99e330e153d5c09');
INSERT INTO `prc_role_function` VALUES ('e584521ba07b2ae6b8dbf521e8920171', 'f1b425c39359e053eed89a069c9601cf', 'd258d8c1f73d4afc733600ab6504bf0c');
INSERT INTO `prc_role_function` VALUES ('eae3c67c44c6e040a2469f5c794ffa49', 'f1b425c39359e053eed89a069c9601cf', 'd5535a61c9cefaccd71b44d298474775');
INSERT INTO `prc_role_function` VALUES ('f613e919f659d2abe26fb89fd20bef46', 'f1b425c39359e053eed89a069c9601cf', '1ac7a5144aab83db56d0bdaab9da97dc');
INSERT INTO `prc_role_function` VALUES ('f8ef3ab245f87dd9012907efc60e9c20', 'f1b425c39359e053eed89a069c9601cf', '8700a662f1aebb83f790783c3abe15b5');
INSERT INTO `prc_role_function` VALUES ('ffcfba5120c8a26b9d3b02a6178df68f', '83dc0140bc3a491383b8de5099b1d87b', '1ac7a5144aab83db56d0bdaab9da97dc');

INSERT INTO `prc_role_user` VALUES ('3822dcb805855ae85c51a3522c90e623', 'f1b425c39359e053eed89a069c9601cf', '111111');
INSERT INTO `prc_role_user` VALUES ('95cbb9ee728100b79cf9a18290298ace', '83dc0140bc3a491383b8de5099b1d87b', '222222');
