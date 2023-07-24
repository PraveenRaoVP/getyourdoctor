package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.AppointmentAlreadyBookedException;
import com.getyourdoc.getyourdoctors.exceptions.PatientNotFoundException;
import com.getyourdoc.getyourdoctors.exceptions.SlotNotAvailableException;
import com.getyourdoc.getyourdoctors.exceptions.SlotNotFoundException;
import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.ClinicArea;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.models.Slot;
import com.getyourdoc.getyourdoctors.repositories.AppointmentRepository;
import com.getyourdoc.getyourdoctors.repositories.ClinicAreaRepository;
import com.getyourdoc.getyourdoctors.repositories.PatientRepository;
import com.getyourdoc.getyourdoctors.repositories.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;
    private final ClinicAreaRepository clinicAreaRepository;

    @Autowired
    public SlotService(SlotRepository slotRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, ClinicAreaRepository clinicAreaRepository) {
        this.slotRepository = slotRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.clinicAreaRepository = clinicAreaRepository;
    }

    // Method to create a new slot
    public Slot createSlot(Slot slot, Long clinicAreaId) {
        ClinicArea clinicArea = clinicAreaRepository.findClinicAreaByClinicAreaId(clinicAreaId);
        slot.setClinicArea(clinicArea);
        return slotRepository.save(slot);
    }

    // Method to get a slot by ID
    public Slot getSlotById(Long slotId) {
        return slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot with ID " + slotId + " not found"));
    }

    // Method to update an existing slot
    public Slot updateSlot(Long slotId, Slot updatedSlot) {
        Slot existingSlot = getSlotById(slotId);

        // Update the properties of the existing slot with the properties from the updatedSlot
        existingSlot.setStartTime(updatedSlot.getStartTime());
        existingSlot.setEndTime(updatedSlot.getEndTime());
        existingSlot.setAvailable(updatedSlot.isAvailable());
        existingSlot.setClinicArea(updatedSlot.getClinicArea());
        existingSlot.setAppointments(updatedSlot.getAppointments());

        // You can update other slot properties here

        return slotRepository.save(existingSlot);
    }

    // Method to delete a slot by ID
    public void deleteSlot(Long slotId) {
        Slot slot = getSlotById(slotId);
        slotRepository.delete(slot);
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public List<Slot> getAvailableSlots() {
        return slotRepository.findByAvailable(true);
    }

    public List<Slot> getSlotsByClinicArea(Long clinicAreaId) {
        return slotRepository.findByClinicAreaClinicAreaId(clinicAreaId);
    }

    public Slot markSlotAsBooked(Long slotId) {
        Slot slot = getSlotById(slotId);
        slot.setAvailable(false); // Set the availability status to false when booked
        return slotRepository.save(slot);
    }

    public List<Slot> getAvailableSlotsByDate(LocalDate date) {
        return slotRepository.findByAppointmentsAppointmentDateAndAvailable(date, true);
    }

    public List<Slot> getSlotsByTimeRange(Time startTime, Time endTime) {
        return slotRepository.findByStartTimeBetween(startTime, endTime);
    }

    public Slot bookSlot(Long slotId, Long patientId) {
        Slot slot = getSlotById(slotId);
        if (!slot.isAvailable()) {
            throw new SlotNotAvailableException("Slot is not available for booking.");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        LocalDate appointmentDate = LocalDate.now(); // Set the appointment date here

        // Check for any existing appointment for the same slot and patient on the same date
        Optional<Appointment> existingAppointment = appointmentRepository.findBySlot_SlotIdAndPatient_PatientIdAndAppointmentDate(
                slotId, patientId, appointmentDate);

        if (existingAppointment.isPresent()) {
            throw new AppointmentAlreadyBookedException("Appointment already booked for the same patient on this slot and date.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setClinicArea(slot.getClinicArea());
        appointment.setSlot(slot);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStartTime(slot.getStartTime().toLocalTime());
        appointment.setEndTime(slot.getEndTime().toLocalTime());

        slot.getAppointments().add(appointment);
        slot.setAvailable(false);

        slotRepository.save(slot);

        return slot;
    }

}
