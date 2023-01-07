CREATE TABLE permissions
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at timestamp,
    updated_at timestamp
);
CREATE TABLE roles
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at timestamp,
    updated_at timestamp
);
CREATE TABLE role_permissions
(
    id            bigserial PRIMARY KEY,
    role_id       INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (permission_id) REFERENCES permissions (id)
);
CREATE TABLE users
(
    id       bigserial PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    role_id  INTEGER      NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);