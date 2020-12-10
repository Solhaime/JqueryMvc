delete from badpracticetest.roles;
delete from badpracticetest.users;
delete from badpracticetest.users_roles;

ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE roles AUTO_INCREMENT=1;


INSERT INTO badpracticetest.roles (name) VALUES ('ADMIN');
INSERT INTO badpracticetest.roles (name) VALUES ('USER');
INSERT INTO badpracticetest.users (is_active, name, lastname, username, password) values (true, 'admin','adminovich', 'admin', '$2a$10$otELPf/Ign5yYmU1Eib9UegA8p9MaNCw/SwjKy/5bk6xd3GnyfS3K');
INSERT INTO badpracticetest.users (is_active, name, lastname, username, password) values (true, 'user','userovich', 'user', '$2a$10$kR3M73NBpqAcYLxaFTx17OCuh3YOqsvN6ILrlR1GmZjCsH5J3WRb.');
INSERT INTO badpracticetest.users_roles (user_id, roles_id) values (1, 1);
INSERT INTO badpracticetest.users_roles (user_id, roles_id) values (2, 2);
