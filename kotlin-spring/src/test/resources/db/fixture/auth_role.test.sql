TRUNCATE TABLE auth_role CASCADE;

INSERT INTO auth_role (id, name, parent_id) VALUES (1, 'ROLE1', NULL), (2, 'ROLE2', 1), (3, 'ROLE3', 1);