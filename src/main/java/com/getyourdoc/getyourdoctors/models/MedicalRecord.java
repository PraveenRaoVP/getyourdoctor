package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="patient_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer"})
//    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer","medicalRecord"})
    private Appointment appointment;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String prescription;

    @Column(nullable = true)
    private String notes;
}
