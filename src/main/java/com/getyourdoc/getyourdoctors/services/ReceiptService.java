package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.AppointmentNotFoundException;
import com.getyourdoc.getyourdoctors.exceptions.PatientNotFoundException;
import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.models.Receipt;
import com.getyourdoc.getyourdoctors.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    public ReceiptService(ReceiptRepository receiptRepository, PatientService patientService, AppointmentService appointmentService) {
        this.receiptRepository = receiptRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    public Receipt createReceipt(Long appointmentId, Receipt receipt) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        Patient patient = patientService.getPatientById(appointment.getPatient().getPatientId());

        if (patient == null) {
            throw new PatientNotFoundException("Invalid patient ID");
        }


        receipt.setPatient(patient);
        receipt.setAppointment(appointment);

        return receiptRepository.save(receipt);
    }

    public Receipt getReceiptById(Long receiptId) {
        Optional<Receipt> optionalReceipt = receiptRepository.findById(receiptId);
        return optionalReceipt.orElse(null);
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public Receipt updateReceipt(Long receiptId, Receipt updatedReceipt) {
        Receipt receipt = getReceiptById(receiptId);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt not found with ID: " + receiptId);
        }

        if (updatedReceipt.getPatient() != null) {
            receipt.setPatient(updatedReceipt.getPatient());
        }

        if (updatedReceipt.getAppointment() != null) {
            receipt.setAppointment(updatedReceipt.getAppointment());
        }

        // Note: We are not updating the payments here as they are managed by PaymentService

        // Other receipt-related fields can be updated here

        return receiptRepository.save(receipt);
    }

    public void deleteReceipt(Long receiptId) {
        Receipt receipt = getReceiptById(receiptId);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt not found with ID: " + receiptId);
        }
        receiptRepository.delete(receipt);
    }
}
