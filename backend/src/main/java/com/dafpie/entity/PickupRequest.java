package com.dafpie.entity;

import com.dafpie.enums.PickupStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pickup_requests")
public class PickupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private User ngo;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private User volunteer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickupStatus status = PickupStatus.PENDING;

    private String specialInstructions;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Donation getDonation() { return donation; }
    public void setDonation(Donation donation) { this.donation = donation; }
    public User getNgo() { return ngo; }
    public void setNgo(User ngo) { this.ngo = ngo; }
    public User getVolunteer() { return volunteer; }
    public void setVolunteer(User volunteer) { this.volunteer = volunteer; }
    public PickupStatus getStatus() { return status; }
    public void setStatus(PickupStatus status) { this.status = status; }
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(LocalDateTime acceptedAt) { this.acceptedAt = acceptedAt; }
}
