# System Architecture

DAFPIE follows a classic 3-Tier Architecture (Client-Server-Database).

## 1. Presentation Layer (Frontend)
- **Technologies**: HTML5, CSS3, JavaScript, Bootstrap 5.
- **Role**: Handles the User Interface and User Experience.
- **Communication**: Interacts with the backend via RESTful APIs using standard HTTP/JSON.

## 2. Application Layer (Backend)
- **Technologies**: Java 17, Spring Boot 3, Spring Security.
- **Controllers**: Exposes REST endpoints (`/api/auth`, `/api/donations`, `/api/ngos`, etc.).
- **Services**: Contains business logic (Smart Matching, Urgency Calculation, Lifecycle updates).
- **Security**: Stateless/Session-based authentication intercepting requests and enforcing Role-Based Access Control (RBAC).

## 3. Data Layer (Database)
- **Technologies**: Spring Data JPA, Hibernate, MySQL (or H2 in-memory).
- **Role**: Persists user profiles, donation records, pickup tasks, and system logs.

---

# Entity-Relationship (ER) Overview
1. **USER**: Base entity. Has 1-to-1 relationships with optional `NgoProfile` and `VolunteerProfile` based on role.
2. **DONATION**: Belongs to a Donor (User). Has a Many-to-One relationship with an NGO (User) once accepted.
3. **PICKUP_REQUEST**: Belongs to one Donation, one NGO, and one Volunteer.
4. **DELIVERY**: 1-to-1 with a PickupRequest, stores timestamp of completion.

# Data Flow
1. Donor Submits Form -> `DonationController` -> `DonationService` -> Database.
2. NGO Views Available -> `SmartMatchingService` calculates scores -> NGO hits Accept API.
3. NGO requests Volunteer -> `NgoService` creates `PickupRequest`.
4. Volunteer Views Pickups -> Accepts -> Updates status periodically until `DELIVERED`.
