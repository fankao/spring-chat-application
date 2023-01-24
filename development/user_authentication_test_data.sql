-- Insert a user
INSERT INTO users (email, role_id) VALUES ('user1@example.com', 1);
INSERT INTO users (email, role_id) VALUES ('user2@example.com', 1);
INSERT INTO users (email, role_id) VALUES ('user3@example.com', 2);
INSERT INTO users (email, role_id) VALUES ('user4@example.com', 2);

-- Insert a role
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

-- Insert role permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);

-- Insert permissions
INSERT INTO permissions (name) VALUES ('read');
INSERT INTO permissions (name) VALUES ('write');


INSERT INTO permissions (name) VALUES ('CREATE_CHATROOM');
INSERT INTO permissions (name) VALUES ('JOIN_CHATROOM');
INSERT INTO permissions (name) VALUES ('SEND_MESSAGE');
INSERT INTO permissions (name) VALUES ('EDIT_MESSAGE');
INSERT INTO permissions (name) VALUES ('DELETE_MESSAGE');
