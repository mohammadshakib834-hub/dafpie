package com.dafpie.dto;

public class AdminStatsDto {
    private long totalUsers;
    private long totalDonors;
    private long totalNgos;
    private long totalVolunteers;
    private long totalDonations;
    private long pendingDonations;
    private long completedDonations;
    private double foodSavedKg;
    private long peopleHelped;

    // Getters and Setters
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalDonors() { return totalDonors; }
    public void setTotalDonors(long totalDonors) { this.totalDonors = totalDonors; }
    public long getTotalNgos() { return totalNgos; }
    public void setTotalNgos(long totalNgos) { this.totalNgos = totalNgos; }
    public long getTotalVolunteers() { return totalVolunteers; }
    public void setTotalVolunteers(long totalVolunteers) { this.totalVolunteers = totalVolunteers; }
    public long getTotalDonations() { return totalDonations; }
    public void setTotalDonations(long totalDonations) { this.totalDonations = totalDonations; }
    public long getPendingDonations() { return pendingDonations; }
    public void setPendingDonations(long pendingDonations) { this.pendingDonations = pendingDonations; }
    public long getCompletedDonations() { return completedDonations; }
    public void setCompletedDonations(long completedDonations) { this.completedDonations = completedDonations; }
    public double getFoodSavedKg() { return foodSavedKg; }
    public void setFoodSavedKg(double foodSavedKg) { this.foodSavedKg = foodSavedKg; }
    public long getPeopleHelped() { return peopleHelped; }
    public void setPeopleHelped(long peopleHelped) { this.peopleHelped = peopleHelped; }
}
