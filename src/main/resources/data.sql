INSERT INTO users(login, password)
VALUES ('User1', '111'),
       ('User2', '222'),
       ('User3', '333'),
       ('User4', '444'),
       ('User5', '555');

INSERT INTO chatrooms(name, owner)
VALUES ('Chat1', '1'),
       ('Chat2', '2'),
       ('Chat3', '3'),
       ('Chat4', '4'),
       ('Chat5', '5');

    INSERT INTO messages(sender, room, message, date_time)
    VALUES (1, 1, 'Hello world 1', '2024-07-12 10:00:00'),
       (2, 1, 'Hello world 2', '2024-07-12 10:00:10'),
       (3, 1, 'Hello world 3', '2024-07-12 10:01:10'),
       (4, 1, 'Hello world 4', '2024-07-12 10:01:40'),
       (5, 1, 'Hello world 5', '2024-07-12 10:02:00');