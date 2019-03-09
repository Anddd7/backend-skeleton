CREATE TABLE IF NOT EXISTS auth_role_permission
(
  permission_id BIGINT,
  role_id       BIGINT,
  expired_date  TIMESTAMP
)
