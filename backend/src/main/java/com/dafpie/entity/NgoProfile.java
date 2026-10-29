package com.dafpie.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ngo_profiles")
public class NgoProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String registrationNumber;
    private Integer peopleServedCapacity;

    private boolean isApproved = false;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public Integer getPeopleServedCapacity() { return peopleServedCapacity; }
    public void setPeopleServedCapacity(Integer peopleServedCapacity) { this.peopleServedCapacity = peopleServedCapacity; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}
