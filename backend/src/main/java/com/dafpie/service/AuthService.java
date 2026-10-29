package com.dafpie.service;

import com.dafpie.dto.AuthRequest;
import com.dafpie.dto.AuthResponse;
import com.dafpie.dto.RegisterRequest;
import com.dafpie.entity.NgoProfile;
import com.dafpie.entity.User;
import com.dafpie.entity.VolunteerProfile;
import com.dafpie.enums.Role;
import com.dafpie.repository.NgoProfileRepository;
import com.dafpie.repository.UserRepository;
import com.dafpie.repository.VolunteerProfileRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final NgoProfileRepository ngoProfileRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, 
                       NgoProfileRepository ngoProfileRepository, 
                       VolunteerProfileRepository volunteerProfileRepository,
                       PasswordEncoder passwordEncoder, 
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.ngoProfileRepository = ngoProfileRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());

        user = userRepository.save(user);

        if (request.getRole() == Role.NGO) {
            NgoProfile ngoProfile = new NgoProfile();
            ngoProfile.setUser(user);
            ngoProfile.setRegistrationNumber(request.getRegistrationNumber());
            ngoProfile.setPeopleServedCapacity(request.getPeopleServedCapacity());
            ngoProfileRepository.save(ngoProfile);
        } else if (request.getRole() == Role.VOLUNTEER) {
            VolunteerProfile volunteerProfile = new VolunteerProfile();
            volunteerProfile.setUser(user);
            volunteerProfile.setVehicleType(request.getVehicleType());
            volunteerProfile.setVehicleNumber(request.getVehicleNumber());
            volunteerProfileRepository.save(volunteerProfile);
        }

        return new AuthResponse("Registration successful", user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new AuthResponse("Login successful", user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
