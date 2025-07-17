CREATE TABLE IF NOT EXISTS pip_agent_role (
  id VARCHAR(64) PRIMARY KEY,
  type int,
  status int
);

INSERT INTO pip_agent_role (id, type, status) VALUES ('default', 3, 1);