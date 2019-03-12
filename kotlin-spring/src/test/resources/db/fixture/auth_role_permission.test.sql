TRUNCATE TABLE auth_role_permission CASCADE;

INSERT INTO auth_role_permission (permission_id, role_id, expired_date)
VALUES (1, 1, '2019-11-14'::TIMESTAMP), (1, 2, '2019-11-14'::TIMESTAMP), (2, 3, '2019-11-14'::TIMESTAMP);