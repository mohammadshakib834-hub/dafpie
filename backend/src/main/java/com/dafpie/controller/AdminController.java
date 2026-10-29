package com.dafpie.controller;

import com.dafpie.dto.AdminStatsDto;
import com.dafpie.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminStatsDto> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
}
