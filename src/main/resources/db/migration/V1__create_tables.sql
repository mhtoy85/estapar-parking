CREATE TABLE sector (
    id SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    base_price NUMERIC(10,2) NOT NULL,
    max_capacity INT NOT NULL,
    open_hour TIME NOT NULL,
    close_hour TIME NOT NULL,
    duration_limit_minutes INT NOT NULL
);

CREATE TABLE spot (
    id SERIAL PRIMARY KEY,
    sector_id INT NOT NULL REFERENCES sector(id) ON DELETE CASCADE,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL
);

CREATE TABLE parking_event (
    id SERIAL PRIMARY KEY,
    license_plate VARCHAR(15) NOT NULL,
    sector_id INT NOT NULL REFERENCES sector(id) ON DELETE CASCADE,
    spot_id INT REFERENCES spot(id),
    entry_time TIMESTAMP WITH TIME ZONE NOT NULL,
    exit_time TIMESTAMP WITH TIME ZONE,
    price NUMERIC(10,2)
);

CREATE INDEX idx_parking_event_license_plate ON parking_event(license_plate);
CREATE INDEX idx_parking_event_exit_time ON parking_event(exit_time);