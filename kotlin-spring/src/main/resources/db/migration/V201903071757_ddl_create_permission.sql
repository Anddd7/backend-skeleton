CREATE TABLE IF NOT EXISTS auth_permission
(
  id          BIGSERIAL PRIMARY KEY,
  code        VARCHAR(50) NOT NULL,
  description VARCHAR(255)
)
