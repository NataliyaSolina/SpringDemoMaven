CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

-- insert into user (login, passsword)
-- values ('admin', 'admin'),
--        ('Nata', 'nata'),
--        ('Serg', 'serg');