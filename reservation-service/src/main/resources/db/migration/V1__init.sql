create table clients
(
    username     varchar(128) not null primary key,
    first_name   varchar(128) not null,
    last_name    varchar(128) not null,
    email        varchar(128) not null,
    phone_number varchar(30)  not null,
    birthdate    DATE         not null
);

insert into clients (username, first_name, last_name, email, phone_number, birthdate)
VALUES ('kissloryshy', 'lory', 'kiss', 'kiss@gmail.com', '+79044479988', '1998-01-22'),
       ('lanarigata', 'lana', 'rigata', 'lana@gmail.com', '+79044477966', '2000-01-01'),
       ('lofigirl', 'lofi', 'girl', 'lofigirl@mail.com', '+79004469855', '2002-06-20');

create table rooms
(
    room_number   integer primary key,
    room_capacity integer,
    class         integer,
    is_enabled    boolean,
    weekday_cost  numeric(12, 2),
    holiday_cost  numeric(12, 2)
);

insert into rooms (room_number, room_capacity, class, is_enabled, weekday_cost, holiday_cost)
VALUES (1, 1, 1, true, '1500', '1900'),
       (2, 3, 1, true, '3500', '4750'),
       (3, 2, 2, false, '2000', '2500'),
       (4, 2, 2, true, '2100', '2700');

create table reservations
(
    reservation_id    serial primary key,
    client_username   varchar(128),
    room_number       integer,
    contract_signed   date,
    reservation_start date,
    reservation_end   date,
    constraint fk_reservations_clients foreign key (client_username) references clients (username),
    constraint fk_reservations_rooms foreign key (room_number) references rooms (room_number)
);