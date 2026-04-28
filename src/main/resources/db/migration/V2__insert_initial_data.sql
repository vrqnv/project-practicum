INSERT INTO room (name) VALUES ('Гостиная');

INSERT INTO battery (room_id, temperature)
VALUES ((SELECT id FROM room WHERE name = 'Гостиная'), 22);

INSERT INTO blinds (room_id, state)
VALUES ((SELECT id FROM room WHERE name = 'Гостиная'), 'closed');