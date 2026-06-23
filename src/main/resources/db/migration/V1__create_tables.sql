CREATE TABLE sector (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE,
    base_price NUMERIC(10,2) NOT NULL,
    max_capacity INT NOT NULL,
    open_hour TIME NOT NULL,
    close_hour TIME NOT NULL,
    duration_limit_minutes INT NOT NULL
);

CREATE TABLE spot (
    id BIGSERIAL PRIMARY KEY,
    sector_id BIGINT NOT NULL REFERENCES sector(id) ON DELETE CASCADE,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    UNIQUE(lat, lng)
);

CREATE TABLE parking_event (
    id BIGSERIAL PRIMARY KEY,
    license_plate VARCHAR(15) NOT NULL,
    entry_time TIMESTAMPTZ NOT NULL,
    exit_time TIMESTAMPTZ,
    price NUMERIC(10,2),
    sector_id BIGINT NOT NULL REFERENCES sector(id) ON DELETE CASCADE,
    spot_id BIGINT REFERENCES spot(id)
);

-- Índices úteis
CREATE INDEX idx_parking_event_license_plate ON parking_event(license_plate);
CREATE INDEX idx_parking_event_exit_time ON parking_event(exit_time);
