package com.dafpie.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pickup_request_id", nullable = false)
    private PickupRequest pickupRequest;

    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;
    
    private String deliveryNotes;

    // Getters and Setters
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PickupRequest getPickupRequest() { return pickupRequest; }
    public void setPickupRequest(PickupRequest pickupRequest) { this.pickupRequest = pickupRequest; }
    public LocalDateTime getPickedUpAt() { return pickedUpAt; }
    public void setPickedUpAt(LocalDateTime pickedUpAt) { this.pickedUpAt = pickedUpAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public String getDeliveryNotes() { return deliveryNotes; }
    public void setDeliveryNotes(String deliveryNotes) { this.deliveryNotes = deliveryNotes; }
}
