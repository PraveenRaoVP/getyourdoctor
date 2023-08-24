package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String doctorName;
    private String qualifications;

    // many to many relationship with slots
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "doctor_slots",
//            joinColumns = @JoinColumn(name = "doctor_id"),
//            inverseJoinColumns = @JoinColumn(name = "slot_id"))
//    @JsonIgnoreProperties({"hibernateLazyInitializer","doctors"}) // Ignore the "doctors" field in Slot during serialization
//    @JsonBackReference
//    private Set<Slot> slots;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_slots",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "doctors"}) // Ignore the "doctors" field in Slot during serialization
    private Set<Slot> slots;

    // One to many relationship with appointment
    // One to many relationship with appointment
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "doctor"})
    private Set<Appointment> appointments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_area_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "doctors"})
    private ClinicArea clinicArea;

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, doctorName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return Objects.equals(doctorId, doctor.doctorName) &&
                Objects.equals(doctorName, doctor.doctorName);
    }
}