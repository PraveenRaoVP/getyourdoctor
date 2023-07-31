package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.helpers.AppointmentRequest;

import com.getyourdoc.getyourdoctors.services.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;




@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;


    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;

    }

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        Appointment bookedAppointment = appointmentService.bookAppointment(appointmentRequest);

        // Return the booked appointment in the response body
        return ResponseEntity.ok(bookedAppointment);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId) {
        // Add code to cancel an appointment
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/clinic-area/{clinicAreaId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByClinicArea(@PathVariable Long clinicAreaId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByClinicArea(clinicAreaId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Appointment>> getAppointmentsBetweenDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}/upcoming")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsForPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "7") int daysAhead) {
        // Retrieve upcoming appointments for a specific patient within a specified number of days ahead
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointmentsForPatient(patientId, daysAhead);
        return ResponseEntity.ok(upcomingAppointments);
    }

    @GetMapping("/clinic-area/{clinicAreaId}/date/{date}")
    public ResponseEntity<List<Appointment>> getAvailableAppointmentsForClinicArea(
            @PathVariable Long clinicAreaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Retrieve available appointments for a specific clinic area on a specific date
        List<Appointment> availableAppointments = appointmentService.getAvailableAppointmentsForClinicArea(clinicAreaId, date);
        return ResponseEntity.ok(availableAppointments);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        // Retrieve an appointment by its ID
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        // Perform validation and business logic for updating the appointment
        // For example, check if the appointment exists and is updatable, etc.

        Appointment updatesAppointment = appointmentService.updateAppointment(appointmentId, updatedAppointment);
        if (updatesAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatesAppointment);
    }

}