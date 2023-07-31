package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.ClinicArea;
import com.getyourdoc.getyourdoctors.services.ClinicAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic-areas")
public class ClinicAreaController {
    private final ClinicAreaService clinicAreaService;

    @Autowired
    public ClinicAreaController(ClinicAreaService clinicAreaService) {
        this.clinicAreaService = clinicAreaService;
    }

    // Endpoint to create a new ClinicArea
    @PostMapping("/create")
    public ResponseEntity<ClinicArea> createClinicArea(@RequestBody ClinicArea clinicArea) {
        ClinicArea createdClinicArea = clinicAreaService.saveClinicArea(clinicArea);
        return new ResponseEntity<>(createdClinicArea, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all ClinicAreas
    @GetMapping("/all")
    public ResponseEntity<List<ClinicArea>> getAllClinicAreas() {
        List<ClinicArea> clinicAreas = clinicAreaService.getAllClinicAreas();
        return new ResponseEntity<>(clinicAreas, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific ClinicArea by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClinicArea> getClinicAreaById(@PathVariable("id") Long clinicAreaId) {
        ClinicArea clinicArea = clinicAreaService.getClinicAreaById(clinicAreaId);
        return new ResponseEntity<>(clinicArea, HttpStatus.OK);
    }

    // Endpoint to update an existing ClinicArea
    @PutMapping("/update/{id}")
    public ResponseEntity<ClinicArea> updateClinicArea(
            @PathVariable("id") Long clinicAreaId,
            @RequestBody ClinicArea updatedClinicArea
    ) {
        ClinicArea updatedArea = clinicAreaService.updateClinicArea(clinicAreaId, updatedClinicArea);
        return new ResponseEntity<>(updatedArea, HttpStatus.OK);
    }

    // Endpoint to delete a ClinicArea by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicAreaById(@PathVariable("id") Long clinicAreaId) {
        clinicAreaService.deleteClinicAreaById(clinicAreaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/clinics")
    public ResponseEntity<List<ClinicArea>> searchClinic(@RequestParam("search") String search) {
        List<ClinicArea> clinicAreas = clinicAreaService.searchClinic(search);
        return new ResponseEntity<>(clinicAreas, HttpStatus.OK);
    }
}