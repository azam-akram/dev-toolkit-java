CREATE TABLE message (
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    message_key VARCHAR(255) NOT NULL,
    sender      VARCHAR(255) NOT NULL,
    saved_at    TIMESTAMP    NOT NULL
);
