
DELETE FROM pcs_op_log_type;

INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('PIP_DELETE', '删除流水线', 'matflow');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('PIP_UPDATE', '更新流水线', 'matflow');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('PIP_RUN', '运行流水线', 'matflow');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('PIP_CREATE', '创建流水线', 'matflow');


DELETE FROM pcs_op_log;


DELETE FROM pcs_mec_message_type;
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('8a67e9486921', '部门消息类型', '部门消息类型', 'darth');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIP_DELETE', '删除流水线', '流水线删除消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIP_CREATE', '创建流水线', '流水线创建消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIP_UPDATE', '更新流水线', '流水线更新消息', 'matflow');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('PIP_RUN', '运行流水线', '流水线运行消息', 'matflow');


DELETE FROM pcs_mec_message_notice;
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('0fae4e670d99', '8a67e9486921', 1, 'darth', 'email,qywechat');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('PIP_CREATE', 'PIP_CREATE', 1, 'matflow', 'site,email,qywechat');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('PIP_RUN', 'PIP_RUN', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('PIP_UPDATE', 'PIP_UPDATE', 1, 'matflow', 'dingding,email,qywechat,site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('PIP_DELETE', 'PIP_DELETE', 1, 'matflow', 'site,email,qywechat');


DELETE FROM pcs_mec_message_notice_connect_user;
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('7f1bf4b13bfc', '0fae4e670d99', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('f6bfd4c9b423', '0fae4e670d99', '9780981085ad');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('59db0b9aa4f0', 'PIP_CREATE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('672b2b9bfd2e', 'PIP_RUN', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('9f120fd530ea', 'PIP_UPDATE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('115263b28f0d', 'PIP_DELETE', '111111');


DELETE FROM pcs_mec_message_dispatch_item;

DELETE FROM pcs_mec_message;






