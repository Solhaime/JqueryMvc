INSERT INTO badpractice.roles (name) VALUES ('ADMIN');
INSERT INTO badpractice.roles (name) VALUES ('USER');
INSERT INTO badpractice.users (is_active, name, lastname, username, password) values (true, 'admin','adminovich', 'admin', '$2a$10$otELPf/Ign5yYmU1Eib9UegA8p9MaNCw/SwjKy/5bk6xd3GnyfS3K');
INSERT INTO badpractice.users (is_active, name, lastname, username, password) values (true, 'user','userovich', 'user', '$2a$10$kR3M73NBpqAcYLxaFTx17OCuh3YOqsvN6ILrlR1GmZjCsH5J3WRb.');
INSERT INTO badpractice.users_roles (user_id, roles_id) values (1, 1);
INSERT INTO badpractice.users_roles (user_id, roles_id) values (2, 2);
