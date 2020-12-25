INSERT INTO ROOMS (id, name, created_at, updated_at)
VALUES (2, 'two', 1608822796123, 1608822796123);

INSERT INTO messages (id, room_id, content, created_at, updated_at)
VALUES (2, 2, 'first post in room two', 1608822796456, 1608822796456);

INSERT INTO messages (id, room_id, content, created_at, updated_at)
VALUES (3, 1, 'second post in room one', 1608822796456, 1608822796456);
