package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.InvalidCredentialsException;
import com.getyourdoc.getyourdoctors.exceptions.PatientAlreadyExistsException;
import com.getyourdoc.getyourdoctors.exceptions.PatientNotFoundException;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient registerPatient(Patient patient) {
        // Check if a patient with the given email already exists
        Patient existingPatient = patientRepository.findByPatientEmail(patient.getPatientEmail());

        if (existingPatient != null) {
            // Throw an exception or handle the error if the email is already in use
            throw new PatientAlreadyExistsException("Email already in use. Please use a different email.");
        }

        // Save the patient to the database if the email is unique
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    public void deletePatientById(Long patientId) {
        patientRepository.deleteById(patientId);
    }

        public Patient loginPatient(String email, String password) {
        // Find the patient by email
        Patient patient = patientRepository.findByPatientEmail(email);

        if (patient != null && patient.getPatientPassword().equals(password)) {
            return patient; // Return the authenticated patient if login is successful
        }

        throw new InvalidCredentialsException("Invalid email or password"); // Throw an exception if login is unsuccessful
    }
    public Patient updatePatient(Patient updatedPatient) {
        Optional<Patient> existingPatientOptional = patientRepository.findById(updatedPatient.getPatientId());

        if (existingPatientOptional.isPresent()) {
            Patient existingPatient = existingPatientOptional.get();

            // Update fields based on business logic, avoid updating sensitive data like password here
            existingPatient.setPatientName(updatedPatient.getPatientName());
            existingPatient.setPatientPhone(updatedPatient.getPatientPhone());
            existingPatient.setPatientGender(updatedPatient.getPatientGender());
            existingPatient.setPatientAge(updatedPatient.getPatientAge());

            // Check for null values before updating the patientAddress fields
            if (updatedPatient.getPatientAddress() != null) {
                if (updatedPatient.getPatientAddress().getDoorNo() != null) {
                    existingPatient.getPatientAddress().setDoorNo(updatedPatient.getPatientAddress().getDoorNo());
                }
                if (updatedPatient.getPatientAddress().getStreet() != null) {
                    existingPatient.getPatientAddress().setStreet(updatedPatient.getPatientAddress().getStreet());
                }
                if (updatedPatient.getPatientAddress().getCity() != null) {
                    existingPatient.getPatientAddress().setCity(updatedPatient.getPatientAddress().getCity());
                }
                if (updatedPatient.getPatientAddress().getState() != null) {
                    existingPatient.getPatientAddress().setState(updatedPatient.getPatientAddress().getState());
                }
                if (updatedPatient.getPatientAddress().getCountry() != null) {
                    existingPatient.getPatientAddress().setCountry(updatedPatient.getPatientAddress().getCountry());
                }
                if (updatedPatient.getPatientAddress().getPincode() != null) {
                    existingPatient.getPatientAddress().setPincode(updatedPatient.getPatientAddress().getPincode());
                }
            }

            existingPatient.setAppointments(updatedPatient.getAppointments());
            existingPatient.setProfilePictureUrl(updatedPatient.getProfilePictureUrl());
            // Add any additional fields you want to update

            return patientRepository.save(existingPatient); // Save the updated patient to the database
        } else {
            // Handle case where the patient with the provided ID does not exist
            // You can throw an exception or handle it as needed
            throw new PatientNotFoundException("Patient not found with ID: " + updatedPatient.getPatientId());
        }
    }
    public List<Patient> searchUsers(String keyword){
        return patientRepository.findByPatientNameContainingIgnoreCaseOrPatientEmailContainingIgnoreCase(keyword, keyword);
    }
}
