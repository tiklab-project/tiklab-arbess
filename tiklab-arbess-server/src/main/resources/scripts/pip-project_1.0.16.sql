UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_RUN', msg_send_type_id = 'qywechat', title = NULL, content = E'## 流水线执行信息\n
> 执行人：<font color=comment>${userName}</font>\n
> 流水线：<font color=comment>[${pipelineName}](${qywxurl})</font>\n
> 执行时间：<font color=comment>${execTime}</font>\n
> 执行状态：<font color=${colour}>**${execStatus}**</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = '7ff3948a5c9b';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_CREATE', msg_send_type_id = 'qywechat', title = NULL, content = E'## 创建流水线\n
> 创建人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=info>[${pipelineName}](${qywxurl})</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = 'ea2656a4c4bf';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_DELETE', msg_send_type_id = 'qywechat', title = NULL, content = E'## 删除流水线\n
> 执行人：<font color=comment>${userName}</font>\n
> 流水线名称：<font color=warning>[${pipelineName}](${qywxurl})</font>', link = '/#/home', bgroup = 'arbess', link_params = NULL WHERE id = 'a48b296dce37';
UPDATE pcs_mec_message_template SET msg_type_id = 'MF_MES_TYPE_UPDATE', msg_send_type_id = 'qywechat', title = NULL, content = E'## 更新流水线名称\n
> 执行人：<font color=comment>${userName}</font>\n
> 更新前名称：<font color=comment>${lastName}</font>\n
> 更新前后名称：<font color=warning>[${pipelineName}](${qywxurl})</font>', link = '/#/pipeline/${pipelineId}/history', bgroup = 'arbess', link_params = NULL WHERE id = 'bb09db8fa4d1';