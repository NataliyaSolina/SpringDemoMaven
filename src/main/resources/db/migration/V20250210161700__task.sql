CREATE TABLE task
(
    id          BIGSERIAL PRIMARY KEY,
    date        DATE    NOT NULL,
    description TEXT,
    user_id     BIGSERIAL NOT NULL,
    done        BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX task_date_idx ON task (date);
CREATE INDEX task_done_idx ON task (done);

insert into task (date, description, user_id)
values ('2025-02-21', 'Сережке на стрижку', 3),
       ('2025-02-20', 'Митап по кролику', 2),
       ('2025-02-12', 'Скомпоновать доклад', 2),
       ('2025-02-11', 'Закончить курс', 2);