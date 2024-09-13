
--  pip_task
UPDATE pip_task SET task_type = 'gitpuk' WHERE task_type = 'gittok';

UPDATE pip_task SET task_type = 'testrubo' WHERE task_type = 'teston';

--
UPDATE pcs_tool_app_link SET app_type = 'arbess'  WHERE app_type = 'matflow';

UPDATE pcs_op_log SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_op_log_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_op_log_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_notice SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_mec_message_dispatch_item SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task_template SET bgroup = 'arbess'  WHERE bgroup = 'matflow';

UPDATE pcs_todo_task_type SET bgroup = 'arbess'  WHERE bgroup = 'matflow';


UPDATE pip_auth_server SET type = 'testrubo' WHERE type = 'teston';
UPDATE pip_auth_server SET type = 'gitpuk' WHERE type = 'gittok';








