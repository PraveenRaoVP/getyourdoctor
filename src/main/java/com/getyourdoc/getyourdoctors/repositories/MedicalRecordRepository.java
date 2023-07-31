package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.MedicalRecord;
import com.getyourdoc.getyourdoctors.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord getMedicalRecordById(Long medicalRecordId);

    List<MedicalRecord> findByAppointmentPatient(Patient patient);

}
