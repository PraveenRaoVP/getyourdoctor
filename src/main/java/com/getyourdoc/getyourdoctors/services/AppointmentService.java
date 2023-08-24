package com.getyourdoc.getyourdoctors.services;
import com.getyourdoc.getyourdoctors.exceptions.AppointmentNotAvailable;
import com.getyourdoc.getyourdoctors.exceptions.SlotNotAvailableException;
import com.getyourdoc.getyourdoctors.models.*;
import com.getyourdoc.getyourdoctors.models.helpers.AppointmentRequest;
import com.getyourdoc.getyourdoctors.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClinicAreaRepository clinicAreaRepository;
    private final SlotRepository slotRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    public AppointmentService(AppointmentRepository appointmentRepository, ClinicAreaRepository clinicAreaRepository, SlotRepository slotRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.clinicAreaRepository = clinicAreaRepository;
        this.slotRepository = slotRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

//    public Appointment bookAppointment(Appointment appointment) {
//        // Check if the appointment slot is available
//        if (!isAppointmentSlotAvailable(appointment.getClinicArea().getClinicAreaId(),
//                appointment.getAppointmentDate(), appointment.getStartTime(), appointment.getEndTime())) {
//            throw new AppointmentNotAvailable("Appointment slot is not available.");
//        }
//
//        // Check for appointment clashes with existing appointments
//        List<Appointment> existingAppointments = appointmentRepository.findAppointmentsInSlot(
//                appointment.getClinicArea().getClinicAreaId(),
//                appointment.getAppointmentDate(), appointment.getStartTime(), appointment.getEndTime());
//
//        if (!existingAppointments.isEmpty()) {
//            throw new AppointmentNotAvailable("There is a clash with an existing appointment.");
//        }
//
//        // Add any other validation or business logic as needed
//
//        // Save the appointment to the database
//        return appointmentRepository.save(appointment);
//    }

    public Appointment bookAppointment(AppointmentRequest appointmentRequest) {



        // Retrieve the patient, clinic area, and slot based on the provided IDs
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + appointmentRequest.getPatientId()));

        ClinicArea clinicArea = clinicAreaRepository.findById(appointmentRequest.getClinicAreaId())
                .orElseThrow(() -> new EntityNotFoundException("ClinicArea not found with ID: " + appointmentRequest.getClinicAreaId()));

        Slot slot = slotRepository.findById(appointmentRequest.getSlotId())
                .orElseThrow(() -> new EntityNotFoundException("Slot not found with ID: " + appointmentRequest.getSlotId()));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + appointmentRequest.getDoctorId()));

        // Check if the slot is available for booking
        if (!slot.isAvailable()) {
            throw new SlotNotAvailableException("Slot is not available for booking.");
        }

        // Create the appointment object
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setClinicArea(clinicArea);
        appointment.setSlot(slot);
        appointment.setSymptoms(appointmentRequest.getSymptoms());
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());
        appointment.setDoctor(doctor);

        // Update the slot availability status to booked
        slot.setAvailable(false);

        // Save the updated slot in the database
        slotRepository.save(slot);
        // Save the appointment in the database
        return appointmentRepository.save(appointment);

    }


    public List<Appointment> cancelAppointment(Long appointmentId) {
        // Check if the appointment exists
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));
        Slot slot = existingAppointment.getSlot();

        // Check if the appointment is cancellable within a certain time limit (e.g., 24 hours before the appointment)
        LocalDate today = LocalDate.now();
        if (!isCancellable(existingAppointment.getAppointmentDate(), existingAppointment.getStartTime(), today)) {
            throw new IllegalArgumentException("Appointment is not cancellable.");
        }

        // Update the slot availability to true
        slot.setAvailable(true);
        slotRepository.save(slot);

        // Remove the appointment from the doctor's appointments
        Doctor doctor = existingAppointment.getDoctor();
        doctor.getAppointments().remove(existingAppointment);

        // Remove the appointment from the patient's appointments
        Patient patient = existingAppointment.getPatient();
        patient.getAppointments().remove(existingAppointment);

        // Save the updated doctor and patient entities
        doctorRepository.save(doctor);
        patientRepository.save(patient);

        // Delete the appointment
        appointmentRepository.deleteById(appointmentId);
        return getAppointmentsByPatient(existingAppointment.getPatient().getPatientId());
    }

    private boolean isCancellable(LocalDate appointmentDate, LocalTime appointmentTime, LocalDate currentDate) {
        // Define the time limit for cancellations (e.g., 24 hours before the appointment)
        long hoursBeforeAppointmentToCancel = 24;

        // Calculate the difference in hours between the current date/time and the appointment date/time
        long hoursDifference = ChronoUnit.HOURS.between(currentDate.atTime(LocalTime.now()), appointmentDate.atTime(appointmentTime));

        // Return true if the difference is greater than the time limit, meaning it is within the cancellation window
        return hoursDifference > hoursBeforeAppointmentToCancel;
    }

    public boolean isAppointmentSlotAvailable(Long clinicAreaId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Check if a specific time slot is available for booking an appointment in a clinic area on a specific date
        List<Appointment> appointmentsInSlot = appointmentRepository.findAppointmentsInSlot(clinicAreaId, date, startTime, endTime);
        return appointmentsInSlot.isEmpty();
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        // Retrieve appointments for a specific patient using patientId
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    public List<Appointment> getAppointmentsByClinicArea(Long clinicAreaId) {
        // Retrieve appointments for a specific clinic area using clinicAreaId
        return appointmentRepository.findByClinicArea_ClinicAreaId(clinicAreaId);
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        // Retrieve appointments for a specific date
        return appointmentRepository.findByAppointmentDate(date);
    }

    public List<Appointment> getAppointmentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        // Retrieve appointments within a date range
        return appointmentRepository.findByAppointmentDateBetween(startDate, endDate);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        // Retrieve an appointment by its ID
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        return appointmentOptional.orElse(null);
    }

    public List<Appointment> getUpcomingAppointmentsForPatient(Long patientId, int daysAhead) {
        // Retrieve upcoming appointments for a specific patient within a specified number of days ahead
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);
        return appointmentRepository.findByPatient_PatientIdAndAppointmentDateBetween(patientId, today, futureDate);
    }

    public List<Appointment> getAvailableAppointmentsForClinicArea(Long clinicAreaId, LocalDate date) {
        // Retrieve available appointments for a specific clinic area on a specific date
        return findAvailableAppointmentsForClinicArea(clinicAreaId, date);
    }

    public Appointment updateAppointment(Long appointmentId, Appointment updatedAppointment) {
        // Check if the appointment exists
        Optional<Appointment> existingAppointmentOptional = appointmentRepository.findById(appointmentId);
        if (existingAppointmentOptional.isEmpty()) {
            // Appointment with the given ID not found
            throw new AppointmentNotAvailable("Appointment not found with ID: " + appointmentId);
        }

        Appointment existingAppointment = existingAppointmentOptional.get();

        // Perform validation and business logic for updating the appointment
        // For example, check if the appointment is updatable based on your business rules

        // Check if the appointment date and time are in the future
        if (isPastAppointment(existingAppointment.getAppointmentDate(), existingAppointment.getStartTime())) {
            // Appointment is in the past and cannot be updated
            return null;
        }

        // Update fields based on business logic, avoid updating sensitive data like patient or doctor information
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setStartTime(updatedAppointment.getStartTime());
        existingAppointment.setEndTime(updatedAppointment.getEndTime());
        existingAppointment.setClinicArea(updatedAppointment.getClinicArea());
        existingAppointment.setMedicalRecord(updatedAppointment.getMedicalRecord());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setSymptoms(updatedAppointment.getSymptoms());
        // Save the updated appointment to the database
        return appointmentRepository.save(existingAppointment);
    }

    // Helper method to check if the appointment is in the past
    private boolean isPastAppointment(LocalDate appointmentDate, LocalTime appointmentTime) {
        LocalDate today = LocalDate.now();
        return today.isAfter(appointmentDate) || (today.isEqual(appointmentDate) && LocalTime.now().isAfter(appointmentTime));
    }

    public List<Appointment> findAvailableAppointmentsForClinicArea(Long clinicAreaId, LocalDate date) {
        // Get all appointments for the specified clinic area and date
        List<Appointment> allAppointmentsForClinicArea = appointmentRepository.findByClinicArea_ClinicAreaIdAndAppointmentDate(clinicAreaId, date);

        // Find the available slots by comparing all time slots with the booked appointments
        List<Appointment> availableSlots = new ArrayList<>();

        // Assuming that the clinic area has a fixed working hours (e.g., from 9 AM to 5 PM)
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        int appointmentDurationInMinutes = 30;

        // Create time slots from the clinic's working hours
        while (startTime.plusMinutes(appointmentDurationInMinutes).isBefore(endTime.plusSeconds(1))) {
            LocalTime slotStartTime = startTime;
            LocalTime slotEndTime = startTime.plusMinutes(appointmentDurationInMinutes);

            // Check if the slot is available by iterating through all booked appointments
            boolean isSlotAvailable = true;
            for (Appointment appointment : allAppointmentsForClinicArea) {
                LocalTime appointmentStartTime = appointment.getStartTime();
                LocalTime appointmentEndTime = appointment.getEndTime();

                // If the slot overlaps with any existing appointment, it is not available
                if ((slotStartTime.isAfter(appointmentStartTime) && slotStartTime.isBefore(appointmentEndTime))
                        || (slotEndTime.isAfter(appointmentStartTime) && slotEndTime.isBefore(appointmentEndTime))
                        || (slotStartTime.equals(appointmentStartTime) && slotEndTime.equals(appointmentEndTime))) {
                    isSlotAvailable = false;
                    break;
                }
            }

            // If the slot is available, add it to the list of available slots
            if (isSlotAvailable) {
                Appointment availableSlot = new Appointment();
                availableSlot.setAppointmentDate(date);
                availableSlot.setStartTime(slotStartTime);
                availableSlot.setEndTime(slotEndTime);
                availableSlot.setClinicArea(clinicAreaRepository.getClinicAreaByClinicAreaId(clinicAreaId));
                availableSlots.add(availableSlot);
            }

            startTime = startTime.plusMinutes(appointmentDurationInMinutes);
        }

        return availableSlots;
    }
}