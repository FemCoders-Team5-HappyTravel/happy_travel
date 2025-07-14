-- Insert Users
INSERT INTO users (id, username, email, password, role)
VALUES
  (1, 'johndoe', 'john@example.com', 'password123', 'USER'),
  (2, 'adminuser', 'admin@example.com', 'adminpass', 'ADMIN');

-- Insert Destinations
INSERT INTO destinations (id, country, city, description, image_url, user_id)
VALUES
  (1, 'France', 'Paris', 'City of Light', 'https://example.com/paris.jpg', 1),
  (2, 'Japan', 'Tokyo', 'Futuristic and traditional', 'https://example.com/tokyo.jpg', 1),
  (3, 'USA', 'New York', 'The Big Apple', 'https://example.com/ny.jpg', 2);

-- Insert Reviews
INSERT INTO reviews (id, rating, comment, user_id, destination_id, created_at)
VALUES
  (1, 5, 'Amazing experience in Paris!', 1, 1, CURRENT_TIMESTAMP),
  (2, 4, 'Loved the culture and food in Tokyo.', 1, 2, CURRENT_TIMESTAMP),
  (3, 3, 'NYC is fast-paced but fun.', 2, 3, CURRENT_TIMESTAMP);
