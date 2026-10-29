package com.dafpie.controller;

import com.dafpie.entity.Donation;
import com.dafpie.entity.PickupRequest;
import com.dafpie.service.NgoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ngos")
public class NgoController {

    private final NgoService ngoService;

    public NgoController(NgoService ngoService) {
        this.ngoService = ngoService;
    }

    @PostMapping("/donations/{id}/accept")
    public ResponseEntity<?> acceptDonation(@PathVariable Long id, Authentication auth) {
        try {
            return ResponseEntity.ok(ngoService.acceptDonation(id, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/donations/{id}/volunteer")
    public ResponseEntity<?> requestVolunteer(@PathVariable Long id, @RequestBody Map<String, String> body, Authentication auth) {
        try {
            return ResponseEntity.ok(ngoService.requestVolunteer(id, auth.getName(), body.get("instructions")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/donations/accepted")
    public ResponseEntity<List<Donation>> getAcceptedDonations(Authentication auth) {
        return ResponseEntity.ok(ngoService.getAcceptedDonations(auth.getName()));
    }
}
