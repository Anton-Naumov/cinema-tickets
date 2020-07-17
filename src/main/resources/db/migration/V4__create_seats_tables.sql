CREATE TABLE seats (
      id SERIAL PRIMARY KEY,
      room_id BIGINT REFERENCES rooms(id),
      number INT UNIQUE NOT NULL,

      UNIQUE(room_id, number)
);
