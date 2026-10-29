# DAFPIE - Sustainable Food Waste Management and Hunger Relief System

**"Every Meal Matters."**

DAFPIE is a full-stack, responsive web application designed as a college final-year project. It connects food donors (restaurants, hotels, hostels, individuals) with NGOs and volunteers to eliminate food waste and deliver surplus food to people in need.

## Features & Roles

1. **DONOR**: Register, Login, Dashboard, Donate Food, View Active & Completed Donations, Track Status.
2. **NGO**: View Available Food, Smart NGO Matching, Filter by Location/Food Type, Accept Donation, Request Volunteer.
3. **VOLUNTEER**: View Available Pickup Requests, Accept Pickup, View Interactive Map (Leaflet), Update Status (Accepted -> Picked Up -> On The Way -> Delivered).
4. **ADMIN**: View System Statistics (Food Saved in Kg, People Helped), Manage Users, Approve Roles, View Live Analytics Charts.

## Smart Features (Rule-Based)
* **Food Urgency Score**: Calculates remaining time until expiry (< 2 hours = HIGH, 2-4 hours = MEDIUM, > 4 hours = LOW).
* **Smart NGO Matching**: Suggests best NGO matches based on identical city mapping and needed food quantity.

## Technology Stack
* **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5, Bootstrap Icons, Leaflet (Map Fallback), Chart.js
* **Backend**: Java 17+, Spring Boot 3, Spring Web, Spring Data JPA, Spring Security (Session-based Auth).
* **Database**: H2 In-Memory (MySQL Mode for Zero-config execution) + Standard MySQL Dump provided.

## Getting Started

### Option 1: Zero-Configuration (Easiest for testing)
The project is pre-configured with an H2 database running in MySQL compatibility mode. It initializes the tables and injects realistic seed data automatically!
1. Navigate to the `backend/` directory.
2. Run the Spring Boot application using Maven:
   ```bash
   mvnw spring-boot:run
   ```
   *(Or open in IntelliJ IDEA / Eclipse and run `DafpieApplication.java`)*
3. Open `frontend/index.html` directly in your browser or using VS Code Live Server.

### Option 2: MySQL Setup (For Final Viva/Deployment)
1. Import the provided `dafpie_schema.sql` into your local MySQL server to create the `dafpie_db` and tables.
2. In `backend/src/main/resources/application.properties`, comment out the H2 configuration and uncomment the MySQL configuration, OR run with `-Dspring.profiles.active=mysql`.
3. Start the backend and frontend.

## Default Seed Data (Testing Accounts)
Password for all accounts is: `password`
* **Admin**: admin@dafpie.org
* **Donor 1**: donor@dafpie.org (Green Leaf Restaurant)
* **Donor 2**: bakery@dafpie.org (Fresh Bites Bakery)
* **NGO 1**: ngo@dafpie.org (Hope Foundation)
* **NGO 2**: care@dafpie.org (Care & Share NGO)
* **Volunteer**: volunteer@dafpie.org (Alex Volunteer)

## Future Scope
* SMS/Email Notifications using Twilio/SendGrid.
* Deep integration with Google Maps API for real-time live tracking of volunteers.
* Advanced Machine Learning matching.
