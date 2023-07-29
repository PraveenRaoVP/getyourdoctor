package com.getyourdoc.getyourdoctors.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_slots",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","doctors"}) // Ignore the "doctors" field in Slot during serialization
    @JsonBackReference
    private Set<Slot> slots;

}
