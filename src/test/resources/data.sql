SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS destinations;
DROP TABLE IF EXISTS users;

SET REFERENTIAL_INTEGRITY TRUE;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE destinations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    user_id BIGINT,
    CONSTRAINT fk_dest_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment VARCHAR(500),
    created_at TIMESTAMP(6),
    rating INT,
    destination_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (destination_id) REFERENCES destinations(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


-- Insertar usuarios
INSERT INTO users (username, email, password, role) VALUES
  ('admin', 'admin@happytravel.com', '$2a$10$aPXA2kUPmoIsdS0qg74ZYOW496WsFTKjjNLBIYgJSbX0PRyLzbkF6', 'ADMIN'),
  ('johndoe', 'john@example.com', '$2a$10$encryptedpassword', 'USER');

-- Insertar destinos de john_doe (id=2 asumido)
INSERT INTO destinations (city, country, description, image_url, user_id) VALUES
  ('Paris', 'France', 'City of Light', 'https://example.com/paris.jpg', 2);

-- Insertar review de john_doe para destino Paris
INSERT INTO reviews (rating, comment, user_id, destination_id) VALUES
  (5, 'Amazing experience in Paris!', 2, 1);



