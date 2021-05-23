CREATE TABLE todo_item (
    id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(2048) NULL,
    completed_at TIMESTAMP NULL,

    PRIMARY KEY (id)
);