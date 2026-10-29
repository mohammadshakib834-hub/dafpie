-- Seed Data for DAFPIE (Passwords are 'password' encrypted with BCrypt)
-- Password for all seed users: password -> $2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m

-- Admins
INSERT INTO users (name, email, password, phone, role, address, city, created_at) 
VALUES ('System Admin', 'admin@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '9999999999', 'ADMIN', 'HQ', 'Coimbatore', CURRENT_TIMESTAMP);

-- Donors
INSERT INTO users (name, email, password, phone, role, address, city, latitude, longitude, created_at) 
VALUES ('Green Leaf Restaurant', 'donor@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '9876543210', 'DONOR', '12 Main St', 'Coimbatore', 11.0168, 76.9558, CURRENT_TIMESTAMP);
INSERT INTO users (name, email, password, phone, role, address, city, created_at) 
VALUES ('Fresh Bites Bakery', 'bakery@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '9876543211', 'DONOR', '45 West Avenue', 'Chennai', CURRENT_TIMESTAMP);

-- NGOs
INSERT INTO users (name, email, password, phone, role, address, city, latitude, longitude, created_at) 
VALUES ('Hope Foundation', 'ngo@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '9123456780', 'NGO', 'South Block', 'Coimbatore', 11.0000, 77.0000, CURRENT_TIMESTAMP);
INSERT INTO ngo_profiles (user_id, registration_number, people_served_capacity, is_approved) VALUES (4, 'NGO-1234', 100, TRUE);

INSERT INTO users (name, email, password, phone, role, address, city, created_at) 
VALUES ('Care & Share NGO', 'care@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '9123456781', 'NGO', 'North Block', 'Chennai', CURRENT_TIMESTAMP);
INSERT INTO ngo_profiles (user_id, registration_number, people_served_capacity, is_approved) VALUES (5, 'NGO-5678', 250, TRUE);

-- Volunteers
INSERT INTO users (name, email, password, phone, role, address, city, created_at) 
VALUES ('Alex Volunteer', 'volunteer@dafpie.org', '$2a$10$wY.D9d3Hh6Hw9m7uO91u2en8oXJq/yM9V9w1b1w8y5C7Y3.0P3v0m', '8123456790', 'VOLUNTEER', 'East Street', 'Coimbatore', CURRENT_TIMESTAMP);
INSERT INTO volunteer_profiles (user_id, vehicle_type, vehicle_number, is_approved, is_available) VALUES (6, 'Bike', 'TN-38-AB-1234', TRUE, TRUE);

-- Seed Donations (One active, one completed)
-- Active (Vegetable Biryani - Expiry 4 hours from now)
INSERT INTO donations (donor_id, food_name, category, food_type, quantity, unit, preparation_time, expiry_time, pickup_address, city, latitude, longitude, contact_number, description, status, created_at)
VALUES (2, 'Vegetable Biryani', 'COOKED_FOOD', 'VEG', 30, 'Plates', DATEADD('HOUR', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', 3, CURRENT_TIMESTAMP), '12 Main St, Coimbatore', 'Coimbatore', 11.0168, 76.9558, '9876543210', 'Freshly prepared surplus food', 'AVAILABLE', CURRENT_TIMESTAMP);

-- Completed (Bread & Buns - Donated yesterday)
INSERT INTO donations (donor_id, ngo_id, food_name, category, food_type, quantity, unit, preparation_time, expiry_time, pickup_address, city, contact_number, description, status, created_at, accepted_at)
VALUES (3, 5, 'Bread & Buns', 'BAKERY', 'VEG', 50, 'Pieces', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', 4, CURRENT_TIMESTAMP), '45 West Avenue, Chennai', 'Chennai', '9876543211', 'Assorted baked goods', 'COMPLETED', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP));

-- Seed Pickup Request (For the completed donation)
INSERT INTO pickup_requests (donation_id, ngo_id, volunteer_id, status, created_at, accepted_at)
VALUES (2, 5, 6, 'COMPLETED', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP));

-- Seed Delivery
INSERT INTO deliveries (pickup_request_id, picked_up_at, delivered_at, delivery_notes)
VALUES (1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), 'Delivered smoothly');
