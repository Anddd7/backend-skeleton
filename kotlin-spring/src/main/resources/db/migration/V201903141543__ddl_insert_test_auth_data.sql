INSERT INTO auth_role (id, name, parent_id) VALUES (10001, 'USER', NULL), (10002, 'CUSTOMER', 10001);

INSERT INTO auth_user (id, name, role_id) VALUES (11001, 'and777', 10001), (11002, 'and888', 10002);

INSERT INTO auth_role_permission (role_id, permission_code, expired_date)
VALUES (10001, 'DASHBOARD', '2019-11-14'::TIMESTAMP), (10002, 'ORDER', '2019-11-14'::TIMESTAMP)