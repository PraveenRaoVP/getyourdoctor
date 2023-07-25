package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord getMedicalRecordById(Long medicalRecordId);
}
