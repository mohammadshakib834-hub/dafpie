package com.dafpie.controller;

import com.dafpie.dto.DonationDto;
import com.dafpie.entity.Donation;
import com.dafpie.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody DonationDto dto, Authentication auth) {
        return ResponseEntity.ok(donationService.createDonation(auth.getName(), dto));
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAvailableDonations() {
        return ResponseEntity.ok(donationService.getAvailableDonations());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Donation>> getMyDonations(Authentication auth) {
        return ResponseEntity.ok(donationService.getDonorDonations(auth.getName()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getDonationById(id));
    }
}
