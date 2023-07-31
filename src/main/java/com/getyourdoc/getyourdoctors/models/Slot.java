package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Override
    public int hashCode() {
        return Objects.hash(slotId, startTime, endTime, clinicArea, available);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Slot slot = (Slot) obj;
        return Objects.equals(slotId, slot.slotId) &&
                Objects.equals(startTime, slot.startTime) &&
                Objects.equals(endTime, slot.endTime) &&
                Objects.equals(clinicArea, slot.clinicArea) &&
                available == slot.available;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    @Column(nullable = false)
    private java.sql.Time startTime;

    @Column(nullable = false)
    private java.sql.Time endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_area_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","availableSlots"}) // Ignore the "availableSlots" field in ClinicArea during serialization
    private ClinicArea clinicArea;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer","slot"}) // Ignore the "slot" field in Appointment during serialization
    private Set<Appointment> appointments = new HashSet<>();

    @Column(nullable = false)
    private boolean available;

    // Other slot-related fields, getters, and setters can be added here

    // No need to write explicit constructors, getters, and setters
}
