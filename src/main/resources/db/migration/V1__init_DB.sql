create table roles
(
    id bigint auto_increment
        primary key,
    name varchar(100) null unique
)
    engine = MyISAM;

create table users
(
    id bigint auto_increment
        primary key,
    is_active bit not null,
    lastname varchar(255) null,
    name varchar(255) null,
    password varchar(255) null,
    username varchar(100) not null unique
)
    engine = MyISAM;

create table users_roles
(
    user_id bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id)
)
    engine = MyISAM;

create index FKa62j07k5mhgifpp955h37ponj
    on users_roles (roles_id);