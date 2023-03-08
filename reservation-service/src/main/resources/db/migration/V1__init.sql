create table clients
(
    client_id    bigserial   not null primary key,
    username     varchar(32) not null unique,
    first_name   varchar(32) not null,
    last_name    varchar(32) not null,
    email        varchar(32) not null,
    phone_number varchar(20) not null,
    birthdate    DATE        not null
);

insert into clients (username, first_name, last_name, email, phone_number, birthdate)
VALUES ('kissloryshy', 'lory', 'kiss', 'kiss@gmail.com', '+79044479988', '1998-01-22'),
       ('lanarigata', 'lana', 'rigata', 'lana@gmail.com', '+79044477966', '2000-01-01'),
       ('lofigirl', 'lofi', 'girl', 'lofigirl@mail.com', '+79004469855', '2002-06-20');

create table rooms
(
    room_id      bigserial      not null primary key,
    number       integer        not null,
    capacity     integer        not null,
    class        integer        not null,
    is_enabled   boolean        not null,
    weekday_cost numeric(12, 2) not null,
    holiday_cost numeric(12, 2) not null
);

insert into rooms (number, capacity, class, is_enabled, weekday_cost, holiday_cost)
VALUES (1, 1, 1, true, '1500', '1900'),
       (2, 3, 1, true, '3500', '4750'),
       (3, 2, 2, false, '2000', '2500'),
       (4, 2, 2, true, '2100', '2700');

create table reservations
(
    reservation_id    bigserial not null primary key,
    client_id         bigint    not null,
    room_id           bigint    not null,
    contract_signed   date      not null,
    reservation_start date      not null,
    reservation_end   date      not null,
    constraint fk_reservations_clients foreign key (client_id) references clients (client_id),
    constraint fk_reservations_rooms foreign key (room_id) references rooms (room_id)
);