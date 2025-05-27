ALTER TABLE pip_task_code
    ADD COLUMN auth_type varchar(32),
  ADD COLUMN username varchar(255),
  ADD COLUMN password varchar(255),
  ADD COLUMN pri_key text;