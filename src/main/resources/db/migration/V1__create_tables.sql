CREATE TABLE IF NOT EXISTS room (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS battery (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    temperature INT NOT NULL
);

CREATE TABLE IF NOT EXISTS blinds (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    state VARCHAR(10) NOT NULL CHECK (state IN ('open', 'closed'))
);

CREATE TABLE IF NOT EXISTS curtain_schedule (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    schedule_time VARCHAR(10) NOT NULL,
    schedule_action VARCHAR(10) NOT NULL CHECK (schedule_action IN ('open', 'close'))
);