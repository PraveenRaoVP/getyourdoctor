package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Patient findByPatientEmail(String patientEmail);

    List<Patient> findByPatientNameContainingIgnoreCaseOrPatientEmailContainingIgnoreCase(String keyword, String keyword1);

    Patient findPatientByPatientId(Long patientId);
}
