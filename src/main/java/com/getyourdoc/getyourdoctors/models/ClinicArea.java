package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clinic_areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicArea {

    @Override
    public int hashCode() {
        return Objects.hash(clinicAreaId, clinicAreaName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClinicArea clinicArea = (ClinicArea) obj;
        return Objects.equals(clinicAreaId, clinicArea.clinicAreaId) &&
                Objects.equals(clinicAreaName, clinicArea.clinicAreaName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicAreaId;
    private String clinicAreaName;
    private String clinicAreaType;

    @ElementCollection
    @CollectionTable(name = "clinic_area_working_days", joinColumns = @JoinColumn(name = "clinic_area_id"))
    @Column(name = "working_day")
    private Set<String> workingDays = new HashSet<>();

    private String workingHours;
    private String contactNumber;
    private String email;

    @OneToMany(mappedBy = "clinicArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("clinicArea")
    private Set<Slot> availableSlots = new HashSet<>();

    private boolean isAvailable;

    // Other clinic area related fields, getters, and setters can be added here

    // No need to write explicit constructors, getters, and setters
}
