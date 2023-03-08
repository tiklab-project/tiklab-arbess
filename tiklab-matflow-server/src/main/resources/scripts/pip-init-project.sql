-- 初始化项目
INSERT INTO `pip_pipeline` (`id`, `name`, `user_id`, `create_time`, `type`, `state`, `power`, `color`) VALUES ('16065924e37d72b5364c66451386d79d', '示例项目--多阶段', '111111', '2022-12-26 11:37:11', 2, 1, 1, 2);
INSERT INTO `pip_pipeline` (`id`, `name`, `user_id`, `create_time`, `type`, `state`, `power`, `color`) VALUES ('3847d6abea9c7853c6a47b093432c30a', '示例项目--多任务', '111111', '2022-12-26 11:35:00', 1, 1, 1, 5);

-- 初始化阶段
INSERT INTO `pip_stage` (`stage_id`, `create_time`, `stage_name`, `pipeline_id`, `stage_sort`, `parent_id`, `code`) VALUES ('181631570f2439e4ecffb8d7f82b585c', '2022-12-26 11:37:11', '阶段-1', '16065924e37d72b5364c66451386d79d', 1, null,  'true');
INSERT INTO `pip_stage` (`stage_id`, `create_time`, `stage_name`, `pipeline_id`, `stage_sort`, `parent_id`,  `code`) VALUES ('59380f9cd5dd1c1c54f352c2585745dc', '2022-12-26 11:37:11', '源码', null, 1, '181631570f2439e4ecffb8d7f82b585c',  'false');

-- 初始化任务
INSERT INTO `pip_task` (`task_id`, `create_time`, `task_name`, `pipeline_id`, `stage_id`, `task_type`, `task_sort`,`postprocess_id`) VALUES ('406d153ed223d1d5af88d239a1545751', '2022-12-26 11:35:00', '通用Git', '3847d6abea9c7853c6a47b093432c30a', null, 1,1,null);
INSERT INTO `pip_task` (`task_id`, `create_time`, `task_name`, `pipeline_id`, `stage_id`, `task_type`, `task_sort`,`postprocess_id`) VALUES ('056d153edeqwd1d5af88d239a1545423', '2022-12-26 11:35:00', '通用Git', null, '59380f9cd5dd1c1c54f352c2585745dc', 1,1,null);

-- 初始化任务详情
INSERT INTO `pip_task_code` (`task_id`, `code_name`, `code_address`, `code_branch`, `svn_file`, `auth_id`) VALUES ('406d153ed223d1d5af88d239a1545751', 'git@gitee.com:tiklab/tiklab-example.git', 'git@gitee.com:tiklab/tiklab-example.git', 'master', NULL, 'f812ab93d8ffb399dc21d37e8d0e423b');
INSERT INTO `pip_task_code` (`task_id`, `code_name`, `code_address`, `code_branch`, `svn_file`, `auth_id`) VALUES ('056d153edeqwd1d5af88d239a1545423', 'git@gitee.com:tiklab/tiklab-example.git', 'git@gitee.com:tiklab/tiklab-example.git', 'master', NULL, 'f812ab93d8ffb399dc21d37e8d0e423b');

INSERT INTO `pip_auth` (`id`, `auth_type`, `name`, `create_time`, `username`, `password`, `private_key`, `user_id`, `auth_public`) VALUES ('f812ab93d8ffb399dc21d37e8d0e423b', 2, '示例项目私钥', '2022-12-19 14:59:14', NULL, NULL, '-----BEGIN OPENSSH PRIVATE KEY-----\n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\n-----END OPENSSH PRIVATE KEY-----\n', '111111', 1);

INSERT INTO `pcs_prc_dm_role` (`id`, `domain_id`, `role_id`, `business_type`) VALUES ('0a0758b4f53f4bcebc9254f489aa5ea2', '3847d6abea9c7853c6a47b093432c30a', 'bf699ba68c8700a9760b79bdf859a092', 0);
INSERT INTO `pcs_prc_dm_role` (`id`, `domain_id`, `role_id`, `business_type`) VALUES ('2eb55dc9765636764bbc9234d9e478ab', '16065924e37d72b5364c66451386d79d', 'f51b3e9cdf10968d2f1080c43647eac5', 1);
INSERT INTO `pcs_prc_dm_role` (`id`, `domain_id`, `role_id`, `business_type`) VALUES ('c33bb5694058082277d4cee24fd3d98d', '3847d6abea9c7853c6a47b093432c30a', '3f22e1a0ff00ae98132fe5d2fe5d0d5d', 1);
INSERT INTO `pcs_prc_dm_role` (`id`, `domain_id`, `role_id`, `business_type`) VALUES ('e719de1541483f0f8a67bfd6fe0179a7', '16065924e37d72b5364c66451386d79d', 'ae28429ef243647b0c07cd113ef3393c', 0);

--
INSERT INTO `pcs_ucc_dm_user` (`id`, `domain_id`, `user_id`, type) VALUES ('3eedd5b221aa1c66c207116599244fc0', '3847d6abea9c7853c6a47b093432c30a', '111111', 1);
INSERT INTO `pcs_ucc_dm_user` (`id`, `domain_id`, `user_id`, type) VALUES ('e5f0d6a0b33107be61e48b486268fde6', '16065924e37d72b5364c66451386d79d', '111111', 1);

INSERT INTO `pcs_prc_dm_role_user` (id, dmRole_id, domain_id, user_id) VALUES ('asd5dc9765636764bbc9234d9e4767','2eb55dc9765636764bbc9234d9e478ab','16065924e37d72b5364c66451386d79d','111111');
INSERT INTO `pcs_prc_dm_role_user` (id, dmRole_id, domain_id, user_id) VALUES ('34435s694058082277d4cee24fd3gfh','c33bb5694058082277d4cee24fd3d98d','3847d6abea9c7853c6a47b093432c30a','111111');























































