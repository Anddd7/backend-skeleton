ALTER TABLE auth_permission
  DROP CONSTRAINT auth_permission_pkey;
ALTER TABLE auth_permission
  DROP COLUMN id;
ALTER TABLE auth_permission
  ADD PRIMARY KEY (code);

ALTER TABLE auth_role_permission
  DROP permission_id;
ALTER TABLE auth_role_permission
  ADD COLUMN permission_code VARCHAR(50);