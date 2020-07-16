CREATE TABLE movies (
      id SERIAL PRIMARY KEY,
      title VARCHAR(50) UNIQUE NOT NULL,
      length_minutes INT NOT NULL,
      description VARCHAR(250),
      rating DECIMAL,
      actors VARCHAR(150)
);
