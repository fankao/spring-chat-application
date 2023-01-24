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
    role_id       bigserial NOT NULL,
    permission_id bigserial NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (role_id,permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (permission_id) REFERENCES permissions (id)
);
CREATE TABLE users
(
    id       bigserial PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at timestamp,
    updated_at timestamp
);
CREATE TABLE user_roles (
    user_id bigserial NOT NULL,
    role_id bigserial NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);