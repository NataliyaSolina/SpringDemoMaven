CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE INDEX users_done_idx ON users (login);

INSERT INTO users (login, password)
VALUES ('admin', '$2a$10$q0WLoUG8kGLxPbgkIIeSa.dzRt2fzhmKUmuQqiPG2bEOkcDySWnmS'),
       ('Nata', '$2a$10$425Jj.pxUZL4GBEllhMHqOuVkLWLWv9oEd/hZoeKdgVnjXjg3XF.m'),
       ('Serg', '$2a$10$uV2U9B0NLf.D0t7aIcpy3OE6x/BU00JcPTEpxkmc3uIuj0qM4VYF2');