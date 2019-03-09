CREATE TABLE IF NOT EXISTS auth_role
(
  id        BIGSERIAL PRIMARY KEY,
  name      VARCHAR(50),
  parent_id BIGSERIAL
)
