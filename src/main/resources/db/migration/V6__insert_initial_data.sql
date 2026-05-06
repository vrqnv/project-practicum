INSERT INTO room (name) VALUES ('Гостиная');

INSERT INTO battery (room_id, temperature)
SELECT id, 22 FROM room WHERE name = 'Гостиная';

INSERT INTO blinds (room_id, state)
SELECT id, 'closed' FROM room WHERE name = 'Гостиная';