TRUNCATE TABLE auth_user CASCADE;

INSERT INTO auth_user (id, name, role_id) VALUES (1, 'USER1', 1), (2, 'USER2', 1), (3, 'USER3', 2), (4, 'USER4', 3);