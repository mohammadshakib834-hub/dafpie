package com.dafpie.service;

import com.dafpie.dto.SmartMatchDto;
import com.dafpie.entity.Donation;
import com.dafpie.entity.User;
import com.dafpie.enums.Role;
import com.dafpie.repository.DonationRepository;
import com.dafpie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmartMatchingService {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;

    public SmartMatchingService(UserRepository userRepository, DonationRepository donationRepository) {
        this.userRepository = userRepository;
        this.donationRepository = donationRepository;
    }

    public List<SmartMatchDto> findBestNgosForDonation(Long donationId) {
        Donation donation = donationRepository.findById(donationId).orElseThrow();
        List<User> allNgos = userRepository.findAll().stream().filter(u -> u.getRole() == Role.NGO).collect(Collectors.toList());
        
        List<SmartMatchDto> matches = new ArrayList<>();
        
        for (User ngo : allNgos) {
            double score = 0;
            StringBuilder reason = new StringBuilder();
            
            // Rule 1: Same City (+50 points)
            if (donation.getCity() != null && donation.getCity().equalsIgnoreCase(ngo.getCity())) {
                score += 50;
                reason.append("Same city (").append(ngo.getCity()).append("). ");
            }
            
            // Rule 2: Distance proxy (if lat/lon are available)
            if (donation.getLatitude() != null && ngo.getLatitude() != null) {
                double dist = Math.sqrt(Math.pow(donation.getLatitude() - ngo.getLatitude(), 2) + Math.pow(donation.getLongitude() - ngo.getLongitude(), 2));
                if (dist < 0.1) { // roughly 10km
                    score += 30;
                    reason.append("Very close location. ");
                }
            }
            
            if (score > 0) {
                matches.add(new SmartMatchDto(ngo, score, reason.toString()));
            }
        }
        
        matches.sort((a, b) -> Double.compare(b.getMatchScore(), a.getMatchScore()));
        return matches;
    }
}
