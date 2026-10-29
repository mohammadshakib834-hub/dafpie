package com.dafpie.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rated_user_id", nullable = false)
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "rated_by_user_id", nullable = false)
    private User ratedBy;
    
    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @Column(nullable = false)
    private Integer score; // 1 to 5

    private String review;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getRatedUser() { return ratedUser; }
    public void setRatedUser(User ratedUser) { this.ratedUser = ratedUser; }
    public User getRatedBy() { return ratedBy; }
    public void setRatedBy(User ratedBy) { this.ratedBy = ratedBy; }
    public Donation getDonation() { return donation; }
    public void setDonation(Donation donation) { this.donation = donation; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
