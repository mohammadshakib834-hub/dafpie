package com.dafpie.service;

import com.dafpie.entity.Delivery;
import com.dafpie.entity.Donation;
import com.dafpie.entity.PickupRequest;
import com.dafpie.entity.User;
import com.dafpie.enums.DonationStatus;
import com.dafpie.enums.PickupStatus;
import com.dafpie.repository.DeliveryRepository;
import com.dafpie.repository.DonationRepository;
import com.dafpie.repository.PickupRepository;
import com.dafpie.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerService {

    private final PickupRepository pickupRepository;
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final DonationRepository donationRepository;
    private final NotificationService notificationService;

    public VolunteerService(PickupRepository pickupRepository, UserRepository userRepository, DeliveryRepository deliveryRepository, DonationRepository donationRepository, NotificationService notificationService) {
        this.pickupRepository = pickupRepository;
        this.userRepository = userRepository;
        this.deliveryRepository = deliveryRepository;
        this.donationRepository = donationRepository;
        this.notificationService = notificationService;
    }

    public List<PickupRequest> getAvailablePickups() {
        return pickupRepository.findByStatus(PickupStatus.PENDING);
    }
    
    public List<PickupRequest> getMyPickups(String volunteerEmail) {
        User volunteer = userRepository.findByEmail(volunteerEmail).orElseThrow();
        return pickupRepository.findByVolunteerId(volunteer.getId());
    }

    @Transactional
    public PickupRequest acceptPickup(Long requestId, String volunteerEmail) {
        User volunteer = userRepository.findByEmail(volunteerEmail).orElseThrow();
        PickupRequest request = pickupRepository.findById(requestId).orElseThrow();

        if (request.getStatus() != PickupStatus.PENDING) {
            throw new IllegalStateException("Pickup is no longer available");
        }

        request.setVolunteer(volunteer);
        request.setStatus(PickupStatus.ACCEPTED);
        request.setAcceptedAt(LocalDateTime.now());
        
        notificationService.createNotification(request.getDonation().getDonor(), "Volunteer Assigned", "A volunteer has been assigned to your donation.");
        notificationService.createNotification(request.getNgo(), "Volunteer Assigned", "A volunteer has accepted the pickup request.");

        return pickupRepository.save(request);
    }

    @Transactional
    public PickupRequest updateStatus(Long requestId, String volunteerEmail, String newStatus, String notes) {
        User volunteer = userRepository.findByEmail(volunteerEmail).orElseThrow();
        PickupRequest request = pickupRepository.findById(requestId).orElseThrow();

        if (request.getVolunteer() == null || !request.getVolunteer().getId().equals(volunteer.getId())) {
            throw new IllegalStateException("You are not assigned to this pickup");
        }

        PickupStatus status = PickupStatus.valueOf(newStatus.toUpperCase());
        request.setStatus(status);
        
        Donation donation = request.getDonation();
        
        if (status == PickupStatus.PICKED_UP) {
            donation.setStatus(DonationStatus.PICKED_UP);
        } else if (status == PickupStatus.ON_THE_WAY) {
            donation.setStatus(DonationStatus.ON_THE_WAY);
        } else if (status == PickupStatus.DELIVERED) {
            donation.setStatus(DonationStatus.DELIVERED);
            
            Delivery delivery = new Delivery();
            delivery.setPickupRequest(request);
            delivery.setDeliveredAt(LocalDateTime.now());
            delivery.setDeliveryNotes(notes);
            deliveryRepository.save(delivery);
            
            notificationService.createNotification(donation.getDonor(), "Donation Delivered", "Your food donation has been successfully delivered to the NGO.");
        } else if (status == PickupStatus.COMPLETED) {
            donation.setStatus(DonationStatus.COMPLETED);
        }

        donationRepository.save(donation);
        return pickupRepository.save(request);
    }
}
