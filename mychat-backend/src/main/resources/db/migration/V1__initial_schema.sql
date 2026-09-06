
CREATE TABLE app_user (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT chk_app_user_email_lowercase CHECK (email = lower(email)),
    CONSTRAINT chk_app_user_email_not_blank CHECK (length(trim(email)) > 0)
);

CREATE TABLE chat (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    app_user_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT chk_chat_title_not_blank CHECK (length(trim(title)) > 0),

    CONSTRAINT fk_chat_app_user FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE INDEX idx_chat_app_user_id ON chat(app_user_id, created_at DESC);

CREATE TABLE message (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    content VARCHAR NOT NULL,
    received_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT chk_message_content_not_blank CHECK (length(trim(content)) > 0),

    CONSTRAINT fk_message_chat FOREIGN KEY (chat_id) REFERENCES chat(id) ON DELETE CASCADE
);

CREATE INDEX idx_message_chat_id ON message(chat_id, received_at DESC);