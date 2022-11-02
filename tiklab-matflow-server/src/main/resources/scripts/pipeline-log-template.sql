INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('pipeline', '流水线动态', '${user}${message}<span color="blue"><a href="${link}">${pipelineName}</a></span>', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('deletePipeline', '流水线删除动态', '${user}${message}${pipelineName}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
('pipelineExec', '流水线执行动态', '${message}<span color="blue"><a href="${link}">${pipelineName}</a></span>', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
 VALUES
 ('pipelineProof', '流水线凭证动态', '用户${user}${message}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
VALUES
('pipelineOther', '流水线其他动态', '用户${user}${message}', NULL, 'matflow');

INSERT INTO pcs_op_log_template
(id,title,content,link,bgroup)
VALUES
('pipelineConfig', '流水线配置动态', '${user}${type}流水线<span color="blue"><a href="${link}">${pipelineName}</a></span>${message}', NULL, 'matflow');










