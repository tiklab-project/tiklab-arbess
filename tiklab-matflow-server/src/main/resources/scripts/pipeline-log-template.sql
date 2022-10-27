INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('pipeline', '流水线模板', '${message}<span color="blue"><a href="${link}">${pipelineName}</a></span>', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('deletePipeline', '流水线删除模板', '${message}${pipelineName}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
('pipelineExec', '流水线执行模板', '${message}<span color="blue"><a href="${link}">${pipelineName}</a></span>${messages}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('pipelineProof', '流水线凭证模板', '用户${user}${message}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
VALUES
('pipelineOther', '流水线其他模板', '用户${user}${message}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
VALUES
('pipelineConfig', '流水线配置模板', '用户${user}${message}', NULL, 'matflow');










