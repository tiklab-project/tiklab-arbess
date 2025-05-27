UPDATE pcs_prc_role
SET name = '流水线管理员', description = '流水线管理员'
WHERE id = 'pro_111111';

UPDATE pcs_prc_role
SET name = '流水线成员', description = '流水线成员'
WHERE id = '1efcc15ab020';

UPDATE pcs_prc_role
SET name = '流水线管理员', description = '流水线管理员'
WHERE parent_id = 'pro_111111';

UPDATE pcs_prc_role
SET name = '流水线成员', description = '流水线成员', default_role = 1
WHERE parent_id = '1efcc15ab020';