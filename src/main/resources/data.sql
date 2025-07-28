--SHOW TABLES;

--DROP TABLE destinations, reviews, users;

--DESCRIBE destinations;
--DESCRIBE reviews;
--DESCRIBE users;

--SELECT * FROM destinations;
--SELECT * FROM reviews;
--SELECT * FROM users;

--password123
--adminpass

-- USERS (5 standard + 1 admin)
INSERT INTO users (id, username, email, password, role) VALUES
(1, 'alice', 'alice@example.com', '$2a$10$0FfOnnkUKbq5utlMMOCzwevrOQpiTUd8LK0jIcUQv7V2XIIqwfxC.', 'USER'),
(2, 'bob', 'bob@example.com', '$2a$10$98WVBNb.kTLzWhVIwqKtteqoxFwdITJAR.sIKJI8arWXMicRCzDAO', 'USER'),
(3, 'carol', 'carol@example.com', '$2a$10$e06h5dM.YREuPuW6cdyZ..nVWhQILUxtv1x/IUctr1UZK6V9dBJJW', 'USER'),
(4, 'dave', 'dave@example.com', '$2a$10$Vmbe3pNj6XfW04TCDLM8KO7cbaSv9T4.d78HJPa68eRc7EWVlyfNa', 'USER'),
(5, 'eve', 'eve@example.com', '$2a$10$QPfCclgd.SunEFi4.vRmvObDJVu5MGVKr.kyt9VkBkKNnfs8KLp92', 'USER'),
(6, 'admin', 'admin@example.com', '$2a$10$JVskeCkUBXd.AeyDwEwu0uIRAvqCXUo7NX3K568/zFy4i5Ph.ezEq', 'ADMIN');

-- DESTINATIONS (5 per regular user = 25 total)
-- All use the same Unsplash image
INSERT INTO destinations (id, country, city, description, image_url, user_id) VALUES
(1, 'France', 'Paris', 'The city of lights', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1),
(2, 'Italy', 'Rome', 'The eternal city', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1),
(3, 'USA', 'New York', 'The Big Apple', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1),
(4, 'UK', 'London', 'The capital of England', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1),
(5, 'Japan', 'Tokyo', 'City of the future', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1),

(6, 'Spain', 'Barcelona', 'Gaudi’s playground', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2),
(7, 'Germany', 'Berlin', 'A city full of history', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2),
(8, 'Canada', 'Toronto', 'Diverse and modern', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2),
(9, 'Brazil', 'Rio', 'Carnival and beaches', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2),
(10, 'Australia', 'Sydney', 'Harbour city', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2),

(11, 'India', 'Delhi', 'Capital of India', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3),
(12, 'China', 'Beijing', 'Great Wall nearby', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3),
(13, 'Mexico', 'Cancun', 'Beach paradise', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3),
(14, 'Egypt', 'Cairo', 'Land of pyramids', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3),
(15, 'Greece', 'Athens', 'Home of the gods', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3),

(16, 'Russia', 'Moscow', 'Historical city', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4),
(17, 'South Africa', 'Cape Town', 'Mountain and sea', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4),
(18, 'Thailand', 'Bangkok', 'Vibrant street life', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4),
(19, 'Argentina', 'Buenos Aires', 'Tango capital', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4),
(20, 'Turkey', 'Istanbul', 'Bridge between continents', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4),

(21, 'Indonesia', 'Bali', 'Island paradise', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5),
(22, 'Sweden', 'Stockholm', 'City on water', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5),
(23, 'Norway', 'Oslo', 'Scandinavian beauty', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5),
(24, 'UAE', 'Dubai', 'Modern desert city', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5),
(25, 'Portugal', 'Lisbon', 'Colorful and coastal', 'https://images.unsplash.com/photo-1751210392423-d8988823cb6d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5);

-- REVIEWS (5 users x 25 destinations = 125 rows)

-- REVIEWS (user_id 1–5 for destination_id 1–25)
INSERT INTO reviews (id, rating, comment, user_id, destination_id, created_at) VALUES
(1, 5, 'Review by user 1 on destination 1', 1, 1, CURRENT_TIMESTAMP),
(2, 4, 'Review by user 2 on destination 1', 2, 1, CURRENT_TIMESTAMP),
(3, 3, 'Review by user 3 on destination 1', 3, 1, CURRENT_TIMESTAMP),
(4, 5, 'Review by user 4 on destination 1', 4, 1, CURRENT_TIMESTAMP),
(5, 4, 'Review by user 5 on destination 1', 5, 1, CURRENT_TIMESTAMP),

(6, 4, 'Review by user 1 on destination 2', 1, 2, CURRENT_TIMESTAMP),
(7, 5, 'Review by user 2 on destination 2', 2, 2, CURRENT_TIMESTAMP),
(8, 4, 'Review by user 3 on destination 2', 3, 2, CURRENT_TIMESTAMP),
(9, 3, 'Review by user 4 on destination 2', 4, 2, CURRENT_TIMESTAMP),
(10, 5, 'Review by user 5 on destination 2', 5, 2, CURRENT_TIMESTAMP),

(11, 3, 'Review by user 1 on destination 3', 1, 3, CURRENT_TIMESTAMP),
(12, 5, 'Review by user 2 on destination 3', 2, 3, CURRENT_TIMESTAMP),
(13, 4, 'Review by user 3 on destination 3', 3, 3, CURRENT_TIMESTAMP),
(14, 4, 'Review by user 4 on destination 3', 4, 3, CURRENT_TIMESTAMP),
(15, 5, 'Review by user 5 on destination 3', 5, 3, CURRENT_TIMESTAMP),

(16, 4, 'Review by user 1 on destination 4', 1, 4, CURRENT_TIMESTAMP),
(17, 3, 'Review by user 2 on destination 4', 2, 4, CURRENT_TIMESTAMP),
(18, 5, 'Review by user 3 on destination 4', 3, 4, CURRENT_TIMESTAMP),
(19, 4, 'Review by user 4 on destination 4', 4, 4, CURRENT_TIMESTAMP),
(20, 3, 'Review by user 5 on destination 4', 5, 4, CURRENT_TIMESTAMP),

(21, 5, 'Review by user 1 on destination 5', 1, 5, CURRENT_TIMESTAMP),
(22, 4, 'Review by user 2 on destination 5', 2, 5, CURRENT_TIMESTAMP),
(23, 4, 'Review by user 3 on destination 5', 3, 5, CURRENT_TIMESTAMP),
(24, 5, 'Review by user 4 on destination 5', 4, 5, CURRENT_TIMESTAMP),
(25, 4, 'Review by user 5 on destination 5', 5, 5, CURRENT_TIMESTAMP),

(26, 3, 'Review by user 1 on destination 6', 1, 6, CURRENT_TIMESTAMP),
(27, 4, 'Review by user 2 on destination 6', 2, 6, CURRENT_TIMESTAMP),
(28, 5, 'Review by user 3 on destination 6', 3, 6, CURRENT_TIMESTAMP),
(29, 3, 'Review by user 4 on destination 6', 4, 6, CURRENT_TIMESTAMP),
(30, 4, 'Review by user 5 on destination 6', 5, 6, CURRENT_TIMESTAMP),

(31, 5, 'Review by user 1 on destination 7', 1, 7, CURRENT_TIMESTAMP),
(32, 4, 'Review by user 2 on destination 7', 2, 7, CURRENT_TIMESTAMP),
(33, 3, 'Review by user 3 on destination 7', 3, 7, CURRENT_TIMESTAMP),
(34, 5, 'Review by user 4 on destination 7', 4, 7, CURRENT_TIMESTAMP),
(35, 4, 'Review by user 5 on destination 7', 5, 7, CURRENT_TIMESTAMP),

(36, 4, 'Review by user 1 on destination 8', 1, 8, CURRENT_TIMESTAMP),
(37, 3, 'Review by user 2 on destination 8', 2, 8, CURRENT_TIMESTAMP),
(38, 4, 'Review by user 3 on destination 8', 3, 8, CURRENT_TIMESTAMP),
(39, 5, 'Review by user 4 on destination 8', 4, 8, CURRENT_TIMESTAMP),
(40, 4, 'Review by user 5 on destination 8', 5, 8, CURRENT_TIMESTAMP),

(41, 5, 'Review by user 1 on destination 9', 1, 9, CURRENT_TIMESTAMP),
(42, 4, 'Review by user 2 on destination 9', 2, 9, CURRENT_TIMESTAMP),
(43, 4, 'Review by user 3 on destination 9', 3, 9, CURRENT_TIMESTAMP),
(44, 3, 'Review by user 4 on destination 9', 4, 9, CURRENT_TIMESTAMP),
(45, 5, 'Review by user 5 on destination 9', 5, 9, CURRENT_TIMESTAMP),

(46, 4, 'Review by user 1 on destination 10', 1, 10, CURRENT_TIMESTAMP),
(47, 3, 'Review by user 2 on destination 10', 2, 10, CURRENT_TIMESTAMP),
(48, 5, 'Review by user 3 on destination 10', 3, 10, CURRENT_TIMESTAMP),
(49, 4, 'Review by user 4 on destination 10', 4, 10, CURRENT_TIMESTAMP),
(50, 4, 'Review by user 5 on destination 10', 5, 10, CURRENT_TIMESTAMP),

(51, 3, 'Review by user 1 on destination 11', 1, 11, CURRENT_TIMESTAMP),
(52, 5, 'Review by user 2 on destination 11', 2, 11, CURRENT_TIMESTAMP),
(53, 4, 'Review by user 3 on destination 11', 3, 11, CURRENT_TIMESTAMP),
(54, 3, 'Review by user 4 on destination 11', 4, 11, CURRENT_TIMESTAMP),
(55, 5, 'Review by user 5 on destination 11', 5, 11, CURRENT_TIMESTAMP),

(56, 4, 'Review by user 1 on destination 12', 1, 12, CURRENT_TIMESTAMP),
(57, 3, 'Review by user 2 on destination 12', 2, 12, CURRENT_TIMESTAMP),
(58, 5, 'Review by user 3 on destination 12', 3, 12, CURRENT_TIMESTAMP),
(59, 4, 'Review by user 4 on destination 12', 4, 12, CURRENT_TIMESTAMP),
(60, 3, 'Review by user 5 on destination 12', 5, 12, CURRENT_TIMESTAMP),

(61, 5, 'Review by user 1 on destination 13', 1, 13, CURRENT_TIMESTAMP),
(62, 4, 'Review by user 2 on destination 13', 2, 13, CURRENT_TIMESTAMP),
(63, 4, 'Review by user 3 on destination 13', 3, 13, CURRENT_TIMESTAMP),
(64, 3, 'Review by user 4 on destination 13', 4, 13, CURRENT_TIMESTAMP),
(65, 4, 'Review by user 5 on destination 13', 5, 13, CURRENT_TIMESTAMP),

(66, 5, 'Review by user 1 on destination 14', 1, 14, CURRENT_TIMESTAMP),
(67, 4, 'Review by user 2 on destination 14', 2, 14, CURRENT_TIMESTAMP),
(68, 3, 'Review by user 3 on destination 14', 3, 14, CURRENT_TIMESTAMP),
(69, 4, 'Review by user 4 on destination 14', 4, 14, CURRENT_TIMESTAMP),
(70, 5, 'Review by user 5 on destination 14', 5, 14, CURRENT_TIMESTAMP),

(71, 4, 'Review by user 1 on destination 15', 1, 15, CURRENT_TIMESTAMP),
(72, 3, 'Review by user 2 on destination 15', 2, 15, CURRENT_TIMESTAMP),
(73, 5, 'Review by user 3 on destination 15', 3, 15, CURRENT_TIMESTAMP),
(74, 4, 'Review by user 4 on destination 15', 4, 15, CURRENT_TIMESTAMP),
(75, 4, 'Review by user 5 on destination 15', 5, 15, CURRENT_TIMESTAMP),

(76, 3, 'Review by user 1 on destination 16', 1, 16, CURRENT_TIMESTAMP),
(77, 5, 'Review by user 2 on destination 16', 2, 16, CURRENT_TIMESTAMP),
(78, 4, 'Review by user 3 on destination 16', 3, 16, CURRENT_TIMESTAMP),
(79, 3, 'Review by user 4 on destination 16', 4, 16, CURRENT_TIMESTAMP),
(80, 5, 'Review by user 5 on destination 16', 5, 16, CURRENT_TIMESTAMP),

(81, 4, 'Review by user 1 on destination 17', 1, 17, CURRENT_TIMESTAMP),
(82, 3, 'Review by user 2 on destination 17', 2, 17, CURRENT_TIMESTAMP),
(83, 5, 'Review by user 3 on destination 17', 3, 17, CURRENT_TIMESTAMP),
(84, 4, 'Review by user 4 on destination 17', 4, 17, CURRENT_TIMESTAMP),
(85, 4, 'Review by user 5 on destination 17', 5, 17, CURRENT_TIMESTAMP),

(86, 5, 'Review by user 1 on destination 18', 1, 18, CURRENT_TIMESTAMP),
(87, 4, 'Review by user 2 on destination 18', 2, 18, CURRENT_TIMESTAMP),
(88, 3, 'Review by user 3 on destination 18', 3, 18, CURRENT_TIMESTAMP),
(89, 5, 'Review by user 4 on destination 18', 4, 18, CURRENT_TIMESTAMP),
(90, 4, 'Review by user 5 on destination 18', 5, 18, CURRENT_TIMESTAMP),

(91, 4, 'Review by user 1 on destination 19', 1, 19, CURRENT_TIMESTAMP),
(92, 3, 'Review by user 2 on destination 19', 2, 19, CURRENT_TIMESTAMP),
(93, 5, 'Review by user 3 on destination 19', 3, 19, CURRENT_TIMESTAMP),
(94, 4, 'Review by user 4 on destination 19', 4, 19, CURRENT_TIMESTAMP),
(95, 5, 'Review by user 5 on destination 19', 5, 19, CURRENT_TIMESTAMP),

(96, 3, 'Review by user 1 on destination 20', 1, 20, CURRENT_TIMESTAMP),
(97, 4, 'Review by user 2 on destination 20', 2, 20, CURRENT_TIMESTAMP),
(98, 5, 'Review by user 3 on destination 20', 3, 20, CURRENT_TIMESTAMP),
(99, 3, 'Review by user 4 on destination 20', 4, 20, CURRENT_TIMESTAMP),
(100, 4, 'Review by user 5 on destination 20', 5, 20, CURRENT_TIMESTAMP),

(101, 5, 'Review by user 1 on destination 21', 1, 21, CURRENT_TIMESTAMP),
(102, 4, 'Review by user 2 on destination 21', 2, 21, CURRENT_TIMESTAMP),
(103, 4, 'Review by user 3 on destination 21', 3, 21, CURRENT_TIMESTAMP),
(104, 5, 'Review by user 4 on destination 21', 4, 21, CURRENT_TIMESTAMP),
(105, 4, 'Review by user 5 on destination 21', 5, 21, CURRENT_TIMESTAMP),

(106, 4, 'Review by user 1 on destination 22', 1, 22, CURRENT_TIMESTAMP),
(107, 3, 'Review by user 2 on destination 22', 2, 22, CURRENT_TIMESTAMP),
(108, 5, 'Review by user 3 on destination 22', 3, 22, CURRENT_TIMESTAMP),
(109, 4, 'Review by user 4 on destination 22', 4, 22, CURRENT_TIMESTAMP),
(110, 3, 'Review by user 5 on destination 22', 5, 22, CURRENT_TIMESTAMP),

(111, 5, 'Review by user 1 on destination 23', 1, 23, CURRENT_TIMESTAMP),
(112, 4, 'Review by user 2 on destination 23', 2, 23, CURRENT_TIMESTAMP),
(113, 4, 'Review by user 3 on destination 23', 3, 23, CURRENT_TIMESTAMP),
(114, 3, 'Review by user 4 on destination 23', 4, 23, CURRENT_TIMESTAMP),
(115, 4, 'Review by user 5 on destination 23', 5, 23, CURRENT_TIMESTAMP),

(116, 5, 'Review by user 1 on destination 24', 1, 24, CURRENT_TIMESTAMP),
(117, 4, 'Review by user 2 on destination 24', 2, 24, CURRENT_TIMESTAMP),
(118, 3, 'Review by user 3 on destination 24', 3, 24, CURRENT_TIMESTAMP),
(119, 5, 'Review by user 4 on destination 24', 4, 24, CURRENT_TIMESTAMP),
(120, 4, 'Review by user 5 on destination 24', 5, 24, CURRENT_TIMESTAMP),

(121, 3, 'Review by user 1 on destination 25', 1, 25, CURRENT_TIMESTAMP),
(122, 4, 'Review by user 2 on destination 25', 2, 25, CURRENT_TIMESTAMP),
(123, 5, 'Review by user 3 on destination 25', 3, 25, CURRENT_TIMESTAMP),
(124, 3, 'Review by user 4 on destination 25', 4, 25, CURRENT_TIMESTAMP),
(125, 4, 'Review by user 5 on destination 25', 5, 25, CURRENT_TIMESTAMP);