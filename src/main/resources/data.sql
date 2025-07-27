-- Insert users
INSERT INTO users (username, email, password, role) VALUES
('alice', 'alice@example.com', '$2a$10$0FfOnnkUKbq5utlMMOCzwevrOQpiTUd8LK0jIcUQv7V2XIIqwfxC.', 'USER'),
('bob', 'bob@example.com', '$2a$10$98WVBNb.kTLzWhVIwqKtteqoxFwdITJAR.sIKJI8arWXMicRCzDAO', 'USER'),
('admin', 'admin@example.com', '$2a$10$JVskeCkUBXd.AeyDwEwu0uIRAvqCXUo7NX3K568/zFy4i5Ph.ezEq', 'ADMIN');

-- Insert destinations
INSERT INTO destinations (country, city, description, image_url, user_id) VALUES
('France', 'Paris', 'City of lights', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d', 1),
('USA', 'New York', 'The Big Apple', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d', 2);

-- Insert reviews
INSERT INTO reviews (rating, comment, user_id, destination_id, created_at) VALUES
(5, 'Loved Paris!', 1, 1, NOW()),
(4, 'Great place to visit.', 2, 2, NOW());
