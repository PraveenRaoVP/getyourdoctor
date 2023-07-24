package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.getyourdoc.getyourdoctors.models.enums.Genders;
import com.getyourdoc.getyourdoctors.models.helpers.Address;
import com.getyourdoc.getyourdoctors.models.helpers.AddressConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {

    @Override
    public int hashCode() {
        return Objects.hash(patientId, patientEmail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient patient = (Patient) obj;
        return Objects.equals(patientId, patient.patientId) &&
                Objects.equals(patientEmail, patient.patientEmail);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String patientName;
    private Long patientAge;
    private String patientEmail;
    private String patientPassword;
    private String patientPhone;
    @Enumerated(EnumType.STRING)
    private Genders patientGender;

    @Convert(converter = AddressConverter.class)
    @JsonIgnoreProperties("patient") // Ignore the "patient" field in Address during serialization
    private Address patientAddress;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("patient")
    private Set<Appointment> appointments = new HashSet<>();
    @Column
    private String profilePictureUrl;


}
