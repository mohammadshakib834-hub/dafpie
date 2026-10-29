package com.dafpie.service;

import com.dafpie.entity.Donation;
import com.dafpie.entity.PickupRequest;
import com.dafpie.entity.User;
import com.dafpie.enums.DonationStatus;
import com.dafpie.enums.PickupStatus;
import com.dafpie.repository.DonationRepository;
import com.dafpie.repository.PickupRepository;
import com.dafpie.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NgoService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final PickupRepository pickupRepository;
    private final NotificationService notificationService;

    public NgoService(DonationRepository donationRepository, UserRepository userRepository, PickupRepository pickupRepository, NotificationService notificationService) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.pickupRepository = pickupRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Donation acceptDonation(Long donationId, String ngoEmail) {
        User ngo = userRepository.findByEmail(ngoEmail).orElseThrow();
        Donation donation = donationRepository.findById(donationId).orElseThrow();

        if (donation.getStatus() != DonationStatus.AVAILABLE) {
            throw new IllegalStateException("Donation is not available");
        }

        donation.setStatus(DonationStatus.ACCEPTED);
        donation.setAcceptedByNgo(ngo);
        donation.setAcceptedAt(LocalDateTime.now());
        
        notificationService.createNotification(donation.getDonor(), "Donation Accepted", "Your donation has been accepted by an NGO.");
        
        return donationRepository.save(donation);
    }
    
    @Transactional
    public PickupRequest requestVolunteer(Long donationId, String ngoEmail, String instructions) {
        User ngo = userRepository.findByEmail(ngoEmail).orElseThrow();
        Donation donation = donationRepository.findById(donationId).orElseThrow();
        
        if (!donation.getAcceptedByNgo().getId().equals(ngo.getId())) {
            throw new IllegalStateException("You can only request volunteer for your accepted donations");
        }
        
        PickupRequest request = new PickupRequest();
        request.setDonation(donation);
        request.setNgo(ngo);
        request.setSpecialInstructions(instructions);
        request.setStatus(PickupStatus.PENDING);
        
        return pickupRepository.save(request);
    }
    
    public List<Donation> getAcceptedDonations(String ngoEmail) {
        User ngo = userRepository.findByEmail(ngoEmail).orElseThrow();
        return donationRepository.findByAcceptedByNgoId(ngo.getId());
    }
}
