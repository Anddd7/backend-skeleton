CREATE TABLE IF NOT EXISTS auth_role_permission
(
  permission_id BIGSERIAL,
  role_id       BIGSERIAL,
  expired_date  TIMESTAMP
)
