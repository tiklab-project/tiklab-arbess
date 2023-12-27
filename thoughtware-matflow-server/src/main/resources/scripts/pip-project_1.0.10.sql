

-- 对于 MF_MES_TYPE_RUN
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_RUN', 'MF_MES_TYPE_RUN', 2, 'matflow', 'site,email,qywechat', 1)
    ON CONFLICT (id)
DO UPDATE SET
    message_type_id = EXCLUDED.message_type_id,
           type = EXCLUDED.type,
           bgroup = EXCLUDED.bgroup,
           message_send_type_id = EXCLUDED.message_send_type_id,
           scope = EXCLUDED.scope;

-- 对于 MF_MES_TYPE_UPDATE
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope)
VALUES ('MF_MES_TYPE_UPDATE', 'MF_MES_TYPE_UPDATE', 2, 'matflow', 'site,email,qywechat', 1)
    ON CONFLICT (id)
DO UPDATE SET
    message_type_id = EXCLUDED.message_type_id,
           type = EXCLUDED.type,
           bgroup = EXCLUDED.bgroup,
           message_send_type_id = EXCLUDED.message_send_type_id,
           scope = EXCLUDED.scope;









