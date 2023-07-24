package com.getyourdoc.getyourdoctors.models;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String prescription;

    @Column(nullable = true)
    private String notes;
}
