CREATE TABLE rooms (
      id SERIAL PRIMARY KEY,
      number INT UNIQUE NOT NULL
);

CREATE TABLE screenings (
       id SERIAL PRIMARY KEY,
       movie_id BIGINT REFERENCES movies(id),
       room_id BIGINT REFERENCES rooms(id),
       time TIMESTAMP,
       price DECIMAL
);
