package com.dafpie.entity;

import com.dafpie.enums.DonationStatus;
import com.dafpie.enums.FoodCategory;
import com.dafpie.enums.FoodType;
import com.dafpie.enums.UrgencyLevel;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @ManyToOne
    @JoinColumn(name = "ngo_id")
    private User acceptedByNgo;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private LocalDateTime preparationTime;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private String pickupAddress;

    @Column(nullable = false)
    private String city;

    private Double latitude;
    private Double longitude;

    private String contactNumber;
    private String description;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus status = DonationStatus.AVAILABLE;

    @Transient
    private UrgencyLevel urgencyLevel;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PostLoad
    protected void calculateUrgency() {
        if (expiryTime != null) {
            long hoursRemaining = java.time.Duration.between(LocalDateTime.now(), expiryTime).toHours();
            if (hoursRemaining < 2) {
                urgencyLevel = UrgencyLevel.HIGH;
            } else if (hoursRemaining <= 4) {
                urgencyLevel = UrgencyLevel.MEDIUM;
            } else {
                urgencyLevel = UrgencyLevel.LOW;
            }
        }
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }
    public User getAcceptedByNgo() { return acceptedByNgo; }
    public void setAcceptedByNgo(User acceptedByNgo) { this.acceptedByNgo = acceptedByNgo; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public FoodCategory getCategory() { return category; }
    public void setCategory(FoodCategory category) { this.category = category; }
    public FoodType getFoodType() { return foodType; }
    public void setFoodType(FoodType foodType) { this.foodType = foodType; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public LocalDateTime getPreparationTime() { return preparationTime; }
    public void setPreparationTime(LocalDateTime preparationTime) { this.preparationTime = preparationTime; }
    public LocalDateTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }
    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(LocalDateTime acceptedAt) { this.acceptedAt = acceptedAt; }
}
