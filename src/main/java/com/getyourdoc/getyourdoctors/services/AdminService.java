package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.models.Admin;
import com.getyourdoc.getyourdoctors.models.enums.Roles;
import com.getyourdoc.getyourdoctors.repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Create a new administrator
    public Admin createAdministrator(Admin administrator) {
        return adminRepository.save(administrator);
    }

    // Get all administrators
    public List<Admin> getAllAdministrators() {
        return adminRepository.findAll();
    }

    // Get administrator by ID
    public Optional<Admin> getAdministratorById(Long id) {
        return adminRepository.findById(id);
    }

    // Update an existing administrator
    public Admin updateAdministrator(Admin administrator) {
        return adminRepository.save(administrator);
    }

    // Delete an administrator by ID
    public void deleteAdministratorById(Long id) {
        adminRepository.deleteById(id);
    }

    public void assignRole(Long adminId, Roles role) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            // Assign the specified role to the administrator
            admin.setAdminRole(role);
            adminRepository.save(admin);
        } else {
            throw new EntityNotFoundException("Administrator not found with ID: " + adminId);
        }
    }

    public Admin updateRole(Long adminId, Roles role) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            // Update the role of the administrator
            admin.setAdminRole(role);
            return adminRepository.save(admin);
        } else {
            return null; // Return null if administrator is not found
        }
    }

    public Admin login(String adminEmail, String adminPassword) {
        // Find the admin by username
        Optional<Admin> optionalAdmin = adminRepository.findByAdminEmail(adminEmail);

        // Check if the admin exists and the password is correct
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            if (admin.getAdminPassword().equals(adminPassword)) {
                // Update the lastLogin field to current time
                admin.setLastLogin(new java.sql.Timestamp(System.currentTimeMillis()));

                // Save the updated admin entity to the database
                return adminRepository.save(admin);
            }
        }

        return null; // Return null if login fails
    }
}
