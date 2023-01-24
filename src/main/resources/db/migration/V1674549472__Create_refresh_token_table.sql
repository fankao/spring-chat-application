CREATE TABLE refresh_token
(
    id bigserial NOT NULL,
    token text NOT NULL,
    user_id BIGINT,
    expiry_date timestamp,
    created_at timestamp,
    updated_at timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id)
);