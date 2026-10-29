package com.dafpie.controller;

import com.dafpie.entity.PickupRequest;
import com.dafpie.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/pickups/available")
    public ResponseEntity<List<PickupRequest>> getAvailablePickups() {
        return ResponseEntity.ok(volunteerService.getAvailablePickups());
    }

    @GetMapping("/pickups/my")
    public ResponseEntity<List<PickupRequest>> getMyPickups(Authentication auth) {
        return ResponseEntity.ok(volunteerService.getMyPickups(auth.getName()));
    }

    @PostMapping("/pickups/{id}/accept")
    public ResponseEntity<?> acceptPickup(@PathVariable Long id, Authentication auth) {
        try {
            return ResponseEntity.ok(volunteerService.acceptPickup(id, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/pickups/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body, Authentication auth) {
        try {
            return ResponseEntity.ok(volunteerService.updateStatus(id, auth.getName(), body.get("status"), body.get("notes")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
