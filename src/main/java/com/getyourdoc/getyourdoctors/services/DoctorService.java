package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.DoctorNotFoundException;
import com.getyourdoc.getyourdoctors.models.Doctor;
import com.getyourdoc.getyourdoctors.models.Slot;
import com.getyourdoc.getyourdoctors.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SlotService slotService;

    public DoctorService(DoctorRepository doctorRepository, SlotService slotService) {
        this.doctorRepository = doctorRepository;
        this.slotService = slotService;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(Doctor doctor) {
//        Slot slot = slotService.getSlotById(slotId);
//        doctor.getSlots().add(slot);
        return doctorRepository.save(doctor);
    }

    public Doctor addDoctorToSlot(Long slotId, Long doctorId) {
        Slot slot = slotService.getSlotById(slotId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor with id " + doctorId + " not found") );
        doctor.getSlots().add(slot);
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

}