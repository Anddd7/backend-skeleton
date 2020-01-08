CREATE TABLE IF NOT EXISTS temp_table
(
  id      BIGSERIAL PRIMARY KEY,
  name    VARCHAR(50)
);

INSERT INTO temp_table VALUES (1, 'test');
