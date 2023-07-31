package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Admin;
import com.getyourdoc.getyourdoctors.models.enums.Roles;
import com.getyourdoc.getyourdoctors.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/create")
    public ResponseEntity<Admin> createAdministrator(@RequestBody Admin administrator) {
        Admin createdAdmin = adminService.createAdministrator(administrator);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdministrators() {
        List<Admin> administrators = adminService.getAllAdministrators();
        return ResponseEntity.ok(administrators);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdministratorById(@PathVariable Long adminId) {
        Optional<Admin> administrator = adminService.getAdministratorById(adminId);
        return administrator.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<Admin> updateAdministrator(@PathVariable Long adminId, @RequestBody Admin administrator) {
        administrator.setAdminId(adminId);
        Admin updatedAdmin = adminService.updateAdministrator(administrator);
        if (updatedAdmin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdministratorById(@PathVariable Long adminId) {
        adminService.deleteAdministratorById(adminId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignRole(@RequestParam Long adminId, @RequestParam Roles role) {
        adminService.assignRole(adminId, role);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{adminId}/updateRole")
    public ResponseEntity<Admin> updateRole(@PathVariable Long adminId, @RequestParam Roles role) {
        Admin updatedAdmin = adminService.updateRole(adminId, role);
        if (updatedAdmin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAdmin);
    }
    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestParam String adminUsername, @RequestParam String adminPassword) {
        Admin admin = adminService.login(adminUsername, adminPassword);
        if (admin != null) {
            return ResponseEntity.ok(admin); // Return the admin entity if login is successful
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return 401 Unauthorized if login fails
        }
    }

}
