--权限默认
update pcs_prc_function set bgroup= 'matflow' where id =  '0aee720166d8a13beb81f23bcf0f8d2d';
update pcs_prc_function set bgroup= 'matflow' where id =  '0b2120afa185b713a99e330e153d5c09';
update pcs_prc_function set bgroup= 'matflow' where id =  '1ac7a5144aab83db56d0bdaab9da97dc';
update pcs_prc_function set bgroup= 'matflow' where id =  '1de34bdfaad1e2e8d4f5b6a04670f787';
update pcs_prc_function set bgroup= 'matflow' where id =  '36f831865dd4bd490e1fdb4b1d38b763';
update pcs_prc_function set bgroup= 'matflow' where id =  '3af907a0b9d62159c50ae13f17cb2c36';
update pcs_prc_function set bgroup= 'matflow' where id =  '3ff92a1c0d35ff83f37d92aab543bb21';
update pcs_prc_function set bgroup= 'matflow' where id =  '66ffbf32b20033c9546faae7336943f9';
update pcs_prc_function set bgroup= 'matflow' where id =  '6c325e97fc58bd362cd00f74bf3f3609';
update pcs_prc_function set bgroup= 'matflow' where id =  '715a723396096aef0dca4bafa8f4cd24';
update pcs_prc_function set bgroup= 'matflow' where id =  '8700a662f1aebb83f790783c3abe15b5';
update pcs_prc_function set bgroup= 'matflow' where id =  '8d26791c4634aee4121115b29e944bc1';
update pcs_prc_function set bgroup= 'matflow' where id =  'c08882dca6ab325b10fb2b7c0cfc8156';
update pcs_prc_function set bgroup= 'matflow' where id =  'c103338b85c47ee6f5f9bc65e03cfa87';
update pcs_prc_function set bgroup= 'matflow' where id =  'c3a07289216a53628a4f9c558e58d05c';
update pcs_prc_function set bgroup= 'matflow' where id =  'd258d8c1f73d4afc733600ab6504bf0c';
update pcs_prc_function set bgroup= 'matflow' where id =  'd5535a61c9cefaccd71b44d298474775';
update pcs_prc_function set bgroup= 'matflow' where id =  'dfa50d1a6f3466a75c37325c71b9a8fc';
update pcs_prc_function set bgroup= 'matflow' where id =  'f741b2f59129f7b025884b4000de72b0';
update pcs_prc_function set bgroup= 'matflow' where id =  'e49f5de6ceda7ca40c5dfe91bc25df3d';

INSERT INTO pcs_prc_function(id, name, code, parent_function_id, sort, type , bgroup) VALUES ('cf376a9834a61047ed8ec5d90eff517b', '系统功能', 'E1', '0aee720166d8a13beb81f23bcf0f8d2d', NULL, '1','matflow');
INSERT INTO pcs_prc_function(id, name, code, parent_function_id, sort, type , bgroup) VALUES ('16307662c66160adc7a79494135a232d', '项目功能', 'I1', 'f741b2f59129f7b025884b4000de72b0', NULL, '1','matflow');
INSERT INTO pcs_prc_function(id, name, code, parent_function_id, sort, type , bgroup) VALUES ('3fd46dedbb60e6d363583703881f34dd', '环境配置', 'J', NULL, NULL, '1','matflow');

delete from pcs_prc_function where id =  '36f831865dd4bd490e1fdb4b1d38b763';
delete from pcs_prc_function where id =  'c103338b85c47ee6f5f9bc65e03cfa87';
delete from pcs_prc_function where id =  'd258d8c1f73d4afc733600ab6504bf0c';
delete from pcs_prc_function where id =  '66ffbf32b20033c9546faae7336943f9';
--系统角色
update pcs_prc_role_function set bgroup= 'matflow' where id =  '035e08a1613caf6cb13fdbc894df7aa7';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '08c6c732491745692f1463a6da12aff1';
--项目权限
update pcs_prc_role_function set bgroup= 'matflow' where id =  '1d38fbb022cf06d89a39c7ca2d7b7e25';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '26fe8a8b28a617e95894535c2f7f3ce4';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '298de307e3ddfac85416f014923d8d20';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '2c999a039c0749fddfb8417e78531e93';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '35b868b8b34d057caa028455c763caba';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '3706eda84cbd13bbb1192a576b4204fb';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '3aa55fb02a7b9bad039e014a353c6f5c';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '498d681f42d5af60f16cdb680500931b';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '4b62eefdd9c8d558a950970c6376a166';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '4fb44e966c66c0ef89ebf00bd0d07c35';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '69d195ba31757957529182abcf66b578';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '71c6dd5b32f4f1f7079e49323f87c304';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '87f158ec8b353dc0ba4692d4468febeb';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '8b74451cc1df6a117fc34b46a08e0a24';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '8eea0b6e79da80cb2dfebd85dcc0f3d9';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '96c766ac2cb8c033a8c214f95f9a2a9b';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '987189d9243af8b1135659bbbb190e08';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '9a21b50249d40a7894888e5e8b73db0a';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '9e70b160b0d1429428e7e097ef4f4d0c';
update pcs_prc_role_function set bgroup= 'matflow' where id =  '9eca8d031a103cb42d4361729ffbcd67';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'b7a8be493069202e3404a2ed66139d5e';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'c12af496894579339ce94e70c684d9e3';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'd4357701e5040553c341854b28017584';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'da0c3aa4a572241233c0ca419a99e82b';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'f0c64325fc0875f1fe3544c4c5c2a2b6';
update pcs_prc_role_function set bgroup= 'matflow' where id =  'c688ff0e519dc0b917d180090e7c50ac';

delete from pcs_prc_role_function where id =  '2c999a039c0749fddfb8417e78531e93';
delete from pcs_prc_role_function where id =  '3706eda84cbd13bbb1192a576b4204fb';
delete from pcs_prc_role_function where id =  '498d681f42d5af60f16cdb680500931b';
delete from pcs_prc_role_function where id =  '69d195ba31757957529182abcf66b578';
delete from pcs_prc_role_function where id =  '96c766ac2cb8c033a8c214f95f9a2a9b';
delete from pcs_prc_role_function where id =  'd4357701e5040553c341854b28017584';
delete from pcs_prc_role_function where id =  'da0c3aa4a572241233c0ca419a99e82b';

INSERT INTO pcs_prc_role_function (id,role_id,function_id ,bgroup)  VALUES ('ceb6b002cf830f07cd6d97073af042e6', '97a7db718273636659fc5e146a00edd2', 'cf376a9834a61047ed8ec5d90eff517b','matflow');
INSERT INTO pcs_prc_role_function (id,role_id,function_id ,bgroup)  VALUES ('160b09cb15da1477189053434d88c0be', '97a7db718273636659fc5e146a00edd2', '16307662c66160adc7a79494135a232d','matflow');
INSERT INTO pcs_prc_role_function (id,role_id,function_id ,bgroup)  VALUES ('443a74429f92a189dd906d0c531b5e8e', '97a7db718273636659fc5e146a00edd2', '3fd46dedbb60e6d363583703881f34dd','matflow');























