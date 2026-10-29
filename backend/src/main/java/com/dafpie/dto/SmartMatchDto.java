package com.dafpie.dto;

import com.dafpie.entity.User;

public class SmartMatchDto {
    private User ngo;
    private double matchScore;
    private String reason;

    public SmartMatchDto(User ngo, double matchScore, String reason) {
        this.ngo = ngo;
        this.matchScore = matchScore;
        this.reason = reason;
    }

    public User getNgo() { return ngo; }
    public void setNgo(User ngo) { this.ngo = ngo; }
    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
