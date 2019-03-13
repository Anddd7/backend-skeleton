TRUNCATE TABLE auth_role_permission CASCADE;

INSERT INTO auth_role_permission (permission_code, role_id, expired_date)
VALUES ('DASHBOARD', 1, '2019-11-14'::TIMESTAMP),
       ('DASHBOARD', 2, '2019-11-14'::TIMESTAMP),
       ('ORDER', 3, '2019-11-14'::TIMESTAMP);