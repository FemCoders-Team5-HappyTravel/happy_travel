DELETE existing data to ensure clean state for each test run if needed
DELETE FROM reviews;
DELETE FROM destinations;
DELETE FROM users;

--Users Table
--IMPORTANT: Replace {bcrypt_password_for_password123} with the actual BCrypted hash
--Use the PasswordGenerator class or similar to get the bcrypt hashes for your passwords.
--The IDs are assigned automatically if your 'id' column is AUTO_INCREMENT.
--Adjust the number of users to match what your tests expect (e.g., 7 users for hasSize(7))

INSERT INTO users (id, username, email, password, role) VALUES
  (1, 'admin', 'admin@happytravel.com', '$2a$10$aPXA2kUPmoIsdS0qg74ZYOW496WsFTKjjNLBIYgJSbX0PRyLzbkF6', 'ADMIN'),
  (2, 'pico', 'picopico@example.com', '$2a$10$wo3KC4iVMtoXxEpxiQtHRuH81WQ1Y/fJDB7mXZhM07IjKl7jjUqh6', 'USER'),
  (3, 'john_doe', 'john@example.com', '$2a$10$g/vQtPrLcVNCXf.laKpeVulFC/R5UHk5iATCKFKUIKbWVCs01Qh5K', 'USER'),
  (4, 'jane_smith', 'jane@example.com', '$2a$10$b2zJbYudyiYHhYrlu/BTieIuYO6H6XLpl7tqgXzLclYelRY4a2abe', 'USER'),
  (5, 'mike_wilson', 'mike@example.com', '$2a$10$XKGm9J8M1.9LA5iay6rqXeb7PZZKVXsC3rxl.eNwEG1SghzWfez5C', 'USER'),
  (6, 'sarah_jones', 'sarah@example.com', '$2a$10$3W6elhBfv5.q77fNZDLBFeOjR1KIArD8oBnSmmdBbwZqdEbcoKNje', 'USER'),
  (7, 'david_brown', 'david@example.com', '$2a$10$AvY074.FdzEkjFANSRLi6etULgAIG1o5v/I0o0CGrcRu5Os9mhByS', 'USER');


--Destinations Table
--Ensure user_id maps to the IDs inserted in the users table
INSERT INTO destinations (id, country, city, description, image_url, user_id) VALUES
  (1, 'France', 'Paris', 'City of Light', 'https://example.com/paris.jpg', 3), -- user_id 3 (john_doe)
  (2, 'Japan', 'Tokyo', 'Futuristic and traditional', 'https://example.com/tokyo.jpg', 3), -- user_id 3 (john_doe)
  (3, 'USA', 'New York', 'The Big Apple', 'https://example.com/ny.jpg', 4);  -- user_id 4 (jane_smith)

--Reviews Table
--Ensure user_id and destination_id map to the IDs inserted above
INSERT INTO reviews (id, rating, comment, user_id, destination_id) VALUES
  (1, 5, 'Amazing experience in Paris!', 3, 1),
  (2, 4, 'Loved the culture and food in Tokyo.', 3, 2),
  (3, 3, 'NYC is fast-paced but fun.', 4, 3);