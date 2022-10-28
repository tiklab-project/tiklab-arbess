--消息类型
INSERT INTO pcs_mec_message_type
(id,name,description,bgroup) VALUES
('matflow', '流水线消息', '流水线信息','matflow');

INSERT INTO pcs_mec_message_type
(id,name,description,bgroup)
VALUES
('matflowExec', '流水线执行消息', '流水线执行信息','matflow');

INSERT INTO pcs_mec_message_type
(id,name,description,bgroup)
VALUES
('matflowUpdate', '更新流水线消息', '流水线更新消息','matflow');

--发送方式
INSERT INTO pcs_mec_message_send_type
 (id,name,code,description,bgroup)
 VALUES ('matflow','站内发送','site','站内发送','matflow');


--消息模板
INSERT INTO pcs_mec_message_template (id,name,msg_type_id,msg_send_type_id,title,content_config_type,content,content_url,link,bgroup) VALUES
    ('matflowExec', '流水线执行消息', 'matflowExec','matflow','流水线执行消息',1,'${message}${state}',null,'http://192.168.10.23:3004/#/','matflow');

INSERT INTO pcs_mec_message_template (id,name,msg_type_id,msg_send_type_id,title,content_config_type,content,content_url,link,bgroup) VALUES
    ('matflow', '流水线消息', 'matflow','matflow','流水线消息',1,'${message}${state}',null,'http://192.168.10.23:3004/#/','matflow');

INSERT INTO pcs_mec_message_template (id,name,msg_type_id,msg_send_type_id,title,content_config_type,content,content_url,link,bgroup) VALUES
    ('matflowUpdate', '更新流水线消息', 'matflow','matflow','更新流水线消息',1,'${message}${state}',null,'http://192.168.10.23:3004/#/','matflow');