package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.ClinicAreaNotFoundException;
import com.getyourdoc.getyourdoctors.models.ClinicArea;
import com.getyourdoc.getyourdoctors.repositories.ClinicAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClinicAreaService {
    private final ClinicAreaRepository clinicAreaRepository;

    @Autowired
    public ClinicAreaService(ClinicAreaRepository clinicAreaRepository) {
        this.clinicAreaRepository = clinicAreaRepository;
    }

    // Method to save a new ClinicArea
    public ClinicArea saveClinicArea(ClinicArea clinicArea) {
        return clinicAreaRepository.save(clinicArea);
    }

    // Method to retrieve all ClinicAreas
    public List<ClinicArea> getAllClinicAreas() {
        return clinicAreaRepository.findAll();
    }

    public List<ClinicArea> getClinicAreasByAddress(String address, String keywords) {
        // return list of ClinicArea by address

        String[] keywordArray = keywords.split("\\s+");
        // System.out.println(Arrays.toString(keywordArray));
        List<String> keywordList = Arrays.asList(keywordArray);
        List<ClinicArea> clinicAreas = new ArrayList<>();
        for(String keyword: keywordList) {
            clinicAreas.addAll(clinicAreaRepository.findByAddressAndKeywordsContainingIgnoreCase(address, keyword));
        }
        return clinicAreas;
    }

    // Method to retrieve a ClinicArea by its ID
    public ClinicArea getClinicAreaById(Long clinicAreaId) {
        return clinicAreaRepository.findById(clinicAreaId)
                .orElseThrow(() -> new ClinicAreaNotFoundException("ClinicArea not found with id: " + clinicAreaId));
    }

    // Method to update an existing ClinicArea
    public ClinicArea updateClinicArea(Long clinicAreaId, ClinicArea updatedClinicArea) {
        ClinicArea existingClinicArea = getClinicAreaById(clinicAreaId);

        existingClinicArea.setClinicAreaName(updatedClinicArea.getClinicAreaName());
        existingClinicArea.setClinicAreaType(updatedClinicArea.getClinicAreaType());
        existingClinicArea.setWorkingDays(updatedClinicArea.getWorkingDays());
        existingClinicArea.setWorkingHours(updatedClinicArea.getWorkingHours());
        existingClinicArea.setContactNumber(updatedClinicArea.getContactNumber());
        existingClinicArea.setEmail(updatedClinicArea.getEmail());
        existingClinicArea.setAvailableSlots(updatedClinicArea.getAvailableSlots());
        existingClinicArea.setAvailable(updatedClinicArea.isAvailable());
        existingClinicArea.setAddress(updatedClinicArea.getAddress());
        existingClinicArea.setKeywords(updatedClinicArea.getKeywords());
        return clinicAreaRepository.save(existingClinicArea);
    }

    // Method to delete a ClinicArea by its ID
    public void deleteClinicAreaById(Long clinicAreaId) {
        clinicAreaRepository.deleteById(clinicAreaId);
    }

    // Search Clinic
    public List<ClinicArea> searchClinic(String clinicAreaName) {
        return clinicAreaRepository.findByClinicAreaNameContainingIgnoreCase(clinicAreaName);
    }
}