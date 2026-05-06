CREATE INDEX IF NOT EXISTS idx_battery_room_id ON battery(room_id);
CREATE INDEX IF NOT EXISTS idx_blinds_room_id ON blinds(room_id);
CREATE INDEX IF NOT EXISTS idx_curtain_schedule_room_id ON curtain_schedule(room_id);