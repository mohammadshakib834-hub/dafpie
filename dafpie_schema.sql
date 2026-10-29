-- Full MySQL Dump for DAFPIE Project Submission
CREATE DATABASE IF NOT EXISTS dafpie_db;
USE dafpie_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    address VARCHAR(500),
    city VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ngo_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    registration_number VARCHAR(100),
    people_served_capacity INT,
    is_approved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS volunteer_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    vehicle_type VARCHAR(100),
    vehicle_number VARCHAR(100),
    is_approved BOOLEAN DEFAULT FALSE,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    ngo_id BIGINT,
    food_name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    food_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    preparation_time TIMESTAMP NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    pickup_address VARCHAR(500) NOT NULL,
    city VARCHAR(100) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    contact_number VARCHAR(50),
    description TEXT,
    image_url VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accepted_at TIMESTAMP NULL,
    FOREIGN KEY (donor_id) REFERENCES users(id),
    FOREIGN KEY (ngo_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS pickup_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donation_id BIGINT NOT NULL,
    ngo_id BIGINT NOT NULL,
    volunteer_id BIGINT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    special_instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accepted_at TIMESTAMP NULL,
    FOREIGN KEY (donation_id) REFERENCES donations(id),
    FOREIGN KEY (ngo_id) REFERENCES users(id),
    FOREIGN KEY (volunteer_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS deliveries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pickup_request_id BIGINT NOT NULL,
    picked_up_at TIMESTAMP NULL,
    delivered_at TIMESTAMP NULL,
    delivery_notes TEXT,
    FOREIGN KEY (pickup_request_id) REFERENCES pickup_requests(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(500) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status VARCHAR(50) DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ratings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rated_user_id BIGINT NOT NULL,
    rated_by_user_id BIGINT NOT NULL,
    donation_id BIGINT,
    score INT NOT NULL,
    review TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rated_user_id) REFERENCES users(id),
    FOREIGN KEY (rated_by_user_id) REFERENCES users(id),
    FOREIGN KEY (donation_id) REFERENCES donations(id)
);

-- Note: Seed data is identical to data.sql if needed.
