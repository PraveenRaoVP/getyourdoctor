package com.getyourdoc.getyourdoctors.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","appointments", "doctor"}) // Ignore the "appointments" field in Patient during serialization
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_area_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","availableSlots"}) // Ignore the "availableSlots" field in ClinicArea during serialization
    private ClinicArea clinicArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","appointments"}) // Ignore the "appointments" field in Slot during serialization
    private Slot slot;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer","appointment"}) // Ignore the "appointment" field in MedicalRecord during serialization
    private MedicalRecord medicalRecord;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
    // Other appointment-related fields, getters, and setters can be added here

    // No need to write explicit constructors, getters, and setters

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "appointments"}) // Ignore the "appointments" field in Doctor during serialization
    private Doctor doctor;

    @Column(nullable = false)
    private String symptoms;

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, appointmentDate, patient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment appointment = (Appointment) obj;
        return Objects.equals(appointmentId, appointment.appointmentId) &&
                Objects.equals(appointmentDate, appointment.appointmentDate) &&
                Objects.equals(patient, appointment.patient);
    }
}
