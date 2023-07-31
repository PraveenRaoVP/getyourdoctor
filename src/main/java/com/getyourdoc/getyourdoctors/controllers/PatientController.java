package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        Patient registeredPatient = patientService.registerPatient(patient);
        return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Patient> loginPatient(@RequestParam String email, @RequestParam String password) {
        Patient authenticatedPatient = patientService.loginPatient(email, password);

        if (authenticatedPatient != null) {
            return new ResponseEntity<>(authenticatedPatient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.getPatientById(patientId);

        if (patient != null) {
            updatedPatient.setPatientId(patientId);
            Patient updatedPatientEntity = patientService.updatePatient(updatedPatient);
            return new ResponseEntity<>(updatedPatientEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        Patient patient = patientService.getPatientById(patientId);

        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getpatients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatientById(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
