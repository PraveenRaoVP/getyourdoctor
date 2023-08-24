package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.DoctorNotFoundException;
import com.getyourdoc.getyourdoctors.models.ClinicArea;
import com.getyourdoc.getyourdoctors.models.Doctor;
import com.getyourdoc.getyourdoctors.models.Slot;
import com.getyourdoc.getyourdoctors.models.helpers.Criteria;
import com.getyourdoc.getyourdoctors.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicAreaService clinicAreaService;
    private final SlotService slotService;

    public DoctorService(DoctorRepository doctorRepository, ClinicAreaService clinicAreaService, SlotService slotService) {
        this.doctorRepository = doctorRepository;
        this.clinicAreaService = clinicAreaService;
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

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor addDoctorToClinicArea(Long doctorId, Long clinicAreaId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor with id " + doctorId + " not found") );
        doctor.getClinicArea().setClinicAreaId(clinicAreaId);
        ClinicArea clinic = clinicAreaService.getClinicAreaById(clinicAreaId);
        clinic.getDoctors().add(doctor);

        return doctorRepository.save(doctor);
    }

    public List<Doctor> searchDoctors(Criteria criteria) {
        // Implement your doctor search logic here based on the criteria.
        // You can use your data repository (e.g., JPA repository) to query the database.

        // For example:
        List<Doctor> doctors = doctorRepository.findByDoctorNameContainingAndQualificationsContaining(
                criteria.getDoctorName(), criteria.getQualifications());

        return doctors;
    }

}