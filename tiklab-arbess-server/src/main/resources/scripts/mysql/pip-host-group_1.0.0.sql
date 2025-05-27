CREATE TABLE IF NOT EXISTS pip_auth_host_group_details (
   id VARCHAR(256) PRIMARY KEY,
  group_id VARCHAR(256),
  host_id VARCHAR(256)
  );

CREATE TABLE IF NOT EXISTS pip_auth_host_group (
   id VARCHAR(256) PRIMARY KEY,
  group_name VARCHAR(256),
  auth_public VARCHAR(256),
  details VARCHAR(256),
  user_id VARCHAR(256),
  create_time VARCHAR(256)
  );

ALTER TABLE pip_auth_host CHANGE COLUMN id host_id VARCHAR(256);