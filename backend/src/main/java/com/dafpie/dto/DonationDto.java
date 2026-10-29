package com.dafpie.dto;

import com.dafpie.enums.FoodCategory;
import com.dafpie.enums.FoodType;

import java.time.LocalDateTime;

public class DonationDto {
    private String foodName;
    private FoodCategory category;
    private FoodType foodType;
    private Integer quantity;
    private String unit;
    private LocalDateTime preparationTime;
    private LocalDateTime expiryTime;
    private String pickupAddress;
    private String city;
    private Double latitude;
    private Double longitude;
    private String contactNumber;
    private String description;

    // Getters and Setters
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
}
