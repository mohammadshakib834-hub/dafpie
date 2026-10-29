package com.dafpie.service;

import com.dafpie.dto.AdminStatsDto;
import com.dafpie.entity.Donation;
import com.dafpie.enums.DonationStatus;
import com.dafpie.enums.Role;
import com.dafpie.repository.DonationRepository;
import com.dafpie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;

    public AdminService(UserRepository userRepository, DonationRepository donationRepository) {
        this.userRepository = userRepository;
        this.donationRepository = donationRepository;
    }

    public AdminStatsDto getDashboardStats() {
        AdminStatsDto stats = new AdminStatsDto();
        
        List<Donation> allDonations = donationRepository.findAll();
        
        stats.setTotalUsers(userRepository.count());
        stats.setTotalDonors(userRepository.findAll().stream().filter(u -> u.getRole() == Role.DONOR).count());
        stats.setTotalNgos(userRepository.findAll().stream().filter(u -> u.getRole() == Role.NGO).count());
        stats.setTotalVolunteers(userRepository.findAll().stream().filter(u -> u.getRole() == Role.VOLUNTEER).count());
        
        stats.setTotalDonations(allDonations.size());
        stats.setPendingDonations(allDonations.stream().filter(d -> d.getStatus() == DonationStatus.AVAILABLE).count());
        stats.setCompletedDonations(allDonations.stream().filter(d -> d.getStatus() == DonationStatus.COMPLETED || d.getStatus() == DonationStatus.DELIVERED).count());
        
        // Approximate calculations for project purpose (e.g. 1 plate = 0.5kg, 1 plate = 1 person)
        double savedKg = allDonations.stream()
            .filter(d -> d.getStatus() == DonationStatus.COMPLETED || d.getStatus() == DonationStatus.DELIVERED)
            .mapToDouble(d -> d.getQuantity() * 0.5)
            .sum();
            
        long peopleHelped = allDonations.stream()
            .filter(d -> d.getStatus() == DonationStatus.COMPLETED || d.getStatus() == DonationStatus.DELIVERED)
            .mapToLong(Donation::getQuantity)
            .sum();
            
        stats.setFoodSavedKg(savedKg);
        stats.setPeopleHelped(peopleHelped);
        
        return stats;
    }
}
