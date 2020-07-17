CREATE TABLE tickets (
      id SERIAL PRIMARY KEY,
      unique_identifier VARCHAR(100) UNIQUE,
      screening_id BIGINT REFERENCES screenings(id),
      seat_id BIGINT REFERENCES seats(id),
      buyer_email VARCHAR(100),

      CONSTRAINT seat_taken UNIQUE(screening_id, seat_id)
);
