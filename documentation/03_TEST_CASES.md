# DAFPIE - End-to-End Test Cases

| Test ID | Module | Scenario | Expected Result | Status |
|---|---|---|---|---|
| TC_01 | Auth | Register a new Donor | User is created with ROLE_DONOR. DB reflects entry. | Pass |
| TC_02 | Auth | Login with incorrect password | 401 Unauthorized, "Login failed" message. | Pass |
| TC_03 | Auth | Login with valid NGO credentials | 200 OK, returns User ID and Role, session created. | Pass |
| TC_04 | Donation | Donor creates new food donation | Donation saved with status 'AVAILABLE'. | Pass |
| TC_05 | Matching | NGO views available food | Sees donor's donation. Urgency Score correctly calculated based on expiry time. | Pass |
| TC_06 | Workflow | NGO accepts donation | Donation status changes to 'ACCEPTED'. Donor gets notification. | Pass |
| TC_07 | Workflow | NGO requests Volunteer | New PickupRequest created with status 'PENDING'. | Pass |
| TC_08 | Workflow | Volunteer accepts Pickup | PickupRequest status changes to 'ACCEPTED'. NGO notified. | Pass |
| TC_09 | Workflow | Volunteer updates to DELIVERED | PickupRequest -> DELIVERED, Donation -> DELIVERED, new Delivery record created. | Pass |
| TC_10 | Admin | Admin views Dashboard | Counters accurately reflect 1 completed delivery and updated food weight saved. | Pass |
| TC_11 | Security | Donor tries to access NGO endpoint | 403 Forbidden. Role-based protection enforced. | Pass |
