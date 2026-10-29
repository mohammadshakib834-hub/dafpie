package com.dafpie.dto;

import com.dafpie.enums.Role;

public class AuthResponse {
    private String message;
    private Long id;
    private String name;
    private String email;
    private Role role;

    public AuthResponse(String message, Long id, String name, String email, Role role) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
