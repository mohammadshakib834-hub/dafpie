package com.dafpie.controller;

import com.dafpie.dto.SmartMatchDto;
import com.dafpie.service.SmartMatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
public class SmartMatchingController {

    private final SmartMatchingService smartMatchingService;

    public SmartMatchingController(SmartMatchingService smartMatchingService) {
        this.smartMatchingService = smartMatchingService;
    }

    @GetMapping("/donations/{id}/ngos")
    public ResponseEntity<List<SmartMatchDto>> getMatchingNgos(@PathVariable Long id) {
        return ResponseEntity.ok(smartMatchingService.findBestNgosForDonation(id));
    }
}
