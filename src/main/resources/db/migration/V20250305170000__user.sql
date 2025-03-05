ALTER TABLE users
    ADD is_admin BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE users
    SET is_admin = true WHERE login = 'admin';