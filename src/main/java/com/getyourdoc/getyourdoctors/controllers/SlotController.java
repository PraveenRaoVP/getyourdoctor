package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Slot;
import com.getyourdoc.getyourdoctors.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/slots")
public class SlotController {
    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/create/{clinicAreaId}")
    public ResponseEntity<Slot> createSlot(@RequestBody Slot slot, @PathVariable Long clinicAreaId) {
        Slot createdSlot = slotService.createSlot(slot, clinicAreaId);
        return ResponseEntity.ok(createdSlot);
    }

    @GetMapping("/{slotId}")
    public ResponseEntity<Slot> getSlotById(@PathVariable Long slotId) {
        Slot slot = slotService.getSlotById(slotId);
        return ResponseEntity.ok(slot);
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<Slot> updateSlot(@PathVariable Long slotId, @RequestBody Slot updatedSlot) {
        Slot slot = slotService.updateSlot(slotId, updatedSlot);
        return ResponseEntity.ok(slot);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
        slotService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Slot>> getAllSlots() {
        List<Slot> slots = slotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Slot>> getAvailableSlots() {
        List<Slot> availableSlots = slotService.getAvailableSlots();
        return ResponseEntity.ok(availableSlots);
    }

    @GetMapping("/clinicarea/{clinicAreaId}")
    public ResponseEntity<List<Slot>> getSlotsByClinicArea(@PathVariable Long clinicAreaId) {
        List<Slot> slots = slotService.getSlotsByClinicArea(clinicAreaId);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/available/{date}")
    public ResponseEntity<List<Slot>> getAvailableSlotsByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Slot> availableSlots = slotService.getAvailableSlotsByDate(date);
        return ResponseEntity.ok(availableSlots);
    }

    @GetMapping("/timerange")
    public ResponseEntity<List<Slot>> getSlotsByTimeRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) Time startTime,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) Time endTime) {
        List<Slot> slots = slotService.getSlotsByTimeRange(startTime, endTime);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/{slotId}/book/{patientId}")
    public ResponseEntity<Slot> bookSlot(@PathVariable Long slotId, @PathVariable Long patientId) {
        Slot bookedSlot = slotService.bookSlot(slotId, patientId);
        return ResponseEntity.ok(bookedSlot);
    }
}
