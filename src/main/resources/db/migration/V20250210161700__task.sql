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

INSERT INTO task (date, description, user_id)
VALUES ('2025-02-21', 'Сережке на стрижку', 3);