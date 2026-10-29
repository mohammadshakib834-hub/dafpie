package com.dafpie.service;

import com.dafpie.dto.DonationDto;
import com.dafpie.entity.Donation;
import com.dafpie.entity.User;
import com.dafpie.enums.DonationStatus;
import com.dafpie.repository.DonationRepository;
import com.dafpie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public DonationService(DonationRepository donationRepository, UserRepository userRepository, NotificationService notificationService) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public Donation createDonation(String email, DonationDto dto) {
        User donor = userRepository.findByEmail(email).orElseThrow();
        
        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setFoodName(dto.getFoodName());
        donation.setCategory(dto.getCategory());
        donation.setFoodType(dto.getFoodType());
        donation.setQuantity(dto.getQuantity());
        donation.setUnit(dto.getUnit());
        donation.setPreparationTime(dto.getPreparationTime());
        donation.setExpiryTime(dto.getExpiryTime());
        donation.setPickupAddress(dto.getPickupAddress());
        donation.setCity(dto.getCity());
        donation.setLatitude(dto.getLatitude());
        donation.setLongitude(dto.getLongitude());
        donation.setContactNumber(dto.getContactNumber());
        donation.setDescription(dto.getDescription());

        Donation saved = donationRepository.save(donation);
        
        notificationService.createNotification(donor, "Donation Created", "Your food donation has been posted successfully.");
        
        return saved;
    }

    public List<Donation> getAvailableDonations() {
        return donationRepository.findByStatus(DonationStatus.AVAILABLE);
    }
    
    public List<Donation> getDonorDonations(String email) {
        User donor = userRepository.findByEmail(email).orElseThrow();
        return donationRepository.findByDonorId(donor.getId());
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElseThrow();
    }
}
