CREATE TABLE IF NOT EXISTS curtain_schedule (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    schedule_time TIME NOT NULL,
    schedule_action VARCHAR(10) NOT NULL CHECK (schedule_action IN ('open', 'close'))
);