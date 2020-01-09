CREATE TABLE IF NOT EXISTS auth_user
(
  id      BIGSERIAL PRIMARY KEY,
  name    VARCHAR(50)
);

INSERT INTO auth_user VALUES (1, 'test');
