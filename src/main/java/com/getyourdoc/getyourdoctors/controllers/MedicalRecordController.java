package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.MedicalRecord;
import com.getyourdoc.getyourdoctors.services.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-record")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/{appointmentId}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@PathVariable Long appointmentId, @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord createdMedicalRecord = medicalRecordService.createMedicalRecord(appointmentId, medicalRecord);
        return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable Long medicalRecordId) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(medicalRecordId);
        if (medicalRecord != null) {
            return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        if (!medicalRecords.isEmpty()) {
            return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long medicalRecordId, @RequestBody MedicalRecord updatedMedicalRecord) {
        MedicalRecord medicalRecord = medicalRecordService.updateMedicalRecord(medicalRecordId, updatedMedicalRecord);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long medicalRecordId) {
        medicalRecordService.deleteMedicalRecord(medicalRecordId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
