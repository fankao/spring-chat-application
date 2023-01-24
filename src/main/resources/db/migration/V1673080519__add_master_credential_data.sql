-- Insert permissions
INSERT INTO permissions (name)
VALUES ('VIEW_USER'); -- 1
INSERT INTO permissions (name)
VALUES ('ADD_USER'); -- 2
INSERT INTO permissions (name)
VALUES ('EDIT_USER'); -- 3
INSERT INTO permissions (name)
VALUES ('DELETE_USER'); -- 4

-- Insert a role
INSERT INTO roles (name, created_at, updated_at)
VALUES ('USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 1
INSERT INTO roles (name, created_at, updated_at)
VALUES ('ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 2

-- Insert role permissions
INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at)
VALUES (2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Admin credential
INSERT INTO users(first_name,last_name,user_name,password,email)
VALUES ('App','Admin','admin','$2a$10$gXFVdIu2ZOfB.shT4sSsieFrSUqFDDyKxmVr9IXzzZMvWCyUTzkP6','admin@chatapp.com'); --pass: admin1234

INSERT INTO user_roles(user_id,role_id)
VALUES (1,2);


