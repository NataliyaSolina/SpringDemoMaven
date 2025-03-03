ALTER TABLE task
    ADD CONSTRAINT task_users__fk
        FOREIGN KEY (user_id) REFERENCES users;