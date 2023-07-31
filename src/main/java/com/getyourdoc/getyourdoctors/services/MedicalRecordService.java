package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.AppointmentNotFoundException;
import com.getyourdoc.getyourdoctors.exceptions.PatientNotFoundException;
import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.MedicalRecord;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.repositories.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentService appointmentService;
    private final PatientService patientService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, AppointmentService appointmentService, PatientService patientService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    public MedicalRecord createMedicalRecord(Long appointmentId, MedicalRecord medicalRecord) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment == null) {
            // Handle the case when the appointment does not exist
            // You can throw an exception or return null, depending on your business logic
            // For simplicity, let's assume we throw an exception here
            throw new AppointmentNotFoundException("Invalid appointment ID");
        }


        medicalRecord.setAppointment(appointment);

        return medicalRecordRepository.save(medicalRecord);
    }



    public MedicalRecord getMedicalRecordById(Long medicalRecordId) {
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(medicalRecordId);
        return optionalMedicalRecord.orElse(null);
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord updateMedicalRecord(Long medicalRecordId, MedicalRecord updatedMedicalRecord) {
        MedicalRecord medicalRecord = getMedicalRecordById(medicalRecordId);

        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medical record not found with ID: " + medicalRecordId);
        }
        if(medicalRecord.getAppointment().getAppointmentId()!=updatedMedicalRecord.getAppointment().getAppointmentId()){
            Appointment appointment = appointmentService.getAppointmentById(updatedMedicalRecord.getAppointment().getAppointmentId());
            if (appointment == null) {
                // Handle the case when the appointment does not exist
                // You can throw an exception or return null, depending on your business logic
                // For simplicity, let's assume we throw an exception here
                throw new AppointmentNotFoundException("Invalid appointment ID");
            }
            medicalRecord.setAppointment(appointment);
        }

        // Update the medical record fields with the values from the updatedMedicalRecord
        medicalRecord.setDiagnosis(updatedMedicalRecord.getDiagnosis());
        medicalRecord.setPrescription(updatedMedicalRecord.getPrescription());
        medicalRecord.setNotes(updatedMedicalRecord.getNotes());

        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(Long medicalRecordId) {
        MedicalRecord medicalRecord = getMedicalRecordById(medicalRecordId);

        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medical record not found with ID: " + medicalRecordId);
        }

        medicalRecordRepository.delete(medicalRecord);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {

        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            // Handle the case when the patient does not exist
            // You can throw an exception or return null, depending on your business logic
            // For simplicity, let's assume we throw an exception here
            throw new PatientNotFoundException("Invalid patient ID");
        }
        return medicalRecordRepository.findByAppointmentPatient(patient);
    }
}
