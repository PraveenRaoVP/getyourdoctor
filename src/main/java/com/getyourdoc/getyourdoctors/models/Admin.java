package com.getyourdoc.getyourdoctors.models;



import com.getyourdoc.getyourdoctors.models.enums.Roles;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String adminUsername;

    @Column(nullable = false)
    private String adminPassword;

    @Column(nullable = false)
    private String adminFullName;

    @Column(nullable = false)
    private String adminEmail;

    private String adminPhone;

    @Enumerated(EnumType.STRING)
    private Roles adminRole;

    private java.sql.Timestamp lastLogin;

    // Other admin-related fields, getters, and setters can be added here

    // No need to write explicit constructors, getters, and setters
}
